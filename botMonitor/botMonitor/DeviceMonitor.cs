using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Management;
using System.Net;
using System.Net.Sockets;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;

namespace botMonitor
{
    public class DeviceMonitor
    {
        #region Fields and Properties

        private readonly ManagementScope _LocalManagementScope;

        private readonly PerformanceCounter _pcCpuLoad; //CPU计数器,全局
        private const int GwHwndfirst = 0;
        private const int GwHwndnext = 2;
        private const int GwlStyle = -16;
        private const int WsBorder = 8388608;
        private const int WsVisible = 268435456;

        #region CPU占用率

        /// <summary>
        ///     获取CPU占用率(系统CPU使用率)
        /// </summary>
        public float CpuLoad => _pcCpuLoad.NextValue();

        #endregion

        #region 本地主机名

        public string HostName => Dns.GetHostName();

        public IPAddress[] IP=>  Dns.GetHostByName(Dns.GetHostName()).AddressList;

        #endregion

        #region 本地IPV4列表

        public List<IPAddress> IpAddressV4S
        {
            get
            {
                var ipV4S = new List<IPAddress>();
                foreach (var address in Dns.GetHostAddresses(Dns.GetHostName()))
                    if (address.AddressFamily == AddressFamily.InterNetwork)
                        ipV4S.Add(address);
                return ipV4S;
            }
        }

        #endregion

        #region 本地IPV6列表

        public List<IPAddress> IpAddressV6S
        {
            get
            {
                var ipV6S = new List<IPAddress>();
                foreach (var address in Dns.GetHostAddresses(Dns.GetHostName()))
                    if (address.AddressFamily == AddressFamily.InterNetworkV6)
                        ipV6S.Add(address);
                return ipV6S;
            }
        }

        #endregion

        #region 可用内存

        /// <summary>
        ///     获取可用内存
        /// </summary>
        public long MemoryAvailable
        {
            get
            {
                long availablebytes = 0;
                var managementClassOs = new ManagementClass("Win32_OperatingSystem");
                foreach (var managementBaseObject in managementClassOs.GetInstances())
                    if (managementBaseObject["FreePhysicalMemory"] != null)
                        availablebytes = 1024 * long.Parse(managementBaseObject["FreePhysicalMemory"].ToString());
                return availablebytes;
            }
        }

        #endregion

        #region 物理内存

        /// <summary>
        ///     获取物理内存
        /// </summary>
        public long PhysicalMemory { get; }

        #endregion

        #region CPU个数

        /// <summary>
        ///     获取CPU个数
        /// </summary>
        // ReSharper disable once UnusedAutoPropertyAccessor.Global
        public int ProcessorCount { get; }

        #endregion

        #region 已用内存大小

        public long SystemMemoryUsed => PhysicalMemory - MemoryAvailable;

        #endregion

        #endregion

        #region  Constructors

        /// <summary>
        /// 构造函数，初始化计数器等
        /// </summary>
        public DeviceMonitor()
        {
            _LocalManagementScope = new ManagementScope($"\\\\{HostName}\\root\\cimv2");
            //初始化CPU计数器
            _pcCpuLoad = new PerformanceCounter("Processor", "% Processor Time", "_Total") { MachineName = "." };
            _pcCpuLoad.NextValue();

            //CPU个数
            ProcessorCount = Environment.ProcessorCount;

            //获得物理内存
            var managementClass = new ManagementClass("Win32_ComputerSystem");
            var managementObjectCollection = managementClass.GetInstances();
            foreach (var managementBaseObject in managementObjectCollection)
                if (managementBaseObject["TotalPhysicalMemory"] != null)
                    PhysicalMemory = long.Parse(managementBaseObject["TotalPhysicalMemory"].ToString());
        }

        #endregion


        #region 结束指定进程

        /// <summary>
        ///     结束指定进程
        /// </summary>
        /// <param name="pid">进程的 Process ID</param>
        public static void EndProcess(int pid)
        {
            try
            {
                var process = Process.GetProcessById(pid);
                process.Kill();
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception);
            }
        }

        #endregion

        #region 查找所有应用程序标题

        /// <summary>
        ///     查找所有应用程序标题
        /// </summary>
        /// <returns>应用程序标题范型</returns>
        public static List<string> FindAllApps(int handle)
        {
            var apps = new List<string>();

            var hwCurr = GetWindow(handle, GwHwndfirst);

            while (hwCurr > 0)
            {
                var IsTask = WsVisible | WsBorder;
                var lngStyle = GetWindowLongA(hwCurr, GwlStyle);
                var taskWindow = (lngStyle & IsTask) == IsTask;
                if (taskWindow)
                {
                    var length = GetWindowTextLength(new IntPtr(hwCurr));
                    var sb = new StringBuilder(2 * length + 1);
                    GetWindowText(hwCurr, sb, sb.Capacity);
                    var strTitle = sb.ToString();
                    if (!string.IsNullOrEmpty(strTitle))
                        apps.Add(strTitle);
                }
                hwCurr = GetWindow(hwCurr, GwHwndnext);
            }

            return apps;
        }

        #endregion

        public static List<PerformanceCounterCategory> GetAllCategories(bool isPrintRoot = true,
            bool isPrintTree = true)
        {
            var result = new List<PerformanceCounterCategory>();
            foreach (var category in PerformanceCounterCategory.GetCategories())
            {
                result.Add(category);
                if (isPrintRoot)
                {
                    PerformanceCounter[] categoryCounters;
                    switch (category.CategoryType)
                    {
                        case PerformanceCounterCategoryType.SingleInstance:
                            categoryCounters = category.GetCounters();
                            PrintCategoryAndCounters(category, categoryCounters, isPrintTree);
                            break;
                        case PerformanceCounterCategoryType.MultiInstance:
                            var categoryCounterInstanceNames = category.GetInstanceNames();
                            if (categoryCounterInstanceNames.Length > 0)
                            {
                                categoryCounters = category.GetCounters(categoryCounterInstanceNames[0]);
                                PrintCategoryAndCounters(category, categoryCounters, isPrintTree);
                            }

                            break;
                        case PerformanceCounterCategoryType.Unknown:
                            categoryCounters = category.GetCounters();
                            PrintCategoryAndCounters(category, categoryCounters, isPrintTree);
                            break;
                            //default: break;
                    }
                }
            }
            return result;
        }

        /// <summary>
        ///     获取本地所有磁盘
        /// </summary>
        /// <returns></returns>
        public static DriveInfo[] GetAllLocalDriveInfo()
        {
            return DriveInfo.GetDrives();
        }

        /// <summary>
        ///     获取本机所有进程
        /// </summary>
        /// <returns></returns>
        public static Process[] GetAllProcesses()
        {
            return Process.GetProcesses();
        }


        /// <summary>
        ///     获取指定磁盘可用大小
        /// </summary>
        /// <param name="drive"></param>
        /// <returns></returns>
        public static long GetDriveAvailableFreeSpace(DriveInfo drive)
        {
            return drive.AvailableFreeSpace;
        }

        /// <summary>
        ///     获取指定磁盘总空白大小
        /// </summary>
        /// <param name="drive"></param>
        /// <returns></returns>
        public static long GetDriveTotalFreeSpace(DriveInfo drive)
        {
            return drive.TotalFreeSpace;
        }

        /// <summary>
        ///     获取指定磁盘总大小
        /// </summary>
        /// <param name="drive"></param>
        /// <returns></returns>
        public static long GetDriveTotalSize(DriveInfo drive)
        {
            return drive.TotalSize;
        }





        /// <summary>
        ///     获取指定进程最大线程数
        /// </summary>
        /// <returns></returns>
        public static int GetProcessMaxThreadCount(Process process)
        {
            return process.Threads.Count;
        }

        /// <summary>
        ///     获取指定进程最大线程数
        /// </summary>
        /// <returns></returns>
        public static int GetProcessMaxThreadCount(string processName)
        {
            var maxThreadCount = -1;
            foreach (var process in Process.GetProcessesByName(processName))
                if (maxThreadCount < process.Threads.Count)
                    maxThreadCount = process.Threads.Count;
            return maxThreadCount;
        }

        private static void PrintCategoryAndCounters(PerformanceCounterCategory category,
            PerformanceCounter[] categoryCounters, bool isPrintTree)
        {
            Console.WriteLine($@"===============>{category.CategoryName}:[{categoryCounters.Length}]");
            if (isPrintTree)
                foreach (var counter in categoryCounters)
                    Console.WriteLine($@"   ""{category.CategoryName}"", ""{counter.CounterName}""");
        }


        #region 单位转换进制

        private const int KbDiv = 1024;
        private const int MbDiv = 1024 * 1024;
        private const int GbDiv = 1024 * 1024 * 1024;

        #endregion

        #region 单个程序Cpu使用大小

        /// <summary>
        ///     获取进程一段时间内cpu平均使用率（有误差），最低500ms 内的平均值
        /// </summary>
        /// <returns></returns>
        public double GetProcessCpuProcessorRatio(Process process, TimeSpan interVal)
        {
            if (!process.HasExited)
            {
                var processorTime = new PerformanceCounter("Process", "% Processor Time", process.ProcessName);
                processorTime.NextValue();
                if (interVal.TotalMilliseconds < 500)
                    interVal = new TimeSpan(0, 0, 0, 0, 500);
                Thread.Sleep(interVal);
                return processorTime.NextValue() / Environment.ProcessorCount;
            }
            return 0;
        }

        /// <summary>
        ///     获取进程一段时间内的平均cpu使用率（有误差），最低500ms 内的平均值
        /// </summary>
        /// <returns></returns>
        public double GetProcessCpuProcessorTime(Process process, TimeSpan interVal)
        {
            if (!process.HasExited)
            {
                var prevCpuTime = process.TotalProcessorTime;
                if (interVal.TotalMilliseconds < 500)
                    interVal = new TimeSpan(0, 0, 0, 0, 500);
                Thread.Sleep(interVal);
                var curCpuTime = process.TotalProcessorTime;
                var value = (curCpuTime - prevCpuTime).TotalMilliseconds / (interVal.TotalMilliseconds - 10) /
                            Environment.ProcessorCount * 100;
                return value;
            }
            return 0;
        }

        #endregion

        #region 单个程序内存使用大小

        /// <summary>
        ///     获取关联进程分配的物理内存量,工作集(进程类)
        /// </summary>
        /// <returns></returns>
        public long GetProcessWorkingSet64Kb(Process process)
        {
            if (!process.HasExited)
                return process.WorkingSet64 / KbDiv;
            return 0;
        }

        /// <summary>
        ///     获取进程分配的物理内存量,公有工作集
        /// </summary>
        /// <returns></returns>
        public float GetProcessWorkingSetKb(Process process)
        {
            if (!process.HasExited)
            {
                var processWorkingSet = new PerformanceCounter("Process", "Working Set", process.ProcessName);
                return processWorkingSet.NextValue() / KbDiv;
            }
            return 0;
        }

        /// <summary>
        ///     获取进程分配的物理内存量,私有工作集
        /// </summary>
        /// <returns></returns>
        public float GetProcessWorkingSetPrivateKb(Process process)
        {
            if (!process.HasExited)
            {
                var processWorkingSetPrivate =
                    new PerformanceCounter("Process", "Working Set - Private", process.ProcessName);
                return processWorkingSetPrivate.NextValue() / KbDiv;
            }
            return 0;
        }

        #endregion

        #region 系统内存使用大小

        /// <summary>
        ///     系统内存使用大小Mb
        /// </summary>
        /// <returns></returns>
        public long GetSystemMemoryDosageMb()
        {
            return SystemMemoryUsed / MbDiv;
        }


        /// <summary>
        ///     系统内存使用大小Gb
        /// </summary>
        /// <returns></returns>
        public long GetSystemMemoryDosageGb()
        {
            return SystemMemoryUsed / GbDiv;
        }

        #endregion

        #region AIP声明

        [DllImport("IpHlpApi.dll")]
        public static extern uint GetIfTable(byte[] pIfTable, ref uint pdwSize, bool bOrder);

        [DllImport("User32")]
        private static extern int GetWindow(int hWnd, int wCmd);

        [DllImport("User32")]
        private static extern int GetWindowLongA(int hWnd, int wIndx);

        [DllImport("user32.dll")]
        private static extern bool GetWindowText(int hWnd, StringBuilder title, int maxBufSize);

        [DllImport("user32", CharSet = CharSet.Auto)]
        private static extern int GetWindowTextLength(IntPtr hWnd);

        #endregion
    } }