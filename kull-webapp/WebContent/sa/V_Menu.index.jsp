<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="../include/head.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery-easyui-etree/jquery.etree.js"></script>
<script type="text/javascript">

   function doCreate(){
	   
   }
   
   function doEdit(data,index){
	   $('<div></div>').dialog({modal:true,width:400})
	   .dialog('initEditForm',{el:'#table-grid',
		   surl:'sa/D_Menu/save/'+data["_id"]
	      ,rurl:"sa/D_Menu/read/"+data["_id"]
	   });
   }
   
   function doDelete(data,index){
	   $.messager.confirm("","确认删除 "+data["text"]+"?",function(e){
		   if(!e)return;
		   var url="sa/D_Menu/delete/"+data["_id"];
		   $.post(url,{},function(text){
			   $("#table-grid").treegrid('reload');
		   });
	   });
	   
   }
   
   function openEtree(){
	   $('<div></div>').dialog({modal:true,width:300,height:400})
	   .dialog("initEtree",{
		    url: 'common/Tree/node/sa_menu?root=root',  
		    createUrl: "sa/ET_Menu/createNode",  
		    updateUrl: "sa/ET_Menu/updateNode",  
		    destroyUrl: "sa/ET_Menu/destroyNode",  
		    dndUrl: "sa/ET_Menu/dndNode" 
	   });
	   ;
   }

</script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<table id="table-grid" class="easyui-treegrid"   
        data-options="url:'sa/V_Menu/grid?root=root',idField:'_code',treeField:'_text',fit:true,border:false,toolbar:'#div-toolbar'">  
    <thead>  
        <tr>  
       
            <th data-options="field:'_text',width:180,editor:'text'">text</th>  
             <th data-options="field:'_code',width:180,editor:'text'">code</th> 
            <th data-options="field:'href',width:200,align:'left',editor:'text'">href</th>  
             <th data-options="field:'_remark',width:200,align:'left',editor:'text'">remark</th> 
                <th data-options="field:'parent_code',hidden:true,editor:{type:'combotree',width:200,options:{url:'common/Tree/node/sa_menu?root=root',idField:'id'}}">parent</th> 
         <th data-options="field:'op',title:'opear',formatter:operaFormatter({type:'text',titles:['修改','删除'],handlers:['doEdit','doDelete']})"></th>
        </tr>  
    </thead>  
</table>  

  <div id="div-toolbar">
    <a class="easyui-linkbutton" data-options=" iconCls:'icon-add'" onclick="
      $('<div></div>').dialog({modal:true,width:400}).dialog('initEditForm',{el:'#table-grid',surl:'sa/D_Menu/create'});" >新增</a>
    <a class="easyui-linkbutton" data-options=" iconCls:'icon-edit'" onclick="openEtree()">快捷编辑</a>
  </div>

</div>
</body>
</html>