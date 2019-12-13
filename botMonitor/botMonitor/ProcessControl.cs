using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace botMonitor
{
   public class ProcessControl
    {
       public void ReStartProcess(int pid, string restartPath)
       {
           if (CloseProcess(pid))
           {
                Thread.Sleep(1000);
               StartProcess(restartPath);
           }
       }

       public bool CloseProcess(int pid)
       {
            try
            {
                var process = Process.GetProcessById(pid);
                process.Kill();
                return true;
            }
            catch
            {
                return false;
            }
        }

       public bool StartProcess(string path)
       {
           if (string.IsNullOrEmpty(path)) return false;
            try
            {
                Process newProcess = new Process();//创建一个新的进程
                ProcessStartInfo startInfo = new ProcessStartInfo();//启动进程时使用的集合
                                                                    //startInfo.FileName = Environment.CurrentDirectory + "\\Release1\\a.exe";//要启动的应用程序
                startInfo.FileName = path;//要启动的应用程序
                startInfo.WindowStyle = ProcessWindowStyle.Normal;//启动应用程序时使用的窗口状态
                                                                 //startInfo.WorkingDirectory = Environment.CurrentDirectory + "\\Release1\\";//要启动应用程序的路径
                newProcess.StartInfo = startInfo;//把启动进程的信息赋值给新建的进程
                newProcess.StartInfo.UseShellExecute = true;//是否使用操作系统shell执行该程序 
                newProcess.Start();
                return true;
            }
            catch(Exception ex)
            {
                return false;
            }
        }
    }
}
