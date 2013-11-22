package com.kull;

import java.applet.Applet;
import java.awt.Container;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
 
import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;

public  abstract class BaseApplet extends JApplet {

	
    private   JFrame   getParentFrame(){   
        Container   c   =   this;   
        while(c   !=   null){   
            if   (c   instanceof   Frame)   
                return   (JFrame)c;   

            c   =   c.getParent();   
        }   
        return  null;   
    }   

	
	public void alert(String title,String context,boolean isModal){
		JDialog dialog=new JDialog(getParentFrame(),title,isModal);
		dialog.setLayout(new FlowLayout());
		Dimension dimension= this.getToolkit().getScreenSize();
		dialog.setLocation(dimension.width>>1, dimension.height>>1);
		dialog.setSize(300,200);
		dialog.add(new Label(context));
		dialog.setVisible(true);
	}
	
	
	public String doHttpGet(String url,Map<String, String> nvs){
		String context="";
		return context;
	}
	
	public String doHttpPost(String url,Map<String, String> nvs,Map<String, String> nfs){
		String context="";
		
		HttpURLConnection conn = null;
		  BufferedReader br = null;
		  DataOutputStream dos = null;
		  DataInputStream inStream = null;

		  InputStream is = null;
		  OutputStream os = null;
		  boolean ret = false;
		  String StrMessage = "";
		  

		  String lineEnd = "/r/n";
		  String twoHyphens = "--";
		  String boundary = "*****";

		  int bytesRead, bytesAvailable, bufferSize;

		  byte[] buffer;

		  int maxBufferSize = 1 * 1024 * 1024;

		  String responseFromServer = "";

		  try {
			conn = (HttpURLConnection) new URL(url).openConnection();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  
		   // Allow Inputs
		   conn.setDoInput(true);

		   // Allow Outputs
		   conn.setDoOutput(true);

		   // Don't use a cached copy.
		   conn.setUseCaches(false);

		   // Use a post method.
		   try {
			conn.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		   conn.setRequestProperty("Connection", "Keep-Alive");

		   conn.setRequestProperty("Content-Type",
		     "multipart/form-data;boundary=" + boundary);

		   try {
			dos = new DataOutputStream(conn.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  try {
		   // ------------------ CLIENT REQUEST
           for(Iterator<String> it=nfs.keySet().iterator();it.hasNext();){
           String fileName=it.next();
           String fileSrc=nfs.get(fileName);
		   FileInputStream fileInputStream = new FileInputStream(new File(fileSrc));


		   conn = (HttpURLConnection)new URL(url).openConnection();

	

		   dos.writeBytes(twoHyphens + boundary + lineEnd);
		   //dos.writeBytes("Content-Disposition: form-data; name=\"upload\";"
		   //  + " filename=\"" + exsistingFileName + "\"" + lineEnd);
		   String fd=MessageFormat.format("Content-Disposition: form-data; name=\"{0}\"; filename=\"{0}\" " +lineEnd, fileName,fileSrc);
		   out(fd);
		   dos.writeBytes(fd);
		   dos.writeBytes(lineEnd);

		   // create a buffer of maximum size

		   bytesAvailable = fileInputStream.available();
		   bufferSize = Math.min(bytesAvailable, maxBufferSize);
		   buffer = new byte[bufferSize];

		   // read file and write it into form...

		   bytesRead = fileInputStream.read(buffer, 0, bufferSize);

		   while (bytesRead > 0) {
		    dos.write(buffer, 0, bufferSize);
		    bytesAvailable = fileInputStream.available();
		    bufferSize = Math.min(bytesAvailable, maxBufferSize);
		    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		   }

		   // send multipart form data necesssary after file data...

		   dos.writeBytes(lineEnd);
		   dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
		   fileInputStream.close();
           }
           
		   // close streams

		   
		   dos.flush();
		   dos.close();

		  } catch (MalformedURLException ex) {
		   System.out.println("From ServletCom CLIENT REQUEST:" + ex);
		  }

		  catch (IOException ioe) {
		   System.out.println("From ServletCom CLIENT REQUEST:" + ioe);
		  }

		  // ------------------ read the SERVER RESPONSE

		  try {
		   //inStream = new DataInputStream(conn.getInputStream());
		   is=conn.getInputStream();
		   br=new BufferedReader(new InputStreamReader(is));
			  String str;
		   while ((str = br.readLine()) != null) {
		    System.out.println("Server response is: " + str);
		    System.out.println("");
		    context+=str;
		   }
		   is.close();
		   br.close();
		   
           
		  } catch (IOException ioex) {
		   System.out.println("From (ServerResponse): " + ioex);

		  }
		
		return context;
	}
	
	
	public  String post(String actionUrl, Map<String, String> params,
			   Map<String, String> files) {

			  String BOUNDARY = java.util.UUID.randomUUID().toString(); // 分隔符
			  String PREFIX = "--", LINEND = "\r\n"; // 分隔符
			  String MULTIPART_FROM_DATA = "multipart/form-data"; // form提交
			  String CHARSET = "UTF-8"; // 编码

			  HttpURLConnection conn = null;

			  try {
			   // 请求实例对象Url
			   URL uri = new URL(actionUrl);
			   // 打开连接
			   conn = (HttpURLConnection) uri.openConnection();
			   // 连接的最长时间
			   conn.setConnectTimeout(3 * 10000);
			   // 允许输入
			   conn.setDoInput(true);
			   // 允许输出
			   conn.setDoOutput(true);
			   // 不允许使用缓存
			   conn.setUseCaches(false);
			   // POST请求不允许使用缓存
			   conn.setRequestMethod("POST");
			   // 维持连接
			   conn.setRequestProperty("connection", "keep-alive");
			   // 请求编码类型
			   conn.setRequestProperty("Charsert", "UTF-8");
			   // 设置请求类型和分隔符
			   conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
			     + ";boundary=" + BOUNDARY);
			   System.out.println(actionUrl + ":" + files.size());
			   DataOutputStream outStream = new DataOutputStream(
			     conn.getOutputStream());

			   // 发送参数数据
			   if (params != null) {
			    // 首先组拼文本类型的参数
			    StringBuilder sb = new StringBuilder();
			    for (Map.Entry<String, String> entry : params.entrySet()) {
			     sb.append(PREFIX);
			     sb.append(BOUNDARY);
			     sb.append(LINEND);
			     sb.append("Content-Disposition: form-data; name=\""
			       + entry.getKey() + "\"" + LINEND);
			     sb.append("Content-Type: text/plain; charset=" + CHARSET
			       + LINEND);
			     sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			     sb.append(LINEND);
			     sb.append(entry.getValue());
			     sb.append(LINEND);
			    }

			    outStream.write(sb.toString().getBytes());
			   }
			   // 发送文件数据
			   if (files != null) {
			    int i = 0;
			    for (Iterator<String> it=files.keySet().iterator();it.hasNext();) {
			     String fileName=it.next();
			     String fileSrc=files.get(fileName);
			     File file=new File(fileSrc);
			     if(!file.exists()){
			    	 continue;
			     }
			     StringBuilder sb = new StringBuilder();
			     sb.append(PREFIX);
			     sb.append(BOUNDARY);
			     sb.append(LINEND);
			    // sb.append("Content-Disposition: form-data; name=\"file"
			    //   + (i++) + "\"; filename=\"" + file.getKey() + "\""
			    //   + LINEND);
			     sb.append(MessageFormat.format("Content-Disposition: form-data; name=\"{0}\"; filename=\"{0}\"  "+ LINEND, fileName));
			     sb.append("Content-Type: application/octet-stream; charset="
			       + CHARSET + LINEND);
			     sb.append(LINEND);
			     outStream.write(sb.toString().getBytes());

			     InputStream is = new FileInputStream(file);
			     byte[] buffer = new byte[1024];
			     int len = 0;
			     while ((len = is.read(buffer)) != -1) {
			      outStream.write(buffer, 0, len);
			     }

			     is.close();
			     outStream.write(LINEND.getBytes());
			    }
			   }

			   // 请求结束标志
			   byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			   outStream.write(end_data);
			   outStream.flush();

			   // 得到响应码
			   int res = conn.getResponseCode();
			   if (res == 200) {
			    InputStream in = conn.getInputStream();
			    int ch;
			    StringBuilder sb = new StringBuilder();
			    while ((ch = in.read()) != -1) {
			     sb.append((char) ch);
			    }
			    in.close();
			    return sb.toString();
			   }
			  } catch (IOException ex) {
			   ex.printStackTrace();
			  } catch (RuntimeException ex) {
			   ex.printStackTrace();
			  } finally {
			   if (conn != null) {
			    conn.disconnect();
			   }
			  }
			  return "";
			 }
	
	
	protected void out(String str){
		System.out.println(str);
		
	}
}
