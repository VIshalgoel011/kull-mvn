
<%@page import="java.text.MessageFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<% String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
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
   isDebug=!mode.contains("run,");
   boolean isCache=request.getParameter("cache")!=null;
 %>




<base href="<%=basePath%>/">
<%if(isCache){ %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="pragma" content="no-cach" /> 
<meta http-equiv="cache-control" content="no-cache" /> 
<meta http-equiv="expires" content="0" />
<%} %>
<!--  

-->
<link rel="stylesheet" type="text/css" href="css/normalize.css">
<link rel="stylesheet/less" type="text/css" href="css/basic.less.css">
<link rel="stylesheet" type="text/css" href="<%=script %>/jquery-easyui-<%=ver_easyui %>/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=script %>/jquery-easyui-<%=ver_easyui %>/themes/icon.css">


<style type="text/css">
 .icon{
   width: 16px;
   height:16px;
 }
</style>


<script type="text/javascript" src="<%=script %>/kull.js"></script>
<script type="text/javascript" src="<%=script %>/jquery-<%=ver_jquery %>.min.js"></script>
<script type="text/javascript" src="<%=script %>/jquery-easyui-<%=ver_easyui %>/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=script %>/jquery-easyui-<%=ver_easyui %>/locale/easyui-lang-<%=lang %>.js"></script>
<script type="text/javascript" src="<%=script %>/jquery.easyui.extension.js"></script>
<script type="text/javascript" src="<%=script %>/kull.easyui.js"></script>
<script type="text/javascript" src="<%=script %>/knockout-<%=ver_knockout %>.js"></script>
<script type="text/javascript" src="<%=script %>/datajs/date.js"></script>
<script type="text/javascript" src="<%=script %>/less-1.3.1.min.js"></script>

<%
   if(plugin.contains("kingeditor|")){
	   
%>
<script type="text/javascript" src="<%=script %>/kindeditor-4.1.7/kindeditor-all-min.js"></script>
<%} %>

<script type="text/javascript">


function dateFormatter(value, rowData, rowIndex) {
    try {
        //var dv = value.substring(1, value.length - 1);
       // var dt = eval("new " + dv);
   	 var dt=Date.parse(value);
        return dt.toString(Toyz4js["cfg"]["datePattern"]);
    } catch (ex) {

    return "";}
}



function dateTimeFormatter(value, rowData, rowIndex) {
    try {
       // var dv = value.substring(1, value.length - 1);
       // var dt = eval("new " + dv);
   	 var dt=Date.parse(value);
        return dt.toString(Toyz4js["cfg"]["datetimePattern"]);
    } catch (ex) {  return ""; }
}


function dictFormatter(type) {
    var dicts = Toyz4js["cache"]["DictModel"][type]["rows"] || [];
    var dictFormatter = function(value, rowData, rowIndex) {
        
        for (var i = 0; i < dicts.length; i++) {
            var dict = dicts[i];
            var id = dict["value"] || "";
            if (id.toString() == value.toString()) { return dict["text"] || ""; }
        }
        return value;
    };
 return dictFormatter;
}







function operaFormatter(opts) {

   opts = opts || {};
   var type=opts["type"]||"icon";
   if(type!="icon"&&type!="text"){
  
   	type="icon";
   }
   
   var iconClss = opts["iconClss"] || [];
   var titles = opts["titles"] || [];
   var handlers = opts["handlers"] || [];
   var operaFomatter = function(value, rowData, rowIndex) {
	   rowIndex=rowIndex||-1;
       var a="";
       var strRowData =Kull.stringify(rowData);
       for(var i=0;i<handlers.length;i++){
       	var handler=handlers[i];
       	var iconCls=iconClss[i]||"file";
       	var title=titles[i]||handler;
       	if(type=="icon"){
            a += "<a onclick='" + handler + "(" + strRowData + "," + rowIndex + ")' class='icon " + iconCls + "' title='"+title+"' ></a>";
       	}else if(type=="text"){
            a += "<a onclick='" + handler + "(" + strRowData + "," + rowIndex + ")' title='"+title+"' >"+title+"</a>"; 	
       	}
           a +="&nbsp;&nbsp;&nbsp;";
       }
       
      

       return a;
   }
 return operaFomatter;
}





function imgFormatter(style) {
   return function(value, rowData, rowIndex) {
       if (value.length == 0) {
           return "";
       }
       var context = "<img src='" + value + "'";
       if (style) {
           context += " style='" + style + "' ";
       }
       context += " />";
       return context;
   }

}

function hrefFormatter(style) {
   return function(value, rowData, rowIndex) {
       if (value.length == 0) {
           return "";
       }
       var context = "<a href='" + value + "' target='_blank'";
       if (style) {
           context += " style='" + style + "' ";
       }
       context += " >"+value+"</a>";
       return context;
   }

}


function numberDescSorter(n1, n2) {
   return n1 > n2;
}

function stringDescSorter(c1, c2) {
   return c1.localeCompare(c2);
}





function LikeFilter(proName) {
   return function(q, rowData) { return rowData[proName].indexOf(q) != -1; }
}

</script>
