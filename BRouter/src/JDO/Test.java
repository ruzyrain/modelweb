package JDO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;
/**
 * 测试代码，无用代码
 * @author Administrator
 *
 */
public class Test {
	public static void main(String[] args) {
		 try  
	        {  
	            Class.forName("com.mysql.jdbc.Driver");  
	            
	        }  
	        catch (ClassNotFoundException e)  
	        {  
	            e.printStackTrace();  
	           
	        }  
	          
	        try  
	        {  
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ui","root","1234");  
	         
	            //String sqlString="insert into GatewaySelector values('1234567890123456','涓枃',-88,'2013-11-18 15:23:34')";
	            String sqlString="insert into mc values('寮犺悓','1')";
	            
	            Statement stmt = conn.createStatement() ;    
	            java.sql.PreparedStatement pstmt = conn.prepareStatement(sqlString) ;    
	            pstmt.execute();
	             
	           /* ResultSet rs = st.executeQuery("select * from GatewaySelector");  
	            while(rs.next())  
	            {  
	                System.out.println(rs.getString("MDID"));  
	            }  */
	        }  
	        catch(Exception e)  
	        {  
	            e.printStackTrace();  
	          
	        }  

	}
}
