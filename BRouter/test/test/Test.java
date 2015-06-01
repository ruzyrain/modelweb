package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 10; i++) {    
			final int index = i;    
			fixedThreadPool.execute(new Runnable() {         
				@Override        
				public void run() {            
					try {                
						System.out.println(index);                
						Thread.sleep(1000);            
						} 
					catch (InterruptedException e) {               
							// TODO Auto-generated catch block                
							e.printStackTrace();            
							}        
						}    
				});
			}
		}
	}

