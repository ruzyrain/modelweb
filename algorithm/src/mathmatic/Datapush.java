package mathmatic;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sun.org.apache.bcel.internal.generic.PUSH;

import wshare.api.RI;
import wshare.api.RIConf;

public class Datapush {
	private Logger logger = LogManager.getLogger(getClass());

	
	{
		System.setProperty(RI.SP_HOST, "10.10.12.204");
		System.setProperty(RI.SP_PORT, "1099");
	}

	
	public  void push(){
		logger.debug("resourceType=device, enter connector¡­¡­");
			for (int id = 100000; id<110001; id++) {

				String devId = String.valueOf(id);
				String check = "";
				RIConf riConf;
				try {
					riConf = RI.getRIConf();
					riConf.setResource(devId, check, "passphrase");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}						
				logger.debug("setrsc successful,devid="+devId+", check="+check);
				System.out.println("setrsc successful,devid="+devId+", check="+check);			}
	}

	public static void main(String[] args) {
		new Datapush().push();
		
	}

}
