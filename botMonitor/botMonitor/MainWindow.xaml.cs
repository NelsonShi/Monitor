using System;
using System.Collections.Generic;
using System.Configuration;
using System.Windows;
using System.Windows.Forms;
using System.Windows.Threading;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using System.Threading;
using System.Windows.Media;
using System.Xml.Linq;
using botMonitor.entity;
using ThreadState = System.Threading.ThreadState;

namespace botMonitor
{
    /// <summary>
    /// MainWindow.xaml 的交互逻辑
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private string ipAddress;
        private string port;
        private int second;
        private string botName;
        private DispatcherTimer timer;
        private ComputerData cd;
        private DeviceMonitor dm;
        private SocketClient sc;
        private Thread reconnectT;
        private ProcessControl pc;
        private List<ProcessConf> processConfs;


        //window_onLoaded
        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            ipAddress = ConfigurationManager.AppSettings["ServerIp"];
            botName = ConfigurationManager.AppSettings["BotName"];
            port = ConfigurationManager.AppSettings["port"];
            second = Convert.ToInt32(ConfigurationManager.AppSettings["sendDataTime"]);
            StartSocketClient();
            InitTimerAndBaseData(second);
            reconnectT = new Thread(() =>
            {

                while (true)
                {
                    try
                    {
                        Thread.Sleep(second * 1000);
                        if (sc == null || sc.IsConnect) continue;
                        StartSocketClient();
                    }
                    catch (Exception ce)
                    {

                    }
                }

            });
            reconnectT.Start();
        }

        //初始化Timer和基础类
        private void InitTimerAndBaseData(int second)
        {
            if (timer != null) return;
            pc = new ProcessControl();
            cd = new ComputerData();
            dm = new DeviceMonitor();
            processConfs = LoadProcessConfs(AppDomain.CurrentDomain.BaseDirectory + @"Conf\NessaryProcessConfig.xml");
            timer = new DispatcherTimer();
            timer.Interval = new TimeSpan(0, 0, 0, second);
            timer.Tick += ReadLocalData;
            timer.Start();
        }

        private void StartSocketClient()
        {

            if (sc == null) sc = new SocketClient();
            sc.acceptMsgEvent += DelCommand;
            sc.SocketStart(ipAddress, port);
        }


        private void DelCommand(BotCommand command)
        {
            if (command == null || string.IsNullOrEmpty(command.type)) return;
            switch (command.type)
            {
                case "Close":
                    WriteToTextBox("begin close process---id :" + command.processId);
                    pc.CloseProcess(Convert.ToInt32(command.processId));
                    break;
                case "ReStart":
                    WriteToTextBox("begin restart process---id :" + command.processId);
                    var restartP = processConfs.FirstOrDefault(d => d.Key.Equals(command.processId));
                    if (restartP == null)
                    {
                        WriteToTextBox("can not find process ,key:  "+command.processId );
                        break;
                    }
                    pc.ReStartProcess(Convert.ToInt32(command.processId), restartP.StartPath);
                    break;
                case "Start":
                    WriteToTextBox("begin start");
                    var startP = processConfs.FirstOrDefault(d => d.Key.Equals(command.processId));
                    if (startP == null)
                    {
                        WriteToTextBox("can not find process ,key:  " + command.processId);
                    }
                    else
                    {
                        var res = pc.StartProcess(startP.StartPath);
                        WriteToTextBox("start process result :    " + res);
                    }                 
                    break;
            }
        }

        //读取本机数据，并且存入对象中，序列化为JSON数据
        private void ReadLocalData(object sender, EventArgs e)
        {
            cd.CPUUseRate = Math.Round(dm.CpuLoad, 2) + "%";
            var systemUsedRam = dm.SystemMemoryUsed / 1024.00 / 1024.00 / 1024.00;
            cd.RAMUseRate = Math.Round(systemUsedRam / (dm.PhysicalMemory / (1024.00 * 1024 * 1024)) * 100, 2) + "%";
            cd.CPUCount = dm.ProcessorCount.ToString();
            if (string.IsNullOrEmpty(botName))
            {
                cd.BotName = dm.HostName;
            }
            else
            {
                cd.BotName = botName;
            }
            cd.BotIP = dm.IP[0].ToString();
            cd.Resolution = ReadResolution();
            var processArray = DeviceMonitor.GetAllProcesses();
            var resultLits = FindProcessListWithNames( processArray);
            if (resultLits != null || resultLits.Count > 0) { cd.processList = resultLits; } else { cd.processList = null; }
            this.Dispatcher.Invoke(new Action(() => {
                FillDataToUI(cd);
            }));
            var jsonData = JsonJavaScriptSerializer.ToJSON(cd);
            SendDataToServer(jsonData);
        }


        private void SendDataToServer(string jsonData)
        {
            if (sc.IsConnect)
            {
                sc.SendMessageToServer(jsonData);
            }
        }


        //读取分辨率
        private string ReadResolution()
        {
            int SH = Screen.PrimaryScreen.Bounds.Height;
            int SW = Screen.PrimaryScreen.Bounds.Width;
            return SW + " * " + SH;
        }

        //查找对应的process 读取他们的运行状态（BP）
        private List<ProcessInfo> FindProcessListWithNames(Process[] processList)
        {
            List<ProcessInfo> resultProcess = new List<ProcessInfo>();
            foreach (var conf in processConfs)
            {
                bool founded = false;
                foreach (Process p in processList)
                {
                    if (p.ProcessName.Equals(conf.ProcessName)||(!string.IsNullOrEmpty(p.MainWindowTitle)&&p.MainWindowTitle.Contains(conf.WindowTitleKey)))
                    {
                        founded = true;
                        ProcessInfo info = new ProcessInfo(p.ProcessName,p.MainWindowTitle, p.Responding?1:0, p.Id.ToString());
                        conf.Key = p.Id.ToString();
                        resultProcess.Add(info);
                        break;
                    }
                }
                if (!founded)
                {
                    ProcessInfo info = new ProcessInfo(conf.DisplayName,"",0,conf.Key);
                    resultProcess.Add(info);
                }
            }
            return resultProcess;
        }

        //更新界面UI
        private void FillDataToUI(ComputerData cd)
        {
            CPU_Text.Text = cd.CPUCount;
            HostName_Text.Text = cd.BotName;
            IP_Text.Text = cd.BotIP;
            CPUUseRate_Text.Text = cd.CPUUseRate;
            RAMUseRate_Text.Text = cd.RAMUseRate;
            Resolution_Text.Text = cd.Resolution;
            ProcessGrid.ItemsSource = cd.processList;
            if (sc.IsConnect)
            {
                ConnectStatusBorder.Background = new SolidColorBrush(Color.FromArgb(255, 8, 156, 8));
            }
            else
            {
                ConnectStatusBorder.Background = new SolidColorBrush(Color.FromArgb(156, 255, 0, 0));
            }
        }

        private void MainWindow_OnClosed(object sender, EventArgs e)
        {
            if (sc != null) sc.StopSocket();
            if (reconnectT != null) reconnectT.Abort();
        }

        private void WriteToTextBox(string message)
        {
            if (TextBox.LineCount >= 100)
            {
                TextBox.Text = string.Empty;
            }
            this.Dispatcher.Invoke(new Action(() =>
            {
                TextBox.Text += DateTime.Now + " : " + message + "\r\n";
            }));
        }

        private List<ProcessConf> LoadProcessConfs(string path)
        {
            if (!File.Exists(path)) return null;
            try
            {
                XDocument document = XDocument.Load(path);
                //获取到XML的根元素进行操作
                XElement root = document.Root;
                List<ProcessConf> list=new List<ProcessConf>();
                var ProcessInfos = root.Elements("ProcessInfo");
                foreach (var processInfo in ProcessInfos)
                {
                    var processConf=new ProcessConf();
                    processConf.ProcessName = processInfo.Element("ProcessName").Value;
                    processConf.DisplayName = processInfo.Element("DisplayName").Value;
                    processConf.StartPath = processInfo.Element("StartPath").Value;
                    processConf.WindowTitleKey = processInfo.Element("WindowTitleKey").Value;
                    processConf.Key =processInfo.Element("Key").Value ;
                    list.Add(processConf);
                }
                return list;
            }
            catch (Exception e)
            {
                System.Windows.Forms. MessageBox.Show(e.Message);
                return null;
            }
          
        } 
    }
}
