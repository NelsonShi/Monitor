using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace botMonitor
{
    public class SocketClient
    {
        /// <summary>
        /// 负责通信的Socket
        /// </summary>
        Socket socketSend;

        public bool IsConnect;
        public bool HasReturn;
        private string ReturnStr;
        private int connectCount;
        private int maxConnectCount;

        public string GetReturnStr()
        {
            if (HasReturn && HasReturn)
            {
                HasReturn = false;
                return ReturnStr;
            }
            else
            {
                return null;
            }
        }

        public void StopSocket()
        {
            if (socketSend!=null)
            {
                socketSend.Close();
            }
        }


        public void SocketStart(string ipAddress, string port)
        {
            HasReturn = false;
            ConnectToServer(ipAddress, port);
        }

        /// <summary>
        /// 建立连接
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ConnectToServer(string sipAddress, string portNo)
        {
            try
            {
                //创建负责通信的Socket
                socketSend = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                //获取服务端的IP
                IPAddress ip = IPAddress.Parse(sipAddress);
                //获取服务端的端口号
                IPEndPoint port = new IPEndPoint(ip, Convert.ToInt32(portNo));
                //获得要连接的远程服务器应用程序的IP地址和端口号
                socketSend.Connect(port);
                //新建线程，去接收服务端发来的信息
                Thread td = new Thread(AcceptMgs);
                td.IsBackground = true;
                td.Start();
                IsConnect = true;
            }
            catch (Exception ex)
            {
                IsConnect = false;
            }
        }

        /// <summary>
        /// 客户端接收服务器端返回的数据
        /// </summary>
        private void AcceptMgs()
        {
            try
            {
                while (true)
                {
                    byte[] buffer = new byte[1024 * 1024];
                    int r = socketSend.Receive(buffer);
                    if (r == 0)
                    {
                        break;
                    }
                    HasReturn = true;
                    //信息显示
                    ReturnStr = Encoding.ASCII.GetString(buffer, 0, r);
                }
            }
            catch
            {
            }
        }

        public void SendMessageToServer(string message)
        {
            if (socketSend == null) return;
            try
            {
                byte[] buffer = Encoding.ASCII.GetBytes(message);
                //将字节数组传递给客户端
                socketSend.Send(buffer);
            }
            catch (Exception ex)
            {
                IsConnect = false;
            }
        }
    }
}
