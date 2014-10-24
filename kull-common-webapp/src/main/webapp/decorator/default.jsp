<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
String cdnPath="http://smartken.github.io/cdn";
boolean isDebug=true;   
   String script="script";
   String ver_easyui=request.getParameter("ver_easyui")
		   ,ver_jquery=request.getParameter("ver_jquery")
		   ,theme_easyui=request.getParameter("theme_easyui")
		   ,lang=request.getParameter("lang")
		   ,mode=request.getParameter("mode")
		   ,ver_knockout=request.getParameter("ver_knockout")
		   ,plugin=request.getParameter("plugin")
		   ;
   ver_easyui=ver_easyui==null?"1.3.3":ver_easyui;
   ver_jquery=ver_jquery==null?"1.8.3":ver_jquery;
   ver_knockout=ver_knockout==null?"2.2.1":ver_knockout;
   theme_easyui=theme_easyui==null?"default":theme_easyui;
   plugin=plugin==null?"":plugin;
   mode=mode==null?"":ver_jquery;
   lang=lang==null?"zh_CN":lang;
   isDebug=!mode.contains("run");
   boolean isCache=request.getParameter("cache")!=null;
%>  
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
  <head>  
    <base href="<%=basePath%>">  
      
    <title><decorator:title default="装饰器页面"/></title>  
      
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
<link rel="stylesheet/less" type="text/css" href="css/basic.less.css">
<link rel="stylesheet" type="text/css" href="<%=cdnPath %>/jquery-easyui-<%=ver_easyui %>/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=cdnPath %>/jquery-easyui-<%=ver_easyui %>/themes/icon.css">
    <decorator:head />  
  </head>  
    
  <body>  
    <decorator:body />  
    
    
    
    <script type="text/javascript" src="<%=cdnPath %>/kull.js"></script>
<script type="text/javascript" src="<%=cdnPath %>/jquery-<%=ver_jquery %>.min.js"></script>
<script type="text/javascript" src="<%=cdnPath %>/jquery-easyui-<%=ver_easyui %>/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=cdnPath %>/jquery-easyui-<%=ver_easyui %>/locale/easyui-lang-zh_CN.js"></script>
    
  </body>  
</html>  