package threads;



import JDO.UploadBuffer;
/*
 * �ϴ����ݰ���ʱ���и�ȥ�ع��ܣ���Ϊÿ���ڵ�����ݰ��ᱻ�������ת��
 */
public class ClearUploadBuffer extends Thread {
	private int TIME_VALID=1000;  //������Ը������������ҪС��PC����ͬ���ݰ��ط���ʱ��
    public ClearUploadBuffer(String threadName) {
        super(threadName);
        
    }
    public void run() {
       // System.out.println(" clear buffer start��!");
        while(true){
        	
        	 UploadBuffer.getInstance().deleteItem();
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
