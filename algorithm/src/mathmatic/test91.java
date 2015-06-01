package mathmatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class test91 {
	
	public static void main(String[] args){
		String JDriver = "com.mysql.jdbc.Driver"; // SQL数据库引擎
		String connectDB = "jdbc:mysql://10.10.2.91:3306/router"; // 数据源
		Connection con;
		try {
			Class.forName(JDriver); // 加载数据库引擎，返回给定字符串名的类
									// System.out.println("数据库驱动成功")
			String user = "root";
			String password = "1234";
			con = DriverManager.getConnection(connectDB, user, password); // 连接数据库对象
			PreparedStatement stmt = con.prepareStatement("Insert into idmap values('123','11')");
			stmt.executeUpdate();
			stmt.close();
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
		}
	}

}
