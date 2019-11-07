using System;
using System.Collections.Generic;
using System.Configuration;
using System.Windows;
using System.Windows.Forms;
using System.Windows.Threading;
using System.Diagnostics;
using System.Threading;
using System.Windows.Media;
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
            cd = new ComputerData();
            dm = new DeviceMonitor();
            timer = new DispatcherTimer();
            timer.Interval = new TimeSpan(0, 0, 0, second);
            timer.Tick += ReadLocalData;
            timer.Start();
        }

        private void StartSocketClient()
        {

            if (sc == null) sc = new SocketClient();
            sc.SocketStart(ipAddress, port);
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
            cd.BotIP = dm.IP;
            cd.Resolution = ReadResolution();
            var processArray = DeviceMonitor.GetAllProcesses();
            var resultLits = FindProcessListWithNames(new string[] { "Automate", "ScreenConnect", "WeChat" }, processArray);
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

        //查找对应的process 读取他们的运行状态（BP，ConnectedWise）
        private List<ProcessInfo> FindProcessListWithNames(string[] processNames, Process[] processList)
        {
            List<ProcessInfo> resultProcess = new List<ProcessInfo>();
            foreach (Process p in processList)
            {
                foreach (string name in processNames)
                {
                    if (p.ProcessName.Contains(name))
                    {
                        ProcessInfo info = new ProcessInfo(p.ProcessName, p.Responding.ToString(), p.Id.ToString());
                        resultProcess.Add(info);
                    }
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
    }
}
