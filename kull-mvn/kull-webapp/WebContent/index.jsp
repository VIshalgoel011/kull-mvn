<%@page import="org.json.JSONArray"%>
<%@page import="com.kull.bean.WebBean"%>
<%@page import="com.kull.WebHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% 
   WebBean webBean=new WebBean(request,response);
   String basePath=webBean.getWebRootPath();
   Object theme=session.getAttribute("easyuiTheme");
   if(theme==null){
	   theme="default";
   }
   JSONArray jarrTheme=new JSONArray();
   
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<jsp:include page="include/head.jsp"></jsp:include>


<script type="text/javascript">
 
   

   $(document).ready(function(){
	   $("#divMenu").tree({
		   url:"common/Tree/node/sa_menu?root=root"
		   ,
		   onClick:function(node){
			   var nattr=node["attributes"];
			   if(!nattr['href'])return;
				//if(node["attributes"]["_type"]!="link")
				//	return false;
				if($("#divMain").tabs("exists",node["text"])==false)
				{
                    var src=nattr["href"];
                    var iframe="<iframe  frameborder='0' src='"+src+"' />";
					$("#divMain").tabs('add',{
					                       title:node.text,
					                       closable:true 
					                       ,content:iframe
						            });
				}else
			    {
					$("#divMain").tabs("select",node.text);
			    }
			}
	   });
	   
	   $("#inputEasyuiTheme").combobox({
		   data:<%=jarrTheme.toString() %>
		   ,textField:"text"
		   ,valueField:"value"
		   ,onChange:function(nv,ov){
			  var url="admin/Menu/do/setEasyuiTheme.action";
			  $.messager.confirm("操作提示","确认更新主题为"+nv+" 吗？",function(y){
                  if(y){
        			  $.get(url,{easyuiTheme:nv},function(str){
        				  str.messager();
        				  document.location.reload();
        			  });
                  }
		      });

		   }
	   });
	   
   });

</script>





</head>
<body class="easyui-layout">
     <div region="north" data-options="fit:false,border:false,split:true" style="height: 100px; ">
       <div style="text-align: right;">
           当前主题：<input  name="sa_dict_type" class="easyui-combobox" style="" data-options=" valueField:'_code',textField:'_text',url:'common/Combo/rows/sa_dict_type'" />
       </div>
     </div>
     <div region="west"  title="菜单导航" data-options="split:true" style="width: 250px">
       <div id="divMenu"></div>
     </div> 
     <div region="center"  >
        <div id="divMain" class="easyui-tabs" data-options="fit:true,border:false"></div>
     </div>
     <div region="south" data-options="fit:false,border:false" style="height: 100px"></div>
     
     <div id='mm'></div>
</body>
</html>