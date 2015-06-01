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

public class ClickImple {
	public static final String _Url = "http://ideaclub.lenovo.com.cn/forum/forum.php?mod=post&amp;action=reply&amp;fid=98&amp;tid=42142&amp;extra=&amp;replysubmit=yes&amp;infloat=yes&amp;handlekey=fastpost";
	
	public static void readContentPost() throws Exception{
		URL postUrl = new URL(_Url);
		 HttpURLConnection connection = (HttpURLConnection) postUrl
	                .openConnection();
//	
		 connection.setDoOutput(true);
	     connection.setDoInput(true);
	     connection.setRequestMethod("POST");
	     connection.setUseCaches(false);
	     connection.setInstanceFollowRedirects(true);
	     connection.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded");
	     connection.connect();
	     DataOutputStream out = new DataOutputStream(connection
	                .getOutputStream());
	     String content = "message=超赞超好玩&posttime=1417052902&formhash=61476df4&usesig=1&subject=";
		 out.writeBytes(content);
	     out.flush();
	     out.close(); // flush and close
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
	        connection.disconnect();
		
	}
	private static String getHtml(String address){
        StringBuffer html = new StringBuffer();
        String result = null;
        try{
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            try {
                String inputLine;
                byte[] buf = new byte[4096];
                int bytesRead = 0;
                while (bytesRead >= 0) {
                    inputLine = new String(buf, 0, bytesRead, "ISO-8859-1");
                    html.append(inputLine);
                    bytesRead = in.read(buf);
                    inputLine = null;
                }
                buf = null;
            } finally {
                in.close();
                conn = null;
                url = null;
            }
        result = new String(html.toString().trim().getBytes("ISO-8859-1"), "gb2312").toLowerCase();
        }catch(Exception  e){
            e.printStackTrace();
            return null;
        }
        html = null;
        return  result;
    }


	/**
	 * @param args
	 */
	public static void main(String[] args) {
//        System.getProperties().setProperty("http.proxyHost", "111.13.109.52");
//        System.getProperties().setProperty("http.proxyPort", "80");
//        System.out.println(getHtml("http://20140507.ip138.com/ic.asp"));  //
		try {
//			for (int i = 1; i<999; i++)
			readContentPost();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

	}

}
