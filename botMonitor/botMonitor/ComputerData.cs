using System.Collections.Generic;
using System.Linq;
using System.Web.Script.Serialization;

namespace botMonitor
{
    public class ComputerData
    {

       public ComputerData()
       {
          Coefficient = 0.90;
       }

       public ComputerData(double coefficient)
       {
          this.Coefficient = coefficient;
       }
        public string BotName { get; set; }
        public string BotIP { get; set; }
        public double CPUUseRate { get; set; }
        public string Resolution { get; set; }
        public double RAMUseRate { get; set; }
        public string CPUCount { get; set; }
        public List<ProcessInfo> processList { get; set; }
        [ScriptIgnore]
        //报警系数 当cpu或者ram 的使用率高于这个系数时 报警
        public double Coefficient { get; set; }

       public bool NeedToSend(ComputerData inputComputerData)
       {
           if (inputComputerData == null) return true;
           double warningValue = this.Coefficient*100;
           bool aboveRam = this.RAMUseRate > warningValue;
           bool aboveCpu = this.CPUUseRate > warningValue;
           if (aboveRam || aboveCpu||(!aboveRam&&(inputComputerData.RAMUseRate> warningValue))||(!aboveCpu&&inputComputerData.CPUUseRate>warningValue) ) return true;
           bool needToSend = false;
           var isCountMatch = (processList == null
               ? 0
               : processList.Count) == (inputComputerData.processList == null ? 0 : inputComputerData.processList.Count);
           if (!isCountMatch) return true;
           foreach (var processInfo in processList)
           {
               var matchP = inputComputerData.processList.FirstOrDefault(d => d.Equals(processInfo));
               if (matchP == null)
               {
                   needToSend = true;
               }
           }
           return needToSend;
       }

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

        public bool Equals(ProcessInfo processInfo)
        {
            return processInfo.ProcessName.Equals(this.ProcessName) && processInfo.WindowTitle.Equals(this.WindowTitle) &&
                   processInfo.ProcessId.Equals(this.ProcessId) &&
                   processInfo.ProcessRunningStatus == this.ProcessRunningStatus;
        }
        public string ProcessName { get; set; }
        //0 未运行 1 在运行
        public int ProcessRunningStatus { get; set; }
        public string ProcessId { get; set; }
        public string WindowTitle { get; set; }

    }
}
