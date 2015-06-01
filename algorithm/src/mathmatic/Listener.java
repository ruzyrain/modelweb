package mathmatic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Listener {
	private static int i=0;
	
	public void runbat(){
		String s;
		Process process;
		
		try {
	
			process = Runtime.getRuntime().exec("cmd  /c  C://Users//Administrator//Desktop//test.bat"); 
			
			InputStream in=process.getInputStream();
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			while((s=bufferedReader.readLine()) != null){
				i++;
				System.out.println("当前连接数"+s+"当前数目："+i);
			}
			in.close();
			bufferedReader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}				
	}
	
	public static void main(String[] args) throws InterruptedException{
		while(true){
			new Listener().runbat();
			Thread.sleep(500);
		}
	}

}
