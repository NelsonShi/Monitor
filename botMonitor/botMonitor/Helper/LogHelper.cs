using System;
using System.IO;

namespace botMonitor.Helper
{
   public class LogHelper
    {
        /// <summary>
        /// 
        /// </summary>
        /// <param name="str">日志信息</param>
        /// <param name="fileName">文件名称</param>
       public static void Write(string str,string fileName)
       {
            var path = AppDomain.CurrentDomain.BaseDirectory +"Logs";
            if (!Directory.Exists(path))
            {
               Directory.CreateDirectory(path);
            }
            fileName = path+"\\"+fileName+"_" + DateTime.Now.ToString("MMdd") +".log";
            FileStream fs;
            StreamWriter sw;
            if (File.Exists(fileName))
            //验证文件是否存在，有则追加，无则创建
            {
                fs = new FileStream(fileName, FileMode.Append, FileAccess.Write);
            }
            else
            {
                fs = new FileStream(fileName, FileMode.Create, FileAccess.Write);
            }
            sw = new StreamWriter(fs);
            sw.WriteLine(DateTime.Now.ToString("yyyy-MM-dd HH-mm-ss") +"---" + str);
            sw.Close();
            fs.Close();
        }
    }
}
