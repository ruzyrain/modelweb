package mathmatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;


public class Fun extends Thread{

//
//	@Override
//	public int compare(ClickImple o1, ClickImple o2) {
//		// TODO Auto-generated method stub
//		if (o1.hashCode() > o2.hashCode())
//			return 1;
//		return -1;
//	}
	
//	public static void  main(String[] args) {
//		ClickImple o1 = new ClickImple();
//		System.out.println("o1 hashcode" + o1.hashCode());
//		ClickImple o2 = new ClickImple();
//		System.out.println("o2 hashcode" + o2.hashCode());
//		ArrayList<ClickImple> arrayList = new ArrayList<ClickImple>();
//		arrayList.add(o1);
//		arrayList.add(o2);
//		for (Iterator it = arrayList.iterator(); it.hasNext();)
//			System.out.println(it.next().hashCode());
//		Collections.sort(arrayList, new Fun());
//		for (Iterator it = arrayList.iterator(); it.hasNext();)
//			System.out.println(it.next().hashCode());
//	}
	public Fun(int i){
		this.i = i;
	}
	public int i;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String JDriver = "com.mysql.jdbc.Driver"; // SQL数据库引擎
		String connectDB = "jdbc:mysql://10.10.2.194:3306/um"; // 数据源
		Connection con;
		try {
			Class.forName(JDriver); // 加载数据库引擎，返回给定字符串名的类
									// System.out.println("数据库驱动成功")
			String user = "root";
			String password = "1234";
			con = DriverManager.getConnection(connectDB, user, password); // 连接数据库对象
			PreparedStatement stmt = con.prepareStatement("select user.* from user where id=" + i);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){       //循环遍历查询结果集
				    System.out.println("的用户名称为："+rs.getString(2));
				   }
			stmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		while (true){
			Thread[] t = new Thread[300];
			for(int i = 0;i<300;i++){				
				t[i] = new Fun(i);
				t[i].start();
				System.out.println(i + ":");
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			for(int i = 0;i<100;i++){
//				try {
//					t[i].join();
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//			}
		}
	}

}
