package mathmatic;

import java.io.BufferedInputStream;
import java.io.BufferedReader; 
import java.io.DataOutputStream; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.net.HttpURLConnection; 
import java.net.MalformedURLException;
import java.net.URL; 
import java.net.URLConnection;
import java.net.URLEncoder; 
import java.util.List;

public class addNew {
	public static  String _Url1;
	public static  String _Url2; 
	
	
	addNew() throws Exception{
		_Url1 = "http://111.204.219.225:8025/wum/login/validate";
		
		_Url2 = "http://111.204.219.225:8025/wum/myresources/addNew?name=12313&num=1&type="+ URLEncoder.encode("海康","utf-8") + "&des=" + URLEncoder.encode("新告警","utf-8") + "&ri-checkInfo=1&pic-res=";	
		
	}
	
	//登陆系统,并且返回cookie,作为身份表示,保证同一次会话
	public List<String> loginWum() throws Exception{

		String content = "nam=username&psw=3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d&check=";

		HttpURLConnection connection = getConnection(Method.post, _Url1, content);
	     
	    List<String> cookieString = connection.getHeaderFields().get("Set-Cookie");
	     
	    //以下为输出，方便调试
	    System.out.println("cookie is :" + cookieString.get(0));
	     
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
	               connection.getInputStream()));
	    String line;
	    System.out.println("=============================");
	    System.out.println("Contents of post request");
	    System.out.println("=============================");
	    while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
	    
	    System.out.println("=============================");
	    System.out.println("Contents of post request ends");
	    System.out.println("=============================");
	    reader.close();
	    //输出结束，关闭连接
	     
	    connection.disconnect();
	    return cookieString;
	
	}
	
	//获取login信息，并且开始创建资源
	public int addNewRes(List<String> cookieString) throws Exception{

		HttpURLConnection connection = getConnection(Method.get, _Url2, "");
	    
		for (String cookie : cookieString) {    
	    	 
			connection.addRequestProperty("Cookie",  cookie.split(";", 2)[0]);    
	    
	     }
	     
		connection.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded");
	    connection.connect();
	    
	    //以下为输出测试，方便调试
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
	               connection.getInputStream()));
	    String line;
	    System.out.println("=============================");
	    System.out.println("Contents of post request");
	    System.out.println("=============================");
	    while ((line = reader.readLine()) != null) {
//	    	 if (line.contains("lblCounts"))
	           System.out.println(line);
	    }
	    System.out.println("=============================");
	    System.out.println("Contents of post request ends");
	    System.out.println("=============================");
	    reader.close();
	    //输出结束，关闭连接    
	    connection.disconnect();
		
	    return 1;
	}
	
	//获取连接
	public HttpURLConnection getConnection(Method method, String _url,String postParam) throws IOException {
		 
		URL getUrl = new URL(_url);
		HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();
		 
	    connection.setDoInput(true);
	    connection.setUseCaches(false);
	    connection.setInstanceFollowRedirects(true);
	    if (method.equals(Method.get)){
	     connection.setDoOutput(false);
	     connection.setRequestMethod("GET");
	    }
	    else{
	   	 connection.setDoOutput(true);
	     connection.setRequestMethod("POST");
	     DataOutputStream out = new DataOutputStream(connection
	    		 .getOutputStream());
		 String content = postParam;
		 out.writeBytes(content);
	     out.flush();
	     out.close(); // flush and close
		     
	    }     
	    return connection;
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//        System.getProperties().setProperty("http.proxyHost", "111.13.109.52");
//        System.getProperties().setProperty("http.proxyPort", "80");
//        System.out.println(getHtml("http://20140507.ip138.com/ic.asp"));  //
		try {
			addNew add = new addNew();
//			for (int i = 1; i<999; i++)
			List<String> cookie = add.loginWum();
			add.addNewRes(cookie);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

	}


}
