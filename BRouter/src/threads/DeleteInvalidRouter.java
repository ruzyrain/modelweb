package threads;

import internalStorageStructure.GatewayIDIPMap;
import internalStorageStructure.Router;

/*
 * �ڵ㵽��λê�ڵ��Ѱַʱ�������޵ģ���ʱɾ��
 */
public class DeleteInvalidRouter extends Thread {
	private int TIME_VALID=3600000;
    public DeleteInvalidRouter(String threadName) {
        super(threadName);
        
    }
 
    public void run() {
        System.out.println(" �߳����п�ʼ!");
        while(true){
        	 Router.getInstance().deleteInvalid();
        	 //GatewayIDIPMap.getInstance().clearMap();
        	 try {
				Thread.currentThread().sleep(TIME_VALID);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        //System.out.println(" �߳����н���!");
    }
}
