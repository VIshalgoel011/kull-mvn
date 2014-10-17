package com.kull.web.struts.easyui;

import com.kull.easyui.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.plaf.basic.BasicComboBoxUI.ComboBoxLayoutManager;


import org.json.*;

import com.kull.script.*;
import com.kull.util.IScript;




public class EasyUiPlugin implements IScript{

	private String object;
	private String uiName;
	private String method="";
	private StringBuffer parma=new StringBuffer("");
	private boolean isMethod;
	private JsMap<String, Object> attrs=new JsMap<String, Object>();
	
	
	public EasyUiPlugin(String object,String uiName)
	{
		this.object=object;
		this.uiName=uiName;
		this.isMethod=false;
	}
	
	public EasyUiPlugin(String object,String uiName,String method,String... parmas)
	{
		this.object=object;
		this.uiName=uiName;
		this.method=method;
		if(parmas!=null)
		{
			for(int i=0;i<parmas.length;i++)
			{
				parma.append(",").append(parmas[i]);
			}
		}
		this.isMethod=true;
	}
	
	



	public void setObject(String object) {
		this.object = object;
	}



	public String getUiName() {
		return uiName;
	}



	public void setUiName(String uiName) {
		this.uiName = uiName;
	}



	public String getMethod() {
		return method;
	}



	public void setMethod(String method) {
		this.method = method;
	}
	
	
	

	public EasyUiPlugin appendAttrs(String key,Object value)
	{
       return this.appendAttrs(key, value, false);
	}
	
	public EasyUiPlugin appendAttrs(String key,Object value,boolean isStr)
	{	
        this.attrs.put(key, isStr?"\""+value+"\"":value);
		return this;
	}
	
	public String toScirpt() {
		// TODO Auto-generated method stub
		String lStrReturn="";
		String lScriptRe="";
		if(this.isMethod)
		{
		    lScriptRe="{0}.{1}(\"{2}\" {3} \n);    //   end:{0}.{1}";
		    String parmas="";
			lStrReturn=MessageFormat.format(lScriptRe,
					                       this.object,  //0
			                               this.uiName,  //1	      
			                               this.method,   //2
			                               this.parma.toString() //3
			);
		}
		else
		{
		    lScriptRe="{0}.{1}({2} \n);    //   end:{0}.{1}";
			lStrReturn=MessageFormat.format(lScriptRe,
					                       this.object,  //0
			                               this.uiName,  //1	      
			                               this.attrs.toScirpt()   //2
			);
		}
		return lStrReturn;
	}
	
	
	
	
	

	
	
	public static final String ICON_ADD="icon-add";
	public static final String ICON_REMOVE="icon-remove";
	public static final String ICON_SAVE="icon-save";
	public static final String ICON_CUT="icon-cut";
	public static final String ICON_OK="icon-ok";
	public static final String ICON_NO="icon-no";
	public static final String ICON_CANCEL="icon-cancel";
	public static final String ICON_RELOAD="icon-reload";
	public static final String ICON_SEARCH="icon-search";
	public static final String ICON_PRINT="icon-print";
	public static final String ICON_HELP="icon-help";
	public static final String ICON_UNDO="icon-undo";
	public static final String ICON_REDO="icon-redo";
	public static final String ICON_BACK="icon-back";
	
	
	
//layout
public static final class Panel{
		
		public static final String Name="panel";
		public static final String CLASS="easyui-panel";
		public static  class Properties{
         public static final String TITLE="title";
		 public static final String TITLE(String value){return toHtmlKeyValue(TITLE, value);}
         public static final String ICON_CLS="iconCls";
		 public static final String ICON_CLS(String value){return toHtmlKeyValue(ICON_CLS, value);}
         public static final String WIDTH="width";
		 public static final String WIDTH(int value){return toHtmlKeyValue(WIDTH, value);}
         public static final String HEIGHT="height";
		 public static final String HEIGHT(int value){return toHtmlKeyValue(HEIGHT, value);}
         public static final String LEFT="left";
		 public static final String LEFT(int value){return toHtmlKeyValue(LEFT, value);}
         public static final String TOP="top";
		 public static final String TOP(int value){return toHtmlKeyValue(TOP, value);}
         public static final String CLS="cls";
		 public static final String CLS(String value){return toHtmlKeyValue(CLS, value);}
         public static final String HEADER_CLS="headerCls";
		 public static final String HEADER_CLS(String value){return toHtmlKeyValue(HEADER_CLS, value);}
         public static final String BODY_CLS="bodyCls";
		 public static final String BODY_CLS(String value){return toHtmlKeyValue(BODY_CLS, value);}
         public static final String STYLE="style";
		 public static final String STYLE(String value){return toHtmlKeyValue(STYLE, value);}
         public static final String FIT="fit";
		 public static final String FIT(boolean value){return toHtmlKeyValue(FIT, value);}
         public static final String BORDER="border";
		 public static final String BORDER(boolean value){return toHtmlKeyValue(BORDER, value);}
         public static final String DO_SIZE="doSize";
		 public static final String DO_SIZE(boolean value){return toHtmlKeyValue(DO_SIZE, value);}
         public static final String NO_HEADER="noheadr";
		 public static final String NO_HEADER(boolean value){return toHtmlKeyValue(NO_HEADER, value);}
         public static final String CONTENT="content";
		 public static final String CONTENT(String value){return toHtmlKeyValue(CONTENT, value);}
         public static final String COLLAPSIBLE="collapsible";
		 public static final String COLLAPSIBLE(boolean value){return toHtmlKeyValue(COLLAPSIBLE, value);}
         public static final String MINIMIZABLE="minimizable";
		 public static final String MINIMIZABLE(boolean value){return toHtmlKeyValue(MINIMIZABLE, value);}
         public static final String MAXIMIZABLE="maximizable";
		 public static final String MAXIMIZABLE(boolean value){return toHtmlKeyValue(MAXIMIZABLE, value);}
         public static final String CLOSABLE="closable";
		 public static final String CLOSABLE(boolean value){return toHtmlKeyValue(CLOSABLE, value);}
         public static final String TOOLS="tools";
		 public static final String TOOLS(String value){return toHtmlKeyValue(TOOLS, value);}
         public static final String COLLAPSED="collapsed";
		 public static final String COLLAPSED(boolean value){return toHtmlKeyValue(COLLAPSED, value);}
         public static final String MINIMIZED="minimized";
		 public static final String MINIMIZED(boolean value){return toHtmlKeyValue(MINIMIZED, value);}
         public static final String MAXIMIZED="maximized";
		 public static final String MAXIMIZED(boolean value){return toHtmlKeyValue(MAXIMIZED, value);}
         public static final String CLOSED="closed";
		 public static final String CLOSED(boolean value){return toHtmlKeyValue(CLOSED, value);}
         public static final String HREF="href";
		 public static final String HREF(String value){return toHtmlKeyValue(HREF, value);}
         public static final String CACHE="cache";
		 public static final String CACHE(boolean value){return toHtmlKeyValue(CACHE, value);}
         public static final String LOADING_MESSAGE="loadingMessage";
		 public static final String LOADING_MESSAGE(String value){return toHtmlKeyValue(LOADING_MESSAGE, value);}
		}
		public static  class Events{
			public static final String ON_LOAD="onLoad";
			public static final String ON_BEFORE_OPEN="onBeforeOpen";
			public static final String ON_OPEN="onOpen";
			public static final String ON_BEFORE_CLOSE="onBeforeClose";
			public static final String ON_CLOSE="onClose";
			public static final String ON_BEFORE_DESTROY="onBeforeDestroy";
			public static final String ON_DESTROY="onDestroy";
			public static final String ON_BEFORE_COLLAPSE="onBeforeCollapse";
			public static final String ON_COLLAPSE="onCollapse";
			public static final String ON_BEFORE_EXPAND="onBeforeExpand";
			public static final String ON_EXPAND="onExpand";
			public static final String ON_RESIZE="onResize";
			public static final String ON_MOVE="onMove";
			public static final String ON_MAXIMIZE="onMaximize";
			public static final String ON_RESTORE="onRestore";
			public static final String ON_MINIMIZE="onMinimize";
		}
		public static  class Methods{
			public static final String OPTIONS="options";
			public static final String PANEL="panel";
			public static final String HEADER="header";
			public static final String BODY="body";
			public static final String SET_TITLE="setTitle";
			public static final String OPEN="open";
			public static final String CLOSE="close";
			public static final String DESTROY="destroy";
			public static final String REFRESH="refresh";
			public static final String RESIZE="resize";
			public static final String MOVE="move";
			public static final String MAXIMIZE="maximize";
			public static final String MINIMIZE="minimize";
			public static final String RESTORE="restore";
			public static final String COLLAPSE="collapse";
			public static final String EXPAND="expand";
		}
		
	}

   public static final class Layout{
	   public static final String NAME="layout";
	   public static final String CLASS="easyui-layout";
	   
	   public static  class Properties {
		   public static final String TITLE="title";
		   public static final String TITLE(String value){return toHtmlKeyValue(TITLE, value);}
		   public static final String REGION="region";
		   public static final String REGION(String value){return toHtmlKeyValue(REGION, value);}
		   public static final String BORDER="border";
		   public static final String BORDER(boolean value){return toHtmlKeyValue(BORDER, value);}
		   public static final String SPLIT="split";
		   public static final String SPLIT(boolean value){return toHtmlKeyValue(SPLIT, value);}
		   public static final String ICON_CLS="iconCls";
		   public static final String ICON_CLS(String value){return toHtmlKeyValue(ICON_CLS, value);}
		   public static final String HREF="href";
		   public static final String HREF(String value){return toHtmlKeyValue(HREF, value);}
	   }

	   public static class Methods{
			public static final String DIALOG="resize";
			public static final String PANEL="panel";
			public static final String COLAPSE="collapse";
			public static final String EXPAND="expand";
		}
   }
   
   public static final class Tabs{
	   public static final String NAME="tabs";
	   public static final String CLASS="easyui-tabs";
	   
	   public static  class Properties {
		   public static final String WIDTH="width";
		   public static final String WIDTH(int value){return toHtmlKeyValue(WIDTH, value);}
		   public static final String HEIGHT="height";
		   public static final String HEIGHT(int value){return toHtmlKeyValue(HEIGHT, value);}
		   public static final String PLAIN="plain";
		   public static final String PLAIN(boolean value){return toHtmlKeyValue(PLAIN, value);}
		   public static final String FIT="fit";
		   public static final String FIT(boolean value){return toHtmlKeyValue(FIT, value);}
		   public static final String BORDER="border";
		   public static final String BORDER(boolean value){return toHtmlKeyValue(BORDER, value);}
		   public static final String SCROLL_INCREMENT="scrollIncrement";
		   public static final String SCROLL_INCREMENT(int value){return toHtmlKeyValue(SCROLL_INCREMENT, value);}
		   public static final String SCROLL_DURATION="scrollDuration";
		   public static final String SCROLL_DURATION(int value){return toHtmlKeyValue(SCROLL_DURATION, value);}
		   public static final String TOOLS="tools";
		   public static final String TOOLS(String value){return toHtmlKeyValue(TOOLS, value);}

		   
	   }
	   
	   public static  class TabProperties extends EasyUiPlugin.Panel.Properties {
		   public static final String SELECTED="selected";
		   public static final String SELECTED(boolean value){return toHtmlKeyValue(SELECTED, value);}

	   }
	   
	   public static class Events{
		   public static final String ON_LOAD="onLoad";
		   public static final String ON_SELECT="onSelect";
		   public static final String ON_BEFORE_CLOSE="onBeforeClose";
		   public static final String ON_CLOSE="onClose";
		   public static final String ON_ADD="onAdd";
		   public static final String ON_UPDATE="onUpdate";
		   public static final String ON_CONTEXT_MENU="onContextMenu";

	   }

	   public static class Methods{
			public static final String OPTIONS="options";
			public static final String TABS="tabs";
			public static final String RESIZE="resize";
			public static final String ADD="add";
			public static final String CLOSE="close";
			public static final String GET_TAB="getTab";
			public static final String GET_SELECTED="getSelected";
			public static final String SELECT="select";
			public static final String EXISTS="exists";
			public static final String UPDATE="update";


		}
   }



//layout  end
	

//windows
public static final  class Window 
{
	public static final String NAME="window";
	public static final String CLASS="easyui-window";
	public static  class Properties extends Panel.Properties{
		public static final String Z_INDEX="zIndex";
		public static final String Z_INDEX(int value){return toHtmlKeyValue(Z_INDEX, value);}
		public static final String DRAGGABLE="draggable";
		public static final String DRAGGABLE(boolean value){return toHtmlKeyValue(DRAGGABLE, value);}
		public static final String RESIEZABLE="resizable";
		public static final String RESIEZABLE(boolean value){return toHtmlKeyValue(RESIEZABLE, value);}
		public static final String SHADOW="shadow";
		public static final String SHADOW(boolean value){return toHtmlKeyValue(SHADOW, value);}
		public static final String INLINE="inline";
		public static final String INLINE(boolean value){return toHtmlKeyValue(INLINE, value);}
		public static final String MODAL="modal";
		public static final String MODAL(boolean value){return toHtmlKeyValue(MODAL, value);}
	}
	public static  class Events extends Panel.Events{
	
	}
	public static  class Methods extends Panel.Methods{
		public static final String WINDOW="window";
	}
}



public static final class Dialog 
{
	public static final String NAME="dialog";
	public static final class Properties extends Window.Properties{
	}
	public static final class Events extends Window.Events{
		
	}
	public static final class Methods extends Window.Methods{
		public static final String DIALOG="dialog";

	}
}


//windows end
	

//menu and button

   public static final class LinkButton
   {
		public static final String NAME="linkbutton";
		public static final String CLASS="easyui-linkbutton";

		public static final class Properties{
			public static final String ID="id";
			public static final String ID(String value){return toHtmlKeyValue(ID, value);}
			public static final String DISABLED="disabled";
			public static final String DISABLED(boolean value){return toHtmlKeyValue(DISABLED, value);}
			public static final String PLAIN="plain";
			public static final String PLAIN(boolean value){return toHtmlKeyValue(PLAIN, value);}
			public static final String TEXT="text";
			public static final String TEXT(String value){return toHtmlKeyValue(TEXT, value);}
			public static final String ICON_CLS="iconCls";
			public static final String ICON_CLS(String value){return toHtmlKeyValue(ICON_CLS, value);}
		}

		public static final class Methods{
			public static final String ICON_CLS="options";
		    public static final String DISABLE="disable";
		    public static final String ENABLE="enable";
			
		}
		
   }


//menu and button end
	public static final class DataGrid{
		public static final String NAME="datagrid";
		public static final class Properties{
			public static final String TITLE="title";
			public static final String ICON_CLS="iconCls";
			public static final String BORDER="border";
			public static final String WIDTH="width";
			public static final String HEIGHT="height";
			public static final String COLUMNS="columns";
			public static final String FROZEN_COLUMNS="frozenColumns";
			public static final String STRIPED="striped";
			public static final String METHOD="method";
			public static final String NOWRAP="nowrap";
			public static final String ID_FIELD="idField";
			public static final String URL="url";
			public static final String LOAD_MSG="loadMsg";
			public static final String PAGINATION="pagination";
			public static final String ROWNUNMBERS="rownumbers";
			public static final String SINGLE_SELECT="singleSelect";
			public static final String FIT="fit";
			public static final String FIT_COLUMNS="fitColumns";
			public static final String PAGE_NUMBER="pageNumber";
			public static final String PAGE_SIZE="pageSize";
			public static final String PAGE_LIST="pageList";
			public static final String QUERY_PARAMS="queryParams";
			public static final String SORT_NAME="sortName";
			public static final String SORT_ORDER="sortOrder";
			public static final String TOOLBAR="toolbar";
			public static final String VIEW="view";
			public static final String GROUP_FIELD="groupField";
			public static final String GROUP_FORMATTER="groupFormatter";
			public static final String DETAIL_FORMATTER="detailFormatter";

		}
		
		public static final class ToolbarProperties{
			public static final String TEXT="text";
			public static final String ICON_CLS="iconCls";
			public static final String HANDLER="handler";
		}
		
		public static final class ColumnProperties{
			public static final String TITLE="title";
			public static final String FIELD="field";
			public static final String WIDTH="width";
			public static final String ROWSPAN="rowspan";
			public static final String COLSPAN="colspan";
			public static final String ALIGN="align";
			public static final String SORTABLE="sortable";
			public static final String CHECKBOX="checkbox";
			public static final String STYLER="styler";
			public static final String FORMATTER="formatter";
			public static final String SORTER="sorter";
			public static final String EDITOR="editor";
			
		}
		public static final class Events{
			public static final String ON_DBL_CLICK_ROW="onDblClickRow";
			public static final String ON_LOAD_SUCCESS="onLoadSuccess";
			public static final String ON_LOAD_ERROR="onLoadError";
			public static final String ON_CLICK_ROW="onClickRow";
			public static final String ON_SORT_COLUMN="onSortColumn";
			public static final String ON_SELECT="onSelect";
			public static final String ON_UNSELECT="onUnselect";
		}
		
		public static final class Methods{
			public static final String LOAD_DATA="loadData";
			public static final String OPTIONS="options";
			public static final String RESIZE="resize";
			public static final String RELOAD="reload";
			public static final String FIX_COLUMN_SIZE="fixColumnSize";
			public static final String GET_SELECTED="getSelected";
			public static final String GET_SELECTIONS="getSelections";
			public static final String CLEAR_SELECTIONS="clearSelections";
			public static final String SELECT_ROW="selectRow";
			public static final String SELECT_RECORD="selectRecord";
			public static final String UNSELECT_ROW="unselectRow";
		}
	}
	
	

	
	public static final class Tree
	{
		public static final String NAME="tree";
		public static final class Properties{
		}
		public static final class Events{
			
		}
		public static final class Methods{
			
		}
	}
	
	
	public static final class TreeGrid
	{
		public static final String NAME="treegrid";
		public static final class Properties{
		}
		public static final class Events{
			
		}
		public static final class Methods{
			
		}
	}
	
	//form
	public static final class Form
	{
		public static final String NAME="form";
		public static final class Properties{
			public static final String URL="url";
		}
		public static final class Events{
			public static final String ON_SUBMIT="onSubmit";
			public static final String SUCCESS="success";
			public static final String ON_LOAD_SUCCESS="onLoadSuccess";
			public static final String ON_LOAD_ERROR="onLoadError";
		}
		public static final class Methods{
			public static final String SUBMIT="submit";
			public static final String LOAD="load";
			public static final String CLEAR="clear";
			public static final String VALIDATE="validate";
		}
	}
	
	
	public static final class DateBox
	{
		public static final String NAME="datebox";
		public static final String CLASS="easyui-datebox";
		public static final class Properties{
			public static final String PANEL_WIDTH="panelWidth";
			public static final String PANEL_WIDTH(int value){return toHtmlKeyValue(PANEL_WIDTH, value);}
			public static final String PANEL_HEIGHT="panelHeight";
			public static final String PANEL_HEIGHT(int value){return toHtmlKeyValue(PANEL_HEIGHT, value);}
			public static final String CURRENT_TEXT="currentText";
			public static final String CURRENT_TEXT(String value){return toHtmlKeyValue(CURRENT_TEXT, value);}
			public static final String CLOSE_TEXT="closeText";
			public static final String CLOSE_TEXT(String value){return toHtmlKeyValue(CLOSE_TEXT, value);}
			public static final String OK_TEXT="okText";
			public static final String OK_TEXT(String value){return toHtmlKeyValue(OK_TEXT, value);}
			public static final String DISABLED="disabled";
			public static final String DISABLED(boolean value){return toHtmlKeyValue(DISABLED, value);}
			public static final String FORMATTER="formatter";
			public static final String FORMATTER(String value){return toHtmlKeyValue(FORMATTER, value);}
			public static final String PARSER="parser";
			public static final String PARSER(String value){return toHtmlKeyValue(PARSER, value);}

		}
		public static final class Events{
			public static final String ON_SELECT="onSelect";
		}
		public static final class Method{
			public static final String OPTIONS="options";
			public static final String CALENDAR="calendar";
			public static final String SET_VALUE="setValue";
		}
	}
	
	public static final class ValidateBox
	{
		public static final String NAME="validatebox";
		public static final String CLASS="easyui-validatebox";
		public static class Properties{
			public static final String REQUIRED="required";
			public static final String REQUIRED(boolean value){return toHtmlKeyValue(REQUIRED, value);}
			public static final String VALID_TYPE="validType";
			public static final String VALID_TYPE(String value){return toHtmlKeyValue(VALID_TYPE, value);}
			public static final String MISSING_MESSAGE="missingMessage";
			public static final String MISSING_MESSAGE(String value){return toHtmlKeyValue(MISSING_MESSAGE, value);}
			public static final String INVALID_MESSAGE="invalidMessage";
			public static final String INVALID_MESSAGE(String value){return toHtmlKeyValue(INVALID_MESSAGE, value);}
		}

		public static class Methods{
			public static final String DESTROY="destroy";
			public static final String VALIDATE="validate";
			public static final String IS_VALID="isValid";
		}
	}
	
	public static final class Combo
	{
		public static final String NAME="combo";
		public static final String CLASS="easyui-combo";
		public static class Properties extends ValidateBox.Properties{
			public static final String WIDTH="width";
			public static final String WIDTH(int value){return toHtmlKeyValue(WIDTH, value);}
			public static final String PANEL_WIDTH="panelWidth";
			public static final String PANEL_WIDTH(int value){return toHtmlKeyValue(PANEL_WIDTH, value);}
			public static final String PANEL_HEIGHT="panelHeight";
			public static final String PANEL_HEIGHT(int value){return toHtmlKeyValue(PANEL_HEIGHT, value);}
			public static final String MULTIPLE="multiple";
			public static final String MULTIPLE(boolean value){return toHtmlKeyValue(MULTIPLE, value);}
			public static final String SEPARATOR="separator";
			public static final String SEPARATOR(String value){return toHtmlKeyValue(SEPARATOR, value);}
			public static final String EDITABLE="editable";
			public static final String EDITABLE(boolean value){return toHtmlKeyValue(EDITABLE, value);}
			public static final String DISABLED="disabled";
			public static final String DISABLED(boolean value){return toHtmlKeyValue(DISABLED, value);}
			public static final String HAS_DOWN_ARROW="hasDownArrow";
			public static final String HAS_DOWN_ARROW(boolean value){return toHtmlKeyValue(HAS_DOWN_ARROW, value);}
			public static final String VALUE="value";
			public static final String VALUE(String value){return toHtmlKeyValue(VALUE, value);}
			public static final String KEY_HENDLER="keyHandler";
			public static final String KEY_HENDLER(String value){return toHtmlKeyValue(KEY_HENDLER, value);}
		}
		
		public static class Events{
			public static final String ON_SHOW_PANEL="onShowPanel";
			public static final String ON_HIDE_PANEL="onHidePanel";
			public static final String ON_CHANGE="onChange";
		}

		public static class Methods extends ValidateBox.Methods{
			public static final String OPTIONS="options";
			public static final String PANEL="panel";
			public static final String TEXTBOX="textbox";
			public static final String RESIZE="resize";
			public static final String SHOW_PANEL="showPanel";
			public static final String HIDE_PANEL="hidePanel";
			public static final String DISABLE="disable";
			public static final String ENABLE="enable";
			public static final String CLEAR="clear";
			public static final String GET_TEXT="getText";
			public static final String SET_TEXT="setText";
			public static final String GET_VALUE="getValue";
			public static final String SET_VALUE="setValue";
			public static final String GET_VALUES="getValues";
			public static final String SET_VALUES="setValues";
		}
	}
	
	public static final class ComboBox
	{
		public static final String NAME="combobox";
		public static final String CLASS="easyui-combobox";
		public static class Properties extends Combo.Properties{
			public static final String VALUE_FIELD="valueField";
			public static final String VALUE_FIELD(String value){return toHtmlKeyValue(VALUE_FIELD, value);};
			public static final String TEXT_FIELD="textField";
			public static final String TEXT_FIELD(String value){return toHtmlKeyValue(TEXT_FIELD, value);};
			public static final String MODE="mode";
			public static final String MODE(boolean value){return toHtmlKeyValue(MODE, value);};
			public static final String URL="url";
			public static final String URL(String value){return toHtmlKeyValue(URL, value);};
			public static final String DATA="data";
			public static final String DATA(String value){return toHtmlKeyValue(DATA, value);};
			public static final String FILTER="filter";
			public static final String FILTER(String value){return toHtmlKeyValue(FILTER, value);};
			public static final String FORMATTER="formatter";
			public static final String FORMATTER(String value){return toHtmlKeyValue(FORMATTER, value);};
		}

		public static class Methods extends Combo.Methods{
			public static final String GET_DATA="getData";
			public static final String LOAD_DATA="loadData";
			public static final String RELOAD="reload";
			public static final String CLEAR="clear";
			public static final String SELECT="select";
			public static final String UNSELECT="unselect";
		}
		
		public static class Events extends Combo.Events{

			
			public static final String ON_LOAD_SUCCESS="onLoadSuccess";
			public static final String ON_LOAD_ERROR="onLoadError";
			public static final String ON_SELECT="onSelect";
			public static final String ON_UNSELECT="onUnselect";
		}
	}
	
	public static final class ComboTree{
		public static final String NAME="combobox";
		public static final String CLASS="easyui-combotree";
		public static class Properties extends Combo.Properties{
			public static final String EDITABLE="editable";
		}
		
		public static class Events extends Combo.Events{}
	}
	
	
	public static final class ComboGrid{
		public static final String NAME="combogrid";
		public static final String CLASS="easyui-combogrid";
		public static class Properties{
			public static final String LOAD_MSG="loadMsg";
			public static final String LOAD_MSG(String value){return toHtmlKeyValue(LOAD_MSG, value);};
			public static final String TEXT_FIELD="textField";
			public static final String TEXT_FIELD(String value){return toHtmlKeyValue(TEXT_FIELD, value);};
			public static final String ID_FIELD="idField";
			public static final String ID_FIELD(String value){return toHtmlKeyValue(ID_FIELD, value);};
			public static final String MODE="mode";
			public static final String MODE(String value){return toHtmlKeyValue(MODE, value);};
			public static final String FILTER="filter";
			public static final String FILTER(String value){return toHtmlKeyValue(FILTER, value);};
		}
		
		public static class Methods{
			public static final String OPTIONS="options";
			public static final String GRID="grid";
			public static final String SET_VALUES="setValues";
			public static final String SET_VALUE="setValue";
			public static final String CLEAR="clear";
		}
	}

	
	public static final class NumberBox{
		
		public static final String NAME="numberbox";
		public static final String CLASS="easyui-numberbox";
		
		public static class Properties extends ValidateBox.Properties{
			public static final String DISABLED="disabled";
			public static final String DISABLED(boolean value){return toHtmlKeyValue(DISABLED, value);};
			public static final String MIN="min";
			public static final String MIN(int value){return toHtmlKeyValue(MIN, value);};
			public static final String MAX="max";
			public static final String MAX(int value){return toHtmlKeyValue(MAX, value);};
			public static final String PRECISION="precision";
			public static final String PRECISION(int value){return toHtmlKeyValue(PRECISION, value);};
		}
		
		public static class Methods extends ValidateBox.Methods{
			public static final String DISABLE="disable";
			public static final String ENABLE="enable";
			public static final String FIX="fix";
		}
	}
	
	
	public static final class Spinner{
		
		public static final String NAME="spinner";
		public static final String CLASS="easyui-spinner";		
		public static class Properties extends ValidateBox.Properties{
			public static final String WIDTH="width";
			public static final String WIDTH(int value){return toHtmlKeyValue(WIDTH, value);};
			public static final String VALUE="value";
			public static final String VALUE(String value){return toHtmlKeyValue(VALUE, value);};
			public static final String MAX="max";
			public static final String MAX(double value){return toHtmlKeyValue(MAX, value);};
			public static final String MIN="min";
			public static final String MIN(double value){return toHtmlKeyValue(MIN, value);};
			public static final String INCREMENT="increment";
			public static final String INCREMENT(double value){return toHtmlKeyValue(INCREMENT, value);};
			public static final String EDITABLE="editable";
			public static final String EDITABLE(boolean value){return toHtmlKeyValue(EDITABLE, value);};
			public static final String DISABLED="disabled";
			public static final String DISABLED(boolean value){return toHtmlKeyValue(DISABLED, value);};
			public static final String SPIN="spin";
			public static final String SPIN(String value){return toHtmlKeyValue(SPIN, value);};
		}
		
		public static class Events{
			public static final String ON_SPIN_UP="onSpinUp";
			public static final String ON_SPIN_DOWN="onSpinDown";
		}
		
		public static class Methods extends ValidateBox.Methods{
			public static final String OPTIONS="options";
			public static final String RESIZE="resize";
			public static final String ENABLE="enable";
			public static final String DISABLE="disable";
			public static final String GET_VALUE="getValue";
			public static final String SET_VALUE="setValue";
			public static final String CLEAR="clear";
		}
	}
	
	public static final class NumberSpinner{
		
		public static final String NAME="numberspinner";
		public static final String CLASS="easyui-numberspinner";
	}
	
	private static String toHtmlKeyValue(String key,Object value){
		return key+"=\""+value+"\"";
	}
	
	
	
	
	
	
//end form
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws JSONException
	{
        EasyUiPlugin weekPbDG=new EasyUiPlugin("#weekPbDG",EasyUiPlugin.DataGrid.NAME);
        weekPbDG.appendAttrs(EasyUiPlugin.DataGrid.Properties.TITLE,"周排班管理",true)
        .appendAttrs(EasyUiPlugin.DataGrid.Properties.URL,"booked/WeekPb/list/DataGrid.action?format=json")
        .appendAttrs(EasyUiPlugin.DataGrid.Properties.FIT_COLUMNS,true)
        .appendAttrs(EasyUiPlugin.DataGrid.Properties.WIDTH,"innerWidth")
        .appendAttrs(EasyUiPlugin.DataGrid.Properties.HEIGHT,"innerHeight")
        .appendAttrs(EasyUiPlugin.DataGrid.Properties.PAGINATION,true)
        .appendAttrs(EasyUiPlugin.DataGrid.Properties.ID_FIELD,"id")
        .appendAttrs(EasyUiPlugin.DataGrid.Events.ON_DBL_CLICK_ROW,new JsFunction(new String[]{"index,data"}).appendContext("document.location.href=\"booked/WeekPb/to/assign.action?weekid=\"+data.id;"));
        List weekPbColumns=new JsList();
        List weekPbR1=new JsList();
        JsMap<String,Object> weekPbR1C1=new JsMap<String, Object>();
        weekPbR1C1.put(EasyUiPlugin.DataGrid.ColumnProperties.TITLE,"编号",true);
        weekPbR1C1.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD,"id",true);
        weekPbR1C1.put(EasyUiPlugin.DataGrid.ColumnProperties.CHECKBOX,true);
        JsMap<String,Object> weekPbR1C2=new JsMap<String, Object>();
        weekPbR1C2.put(EasyUiPlugin.DataGrid.ColumnProperties.TITLE,"第几周",true);
        weekPbR1C2.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD,"IWeekNum",true);
        weekPbR1C2.put(EasyUiPlugin.DataGrid.ColumnProperties.WIDTH,10);
        JsMap<String,Object> weekPbR1C3=new JsMap<String, Object>();
        weekPbR1C3.put(EasyUiPlugin.DataGrid.ColumnProperties.TITLE,"日期范围",true);
        weekPbR1C3.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD,"CWeekRange",true);
        weekPbR1C3.put(EasyUiPlugin.DataGrid.ColumnProperties.WIDTH,10);
        JsMap<String,Object> weekPbR1C4=new JsMap<String, Object>();
        weekPbR1C4.put(EasyUiPlugin.DataGrid.ColumnProperties.TITLE,"周排班管理",true);
        weekPbR1C4.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD,"CCheckOperator",true);
        weekPbR1C4.put(EasyUiPlugin.DataGrid.ColumnProperties.WIDTH,30);
        JsMap<String,Object> weekPbR1C5=new JsMap<String, Object>();
        weekPbR1C5.put(EasyUiPlugin.DataGrid.ColumnProperties.TITLE,"审核人",true);
        weekPbR1C5.put(EasyUiPlugin.DataGrid.ColumnProperties.FIELD,"IChecked",true);
        weekPbR1C5.put(EasyUiPlugin.DataGrid.ColumnProperties.WIDTH,30);              
                 
        weekPbR1.add(weekPbR1C1);
        weekPbR1.add(weekPbR1C2);
        weekPbR1.add(weekPbR1C3);
        weekPbR1.add(weekPbR1C4);
        weekPbR1.add(weekPbR1C5);
        weekPbColumns.add(weekPbR1);
        weekPbDG.appendAttrs(EasyUiPlugin.DataGrid.Properties.COLUMNS,weekPbColumns);
        System.out.println(weekPbDG.toScirpt());
        System.out.println(EasyUiPlugin.Dialog.Events.ON_LOAD);
        
	}
	
}
