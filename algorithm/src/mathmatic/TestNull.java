package mathmatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class TestNull {
	  public int  testNull() {
		  String JDriver = "com.mysql.jdbc.Driver"; // SQL数据库引擎 
		  String connectDB = "jdbc:mysql://127.0.0.1:3306/areamonitor"; // 数据源 
		  Connection con; 
		  int res = 0;
		  try { 
		   Class.forName(JDriver); // 加载数据库引擎，返回给定字符串名的类 
		 
		   String user = "root"; 
		   String password = "1234"; 
		   con = DriverManager.getConnection(connectDB, user, password); // 连接数据库对象 
		   PreparedStatement stmt = con.prepareStatement("SELECT AVG(RSSI2) as avg FROM a000000000000117 as a where a.time > '18:32:41' LIMIT 0,3;"); 
		   ResultSet rs = stmt.executeQuery(); 
		   while(rs.next()){ //循环遍历查询结果集 
			   res = rs.getInt("avg");
		       } 
		   stmt.close(); 
		   con.close(); 
		  } catch (Exception e) { 
		   // TODO Auto-generated catch block 
		   e.printStackTrace(); 
		  }
		  return res;		
	}
	  public String[][] get_Array(String[][] rs) {
			String[][] reArray = new String[rs.length][];
			for(int i = 0; i < rs.length; i++){
				  String[] m = new String[rs[i].length];
				  for(int j = 0; j < rs[i].length; j++)
					  m[j] = rs[i][j];
				  reArray[i] = m;
			  }
			return reArray;
		}
	  
	  public static void  main(String[] args) {
		  int pri = new TestNull().testNull();
		  System.out.println("the result is :" + pri);
		  Set<String> test = new HashSet<String>();
		  Set<String> test1 = new HashSet<String>();
		  test.add("a");
		  test.add("b");
		  System.out.println("set bao han kong :" + test.contains(null));
		  
		  System.out.println("set bao han a " + test.contains("a"));
		  
		  System.out.println("kong set bao han kong :" + test1.contains(null));
		  
		  System.out.println("kong set bao han a " + test1.contains("a"));
		  
		  String[] a = {"3","6","9"};
		  String[] b = a;
		  String[] c = a;
		  b[1] = "1";
		  System.out.println("c1 is :" + c[1]);
		  
		  String[][] x = {{"1","2"},{"3","4"},{"5","6"}};
		  String[][] y = new TestNull().get_Array(x);
		  String[][] z = new TestNull().get_Array(x);
		  String[][] l = new String[x.length][];
		  for(int i = 0; i < x.length; i++){
			  String[] m = new String[x[i].length];
			  for(int j = 0; j < x[i].length; j++)
				  m[j] = x[i][j];
			  l[i] = m;
		  }
		  y[2][1] = "9";
		  System.out.println("x[2][1] = " + x[2][1]);
		  System.out.println("z[2][1] = " + z[2][1]);
		  System.out.println("l[2][1] = " + l[2][1]);
		  
		
	}
}
