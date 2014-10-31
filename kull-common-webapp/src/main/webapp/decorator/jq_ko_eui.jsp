<%-- 
    Document   : jq_ko.jsp
    Created on : Oct 24, 2014, 10:49:14 PM
    Author     : lin
--%>

<%@page import="com.kull.common.Html"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  

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
   ver_easyui=ver_easyui==null?"1.3.5":ver_easyui;
   ver_jquery=ver_jquery==null?"1.8.3":ver_jquery;
   ver_knockout=ver_knockout==null?"2.2.1":ver_knockout;
   theme_easyui=theme_easyui==null?"default":theme_easyui;
   plugin=plugin==null?"":plugin;
   mode=mode==null?"":ver_jquery;
   lang=lang==null?"zh_CN":lang;
   isDebug=!mode.contains("run");
   boolean isCache=request.getParameter("cache")!=null;
%>  

<!DOCTYPE html>
<html>
    <head>
          <base href="<%=basePath%>" >  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><decorator:title default="装饰器页面"/></title>
        <link rel="stylesheet" type="text/css" href="<%=Html.bootcdnBase %>/normalize/3.0.1/normalize.css">

<link rel="stylesheet" type="text/css" href="<%=Html.cdnPath %>/jquery-easyui-<%=ver_easyui %>/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=Html.cdnPath %>/jquery-easyui-<%=ver_easyui %>/themes/icon.css">

<script type="text/javascript" src="js/com.kull.js"></script>
         <script type="text/javascript" >
             com.kull.writeScript("<%=Html.bootcdnBase %>/jquery/2.1.1-rc2/jquery.min.js");
             com.kull.writeScript("<%=Html.bootcdnBase %>/knockout/3.2.0/knockout-min.js");
             com.kull.writeScript("<%=Html.cdnPath %>/jquery-easyui-<%=ver_easyui %>/jquery.easyui.min.js");
             com.kull.writeScript("<%=Html.cdnPath %>/jquery-easyui-<%=ver_easyui %>/locale/easyui-lang-zh_CN.js");
              com.kull.writeScript("js/jeasyui.extension.js");
         </script>


    <decorator:head />  
    </head>
    <body>
        <decorator:body  />
        
         


<%@include file="/view/ko.applyBindings.jspf"  %>

    </body>
</html>
