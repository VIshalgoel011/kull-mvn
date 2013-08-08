<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.matech.framework.listener.UserSession"%>
<%@ page import="java.security.MessageDigest"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="sun.misc.BASE64Encoder"%>
<%! 
	public static String encoderByBASE64(String initialStr){
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(initialStr.getBytes());
	}
%>
<%
	String currurl = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getRequestURI()+"?"+request.getQueryString(); 
	System.out.println("--------currurl:"+currurl);
	String baseurl = currurl.substring(0,currurl.indexOf("/erp")+1);
	String jumpToUrl = "";
	UserSession userSession = new UserSession();
	//userSession.setUserId("100748");
	//userSession.setUserLoginId("zjh");
	userSession=(UserSession)request.getSession().getAttribute("userSession");
    
	
	
		String url=request.getParameter("url");
		String menuid="";
		System.out.println("--------url0:"+url);
		String id = userSession.getUserId();
			String loginId = userSession.getUserLoginId();
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String serverTime = sdf.format(d);
			String str = menuid+"|"+id  + "|"+loginId+"|" +serverTime + "|panchina";
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] buf = digest.digest(str.getBytes());
			StringBuffer returnValue = new StringBuffer();
			for(int i = 0;i < buf.length;i++) {
				String x = Integer.toHexString(buf[i] & 0XFF);
				if(x.length() == 1) {
					x = 0 + x;
				}
				returnValue.append(x);
			}
			
			char a[]=id.toCharArray();
            for(int i=0;i<a.length;i++){ 
			a[i]=(char)(a[i]^0001);//这儿的^'t'是什么意思
              System.out.println(a[i]);
            }
            String secret=new String(a);
            System.out.println(secret);
            //id=secret;
			String checkCode = returnValue.toString();
			jumpToUrl ="http://172.19.7.89:82/SYS/D_Menu/reminds?eid=" + encoderByBASE64(id) + "&loginId=" + encoderByBASE64(loginId) +
											"&checkCode=" + checkCode;
		
		
		  System.out.println("jumpToUrl="+jumpToUrl);
	      response.sendRedirect(jumpToUrl);
	%>
		
	
<%=jumpToUrl%>