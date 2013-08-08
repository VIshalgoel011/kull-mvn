<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="../include/head.jsp"></jsp:include>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
  
<table id='table-grid' class="easyui-datagrid" data-options="url:'/kull-webapp/sa/V_Dict/grid',idField:'_id',fit:true,toolbar:'#div-toolbar',
groupField:'_type',view: groupview,curl:'sa/D_Dict/save/{id}',uurl:'sa/D_Dict/save/{id}',durl:'sa/D_Dict/delete/{id}',rurl:'sa/D_Dict/read/{id}'
"> 
<thead><tr> 
 
<th data-options="field:'_code',width:180,title:'_code',editor:'text'">_code</th> 
<th data-options="field:'_type',width:180,title:'_type',editor:'text'">_type</th> 
<th data-options="field:'_text',width:180,title:'_text',editor:'text'">_text</th> 
<th data-options="field:'_remark',width:180,title:'_remark',editor:'text'">_remark</th> 
<th data-options="field:'_create_date',width:180,title:'_create_date',editor:'text'">_create_date</th> 
<th data-options="field:'_create_user_code',width:180,title:'_create_user_code',editor:'text'">_create_user_code</th> 
<th data-options="field:'_level',width:180,title:'_level',editor:'text'">_level</th> 
<th data-options="field:'_string_0',width:180,title:'_string_0',editor:'text'">_string_0</th> 
<th data-options="field:'_string_1',width:180,title:'_string_1',editor:'text'">_string_1</th> 
<th data-options="field:'_string_2',width:180,title:'_string_2',editor:'text'">_string_2</th> 
<th data-options="field:'_int_0',width:180,title:'_int_0',editor:'text'">_int_0</th> 
<th data-options="field:'_int_1',width:180,title:'_int_1',editor:'text'">_int_1</th> 
<th data-options="field:'_int_2',width:180,title:'_int_2',editor:'text'">_int_2</th> 
<th data-options="field:'dtype',width:180,title:'dtype',editor:'text'">dtype</th> 
</thead></tr> 
</table>
 <div id="div-toolbar">
 
    
  </div>
</div>

</body>
</html>