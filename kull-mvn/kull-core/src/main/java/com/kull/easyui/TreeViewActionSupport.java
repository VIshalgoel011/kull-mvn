package com.kull.easyui;


import java.sql.Connection;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;


import org.omg.CORBA._IDLTypeStub;


import com.kull.ObjectHelper;
import com.kull.bean.JdbcBean;
import com.kull.struts.SimpleViewActionSupport;
import com.kull.util.IQueryable;
import com.opensymphony.xwork2.util.classloader.JarResourceStore;


public abstract class TreeViewActionSupport extends SimpleViewActionSupport {

	public final String idField="id",textField="text",_parentIdField="_parentId",iconClsField="iconCls",childrenField="children",attributesField="attributes",stateField="state",checkedField="checked";
	
    protected String root;
    
    
    
	public void setRoot(String root) {
		this.root = root;
	}

	@Override
	protected String viewName() {
		// TODO Auto-generated method stub
		return "tree_"+this.pk;
	}

	public void node(){
		Connection conn=this.createConnection();
		JdbcBean jdbcBean=new JdbcBean(conn);
		String dsql=this.createDataSql();
		LinkedList<Map<String,Object>> list= jdbcBean.selectList(dsql);
		
	    Vector<Map<String,Object>> tree=createTreeNodes(list, this.root);
		
		
	    JdbcBean.close(conn, null, null);
	    writeText(toJson(tree));
	}
	
	public void combo(){
		
	}
	
	public void grid(){
		
	}
	
	protected Vector<Map<String,Object>> createTreeNodes(LinkedList<Map<String,Object>> list,String root) {
		Vector<Map<String,Object>> nodes=new Vector<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=list.get(i),mapNew=new HashMap<String,Object>();
			if(ObjectHelper.isNotEquals(root, map.get(_parentIdField.toLowerCase())))continue;
			mapNew.put(_parentIdField, map.get(_parentIdField.toLowerCase()));
			mapNew.put(idField, map.get(idField.toLowerCase()));
			mapNew.put(textField, map.get(textField.toLowerCase()));
			mapNew.put(iconClsField, map.get(iconClsField.toLowerCase()));
			mapNew.put(checkedField, map.get(checkedField.toLowerCase()));
			mapNew.put(childrenField,this.createTreeNodes(list, map.get(idField.toLowerCase()).toString()));
			mapNew.put(attributesField, map);
			nodes.add(mapNew);
		}
		return nodes;
	}

	@Override
	public void help() throws Exception {
		// TODO Auto-generated method stub
		super.help();
		StringBuilder sbr=new StringBuilder("");
		sbr.append("<fieldset><legend>view sql template</legend>");
		sbr.append(MessageFormat.format("select {0} as {0} ,{1} as {1},{2} as {2},{3} as {3},{4} as {4},{5} as {5} from tree_{6} "
				,idField
				,textField
				,_parentIdField
				,iconClsField
				,checkedField
				,stateField
				,this.pk
				));
		sbr.append("<fieldset/>");
		writeText(sbr.toString());
	}

	@Override
	protected int postStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int postLimit() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
