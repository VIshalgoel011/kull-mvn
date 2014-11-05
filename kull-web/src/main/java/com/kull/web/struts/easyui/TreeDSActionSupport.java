package com.kull.web.struts.easyui;



import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;





import com.kull.ObjectHelper;
import com.kull.orm.Session;
import com.kull.web.Utils;
import com.kull.web.struts.DSActionSupport;
import com.kull.web.struts.SqlDSActionSupport;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



public abstract class TreeDSActionSupport extends SqlDSActionSupport {

	public final String idField="id",textField="text",_parentIdField="_parentId",iconClsField="iconCls",childrenField="children",attributesField="attributes",stateField="state",checkedField="checked";
	
    protected String root,pk;

    public void setPk(String pk) {
        this.pk = pk;
    }
    
    
    
	public void setRoot(String root) {
		this.root = root;
	}

	@Override
	protected String viewName() {
		// TODO Auto-generated method stub
		return "tree_"+this.pk;
	}

	public void nodes() throws IOException, SQLException{
		
		Session session=new Session(connection);
		String dsql=this.createDataSql();
		List<Map<String,Object>> list= session.selectList(dsql);
		
	    Vector<Map<String,Object>> tree=createTreeNodes(list, this.root);
		
		
	    Session.close(connection, null, null);
	    
            Utils.writeJson(this.response,tree);
	}
	
	public void combo(){
		
	}
	
	
	
	protected Vector<Map<String,Object>> createTreeNodes(List<Map<String,Object>> list,String root) {
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
		this.response.getWriter().write(sbr.toString());
	}

	
	
	
}
