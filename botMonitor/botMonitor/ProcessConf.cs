using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace botMonitor
{
   public class ProcessConf
    {
        public string ProcessName { get; set; }
        public string DisplayName { get; set; }
        public string WindowTitleKey { get; set; }
        public string Key { get; set; }
        public string StartPath { get; set; }

        public string StartParam { get; set; }
    }
}
