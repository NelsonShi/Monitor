using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Security;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using botMonitor.Helper;

namespace botMonitor
{
   public class ProcessControl
    {
       public void ReStartProcess(int pid, string restartPath,string param)
       {
           if (CloseProcess(pid))
           {
                Thread.Sleep(2000);
               StartProcess(restartPath, param);
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

       public bool StartProcess(string path,string param)
       {
           if (string.IsNullOrEmpty(path)||!File.Exists(path)) return false;
            try
            {
                Process newProcess = new Process();//创建一个新的进程
                ProcessStartInfo startInfo = new ProcessStartInfo();//启动进程时使用的集合
                                                                    //startInfo.FileName = Environment.CurrentDirectory + "\\Release1\\a.exe";//要启动的应用程序
                startInfo.FileName = path;//要启动的应用程序
                startInfo.WindowStyle = ProcessWindowStyle.Normal;//启动应用程序时使用的窗口状态
                                                                 //startInfo.WorkingDirectory = Environment.CurrentDirectory + "\\Release1\\";//要启动应用程序的路径      
                if(!string.IsNullOrEmpty(param))startInfo.Arguments = @param;                                                                                  
                newProcess.StartInfo = startInfo;//把启动进程的信息赋值给新建的进程
                newProcess.StartInfo.UseShellExecute = true;//是否使用操作系统shell执行该程序 
                
                newProcess.Start();
                return true;
            }
            catch(Exception ex)
            {
                LogHelper.Write(ex.Message+"  path:"+path,"StartProcessError");
                return false;
            }
        }
    }
}
