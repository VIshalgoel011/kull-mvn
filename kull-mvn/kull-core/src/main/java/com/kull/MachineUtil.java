package com.kull;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MachineUtil {

	public static Process Exec(String command) throws IOException{
	    //Execable execable=new MachineUtil().new Execable(command);
		
		//Thread thread=new Thread(execable);
        //thread.start();
        //return execable.getProcess();
		Runtime runtime=Runtime.getRuntime();
		Process process= runtime.exec(command);
        InputStream inputStream=process.getInputStream();
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        System.out.println(bufferedReader.readLine());
        
		//runtime.exit(0);
		return process;
	}
	
	private class Execable  implements Runnable  {

		private Process process;
		private String command;
		
		
		public Process getProcess() {
			return process;
		}

        public Execable(String command){
        	this.command=command;
        }

		
		public void run() {
			// TODO Auto-generated method stub
			try {
				
				Runtime runtime=Runtime.getRuntime();
				process = runtime.exec(command);
		        InputStream inputStream=process.getInputStream();
		        InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
		        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
		        System.out.println(bufferedReader.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	 private static StringBuffer getWanPacket(URL url) throws IOException {  
	      
	        InputStream ins = null;  
	        try {  
	            ins = url.openConnection().getInputStream();  
	            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));  
	            String str;  
	            boolean flag = false;  
	            StringBuffer wanPacket = new StringBuffer();  
	            int num = 3;  
	            while ((str = reader.readLine()) != null && num > 0) {  
	                if (str.contains("var wanPara = new Array(")) {  
	                    flag = true;  
	                }  
	                if (flag) {  
	                    wanPacket.append(str);  
	                    num--;  
	                }  
	            }  
	            return wanPacket;  
	        }finally{  
	            if(ins!=null){  
	                ins.close();  
	            }  
	        }  
	    }  
	  
	    private static String getFirstIp(StringBuffer wanPacket) {  
	        // 找出数据包中第一个匹配的IP,即为Ip  
	        Pattern p = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+");  
	        Matcher m = p.matcher(wanPacket);  
	        if (m.find()) {  
	            return m.group();  
	        } else {  
	            return null;  
	        }  
	    }  
	    
	    public static String DNSOnWindows() throws IOException{
	    	String s = "";   
	    	  try {   
	    	  String s1 = "ipconfig /all";   
	    	  Process process = Runtime.getRuntime().exec(s1);   
	    	  BufferedReader bufferedreader = new BufferedReader(   
	    	  new InputStreamReader(process.getInputStream()));   
	    	  String nextLine;   
	    	  for (String line = bufferedreader.readLine(); line != null; line = nextLine) {   
	    	  nextLine = bufferedreader.readLine();   
	    	  if (line.indexOf("DNS Servers") <= 0) {   
	    	  continue;   
	    	  }   
	    	  int i = line.indexOf("DNS Servers") + 36;   
	    	  s = line.substring(i);   
	    	  break;   
	    	  }   
	    	    
	    	  bufferedreader.close();   
	    	  process.waitFor();   
	    	  } catch (Exception exception) {   
	    	  s = "";   
	    	  }   
	    	  return s.trim();   
	    	  }   
	    

	    public static void main(String[] args){
	    	try {
				System.out.println(DNSOnWindows());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	
}
