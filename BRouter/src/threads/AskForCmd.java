package threads;

import org.apache.http.protocol.HttpRequestExecutor;

import HttpServer.ConstantInterface;
import HttpServer.ElementalHttpPostString;
import HttpServer.ElementalHttpPostBytes;

/*
 * ��ʱû�����ϴ������ݰ���Ϊ�˻�ȡ�·����·������Ҳ��Ҫ��ʱ���������������
 * ÿ��һ�����ݰ�
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
