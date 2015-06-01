package JDO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.*;

import org.hibernate.cfg.*;

import model.Gatewayselector;
/**
 * ���ô���
 * @author Administrator
 *
 */
public class DBOperator {

 

    public Boolean insertGatewaySelector(String gateWayId, String MDID, String time,int rssi) {
    	Session session = null;
    	SessionFactory sf = null;
    	try{

              sf = new Configuration().configure().buildSessionFactory();
              session = sf.openSession();
              Transaction tx = session.beginTransaction();

          	  Gatewayselector st = new Gatewayselector();
          	  //GatewayselectorPK pk=new GatewayselectorPK(gateWayId,MDID);
              st.setId(MDID);
              st.setGatewayID(gateWayId);
              st.setLatestTime(time);
              st.setRssi(rssi);

              session.save(st);
              tx.commit();
              
              System.out.println("数据插入成功，请查看数据库！");
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
    public List<String> queryGatewaySelector(String MDID){
    	 SessionFactory sf = new Configuration().configure().buildSessionFactory();
         Session session = sf.openSession();
         Query query =session.createQuery("from Gatewayselector where MDID='"+MDID+"'");
         List GS = query.list();
         List<String> resultList = new ArrayList<String>();
         if(GS.size()==0){
        	 session.close();
        	 return null;
         }
        
         Gatewayselector gs = (Gatewayselector)GS.get(0);
         resultList.add(gs.getGatewayID());
    	 resultList.add(gs.getRssi().toString());
    	 resultList.add(gs.getLatestTime());
    	 System.out.println(resultList.get(0)+'\t'+resultList.get(1));
    	 /*for(int i=0;i<GS.size();i++){
        	 Gatewayselector gs = (Gatewayselector)GS.get(i);
        	 GatewayselectorPK gatewayselectorPK =gs.getId();
             System.out.println(gatewayselectorPK.getGatewayID()+'\t'+gatewayselectorPK.getMdid()+"\t"+gs.getRssi()+'\t'+gs.getLatestTime());

         }*/

         session.close();
         sf.close();
         return resultList;

    }
    public boolean updateGatewaySelector(String gatewayID, String MDID, String time,int rssi){
    	SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query =session.createQuery("from Gatewayselector where MDID='"+MDID+"'");
        List GS = query.list();
        boolean flag=false;
    	if(GS.size()!=0){
    		System.out.println("not null");
            Gatewayselector gs = (Gatewayselector) GS.get(0);
        	//GatewayselectorPK pk=new GatewayselectorPK(gatewayID,MDID);
            gs.setGatewayID(gatewayID);
            gs.setLatestTime(time);
            gs.setRssi(rssi);
            session.update(gs);
            session.flush();
            //session.save(gs);
            /*query =session.createQuery("from Gatewayselector where MDID='"+MDID+"' and GatewayID = '"+gatewayID+"'");
            List GS1 = query.list();
            Gatewayselector gs1=(Gatewayselector) GS1.get(0);
            System.out.println(gs1.getRssi());*/

            
        	flag = true;
    	}
    	
        tx.commit();
        session.close();
        sf.close();
    	return flag;

    }
    public static void main(String[] args) {

    	DBOperator dBOperator = new DBOperator();
    	
    	//dBOperator.updateGatewaySelector("g223456789012345", "d123456789012343", "2013-11-18 15:16:12", -60);
    	//dBOperator.queryGatewaySelector("d123456789012343","1234567890123456");
    	dBOperator.insertGatewaySelector("1234567890123456", "ddd3456789012343", "2013-11-18 15:16:12", -55);
    
    }

 

}

