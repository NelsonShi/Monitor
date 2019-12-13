using System.Collections.Generic;

namespace botMonitor
{
   public class ComputerData
    {
        public string BotName { get; set; }
        public string BotIP { get; set; }
        public string CPUUseRate { get; set; }
        public string Resolution { get; set; }
        public string RAMUseRate { get; set; }
        public string CPUCount { get; set; }
        public List<ProcessInfo> processList { get; set; }

    }

    public class ProcessInfo
    {
        public ProcessInfo(string name,string windowTitle, int status,string id)
        {
            this.ProcessName = name;
            this.ProcessRunningStatus = status;
            this.WindowTitle = windowTitle;
            this.ProcessId = id;
        }
        public string ProcessName { get; set; }
        //0 未运行 1 在运行
        public int ProcessRunningStatus { get; set; }
        public string ProcessId { get; set; }
        public string WindowTitle { get; set; }

    }
}
