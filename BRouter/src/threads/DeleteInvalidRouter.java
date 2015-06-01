package threads;

import internalStorageStructure.GatewayIDIPMap;
import internalStorageStructure.Router;

/*
 * 节点到定位锚节点的寻址时间有有限的，超时删除
 */
public class DeleteInvalidRouter extends Thread {
	private int TIME_VALID=3600000;
    public DeleteInvalidRouter(String threadName) {
        super(threadName);
        
    }
 
    public void run() {
        System.out.println(" 线程运行开始!");
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
        //System.out.println(" 线程运行结束!");
    }
}
