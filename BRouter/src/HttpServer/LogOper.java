package HttpServer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.apache.commons.logging.*;
/*
 * 日志操作logger方法，在本地记录定位毛节点异常
 */
public class LogOper {
	private static LogOper logOper=null;
	private Logger log;
	private LogOper(){
		String filePath=this.getClass().getResource("/").getPath();
		FileHandler fileHandler = null;
		try {
			fileHandler=new FileHandler(filePath+"log.txt",true);
	
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileHandler.setLevel(Level.SEVERE);
		fileHandler.setFormatter(new MyLogFormatter());
		log=Logger.getLogger(this.getClass().getName());
		log.addHandler(fileHandler);
		
		//log.log(Level.INFO, "log content");
	}
	public static LogOper GetLogOper(){
		if(logOper==null){
			logOper=new LogOper();		
		}
		return logOper;
	}
	public synchronized void  addLog(String logContent){
		log.severe(logContent);
	}
	public static void main(String[] args) {
		LogOper.GetLogOper().addLog("hi log");
	}
}
class MyLogFormatter extends Formatter{

	@Override
	public String format(LogRecord record) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String theTime=df.format(record.getMillis());
		return theTime+"   "+record.getLevel()+":"+record.getMessage()+"\n";
	}
	
}
