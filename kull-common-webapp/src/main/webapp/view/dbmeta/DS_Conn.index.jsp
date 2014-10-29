<%-- 
    Document   : DS_Conn.index
    Created on : Oct 21, 2014, 3:32:41 PM
    Author     : lin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table id="table-datagrid" class="easyui-datagrid" data-options="url:'/kull-common-webapp/dbmeta/DS_Conn/grid',fit:true,toolbar:'#div-toolbar',idField:'_id'"> 
<thead data-options="frozen:true"><tr> </thead><thead><tr> 
	<th data-options="field:'id',width:180,title:'id',editor:{ type:'validatebox',options:{required:true } }">id</th> 
	<th data-options="field:'name',width:180,title:'name',editor:{ type:'validatebox',options:{required:true } }">name</th> 
	<th data-options="field:'descp',width:180,title:'descp',editor:{ type:'validatebox',options:{required:true } }">descp</th> 
	<th data-options="field:'dialect',width:180,title:'dialect',editor:{ type:'validatebox',options:{required:true } }">dialect</th> 
	<th data-options="field:'driver',width:180,title:'driver',editor:{ type:'validatebox',options:{required:true } }">driver</th> 
	<th data-options="field:'user',width:180,title:'user',editor:{ type:'validatebox',options:{required:true } }">user</th> 
	<th data-options="field:'pwd',width:180,title:'pwd',editor:{ type:'validatebox',options:{required:true } }">pwd</th> 
	<th data-options="field:'url',width:180,title:'url',editor:{ type:'validatebox',options:{required:true } }">url</th> 
	<th data-options="field:'lastcheck_at',width:180,title:'lastcheck_at',editor:{ type:'validatebox',options:{required:true } }">lastcheck_at</th> 
	<th data-options="field:'lastcheck_re',width:180,title:'lastcheck_re',editor:{ type:'validatebox',options:{required:true } }">lastcheck_re</th> 
	<th data-options="field:'lastcheck_msg',width:180,title:'lastcheck_msg',editor:{ type:'validatebox',options:{required:true } }">lastcheck_msg</th> 
</thead></tr> 
</table>
<div id="div-toolbar"></div>
        
       
    </body>
</html>
