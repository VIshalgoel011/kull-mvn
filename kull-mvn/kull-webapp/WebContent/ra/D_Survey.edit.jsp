<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s2" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="/include/head.jsp">
  <jsp:param value="kingeditor|" name="plugin"/>
</jsp:include>

<script type="text/javascript">

   $(document).ready(function(){
	   
	   var editor1 = KindEditor.create('textarea[name="content1"]');
	   
   });

</script>

</head>
<body class="easyui-layout">
    <div data-options="region:'north',border:false">
       <a class='easyui-menubutton'>保存</a>
    </div>
    <div class="easyui-tabs" data-options="region:'center',tabPosition:'bottom',border:false">
    
    <table >
       <thead>
       
       </thead>
       <tbody>
          <tr>
          <th><s2:text name="text"></s2:text></th>
          <td><s2:textfield name="text"></s2:textfield></td>
          </tr>
          
       </tbody>
    </table>
    
    <div title="基本信息" style="padding:20px;">  
       
    </div>  
    <div title="需求文档" data-options="closable:false" style="overflow:auto;padding:20px;">  
         <textarea name="content1" rows="7" cols="4">
                                                                     
        </textarea>
    </div>  
    <div title="UML" data-options="iconCls:'icon-reload',closable:false" style="padding:20px;">  
        tab3  
    </div>
    
    </div>
    
    
  
</body>
</html>