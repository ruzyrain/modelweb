package mathmatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class test91 {
	
	public static void main(String[] args){
		String JDriver = "com.mysql.jdbc.Driver"; // SQL���ݿ�����
		String connectDB = "jdbc:mysql://10.10.2.91:3306/router"; // ����Դ
		Connection con;
		try {
			Class.forName(JDriver); // �������ݿ����棬���ظ����ַ���������
									// System.out.println("���ݿ������ɹ�")
			String user = "root";
			String password = "1234";
			con = DriverManager.getConnection(connectDB, user, password); // �������ݿ����
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
