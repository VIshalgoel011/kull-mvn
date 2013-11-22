package com.kull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;


import com.kull.script.*;

import com.kull.easyui.EasyUiPlugin;
import com.kull.easyui.TreeNodeJSONObject;
import com.kull.easyui.TreeNodeJSONObject.ITreeNodeable;
import com.kull.easyui.TreeNodeJSONObject.Tree;






public class EasyUiUtil {

    private EasyUiUtil(){};
	
   
	
	
	public static enum Editor{
		text,hidden,textarea,checkbox,numberbox,validatebox,datebox,combobox,combotree,combo,toyzCombogrid
		,datetimebox,numberspinner,combogrid
	}
	
	public static enum Messager{
		show,confirm,alert,prompt
	}
	
	public static enum Datagrid{
		total,rows
	}
	
	public static enum Region{
		north,center,south,west,east
	}
	
	public static enum DatagridView{
		groupview,detailview
	}
	
	public static enum IconCls{
		ok,add,remove,save,cut,no,cancel,reload,search,print,help,undo,redo,back
	}
	
	
	public static enum EditorAttr{
		type,options
	}
	
	
	
	public static JSONObject createDatagrid(List list){
		JSONArray jarr=new JSONArray(list);
		return createDatagrid(jarr);
	}
	
	public static JSONObject createDatagrid(List list,int pIntTotal){
		JSONArray jarr=new JSONArray(list);
		return createDatagrid(jarr,pIntTotal);
	}
	
	public static JSONObject createDatagrid(JSONArray lArrRows)
	{
         return createDatagrid(lArrRows, lArrRows.length());		
	}
	
	public static JSONObject createDatagrid(JSONArray lArrRows,int pIntTotal)
	{
		JSONObject lJsonDataGrid=new JSONObject();
		try {
			lJsonDataGrid.put(Datagrid.total.name(), pIntTotal);
			lJsonDataGrid.put(Datagrid.rows.name(), lArrRows);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lJsonDataGrid;
		
	}
	
	public static JSONArray createTree (List list,Object root,String idField,String textField,String iconClsField,String parentIdField,Set<String> attrFields) throws Exception{
	     JSONArray jarr=new JSONArray();
	     for(Object obj: list){
	    	 if(obj==null)continue;
	    	 JSONArray tempJarr=new JSONArray();
	    	 Object parentId=ObjectHelper.attr(obj, parentIdField);
	    	 if(ObjectHelper.isNotEquals(parentId, root))continue;
	    	 Object id=ObjectHelper.attr(obj, idField);
	    	 String text=ObjectHelper.attr(obj, textField);
	    	 String iconCls=ObjectHelper.attr(obj, iconClsField);
	    	 JSONObject attrs=null;//ObjectHelper.toJsonInclude(obj, attrFields);
	    	 JSONObject tempJson=new JSONObject();
	    	 tempJson.put(Tree.id.name(), id);
	    	 tempJson.put(Tree.text.name(), text);
	    	 tempJson.put(Tree.iconCls.name(), iconCls);
	    	 tempJson.put(Tree.attributes.name(), attrs);
	    	 tempJson.put(Tree.children.name(), createTree(list, id, idField, textField, iconClsField, parentIdField, attrFields));
	    	 jarr.put(tempJson);
	     }
	     return jarr;
	}
	
	public static <T> List<TreeNodeJSONObject> toNodes(List<T> list,ITreeNodeable<T>... nodeables ){
		List<TreeNodeJSONObject> treenodes=new ArrayList<TreeNodeJSONObject>();
		return treenodes;
	}
	
	
	public static JsMap createTextColumn(String field){
		return createTextColumn(field, false);
	}
	
	public static JsMap createTextColumn(Enum en){
		return createTextColumn(en.name(), true);
	}
	
	public static JsMap createTextColumn(String field,boolean isStr){
		JsMap column=new JsMap();
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD, field,isStr);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.WIDTH, 200);
		JsMap editor=createTextEditor(null);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.EDITOR,editor);
		return column;
	}
	
	public static JsMap createTextareaColumn(String field){
		return createTextareaColumn(field, false);
	}
	
	public static JsMap createTextareaColumn(Enum en){
		return createTextareaColumn(en.name(), true);
	}
	
	public static JsMap createTextareaColumn(String field,boolean isStr){
		JsMap column=new JsMap();
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD, field,isStr);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.WIDTH, 200);
		JsMap editor=createTextareaEditor(false,null);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.EDITOR,editor);
		return column;
	}
	
	public static JsMap createIntColumn(String field){
		return createIntColumn(field,false);
	}
	
	public static JsMap createIntColumn(Enum en){
		return createIntColumn(en.name(), true);
	}
	
	public static JsMap createIntColumn(String field,boolean isStr){
		JsMap column=new JsMap();
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD, field,isStr);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.WIDTH, 200);
		JsMap editor=createIntEditor(true, null);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.EDITOR,editor);
		return column;
	}
	
	public static JsMap createDoubleColumn(String field){
		return createDoubleColumn(field, false);
	}
	
	public static JsMap createDoubleColumn(Enum en){
		return createDoubleColumn(en.name(), true);
	}
	
	public static JsMap createDoubleColumn(String field,boolean isStr){
		JsMap column=new JsMap();
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD, field,isStr);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.WIDTH, 200);
		JsMap editor=createDoubleEditor(true, null);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.EDITOR,editor);
		return column;
	}
	
	public static JsMap createDateTimeColumn(String field){
		return createDateTimeColumn(field, false);
	}
	
	public static JsMap createDateTimeColumn(Enum en){
		return createDateTimeColumn(en.name(), true);
	}
	
	public static JsMap createDateTimeColumn(String field,boolean isStr){
		JsMap column=new JsMap();
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD, field,isStr);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.WIDTH, 150);
		JsMap editor=createDateboxEditor(true, null);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.EDITOR,editor);
		return column;	
	}
	
	public static JsMap createDateColumn(String field){
		return createDateColumn(field, false);
	}
	
	public static JsMap createDateColumn(Enum en){
		return createDateColumn(en.name(), true);
	}
	
	public static JsMap createDateColumn(String field,boolean isStr){
		JsMap column=new JsMap();
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD, field,isStr);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.WIDTH, 100);
		JsMap editor=createDateboxEditor(true, null);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.EDITOR,editor);
		return column;	
	}
	
	public static JsMap createCheckBoxColumn(String field){
		return createCheckBoxColumn(field, false);
	}
	
	public static JsMap createCheckBoxColumn(Enum en){
		return createCheckBoxColumn(en.name(), true);
	}
	
	public static JsMap createCheckBoxColumn(String field,boolean isStr){
		JsMap column=new JsMap();
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD, field,isStr);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.CHECKBOX, true);
		//column.put(EasyUiPlugin.DataGrid.ColumnProperties.FORMATTER, "dateboxFormatter");
		return column;	
	}
	
	public static JsMap createOperaColumn(){
		JsMap column=new JsMap();
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.TITLE,"操作",true);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.WIDTH,60);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD,"xx",true);
		column.put(EasyUiPlugin.DataGrid.ColumnProperties.FORMATTER,"operaFormatter");
		return column;
	}
	
	public static JsMap createHiddenEditor(){
		JsMap editor=new JsMap();
		editor.put(EasyUiUtil.EditorAttr.type, Editor.hidden.name(),true);
		return editor;
	}
	
	public static JsMap createTextEditor(JsMap opts){
		JsMap editor=new JsMap();
		editor.put(EasyUiUtil.EditorAttr.type, Editor.text.name(),true);
		
		return editor;
	}
	
	public static JsMap createTextareaEditor(boolean required,JsMap opts){
		JsMap editor=new JsMap();
		editor.put(EasyUiUtil.EditorAttr.type, Editor.textarea.name(),true);
		opts=opts==null?new JsMap():opts;
		opts.put(EasyUiPlugin.ValidateBox.Properties.REQUIRED,required);
		editor.put(EasyUiUtil.EditorAttr.options, opts);
		return editor;
	}
	
	public static JsMap createValidateboxEditor(boolean required,JsMap opts){
		JsMap editor=new JsMap();
		editor.put(EasyUiUtil.EditorAttr.type, Editor.validatebox.name(),true);
		opts=opts==null?new JsMap():opts;
		if(!opts.containsKey(EasyUiPlugin.ValidateBox.Properties.REQUIRED)){
			opts.put(EasyUiPlugin.ValidateBox.Properties.REQUIRED, required);
		}
		editor.put(EasyUiUtil.EditorAttr.options, opts);
		return editor;
	}
	
	public static JsMap createIntEditor(boolean required,JsMap opts){
		JsMap editor=new JsMap();
		editor.put(EasyUiUtil.EditorAttr.type,Editor.numberbox.name(),true);
		opts=opts==null?new JsMap():opts;
		if(!opts.containsKey(EasyUiPlugin.NumberBox.Properties.PRECISION)){
			opts.put(EasyUiPlugin.NumberBox.Properties.PRECISION, 0);
		}
		if(!opts.containsKey(EasyUiPlugin.Spinner.Properties.INCREMENT)){
			opts.put(EasyUiPlugin.Spinner.Properties.INCREMENT, 10);
		}
		if(!opts.containsKey(EasyUiPlugin.NumberBox.Properties.REQUIRED)){
			opts.put(EasyUiPlugin.NumberBox.Properties.REQUIRED, required);
		}
		editor.put(EasyUiUtil.EditorAttr.options, opts);
		return editor;
	}
	
	public static JsMap createDoubleEditor(boolean required,JsMap opts){
		JsMap editor=new JsMap();
		editor.put(EasyUiUtil.EditorAttr.type,Editor.numberbox.name(),true);
		opts=opts==null?new JsMap():opts;
		if(!opts.containsKey(EasyUiPlugin.NumberBox.Properties.PRECISION)){
			opts.put(EasyUiPlugin.NumberBox.Properties.PRECISION, 2);
		}
		if(!opts.containsKey(EasyUiPlugin.Spinner.Properties.INCREMENT)){
			opts.put(EasyUiPlugin.Spinner.Properties.INCREMENT, 10);
		}
		if(!opts.containsKey(EasyUiPlugin.NumberBox.Properties.REQUIRED)){
			opts.put(EasyUiPlugin.NumberBox.Properties.REQUIRED, required);
		}
		editor.put(EasyUiUtil.EditorAttr.options, opts);
		return editor;
	}
	
	public static JsMap createComboboxEditor(boolean required,JsMap opts){
		JsMap editor=new JsMap();
		editor.put(EasyUiUtil.EditorAttr.type,Editor.combobox.name(),true);
		opts=opts==null?new JsMap():opts;
		if(!opts.containsKey(EasyUiPlugin.ComboBox.Properties.REQUIRED)){
			opts.put(EasyUiPlugin.ComboBox.Properties.REQUIRED,required);
		}
		if(!opts.containsKey(EasyUiPlugin.ComboBox.Properties.VALUE_FIELD)){
			opts.put(EasyUiPlugin.ComboBox.Properties.VALUE_FIELD,"Id",true);
		}
		if(!opts.containsKey(EasyUiPlugin.ComboBox.Properties.TEXT_FIELD)){
			opts.put(EasyUiPlugin.ComboBox.Properties.TEXT_FIELD,"Name",true);
		}
		
		editor.put(EasyUiUtil.EditorAttr.options, opts);
		return editor;
	}
	
	public static JsMap createComboTreeEditor(boolean required,JsMap opts){
		JsMap editor=new JsMap();
		editor.put(EasyUiUtil.EditorAttr.type,Editor.combotree.name(),true);
		opts=opts==null?new JsMap():opts;
		if(!opts.containsKey(EasyUiPlugin.ComboTree.Properties.REQUIRED)){
			opts.put(EasyUiPlugin.ComboTree.Properties.REQUIRED,required);
		}
		if(!opts.containsKey(EasyUiPlugin.ComboBox.Properties.VALUE_FIELD)){
			opts.put(EasyUiPlugin.ComboBox.Properties.VALUE_FIELD,"Id",true);
		}
		if(!opts.containsKey(EasyUiPlugin.ComboBox.Properties.TEXT_FIELD)){
			opts.put(EasyUiPlugin.ComboBox.Properties.TEXT_FIELD,"Name",true);
		}
		editor.put(EasyUiUtil.EditorAttr.options, opts);
		return editor;
	}
	
	public static JsMap createCheckboxEditor(){
		JsMap editor=new JsMap();
		editor.put(EasyUiUtil.EditorAttr.type,Editor.checkbox.name(),true);
		//JsMap opts=new JsMap();
		//opts.put(EasyUiPlugin.NumberBox.Properties.PRECISION, 2);
		//editor.put(EasyUiUtil.EditorAttr.options, opts);
		return editor;
	}
	
	public static JsMap createDateboxEditor(boolean required,JsMap opts){
		JsMap editor=new JsMap();
		editor.put(EasyUiUtil.EditorAttr.type,Editor.datebox.name(),true);
		opts=opts==null?new JsMap():opts;
		if(!opts.containsKey(EasyUiPlugin.ValidateBox.Properties.REQUIRED)){
			opts.put(EasyUiPlugin.ValidateBox.Properties.REQUIRED,required);
		}
		editor.put(EasyUiUtil.EditorAttr.options, opts);
		return editor;
	}
	
	/*
	public static Map<String, String> genDatagridColumns(Class cls){
		Map<String, String> cols=new HashMap<String,String>();
		String colPattern=colPattern="var col{0} = '{' title:\"{0}\" , field:\"{0}\", width:{1},editor:'{'type:\"{2}\",options:'{' {3} '}' '}' '}' ; ";
		Set<Field> lArrField= ObjectHelper.getAllDeclaredFields(cls);
	
		EasyuiDatagrid easyuiDatagrid=null; //ObjectHelper.getAnnotation(cls, EasyuiDatagrid.class);
		for(Field field : lArrField){
			Type type=field.getType();
			if(String.class.equals(type)){
				if(easyuiDatagrid==null){
				   cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),200,Editor.validatebox.name(),"required:false,missingMessage:\"\",invalidMessage:\"\""));
				}else{
				  if(LinqUtil.isIn(field.getName(), easyuiDatagrid.inds())){
						String colIndPattern=colPattern="var col{0} = '{' title:\"{0}\" , field:\"{0}\", width:{1},editor:GetIndEditor() '}' ; ";
					  cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),200,Editor.combobox.name(),""));
				  }else if(LinqUtil.isIn(field.getName(), easyuiDatagrid.combos())){
					   cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),200,Editor.combo.name(),"editable:true,multiple:false"));

				  }else if(LinqUtil.isIn(field.getName(), easyuiDatagrid.comboboxs())){
					   cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),200,Editor.combobox.name(),"url:\"\",textField:\"\",valueField:\"\",editable:false,multiple:false"));

				  }else if(LinqUtil.isIn(field.getName(), easyuiDatagrid.combogrids())){
					   cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),200,Editor.combogrid.name(),"url:\"\",textField:\"\",valueField:\"\",editable:false,multiple:false"));

				  }else if(LinqUtil.isIn(field.getName(), easyuiDatagrid.combotrees())){
					   cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),200,Editor.combotree.name(),"url:\"\",textField:\"\",valueField:\"\",editable:false,multiple:false"));

				  }else if(LinqUtil.isIn(field.getName(), easyuiDatagrid.toyzCombogrids())){
					   cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),200,Editor.toyzCombogrid.name(),"required:false,missingMessage:\"\",invalidMessage:\"\""));

				  }else if(LinqUtil.isIn(field.getName(), easyuiDatagrid.textareas())){
					   cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),200,Editor.textarea.name(),""));

				  }else if(LinqUtil.isIn(field.getName(), easyuiDatagrid.emails())){
					   cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),200,Editor.validatebox.name(),"required:false,missingMessage:\"\",invalidMessage:\"\",validType:\"email\""));

				  }else if(LinqUtil.isIn(field.getName(), easyuiDatagrid.urls())){
					   cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),200,Editor.validatebox.name(),"required:false,missingMessage:\"\",invalidMessage:\"\",validType:\"url\""));

				  }
				  else{
					   cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),200,Editor.validatebox.name(),"required:false,missingMessage:\"\",invalidMessage:\"\""));

				  }
				}
			}else if(Integer.class.equals(type)){
				cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),100,Editor.numberspinner.name(),"increment:1,precision:0"));
			}else if(Double.class.equals(type)||Float.class.equals(type)){
				cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),100,Editor.numberspinner.name(),"increment:1,precision:2"));
			}
			else if(Date.class.equals(type)){
				cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),120,Editor.datebox.name(),""));
			}
			else if(Timestamp.class.equals(type)){
				cols.put(field.getName(), MessageFormat.format(colPattern, field.getName(),120,Editor.datetimebox.name(),""));
			}
		}
		return cols;
	}
	
	public static JsContext genDatagirdContext(Class cls) throws Exception{
		EasyuiDatagrid easyuiDatagrid=ObjectHelper.getAnnotation(cls, EasyuiDatagrid.class);
		return genDatagirdContext(cls, easyuiDatagrid.id());
	}
	
	public static JsContext genDatagirdContext(Class cls,String id){
		id=id==null?"Id":id;
		JsContext JsContext=new JsContext();
		Map<String, String> cols=genDatagridColumns(cls);
		String colNames="";
		for(Iterator<String> it=cols.keySet().iterator();it.hasNext();){
			String key=it.next();
			colNames+=MessageFormat.format("col{0},", key);
			JsContext.appendScript(cols.get(key));
		}
		String nameSpace=cls.getPackage().getName();
		String modelName=cls.getSimpleName();
		String urlPrefix=nameSpace.substring(nameSpace.lastIndexOf(".")+1,nameSpace.length())+"/"
		+modelName.substring(0,modelName.lastIndexOf("Model"));
		JsContext
		.appendScript(
	    "\n var editors{0}=[ {1} ] ; \n"
	    +"var opts{0} = '{' '}'; \n"
	    +"opts{0}[\"regexp\"] = \"#table{0}\"; \n"
	    +"opts{0}[\"id\"] = \"{2}\"; \n"
	    +"opts{0}[\"urlUpdate\"] =\"{3}/do/update.action\" ; \n"
	    +"opts{0}[\"urlCreate\"]=\"{3}/do/create.action\" ; \n"
	    +"opts{0}[\"urlDelete\"]=\"{3}/do/deletePks.action\"; \n"
	    +"opts{0}[\"urlExportExcel\"]=\"{3}/excel/list.action\"; \n"
	    +"opts{0}[\"urlQuery\"]=\"{3}/datagrid/query.action\"; \n"
	    +"opts{0}[\"editors\"] = editors{0}; \n\n"
	    +"var crud{0} = new CrudDatagrid(opts{0}); \n"
	    +"var toolbar{0} = crud{0}.getToolbar(); \n"
        +"var editRow{0} = crud{0}.getEditRowHandler(); \n\n"
        +"$(document).ready(function()'{' \n"
        +"$(\"#table{0}\").datagrid('{' \n url:\"{3}/datagrid/list.action\" \n , idField:\"{2}\" \n , columns:[[{1}]] \n , toolbar:toolbar{0} \n, onDblClickRow:editRow{0} \n '}') ; //$(\"#table{0}\").datagrid \n"
        +"'}'); \n"
       
	    , cls.getSimpleName()  //0
		, StringUtil.trim(colNames, ",")  //1
		,id  //2
		,urlPrefix  //3
		);
		
		;
		return JsContext;
	}
	
	*/

	
}
