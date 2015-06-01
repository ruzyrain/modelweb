package threads;

import org.apache.http.protocol.HttpRequestExecutor;

import HttpServer.ConstantInterface;
import HttpServer.ElementalHttpPostString;
import HttpServer.ElementalHttpPostBytes;

/*
 * 及时没有需上传得数据包，为了获取下发命令，路由网关也需要定时向服务器发送数据
 * 每秒一个数据包
 */
public class AskForCmd extends Thread {
	private int TIME_INTERAL=1000;
    public AskForCmd(String threadName) {
        super(threadName);
    }
 
    public void run() {
      
        while(true){
        	String contentString="0";
        	
        	try {
//        		System.out.println("come into askforcmd");
        		new ElementalHttpPostString().post2Server(ConstantInterface.SERVERID,contentString);
        		Thread.currentThread().sleep(TIME_INTERAL);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        
    }
}
