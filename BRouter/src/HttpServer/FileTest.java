package HttpServer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Pattern;


/*
 * 调试过程中记录信号强度值的文件操作
 */
public class FileTest {
	private static FileTest instance = null;
	FileOutputStream out=null;
    PrintStream p=null;
	private FileTest(){
		try {
			out=new FileOutputStream("D:/test.txt");
			p=new PrintStream(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public static FileTest getInstance() {
		if (instance == null) { 
		      instance = new FileTest(); 
	    }
		return instance;
	}
	public synchronized void PrintStreamDemo(String data){
        p.println(data);
	}
	public static void main(String[] args) throws InterruptedException {
		//PrintStreamDemo();
	
	}

}
