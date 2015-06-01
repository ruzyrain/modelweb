package JDO;

import java.util.List;

import model.Gatewayidipmap;
import model.Idmap;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
/**
 * ÎÞÓÃ´úÂë
 * @author Administrator
 *
 */

public class GateWayIDMap {
	private static GateWayIDMap instance = null;
	private GateWayIDMap() {
	}
		
	public static GateWayIDMap getInstance() {
		if (instance == null) { 
		      instance = new GateWayIDMap(); 
	    }
		return instance;
	}
    public  Boolean insertIDMap(String GID, String ip) {
    	SessionFactory sf = null;
    	Session session = null;
        try{

              sf = new Configuration().configure().buildSessionFactory();
              session = sf.openSession();
              Transaction tx = session.beginTransaction();

              Query query =session.createQuery("from Gatewayidipmap where GatewayID='"+GID+"'");
              List GS = query.list();
              String theip=null;
              if(GS.size()==0){
            	  Gatewayidipmap idmap = new Gatewayidipmap();
                  idmap.setGatewayID(GID);
                  idmap.setIp(ip);

                  session.save(idmap);
                  tx.commit();
                  System.out.println("insert gateway map");
              }
              else{
            	  Gatewayidipmap gs = (Gatewayidipmap)GS.get(0);
                  theip=gs.getIp();
                  if(!theip.equals(ip)){
                	  gs.setIp(ip);
                	  session.update(gs);
                      session.flush();
                      tx.commit();
                      System.out.println("update gateway map");
                  }
              }

          }catch(HibernateException e){
              e.printStackTrace();
              return false;

          }
          finally{
        	  session.close();
              sf.close();
             
          }
          return true;
	}
    public  String queryIDMap(String GatewayID){
   	    SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Query query =session.createQuery("from Gatewayidipmap where GatewayID='"+GatewayID+"'");
        List GS = query.list();
        String ip=null;
        if(GS.size()==0){
       	 session.close();
       	 return null;
        }
        Gatewayidipmap gs = (Gatewayidipmap)GS.get(0);
        ip=gs.getIp();
        System.out.println(ip);

        session.close();
        sf.close();
        return ip;

   }
    public List<Gatewayidipmap> queryAllIDMap(){
   	    SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Query query =session.createQuery("from Gatewayidipmap");
        List<Gatewayidipmap> GS = query.list();
        String ip=null;
        session.close();
      	 sf.close();
        if(GS.size()==0){
       	
       	 return null;
        }
        return GS;

   }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//insertIDMap("1222223333333333","192.168.111.171");
		//queryIDMap("2222223333333333");
	}

}
