package mathmatic;

import java.util.Timer;
import java.util.TimerTask;

import org.omg.CORBA.PUBLIC_MEMBER;


public class TimerCheck {
	
	public TimerCheck () {
		System.out.println("start timer check");
		Timer timer = new Timer();  //任务定时器
		//在1秒后执行此任务,每次间隔1秒,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.
		//做实验时要求张萌移动位置感知设备1S发射4个数据包，网关每隔0.5秒上报一次
		timer.schedule(new check(), 1000, 1000);// 等待2S不同的网关收集数据
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
