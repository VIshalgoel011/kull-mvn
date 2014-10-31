<%-- 
    Document   : DS_Conn.index
    Created on : Oct 21, 2014, 3:32:41 PM
    Author     : lin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.kull.common.Html"%>

<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript">
            com.kull.writeScript("js/com.kull.easyui.js");
            com.kull.writeScript("<%=Html.cdnPath %>/jquery-easyui-datagridview/datagrid-groupview.js"); 
             
           
        </script>
    </head>
    <body>
        <table id="grid" class="easyui-datagrid"  data-options="url:'dbmeta/DS_Conn/grid',fit:true,toolbar:'#div-toolbar',idField:'id'
             , groupField:'driver' ,view: groupview,curl:'dbmeta/MD_Conn/save/{id}',uurl:'dbmeta/MD_Conn/save/{id}',durl:'dbmeta/MD_Conn/delete/{id}',rurl:'dbmeta/MD_Conn/read/{id}'
               "> 
<thead data-options="frozen:true"><tr> </thead><thead><tr> 
	
	<th data-options="field:'name',width:180,title:'name',editor:{ type:'validatebox',options:{required:true } }">name</th> 
	<th data-options="field:'descp',width:180,title:'descp',editor:{ type:'validatebox',options:{required:true } }">descp</th> 
	
	<th data-options="field:'driver',width:180,title:'driver',editor:{ type:'validatebox',options:{required:true } }">driver</th> 
	<th data-options="field:'user',width:180,title:'user',editor:{ type:'validatebox',options:{required:false } }">user</th> 
	<th data-options="field:'pwd',width:180,title:'pwd',editor:{ type:'validatebox',options:{required:false } }">pwd</th> 
	<th data-options="field:'url',width:180,title:'url',editor:{ type:'validatebox',options:{required:false } }">url</th> 
	
</thead></tr> 
</table>
<div id="div-toolbar"></div>
        
       
    </body>
</html>
