<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="/include/head.jsp"></jsp:include>

<script type="text/javascript">


   function doCreateSubject(){
	   var url="ra/D_SurveyKB/create";
	   $.messager.prompt("","请输入科目",function(text){
		   if(!text)return;
		   $.post(url,{subject:text},function(str){
			   //var json=eval('('+str+')');
			   $("#table-datagrid").datagrid("load");
		   });
	   });
   }

</script>

</head>
<body class="easyui-layout">
  <div data-options="region:'center'">
   <table id="table-datagrid" class="easyui-datagrid" 
   data-options="url:'ra/V_SurveyKB/grid',curl:'ra/D_SurveyKB/create',uurl:'ra/D_SurveyKB/save/{id}',
   durl:'ra/D_SurveyKB/delete/{id}',rurl:'ra/D_SurveyKB/read/{id}',
   groupField:'subject',
   fit:true,toolbar:'#div-toolbar',idField:'_id'"> 
<thead data-options="frozen:true"><tr> 

	<th data-options="field:'subject',width:120,title:'subject',editor:{type:'combobox',options:{required:true,url:'common/Combo/rows/ra_survey_kb_subject'}}">科目</th> 
	<th data-options="field:'_text',width:120,title:'_text',editor:{ type:'validatebox',options:{required:true }}">标题</th> 
	
	</tr>
</thead>
<thead><tr> 
	 
	<th data-options="field:'_code',width:180,title:'_code',editor:{ type:'validatebox',options:{required:true }}">代码</th> 
	
	<th data-options="field:'_remark',width:180,title:'_remark',editor:{ type:'validatebox',options:{required:true } }">_remark</th> 
	<th data-options="field:'_create_date',width:180,title:'_create_date',editor:{ type:'datebox',options:{required:true } }">_create_date</th> 
	<th data-options="field:'_create_user_code',width:180,title:'_create_user_code',editor:{ type:'validatebox',options:{required:true } }">_create_user_code</th> 
	<th data-options="field:'_level',width:180,title:'_level',editor:{ type:'numberspinner',options:{required:true,precision:0 } }">_level</th> 
	<th data-options="field:'_string_0',width:180,title:'_string_0',editor:{ type:'validatebox',options:{required:true } }">_string_0</th> 
	<th data-options="field:'_string_1',width:180,title:'_string_1',editor:{ type:'validatebox',options:{required:true } }">_string_1</th> 
	<th data-options="field:'_string_2',width:180,title:'_string_2',editor:{ type:'validatebox',options:{required:true } }">_string_2</th> 
	<th data-options="field:'_int_0',width:180,title:'_int_0',editor:{ type:'numberspinner',options:{required:true,precision:0 } }">_int_0</th> 
	<th data-options="field:'_int_1',width:180,title:'_int_1',editor:{ type:'numberspinner',options:{required:true,precision:0 } }">_int_1</th> 
	<th data-options="field:'_int_2',width:180,title:'_int_2',editor:{ type:'numberspinner',options:{required:true,precision:0 } }">_int_2</th> 
	<th data-options="field:'link',width:180,title:'link',editor:{ type:'validatebox',options:{required:true } }">link</th>  
	</tr> 
</thead>
</table>
<div id="div-toolbar">
     <a class="easyui-menubutton" data-options="iconCls:'icon-add'" onclick="doCreateSubject()" >新增科目</a>
  </div>
  </div>
</body>
</html>