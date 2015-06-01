package threads;



import JDO.UploadBuffer;
/*
 * 上传数据包段时间有个去重功能，因为每个节点的数据包会被多个网关转发
 */
public class ClearUploadBuffer extends Thread {
	private int TIME_VALID=1000;  //这里可以根据需求调整，要小于PC端相同数据包重发的时间
    public ClearUploadBuffer(String threadName) {
        super(threadName);
        
    }
    public void run() {
       // System.out.println(" clear buffer start!");
        while(true){
        	
        	 UploadBuffer.getInstance().deleteItem();
        	 try {
				Thread.currentThread().sleep(TIME_VALID);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        //System.out.println(" 绾跨▼杩愯缁撴潫!");
    }
}
