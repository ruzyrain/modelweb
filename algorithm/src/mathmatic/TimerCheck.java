package mathmatic;

import java.util.Timer;
import java.util.TimerTask;

import org.omg.CORBA.PUBLIC_MEMBER;


public class TimerCheck {
	
	public TimerCheck () {
		System.out.println("start timer check");
		Timer timer = new Timer();  //����ʱ��
		//��1���ִ�д�����,ÿ�μ��1��,�������һ��Data����,�Ϳ�����ĳ���̶���ʱ��ִ���������.
		//��ʵ��ʱҪ�������ƶ�λ�ø�֪�豸1S����4�����ݰ�������ÿ��0.5���ϱ�һ��
		timer.schedule(new check(), 1000, 1000);// �ȴ�2S��ͬ�������ռ�����
		System.out.println("zhuxiancheng wancheng");
	}
	
	class check extends TimerTask{
		
		@Override
		public void run(){
			
			System.out.println("start");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("end");
		}
	}
	public static void main(String[] args){
		new TimerCheck();
	}

}
