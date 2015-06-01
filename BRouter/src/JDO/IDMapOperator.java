package JDO;

import java.util.ArrayList;
import java.util.List;

import javassist.expr.NewArray;

import model.Gatewayselector;
import model.Idmap;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
/**
 * 有效代码，主要从数据库中读入 GID-panid对应关系
 * @author Administrator
 *
 */
public class IDMapOperator {
    public Boolean insertIDMap(String MDID, String panid) {
    	SessionFactory sf = null;
    	Session session = null;
        try{

              sf = new Configuration().configure().buildSessionFactory();
              session = sf.openSession();
              Transaction tx = session.beginTransaction();

          	  Idmap idmap = new Idmap();
	          idmap.setId(MDID);
	          idmap.setPanid(panid);


              session.save(idmap);
              tx.commit();
              
              System.out.println("");
              return true;

          }catch(HibernateException e){
              e.printStackTrace();
              return false;

          }
          finally{
        	  session.close();
              sf.close();
          }
	}
    public char[] queryIDMap(String MDID){
    	System.out.println("query mdid:"+MDID);
   	    SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Query query =session.createQuery("from Idmap where GID='"+MDID+"'");
        List GS = query.list();
        char[]panid=new char[2];
        if(GS.size()==0){
       	 session.close();
       	 return null;
        }
        Idmap gs = (Idmap)GS.get(0);
        String result = gs.getPanid(); 
        System.out.println("result len:"+result.length());
        if(result.length()==1){
        	result.getChars(0, 1, panid, 0);
        	panid[1]=0;
        }
        else
        {
        	result.getChars(0, 2, panid, 0);
        }
        int a=panid[0];
        int b=panid[1];
        System.out.println(a+","+b);
        session.close();
        sf.close();
        return panid;

   }
    public List<Idmap> QueryAllIDIPMap()
    {
   	    SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Query query =session.createQuery("from Idmap");
        List<Idmap>GS = query.list();
        if(GS.size()==0){
       	 session.close();
       	 return null;
        }
      
        session.close();
        sf.close();
        return GS;
    }
    public String getGID(String panid){
   	    SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Query query =session.createQuery("from Idmap where panid='"+panid+"'");
        List GS = query.list();
        //char[]panid=new char[2];
        if(GS.size()==0){
       	 session.close();
       	 return null;
        }
        Idmap gs = (Idmap)GS.get(0);
        String result = gs.getId(); 
        session.close();
        sf.close();
        return result;
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//char[]panid={'5','5'};
		char[]panid={'0','5'};
		String string=new String(panid);
		/*String gid="1122222222333333";
		panid=new IDMapOperator().queryIDMap(gid);
		int a=panid[0];
		int b=panid[1];
		System.out.println(a+","+b);*/
		String rString=new IDMapOperator().getGID(string);
		System.out.println(rString);
		//String response ="331444444433144444443314444444331444444433144444443314444444331444444433144444443314441";
        //System.out.println(string.getBytes().length);
        //System.out.println(response.length());
		//char[] rString= new IDMapOperator().queryIDMap("1122222222333333");
		//System.out.println(new String(rString));
		
		
		//new IDMapOperator().insertIDMap("5333334343444444", new String(panid));
		/*for(char i=0;i<=0xff;i++){
			for(char j=0;j<=0xff;j++){
				char[]panid={i,j};
				new IDMapOperator().insertIDMap("", new String(panid));
			}
		}*/
		
	}

}
