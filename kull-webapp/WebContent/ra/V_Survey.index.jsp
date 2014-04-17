<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">


var col__id={field:'_id',width:180,title:'_id',editor:{ type:"numberspinner",options:{required:true,precision:0 } } }; 
var col__code={field:'_code',width:180,title:'_code',editor:{ type:"validatebox",options:{required:true } } }; 
var col__type={field:'_type',width:180,title:'_type',editor:{ type:"validatebox",options:{required:true } } }; 
var col__text={field:'_text',width:180,title:'_text',editor:{ type:"validatebox",options:{required:true } } }; 
var col__remark={field:'_remark',width:180,title:'_remark',editor:{ type:"validatebox",options:{required:true } } }; 
var col__create_date={field:'_create_date',width:180,title:'_create_date',editor:{ type:"datebox",options:{required:true } } }; 
var col__create_user_code={field:'_create_user_code',width:180,title:'_create_user_code',editor:{ type:"validatebox",options:{required:true } } }; 
var col__level={field:'_level',width:180,title:'_level',editor:{ type:"numberspinner",options:{required:true,precision:0 } } }; 
var col__string_0={field:'_string_0',width:180,title:'_string_0',editor:{ type:"validatebox",options:{required:true } } }; 
var col__string_1={field:'_string_1',width:180,title:'_string_1',editor:{ type:"validatebox",options:{required:true } } }; 
var col__string_2={field:'_string_2',width:180,title:'_string_2',editor:{ type:"validatebox",options:{required:true } } }; 
var col__int_0={field:'_int_0',width:180,title:'_int_0',editor:{ type:"numberspinner",options:{required:true,precision:0 } } }; 
var col__int_1={field:'_int_1',width:180,title:'_int_1',editor:{ type:"numberspinner",options:{required:true,precision:0 } } }; 
var col__int_2={field:'_int_2',width:180,title:'_int_2',editor:{ type:"numberspinner",options:{required:true,precision:0 } } }; 
var col_context={field:'context',width:180,title:'context',editor:{ type:"validatebox",options:{required:true } } }; 
var col_kb_code={field:'kb_code',width:180,title:'kb_code',editor:{ type:"validatebox",options:{required:true } } }; 


var btn_apply={text:"申请",iconCls:"icon-add",handler:function(){
	window.location.href="ra/D_Survey/edit";
}};

$(document).ready(function(){
	
	
	$("#table-datagrid_crud").datagrid({url:"ra/V_Survey/grid",fit:true,toolbar:[btn_apply],idField:"_id",frozenColumns:[],columns:[ 
	[col__id,col__code,col__type,col__text,col__remark,col__create_date,col__create_user_code,col__level,col__string_0,col__string_1,col__string_2,col__int_0,col__int_1,col__int_2,col_context,col_kb_code]]
	,durl:"ra/D_Survey/delete/{id}",rurl:"ra/D_Survey/read/{id}"
	} ) ;  //$("#table-datagrid_crud").datagrid
	
});

</script>
</head>
<body class="easyui-layout">
    <div data-options="region:'center'">
      <table id="table-datagrid_crud"></table>
      
    </div>
</body>
</html>