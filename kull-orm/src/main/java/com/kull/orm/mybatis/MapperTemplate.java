package com.kull.orm.mybatis;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;




import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentType;
import org.dom4j.io.SAXReader;






import com.kull.*;
import com.kull.orm.Column;
import com.kull.orm.dialect.Dialect;

import com.kull.util.IModel;
import com.kull.util.Resultable;



public  class MapperTemplate  {

	
	protected Dialect dialect;
	
	private boolean isOverWriterExtra=false;
	
	private String baseModelPrefix="";
	
	public void setOverWriterExtra(boolean isOverWriterExtra) {
		this.isOverWriterExtra = isOverWriterExtra;
	}


	
	public enum Name{
		model,query
	}
	
	public enum Tag{
		trim,set,foreach,sql,otherwise,when,where,choose,select,update,delete
	}
	
	public static Class[] RANAGE_CLASS={Integer.class,Double.class,Float.class,Date.class,Timestamp.class};
	
	
	protected  Map<Class,List<ColumnTemplate>> mapJavaCols=new HashMap<Class, List<ColumnTemplate>>();
	

    public enum SqlId{
    	selectCondition,selectViewCondition,orderby,updateCols,insertCols,queryCols,columns,table
    	,pk,joinCols,join
    }
	
	
	protected static String XML_UTF8="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
	protected static String DOCTYPE_MYBATIS3="<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">";
	protected static String TAG_TRIM_CONDITIOM_START="<trim prefix=\"where 1=2 or (\" suffix=\")\" prefixOverrides=\"and|or\" suffixOverrides=\"and|or\">";
	protected static String TAG_TRIM_COMMA_START="<trim  prefixOverrides=\",\" suffixOverrides=\",\">";
	protected static String TAG_TRIM_UNION_START="<trim  prefixOverrides=\"union\" suffixOverrides=\"union\">";
	protected static String TAG_TRIM_END="</trim>";
	protected static String TAG_IF_END="</if>";
	protected static String TAG_FOREACH_END="</foreach>";
	protected static String TAG_SQL_END="</sql>";
	
	protected static String TAG_OTHERWISE_START="<otherwise>";
	protected static String TAG_CHOOSE_START="<choose>";
	protected static String TAG_OTHERWISE_END="</otherwise>";
	protected static String TAG_WHEN_END="</when>";
	protected static String TAG_WHERE_START="<where>";
	protected static String TAG_WHERE_END="</where>";
	protected static String TAG_CHOOSE_END="</choose>";
	protected static String TAG_SELECT_END="</select>";
	protected static String TAG_UPDATE_END="</update>";
	protected static String TAG_INSERT_END="</insert>";
	protected static String TAG_DELETE_END="</delete>";

	protected static String NAME_COLUMN=IMapper.Params.colunm.name();
    protected static String NAME_MODEL=IMapper.Params.model.name();
    protected static String NAME_MODELS=IMapper.Params.models.name();
    protected static String NAME_CONDITIDON=IMapper.Params.condition.name();
    protected static String NAME_CONDITIDONS=IMapper.Params.conditions.name();
    protected static String NAME_MAIN_TABLE="main";
    protected static String NAME_EXTRA_TABLE="extra";
    public static String NAME_PK=IMapper.Params.pk.name();
    public static String NAME_PKS=IMapper.Params.pks.name();
    public static String NAME_DYNAMIC=IMapper.Params.dynamic.name();
    public static String TAG_ID_RESULT_MAP="resultMap";
    protected static String TAG_ID_RESULT_VIEW="resultView";
    protected static String TAG_ID_SELECT=IMapper.Methods.select.name();
    protected static String TAG_ID_SELECT_GROUPBY=IMapper.Methods.selectGroupby.name();
    protected static String TAG_ID_SELECT_DISTINCT=IMapper.Methods.selectDistinct.name();
    protected static String TAG_ID_SELECT_UNION=IMapper.Methods.selectUnion.name();
    protected static String TAG_ID_SELECT_EQ_PK=IMapper.Methods.selectEqPk.name();
    protected static String TAG_ID_SELECT_ALL=IMapper.Methods.selectAll.name();
    protected static String TAG_ID_COUNT=IMapper.Methods.count.name();
    protected static String TAG_ID_SUM=IMapper.Methods.sum.name();
    protected static String TAG_ID_MAX=IMapper.Methods.max.name();
    protected static String TAG_ID_MIN=IMapper.Methods.min.name();
    protected static String TAG_ID_AVG=IMapper.Methods.avg.name();
    protected static String TAG_ID_COUNT_ALL=IMapper.Methods.countAll.name();
    protected static String TAG_ID_SELECT_VIEW=IMapper.Methods.selectView.name();
    protected static String TAG_ID_SELECT_VIEW_UNION=IMapper.Methods.selectViewUnion.name();
    protected static String TAG_ID_SELECT_VIEW_EQ_PK=IMapper.Methods.selectViewEqPk.name();
    protected static String TAG_ID_SELECT_VIEW_ALL=IMapper.Methods.selectViewAll.name();
    protected static String TAG_ID_UPDATE=IMapper.Methods.update.name();
    protected static String TAG_ID_INSERT=IMapper.Methods.insert.name();
    protected static String TAG_ID_DELETE_EQ_PK=IMapper.Methods.deleteEqPk.name();
    protected static String TAG_ID_DELETE_IN_PK=IMapper.Methods.deleteInPk.name();
    
    protected static String TAG_START(Tag tag){
    	return "<"+tag.name()+">";
    }
    
    protected static String TAG_END(Tag tag){
    	return "</"+tag.name()+">";
    }
    
    protected static String TAG_INCLUDE(Enum id){
    	return TAG_INCLUDE(id.name());
    }
	protected static String TAG_INCLUDE(String id){
		return "<include refid=\""+id+"\"/>";
	}
	protected static String TAG_IF_START(String regexp){
		return "<if test=\""+regexp+"\" >";
	}
	
	protected static String TAG_WHEN_START(String regexp){
		return "<when test=\""+regexp+"\" >";
	}
	
	
	
	
	protected String table;
	protected String pk;
	protected ColumnTemplate pkColumn;
	protected ArrayList<ColumnTemplate> cols=new ArrayList<ColumnTemplate>();
	protected Class namespace;
	protected Class modelClass;
	private String idGener=null;
	
	public static String proCheckPattern(String prefix){
		//return NAME_MODEL+".getClass().getDeclaredFields().'{'?#this.getName() eq ''"+prefix+"{0}'' '}'.size() gt 0";
		//return  NAME_MODEL+".getClass().getDeclaredFields().'{'?#this.getName() eq ''"+prefix+"{0}'' '}'.size() eq 1";
        //return "1=2 or ";
		if(LinqHelper.isIn(prefix,"isnull","isnotnull")){
		  return "1 eq 1 and "+NAME_CONDITIDON+"[''"+prefix+"{0}''] neq null and "+NAME_CONDITIDON+"[''"+prefix+"{0}''] eq true";	
		}else if(LinqHelper.isIn(prefix,"isin","isnotin")){
			  return "1 eq 1 and "+NAME_CONDITIDON+"[''"+prefix+"{0}''] neq null and  "+NAME_CONDITIDON+"[''"+prefix+"{0}''] neq null and "+NAME_CONDITIDON+"[''"+prefix+"{0}''].size>0";	
			}
		else{
		return "1 eq 1 and "+NAME_CONDITIDON+"[''"+prefix+"{0}''] neq null";
		}
	}
	
	protected String eqPattern="<if test=\" "+proCheckPattern("")+"\">and "+NAME_MAIN_TABLE+".{1} = #'{'"+NAME_CONDITIDON+".{0} '}' </if> ";
	protected String eqPattern2="<if test=\" "+proCheckPattern("eq")+"\">and "+NAME_MAIN_TABLE+".{1} = #'{'"+NAME_CONDITIDON+".eq{0} '}' </if> ";
	protected String neqPattern="<if test=\" "+proCheckPattern("neq")+"\">and "+NAME_MAIN_TABLE+".{1} != #'{'"+NAME_CONDITIDON+".neq{0} '}' </if> ";
	protected String likeStrPattern="<if test=\" "+proCheckPattern("like")+"\">and "+NAME_MAIN_TABLE+".{1} like #'{'"+NAME_CONDITIDON+".like{0}'}' </if> ";
	protected String notlikePattern="<if test=\" "+proCheckPattern("notlike")+"\">and "+NAME_MAIN_TABLE+".{1} not like #'{'"+NAME_CONDITIDON+".notlike{0}  '}' </if> ";
	protected String isinPattern="<if test=\" "+proCheckPattern("isin")+"\">and "+NAME_MAIN_TABLE+".{1} in (<foreach collection=\""+NAME_CONDITIDON+".isin{0}\" item=\"sub\"  separator=\",\" > #'{'sub '}' </foreach> )</if> ";
	protected String isnotinPattern="<if test=\" "+proCheckPattern("isnotin")+"\">and "+NAME_MAIN_TABLE+".{1} not in (<foreach collection=\""+NAME_CONDITIDON+".isnotin{0}\" item=\"sub\"  separator=\",\" > #'{'sub '}' </foreach> )</if> ";
	protected String isnullPattern="<if test=\" "+proCheckPattern("isnull")+"\">and "+NAME_MAIN_TABLE+".{1} is null  </if> ";
	protected String isnotnullPattern="<if test=\" "+proCheckPattern("isnotnull")+"\">and "+NAME_MAIN_TABLE+".{1} is not null </if> ";
	protected String minPattern="<if test=\" "+proCheckPattern("min")+"\">and "+NAME_MAIN_TABLE+".{1} &gt;= #'{'"+NAME_CONDITIDON+".min{0} '}' </if> ";
	protected String maxPattern="<if test=\" "+proCheckPattern("max")+"\">and "+NAME_MAIN_TABLE+".{1} &lt;= #'{'"+NAME_CONDITIDON+".max{0}'}' </if> ";
	protected String eqDatePattern="<if test=\" "+proCheckPattern("")+"\">and to_char"+NAME_MAIN_TABLE+"m.{1},''{4}'') = to_char(#'{'"+NAME_CONDITIDON+".{0} '}',''{4}'') </if> ";
	protected String neqDatePattern="<if test=\" "+proCheckPattern("neq")+"\">and to_char("+NAME_MAIN_TABLE+".{1},''{4}'') != to_char(#'{'"+NAME_CONDITIDON+".neq{0} '}',''{4}'') </if> ";
	//protected String minDatePattern="<if test=\" "+proCheckPattern("min")+"\">and to_char("+NAME_MAIN_TABLE+".{1},''{4}'') &gt;= to_char(#'{'"+NAME_CONDITIDON+".min{0} '}',''{4}'') </if> ";
	//protected String maxDatePattern="<if test=\" "+proCheckPattern("max")+"\">and to_char("+NAME_MAIN_TABLE+".{1},''{4}'') &lt;= to_char(#'{'"+NAME_CONDITIDON+".max{0} '}',''{4}'') </if> ";
	protected String isinDate(ColumnTemplate columnTemplate){
	  StringBuffer context=new StringBuffer("");
	  context
	  .append(TAG_IF_START("1 eq 1 and "+NAME_CONDITIDON+"['isin"+columnTemplate.getJavaName()+"'] neq null and  "+NAME_CONDITIDON+"['isin"+columnTemplate.getJavaName()+"'] neq null and "+NAME_CONDITIDON+"['isin"+columnTemplate.getJavaName()+"'].size>0"))
	  .append("and "+NAME_MAIN_TABLE+"."+columnTemplate.getDbColName()+" in" )
	  .append("<foreach collection=\""+NAME_CONDITIDON+".isin"+columnTemplate.getJavaName()+"\" item=\"sub\" open=\"(\" separator=\",\" close=\")\">")
	  .append(dialect.methodStrToDate("#{sub}"))
	  .append(TAG_FOREACH_END)
	  .append(TAG_IF_END)
	  ;
	  return context.toString();
	}
	
	protected String isnotinDate(ColumnTemplate columnTemplate){
		  StringBuffer context=new StringBuffer("");
		  context
		  .append(TAG_IF_START("1 eq 1 and "+NAME_CONDITIDON+"['isnotin"+columnTemplate.getJavaName()+"'] neq null and  "+NAME_CONDITIDON+"['isnotin"+columnTemplate.getJavaName()+"'] neq null and "+NAME_CONDITIDON+"['isnotin"+columnTemplate.getJavaName()+"'].size>0"))
		  .append("and "+NAME_MAIN_TABLE+"."+columnTemplate.getDbColName()+" not in" )
		  .append("<foreach collection=\""+NAME_CONDITIDON+".isnotin"+columnTemplate.getJavaName()+"\" item=\"sub\" open=\"(\" separator=\",\" close=\")\">")
		  .append(dialect.methodStrToDate("#{sub}"))
		  .append(TAG_FOREACH_END)
		  .append(TAG_IF_END)
		  ;
		  return context.toString();
		}
	
	
	public void setNamespace(Class namespace) {
		
		this.namespace = namespace;
	}

	public void setModelName(Class modelClass) {
		this.modelClass = modelClass;
	}

    

	private int rowCount=10;
	
	public MapperTemplate(Dialect dialect,String table,String pk,ArrayList<String> dbCols,ArrayList<String> dbTypes,ArrayList<Integer> precisions){
	    this.dialect=dialect;
		this.table=table;
		this.pk=pk;
        for (int i = 0; i < dbCols.size(); i++) {
        	ColumnTemplate ct=new ColumnTemplate(dbCols.get(i), dbTypes.get(i),precisions.get(i));
			this.cols.add(ct);
			if(dbCols.get(i).equalsIgnoreCase(pk))
			{
				this.pkColumn=ct;
			}
			if(!this.mapJavaCols.containsKey(ct.getColumn().getJavaType())){
			   	this.mapJavaCols.put(ct.getColumn().getJavaType(), new ArrayList<ColumnTemplate>());
			}
			this.mapJavaCols.get(ct.getColumn().getJavaType()).add(ct);
		}
       
	}
	
	public MapperTemplate(Dialect dialect,String table,String pk,ArrayList<String> dbCols,ArrayList<String> dbTypes,ArrayList<Integer> precisions,String idGener){
		this.dialect=dialect;
		this.table=table;
		this.pk=pk;
		this.idGener=idGener;
        for (int i = 0; i < dbCols.size(); i++) {
           	ColumnTemplate ct=new ColumnTemplate(dbCols.get(i), dbTypes.get(i),precisions.get(i));
			this.cols.add(ct);
			if(dbCols.get(i).equalsIgnoreCase(pk))
			{
				this.pkColumn=ct;
			}
			if(!this.mapJavaCols.containsKey(ct.getColumn().getJavaType())){
			   	this.mapJavaCols.put(ct.getColumn().getJavaType(), new ArrayList<ColumnTemplate>());
			}
			this.mapJavaCols.get(ct.getColumn().getJavaType()).add(ct);
		}
	}
	
	public String getPkCol(){
		String pattern="{0},jdbcType={1}";
		String pk=MessageFormat.format(pattern, this.pkColumn.getJavaName(),this.pkColumn.getColumn().getJdbcType(),this.pkColumn.getColumn().getJavaType().getSimpleName());
		return pk;
	}
	
   final private String getDbCols(String prefix){
		if(cols==null)return "";
		StringBuffer lSbrReturn=new StringBuffer("");
		int colSize=cols.size();
		for(int i=0;i<colSize;i++)
		{
			String tempCols=cols.get(i).getDbColName();
			lSbrReturn.append(prefix+tempCols);
			if(i!=colSize-1)
			{
				lSbrReturn.append(",");
				if(i%rowCount==0&&i!=0){
				    lSbrReturn.append("\n");
				}
			}

		}
		return lSbrReturn.toString();
	}
	
	public String getPol(ColumnTemplate ct){
       return getPol(ct,"");
	}
	
	public String getPol(ColumnTemplate ct,String prefix){
		prefix=ObjectHelper.<String>parse(prefix,"");
		String pattern="#'{'{3}{0},jdbcType={1}'}'";
		String patternBytes="#'{'{3}{0},jdbcType={1}'}'";
		String tempPattern=pattern;
		if(LinqHelper.isIn(ct.getColumn().getJdbcType(),Column.JDBC_TYPES_BYTES)){
			tempPattern=patternBytes;
		}
		String str=MessageFormat.format(tempPattern
				,ct.getJavaName()  //0
				,ct.getColumn().getJdbcType()  //1
				,ct.getColumn().getJavaType().getSimpleName()  //2
				,prefix
		);
		return str;
	}
	
	public String getInsertCol(ColumnTemplate ct){
		if(idGener!=null && ObjectHelper.isEquals(ct.getDbColName(), pkColumn.getDbColName()))
		{return idGener;}		
		return this.getPol(ct,NAME_MODEL+".");
		
	};
	
	final private String getInsertCols(){
		if(cols==null)return "";
		StringBuffer lSbrReturn=new StringBuffer("");
		int colSize=cols.size();
		//String pattern="#'{'{0},jdbcType={1},javaType={2}'}'";
		for(int i=0;i<colSize;i++){
			ColumnTemplate tempCol=cols.get(i);
			String tempStr=this.getInsertCol(tempCol);
			lSbrReturn.append(tempStr).append(",\n");
		}
		return lSbrReturn.toString();
	}
	
	public String getUpdateCol(ColumnTemplate ct){
		String str="";
		String pattern="{0}={1},";
		if(ct.getDbColName().equalsIgnoreCase(pk)){
			str=MessageFormat.format("<if test=\"pk neq null\">{0}=#'{'pk'}',</if>",pk);
		}else{
		    str=MessageFormat.format(pattern
				,ct.getDbColName()   //0
				,this.getPol(ct,IMapper.Params.model+".")   //1
				,NAME_MAIN_TABLE
		);
		}
		return str;
		
	};
	
   final	public String getUpdateCols(){
		if(cols==null)return "";
		StringBuffer lSbrReturn=new StringBuffer("");
		int colSize=cols.size();
		//String pattern="{0}=#'{'{1},jdbcType={2},javaType={3}'}'";
		for(int i=0;i<colSize;i++){
			ColumnTemplate tempCol=cols.get(i);
			String tempStr=this.getUpdateCol(tempCol);
			lSbrReturn.append(tempStr).append("\n");
		}
		return lSbrReturn.toString();
	}
	

	public String getResultMapPol(ColumnTemplate ct) {
		// TODO Auto-generated method stub
		String str="";
		String patternId="<id column=\"{0}\"  property=\"{1}\" jdbcType=\"{2}\"  />";
		String patternResult="<result column=\"{0}\"  property=\"{1}\" jdbcType=\"{2}\"  />";
		String patternResult4bytes="<result column=\"{0}\"  property=\"{1}\" jdbcType=\"{2}\" />";
		String tempPattern=patternResult;
		if(ct.getDbColName().equalsIgnoreCase(this.pk)){
			tempPattern=patternId;
		}else if(LinqHelper.isIn(ct.getColumn().getJdbcType(),Column.JDBC_TYPES_BYTES )) {
		    tempPattern=patternResult4bytes;
		}
		str=MessageFormat.format(tempPattern,
						ct.getDbColName(),   //0
						ct.getJavaName(),   //1
						ct.getColumn().getJdbcType(),   //2 
						ct.getColumn().getJavaType().getSimpleName()  //3
				  );
		return str;
	}
	
	
  final	private String getResultMapPols(){
		if(cols==null)return "";
		StringBuffer lSbrReturn=new StringBuffer("");
		int colSize=cols.size();

		for(int i=0;i<colSize;i++){
			ColumnTemplate tempCol=cols.get(i);
			if(!tempCol.getDbColName().equalsIgnoreCase(this.pk))continue;
				String tempStr=this.getResultMapPol(tempCol);
				lSbrReturn.append(tempStr).append("\n");
				break;
		}
		
		
		for(int i=0;i<colSize;i++){
			ColumnTemplate tempCol=cols.get(i);
			if(tempCol.getDbColName().equalsIgnoreCase(this.pk))continue;
			String tempStr=this.getResultMapPol(tempCol);
			lSbrReturn.append(tempStr).append("\n");
		}
		return lSbrReturn.toString();
	}
	


	public String getJavaCol(ColumnTemplate ct){
		String pattern="protected {0} {1}; ";
		String clsName=ct.getColumn().getJavaType().getSimpleName();
		if(Byte.class.equals(ct.getColumn().getJavaType())){
			clsName="byte[]";
		}
		String tempStr=MessageFormat.format(pattern,
				clsName,  //0
				ct.getJavaName()  //1
				,ct.getDbColName()  //2
				,ct.getDbColType()  //3
				,ct.getPrecision()  //4
		);
		return tempStr;
	};
	
  final	private String getJavaColsContext(){
		if(cols==null)return "";
		StringBuffer lSbrReturn=new StringBuffer("");
		String pattern="private {0} {1};";
		String bypePattern="private byte[] {1};";
		
		int colSize=cols.size();
		
		for(int i=0;i<colSize;i++){
			ColumnTemplate tempCol=cols.get(i);
            String tempStr=getJavaCol(tempCol);
			lSbrReturn.append(tempStr+"\n");
		}
		/*
		for(Iterator<Class> it=this.mapJavaCols.keySet().iterator();it.hasNext();){
			Class javaType=it.next();
			List<ColumnTemplate> cols=this.mapJavaCols.get(javaType);
			StringBuffer sbrCols=new StringBuffer("");
			String tempPattern=pattern;
			int index=0;
			if(LinqHelper.isInArray(javaType, new Class[]{byte.class,Byte.class})){
				tempPattern=bypePattern;
			}
			for(ColumnTemplate col :cols){
				sbrCols.append(col.getJavaName()).append(",");
				if(++index>3){
					index=0;
					sbrCols.append(StringHelper.ln()).append(StringHelper.tab(4));
				}
			}
			String colsContext=sbrCols.substring(0,sbrCols.lastIndexOf(","));
			lSbrReturn.append(MessageFormat.format( tempPattern,javaType.getSimpleName(),colsContext)).append(StringHelper.ln(2));
		}
		*/
		return lSbrReturn.toString();
	}
  
  final private String getQueryColsContext(){
		if(cols==null)return "";
		StringBuffer sbrReturn=new StringBuffer();
	    Map<String,List<String>> mapQueryCols=new HashMap<String, List<String>>();
        
		for(ColumnTemplate ct : cols){
			if(LinqHelper.isIn(ct.getColumn().getJavaType(),byte.class,Byte.class))continue;

			String listKeyName=Boolean.class.getSimpleName();
			if(!mapQueryCols.containsKey(listKeyName)){
				mapQueryCols.put(listKeyName, new ArrayList<String>());
			}
			mapQueryCols.get(listKeyName).add(MessageFormat.format("isnull{0}",ct.getJavaName() ));
			mapQueryCols.get(listKeyName).add(MessageFormat.format("isnotnull{0}",ct.getJavaName() ));
	
			listKeyName=MessageFormat.format("Set<{0}>", ct.getColumn().getJavaType().getSimpleName());
			if(!mapQueryCols.containsKey(listKeyName)){
				mapQueryCols.put(listKeyName, new ArrayList<String>());
			}
			mapQueryCols.get(listKeyName).add(MessageFormat.format("isin{0}",ct.getJavaName() ));
			mapQueryCols.get(listKeyName).add(MessageFormat.format("isnotin{0}",ct.getJavaName() ));

			listKeyName=ct.getColumn().getJavaType().getSimpleName();
			if(!mapQueryCols.containsKey(listKeyName)){
				mapQueryCols.put(listKeyName, new ArrayList<String>());
			}
			mapQueryCols.get(listKeyName).add(MessageFormat.format("neq{0}",ct.getJavaName() ));
			mapQueryCols.get(listKeyName).add(MessageFormat.format("eq{0}",ct.getJavaName() ));
			if( LinqHelper.isIn(ct.getColumn().getJavaType(), String.class,Character.class)){
				mapQueryCols.get(listKeyName).add(MessageFormat.format("like{0}",ct.getJavaName() ));
				mapQueryCols.get(listKeyName).add(MessageFormat.format("notlike{0}",ct.getJavaName() ));
	            
			}else if(LinqHelper.isIn(ct.getColumn().getJavaType(),Integer.class,Float.class,Double.class,BigDecimal.class,Number.class))
	        {
				mapQueryCols.get(listKeyName).add(MessageFormat.format("min{0}",ct.getJavaName() ));
				mapQueryCols.get(listKeyName).add(MessageFormat.format("max{0}",ct.getJavaName() ));

	        }
			else if(LinqHelper.isIn(ct.getColumn().getJavaType(), Date.class,Timestamp.class)){
				mapQueryCols.get(listKeyName).add(MessageFormat.format("min{0}",ct.getJavaName() ));
				mapQueryCols.get(listKeyName).add(MessageFormat.format("max{0}",ct.getJavaName() ));

	        }

			/*
			StringBuffer pattern= new StringBuffer("");
			pattern
			.append("private Boolean isnull{1},isnotnull{1} ; \n\t")
			.append("private {0} neq{1} ; \n\t")
			.append("private List<{0}> in{1},notin{1} ; \n\t")
			;
					
			if( LinqHelper.isInArray(ct.getJavaType(), new Class[]{String.class,Character.class})){
				pattern
				.append("private {0} like{1},notlike{1} ; \n\t")
				
				;
	            
			}else if(LinqHelper.isInArray(ct.getJavaType(),new Class[]{Integer.class,Float.class,Double.class,BigDecimal.class,Number.class}))
	        {
				pattern
				.append("private {0} min{1},max{1} ; \n\t")
				;
	        }
			else if(LinqHelper.isInArray(ct.getJavaType(), new Class[]{Date.class,Timestamp.class})){
				pattern
				.append("private {0} min{1},max{1} ; \n\t")
				;
	        }
			sbrReturn.append(MessageFormat.format(pattern.toString(), ct.getJavaType().getSimpleName(),ct.getJavaName()));
		*/
		}
		StringBuffer getsetContext=new StringBuffer("");
		for(Iterator<String> it =mapQueryCols.keySet().iterator();it.hasNext();){
			String key=it.next();
			List<String> colNames=mapQueryCols.get(key);
			StringBuffer colsContext=new StringBuffer("") ;
			
			int index=0;
			for(String colName:colNames){
				colsContext.append(colName).append(",");
				if(++index>3){
					index=0;
					colsContext.append(StringHelper.ln()).append(StringHelper.tab(4));
				}
				getsetContext.append(getsetContext(key, colName)).append(StringHelper.ln(2));
			}
			String context=MessageFormat.format("protected {0} {1};",
				key
				,colsContext.substring(0, colsContext.lastIndexOf(","))
					);
			sbrReturn.append(context).append(StringHelper.ln(2));
			//.append(getsetContext.toString()).append(StringHelper.ln(2));
		}
		sbrReturn.append(getsetContext.toString()).append(StringHelper.ln(2));
		return sbrReturn.toString();
  }
	
	public String getEnum(){
		if(cols==null)return "";
		StringBuffer lSbrReturn=new StringBuffer("");
		int colSize=cols.size();
		String pattern="{0}";
		for(int i=0;i<colSize;i++){
			ColumnTemplate tempCol=cols.get(i);
			lSbrReturn.append(tempCol.getJavaName());
			if(i!=colSize-1)
			{
				lSbrReturn.append(",");
				if(i%rowCount==0&&i!=0){
				    lSbrReturn.append("\n");
				}
			}
		}
		return lSbrReturn.toString();
	}
	
	public String getCondition(ColumnTemplate ct){
		StringBuffer sbr=new StringBuffer("");
		sbr
        .append(this.eqPattern).append(StringHelper.ln())
        .append(this.eqPattern2).append(StringHelper.ln())
        .append(this.neqPattern).append(StringHelper.ln())
		.append(this.isnullPattern).append(StringHelper.ln())
		.append(this.isnotnullPattern).append(StringHelper.ln())
        .append(this.isinPattern).append(StringHelper.ln())
		.append(this.isnotinPattern).append(StringHelper.ln())
		.append(this.minPattern).append(StringHelper.ln())
		.append(this.maxPattern).append(StringHelper.ln())
		;
		if( LinqHelper.isIn(ct.getColumn().getJavaType(), String.class,Character.class)){
            sbr
           // .append(this.eqPattern).append(StringHelper.ln())
          //  .append(this.eqPattern2).append(StringHelper.ln())
           // .append(this.neqPattern).append(StringHelper.ln())
		    .append(this.likeStrPattern).append(StringHelper.ln())
		    .append(this.notlikePattern).append(StringHelper.ln())
		    .append(this.isinPattern).append(StringHelper.ln())
		    .append(this.isnotinPattern).append(StringHelper.ln())
            ;
            
		}else if(LinqHelper.isIn(ct.getColumn().getJavaType(),Integer.class,Float.class,Double.class,BigDecimal.class,Number.class))
        {
            sbr
            .append(this.isinPattern).append(StringHelper.ln())
    		.append(this.isnotinPattern).append(StringHelper.ln())
          // .append(this.eqPattern).append(StringHelper.ln())
         //   .append(this.neqPattern).append(StringHelper.ln())
		 //   .append(this.minPattern).append(StringHelper.ln())
		  //  .append(this.maxPattern).append(StringHelper.ln())
            ;
        }
		else if(LinqHelper.isIn(ct.getColumn().getJavaType(), Date.class,Timestamp.class)){
		//	sbr
		//	.append(this.eqDatePattern).append(StringHelper.ln())
		//	.append(this.neqDatePattern).append(StringHelper.ln())
		//	.append(this.minDatePattern).append(StringHelper.ln())
		//	.append(this.maxDatePattern).append(StringHelper.ln())
		//	;
        }
		String context=	MessageFormat.format(sbr.toString(), 
				ct.getJavaName()  //0
				,ct.getDbColName()  //1
				,ct.getColumn().getJdbcType()  //2
				,ct.getColumn().getJavaType().getSimpleName()  //3
				,this.dialect.dateRegexp  //4
	    );
		if(LinqHelper.isIn(ct.getColumn().getJavaType(), Date.class,Timestamp.class)){
			context+=this.isinDate(ct)+"\n"+this.isnotinDate(ct)+"\n";
		}
		return context;
	}
	
	final private String getConditions(){
		if(cols==null)return "";
		StringBuffer lSbrReturn=new StringBuffer("");
		int colSize=cols.size();
		//String pattern="<if test=\"model.{0} neq null\">and m.{1}=#'{'model.{0},jdbcType={2} javaType={3} '}' </if> ";
		//String datePattern="<if test=\"model.{0} neq null\">and to_char(m.{1},{4})= to_char(#'{'model.{0},jdbcType={2} javaType={3} '}',{4}) </if> ";
		for(int i=0;i<colSize;i++){
			ColumnTemplate tempCol=cols.get(i);
			if(LinqHelper.isIn(tempCol.getColumn().getJavaType(),byte.class,Byte.class))continue;
			String tempStr=this.getCondition(tempCol);
			lSbrReturn.append(tempStr).append("\n");
			
		}
		return lSbrReturn.toString();
	}
	
	public String getPropertiesKey(){
		if(cols==null)return "";
		StringBuffer lSbrReturn=new StringBuffer("");
		int colSize=cols.size();
		String pattern="{0}.F.{1}={1}";
		for(int i=0;i<colSize;i++){
			ColumnTemplate tempCol=cols.get(i);
			String tempStr=MessageFormat.format(pattern,
					this.modelClass.getSimpleName()
					,tempCol.getJavaName()  //0
			);
			lSbrReturn.append(tempStr+"\n");
		}
		return lSbrReturn.toString();
	}
	
	
	
	
    public String getExtraMapperContext(){
    	StringBuffer pattern=new StringBuffer("");
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	pattern
    	.append(XML_UTF8).append(StringHelper.ln(1))
        .append("<!--").append(StringHelper.ln(1))
    	.append(StringHelper.tab()).append("Create Date:").append(sdf.format(new Date())).append(StringHelper.ln(1))
        .append(StringHelper.tab()).append("MapperTemplate:{12}").append(StringHelper.ln(1))
       // .append(StringHelper.tab()).append("Model:{8} ").append(StringHelper.ln(3))
       // .append(" public static enum F '{' \n  {11}  \n '}' ").append(StringHelper.ln(2))
       // .append("{10} ").append(StringHelper.ln(2))
       // .append(" public  class SimpleQueryModel extends {8} '{' \n {13} \n '}' ").append(StringHelper.ln())
        .append("--> ").append(StringHelper.ln(2))
        .append(DOCTYPE_MYBATIS3).append(StringHelper.ln())
    	.append("<mapper namespace=\"{0}\">").append(StringHelper.ln(2))
    	.append("<cache flushInterval=\"30000\" readOnly=\"true\"></cache>").append(StringHelper.ln(3))
    	//.append("<sql id=\""+SqlId.table+"\">{1}</sql>").append(StringHelper.ln(2))
    	//.append("<sql id=\""+SqlId.pk+"\">{2}</sql>").append(StringHelper.ln(2))
    	//.append("<sql id=\""+SqlId.columns+"\">\n{3} \n</sql>").append(StringHelper.ln(2))
    	//.append("<sql id=\""+SQL_ID_INSERT_COLS+"\">\n{4}\n</sql>").append(StringHelper.ln(2))
    	//.append("<sql id=\""+SQL_ID_UPDATE_COLS+"\">\n{5}\n</sql>").append(StringHelper.ln(2))
    
       	.append("<!--  ,[name],.....   -->").append(StringHelper.ln(2))
    	.append("<sql id=\""+SqlId.joinCols+"\"></sql>").append(StringHelper.ln(2)) 	
    	.append("<!--  join table on xx=xx.....  别名"+NAME_MAIN_TABLE+" 已被主表使用 ,  -->").append(StringHelper.ln(2))
    	.append("<sql id=\""+SqlId.join+"\"></sql>").append(StringHelper.ln(2))
   	    .append("<resultMap type=\"{8}\" id=\""+TAG_ID_RESULT_VIEW+"\" extends=\""+TAG_ID_RESULT_MAP+"\" ></resultMap>").append(StringHelper.ln(3))
    
   	    
   	    .append("<sql id=\""+SqlId.selectViewCondition+"\">\n\t "+TAG_INCLUDE(SqlId.selectCondition.name())+"  \n</sql>").append(StringHelper.ln(3))
        
        
        
   	    
    	//.append("<resultMap type=\"{8}\" id=\""+TAG_ID_RESULT_MAP+"\">\n {6}\n </resultMap>").append(StringHelper.ln(3))
        //.append("<sql id=\""+SqlId.selectCondition+"\">\n{7}\n</sql>").append(StringHelper.ln(3))
   
      .append(StringHelper.ln(3))
       
        
        .append("</mapper>")
    	;
    	/*
        err(pattern.toString());
        
    	String path=MessageFormat.format("{0}{1}/IMapper.v1.tc", 
    	    FileUtil.ClassPath()
    	    ,FileUtil.ToPathFormat(MapperTemplate.class)
    	);
    	
    	err(path);
    	try {
    		pattern=new StringBuffer();
			pattern.append(new String(FileUtil.toBytes(path)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
    	
    	String lStrMapper=MessageFormat.format(pattern.toString(),
    			this.namespace.getName()  //0
    			,this.table    //1
    			,this.pk         //2
    			,this.getDbCols(NAME_MAIN_TABLE+".")  //3
    			,this.getInsertCols()  //4
    			,this.getUpdateCols()   //5
    			,this.getResultMapPols()   //6
    			,this.getConditions()  //7
    			,this.modelClass.getName() //8
    			,this.getPkCol()  //9
    			,this.getJavaColsContext() //10
    			,this.getEnum()  //11
    			,this.dialect.getClass().getName()  //12
    			,this.getQueryColsContext()  //13
    			,DateTimeHelper.Formatter.DATE_FORMAT_DB.format()  //14
    			,this.getDbCols("") //15
    	);
		return lStrMapper;
	   
   }
    
    
    public String getMapperContext(){
    	StringBuffer pattern=new StringBuffer("");
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	pattern
    	.append(XML_UTF8).append(StringHelper.ln(1))
        .append("<!--").append(StringHelper.ln(1))
    	.append(StringHelper.tab()).append("Create Date:").append(sdf.format(new Date())).append(StringHelper.ln(1))
        .append(StringHelper.tab()).append("MapperTemplate:{12}").append(StringHelper.ln(1))
        .append(StringHelper.tab()).append("Model:{8} ").append(StringHelper.ln(3))
       // .append(" public static enum F '{' \n  {11}  \n '}' ").append(StringHelper.ln(2))
       // .append("{10} ").append(StringHelper.ln(2))
       // .append(" public  class SimpleQueryModel extends {8} '{' \n {13} \n '}' ").append(StringHelper.ln())
        .append("--> ").append(StringHelper.ln(2))
    	.append(DOCTYPE_MYBATIS3).append(StringHelper.ln())
    	.append("<mapper namespace=\"{0}\">").append(StringHelper.ln(2))
    	//.append("<cache flushInterval=\"30000\" readOnly=\"true\"></cache>").append(StringHelper.ln(3))
    	.append("<sql id=\""+SqlId.table+"\">{1}</sql>").append(StringHelper.ln(2))
    	.append("<sql id=\""+SqlId.pk+"\">{2}</sql>").append(StringHelper.ln(2))
    	//.append("<sql id=\""+SqlId.columns+"\">\n{3} \n</sql>").append(StringHelper.ln(2))
    	.append("<sql id=\""+SqlId.columns+"\">\n{3}\n</sql>").append(StringHelper.ln())

    	.append("<sql id=\""+SqlId.queryCols+"\">").append(StringHelper.ln())
    	//.append(StringHelper.tab(1)).append("<trim prefixOverrides=\",\"  suffixOverrides=\",\">").append(StringHelper.ln())
    	.append(StringHelper.tab(2)).append("<choose>").append(StringHelper.ln())
        .append(StringHelper.tab(3)).append("<when test=\""+NAME_DYNAMIC+" eq null or "+NAME_DYNAMIC+"[''"+DynamicQueryBound.Propertys.colums+"''] eq null or "+NAME_DYNAMIC+"[''"+DynamicQueryBound.Propertys.colums+"''].size lt 1 \" >").append(StringHelper.ln())
        .append(StringHelper.tab(4)).append(TAG_INCLUDE(SqlId.columns)).append(StringHelper.ln())
        .append(StringHelper.tab(3)).append("</when>").append(StringHelper.ln())
    	.append(StringHelper.tab(3)).append("<otherwise>").append(StringHelper.ln())
        .append(StringHelper.tab(4)).append("<foreach collection=\""+NAME_DYNAMIC+"."+DynamicQueryBound.Propertys.colums+"\" item=\"col\" separator=\",\"  >").append(StringHelper.ln())
        .append(StringHelper.tab(5)).append(" "+NAME_MAIN_TABLE+".$'{'col.column'}' ").append(StringHelper.ln())
        .append(StringHelper.tab(4)).append("</foreach>").append(StringHelper.ln())
        .append(StringHelper.tab(3)).append("</otherwise>").append(StringHelper.ln())
        //.append(StringHelper.tab(3)).append("<otherwise>").append(StringHelper.ln())
    	//.append(StringHelper.tab(4)).append("{3}").append(StringHelper.ln())
    	//.append(StringHelper.tab(3)).append("</otherwise>").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append("</choose>").append(StringHelper.ln())
    	//.append(StringHelper.tab(1)).append("</trim>").append(StringHelper.ln())
    	.append("</sql>")
    	.append(StringHelper.ln(3))
    	//.append("<sql id=\""+SQL_ID_INSERT_COLS+"\">\n{4}\n</sql>").append(StringHelper.ln(2))
    	//.append("<sql id=\""+SQL_ID_UPDATE_COLS+"\">\n{5}\n</sql>").append(StringHelper.ln(2))
    
    	.append("<sql id=\""+SqlId.orderby+"\">").append(StringHelper.ln())
    	//.append(StringHelper.tab(1)).append("<trim  suffixOverrides=\",\">").append(StringHelper.ln())
    	.append(StringHelper.tab(2)).append("<choose>").append(StringHelper.ln())
    	.append(StringHelper.tab(3)).append("<when test=\""+NAME_DYNAMIC+" eq null or  "+NAME_DYNAMIC+"[''"+DynamicQueryBound.Propertys.orderbyBounds+"''] eq null or "+NAME_DYNAMIC+"[''"+DynamicQueryBound.Propertys.orderbyBounds+"''].size lt 1 \">").append(StringHelper.ln())
    	.append(StringHelper.tab(4)).append("order by  <include refid=\""+SqlId.pk+"\" /> DESC").append(StringHelper.ln())
        .append(StringHelper.tab(3)).append("</when>").append(StringHelper.ln())
    	.append(StringHelper.tab(3)).append("<otherwise >").append(StringHelper.ln())
        .append(StringHelper.tab(4)).append("<foreach collection=\""+NAME_DYNAMIC+"."+DynamicQueryBound.Propertys.orderbyBounds+"\" item=\"ob\" open=\" order by \" separator=\",\" >").append(StringHelper.ln())
        .append(StringHelper.tab(5)).append(" "+NAME_MAIN_TABLE+".$'{'ob."+OrderbyBound.F.column+"'}' $'{'ob."+OrderbyBound.F.order+"'}' ").append(StringHelper.ln())
        .append(StringHelper.tab(4)).append("</foreach>").append(StringHelper.ln())
        .append(StringHelper.tab(3)).append("</otherwise>").append(StringHelper.ln())
        //.append(StringHelper.tab(3)).append("<otherwise>").append(StringHelper.ln())
    	//.append(StringHelper.tab(4)).append("order by  <include refid=\""+SqlId.pk+"\" /> DESC").append(StringHelper.ln())
    	//.append(StringHelper.tab(3)).append("</otherwise>").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append("</choose>").append(StringHelper.ln())
    	//.append(StringHelper.tab(1)).append("</trim>").append(StringHelper.ln())
    	.append("</sql>").append(StringHelper.ln(3))
    	
    	//.append("<sql id=\""+SqlId.joinCols+"\"></sql>").append(StringHelper.ln(2))
    	//.append("<sql id=\""+SqlId.join+"\"></sql>").append(StringHelper.ln(0))
    	//.append("<!-- 别名m已被主表使用  -->").append(StringHelper.ln(2))
    	.append("<resultMap type=\"{8}\" id=\""+TAG_ID_RESULT_MAP+"\">\n {6}\n </resultMap>").append(StringHelper.ln(3))
        .append("<sql id=\""+SqlId.selectCondition+"\">\n{7}\n</sql>").append(StringHelper.ln(3))
   
         .append("<select id=\""+TAG_ID_SELECT+"\" resultType=\"List\" resultMap=\""+TAG_ID_RESULT_MAP+"\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select "+TAG_INCLUDE(SqlId.queryCols)+"  from "+TAG_INCLUDE(SqlId.table)+"  "+NAME_MAIN_TABLE+"   ").append(StringHelper.ln())
        //.append(StringHelper.tab(2)).append(TAG_CHOOSE_START).append(StringHelper.ln())
        //.append(StringHelper.tab(3)).append(TAG_WHEN_START(NAME_CONDITIDON +" neq null")).append(StringHelper.ln())
        //.append(StringHelper.tab(4)).append(TAG_WHERE_START+TAG_INCLUDE(SqlId.queryCols)+TAG_WHERE_END).append(StringHelper.ln())
        //.append(StringHelper.tab(3)).append(TAG_WHEN_END).append(StringHelper.ln())
        //.append(StringHelper.tab(3)).append(TAG_OTHERWISE_START).append(StringHelper.ln())
        //.append(StringHelper.tab(3)).append(TAG_WHERE_START+" 1=2 "+TAG_WHERE_END).append(StringHelper.ln())
        //.append(StringHelper.tab(3)).append(TAG_OTHERWISE_END).append(StringHelper.ln())
        //.append(StringHelper.tab(2)).append(TAG_CHOOSE_END).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_CONDITIOM_START).append(StringHelper.ln())
        .append(StringHelper.tab(3)).append(TAG_INCLUDE(SqlId.selectCondition)).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_END).append(StringHelper.ln())
        .append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        .append("<select id=\""+TAG_ID_SELECT_GROUPBY+"\" resultType=\"List\" resultMap=\""+TAG_ID_RESULT_MAP+"\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select "+TAG_INCLUDE(SqlId.queryCols)+"  from "+TAG_INCLUDE(SqlId.table)+"  "+NAME_MAIN_TABLE+" group by("+NAME_MAIN_TABLE+".$'{'"+NAME_COLUMN+"'}')   ").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        .append("<select id=\""+TAG_ID_SELECT_DISTINCT+"\" resultType=\"object\" >").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select distinct("+NAME_MAIN_TABLE+".$'{'"+NAME_COLUMN+"'}')  from "+TAG_INCLUDE(SqlId.table.name())+"  "+NAME_MAIN_TABLE+"  ").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        .append("<select id=\""+TAG_ID_SELECT_UNION+"\" resultType=\"List\" resultMap=\""+TAG_ID_RESULT_MAP+"\">").append(StringHelper.ln())
        .append("  ").append("select  "+NAME_MAIN_TABLE+".* from (").append(StringHelper.ln())
        //.append(StringHelper.tab(1)).append(TAG_TRIM_UNION_START).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append("<foreach collection=\""+NAME_CONDITIDONS+"\" item=\""+NAME_CONDITIDON+"\" separator=\" union \" >").append(StringHelper.ln())
        .append(StringHelper.tab(3)).append(TAG_IF_START(NAME_CONDITIDON+" neq null")).append(StringHelper.ln())
        .append(StringHelper.tab(4)).append(" select "+TAG_INCLUDE(SqlId.queryCols)+" from "+TAG_INCLUDE(SqlId.table)+"  "+NAME_MAIN_TABLE+" ").append(StringHelper.ln())
        .append(StringHelper.tab(5)).append(TAG_TRIM_CONDITIOM_START).append(StringHelper.ln())
        .append(StringHelper.tab(6)).append(TAG_INCLUDE(SqlId.selectCondition)).append(StringHelper.ln())
        .append(StringHelper.tab(5)).append(TAG_TRIM_END).append(StringHelper.ln())
        .append(StringHelper.tab(3)).append(TAG_IF_END).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_FOREACH_END).append(StringHelper.ln())
        //.append(StringHelper.tab(1)).append(TAG_TRIM_END).append(StringHelper.ln())
        .append("   ) "+NAME_MAIN_TABLE+" ").append(StringHelper.ln())
        .append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
                
        .append("<select id=\""+TAG_ID_SELECT_EQ_PK+"\"  resultMap=\""+TAG_ID_RESULT_MAP+"\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select "+TAG_INCLUDE(SqlId.queryCols)+" from "+TAG_INCLUDE(SqlId.table.name())+" "+NAME_MAIN_TABLE+"  where "+NAME_MAIN_TABLE+"."+TAG_INCLUDE(SqlId.pk)+" = #'{'"+NAME_PK+"'}'").append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        .append("<select id=\""+IMapper.Methods.selectInPk+"\" resultType=\"List\" resultMap=\""+TAG_ID_RESULT_MAP+"\" >").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select "+TAG_INCLUDE(SqlId.queryCols)+" from "+TAG_INCLUDE(SqlId.table.name())+" "+NAME_MAIN_TABLE+"  where "+NAME_MAIN_TABLE+"."+TAG_INCLUDE(SqlId.pk)+" in (").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append("<foreach item=\""+NAME_PK+"\" collection=\""+NAME_PKS+"\"  separator=\",\" > #'{'"+NAME_PK+"'}' </foreach>").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append(")").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
      //  .append("<select id=\"selectNotInPk\" resultType=\"ArrayList\" resultMap=\"resultMap\" >").append(StringHelper.ln())
      //  .append(StringHelper.tab(1)).append("select m.* from <include refid=\"table\" /> m  where m.<include refid=\"pk\" /> not in").append(StringHelper.ln())
      //  .append(StringHelper.tab(2)).append("<foreach item=\"pk\" collection=\"list\" open=\"(\" separator=\",\" close=\")\"> #'{'pk'}' </foreach>").append(StringHelper.ln())
      //  .append(StringHelper.tab(1)).append(TAG_INCLUDE(SQL_ID_SELECT_ORDERBY)).append(StringHelper.ln())
      //  .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        .append("<select  id=\""+TAG_ID_SELECT_ALL+"\"  resultType=\"List\"  resultMap=\""+TAG_ID_RESULT_MAP+"\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select "+TAG_INCLUDE(SqlId.queryCols)+" from  "+TAG_INCLUDE(SqlId.table.name())+" "+NAME_MAIN_TABLE+"  ").append(StringHelper.ln())        
        .append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        .append("<select id=\""+TAG_ID_COUNT_ALL+"\" resultType=\"int\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select count("+NAME_MAIN_TABLE+"."+TAG_INCLUDE(SqlId.pk)+") from "+TAG_INCLUDE(SqlId.table)+" "+NAME_MAIN_TABLE+" ").append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        .append("<select id=\""+TAG_ID_COUNT+"\" resultType=\"int\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select count("+NAME_MAIN_TABLE+"."+TAG_INCLUDE(SqlId.pk)+") from "+TAG_INCLUDE(SqlId.table)+" "+NAME_MAIN_TABLE+" ").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_IF_START(NAME_CONDITIDON+ " neq null")).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_CONDITIOM_START).append(StringHelper.ln())
        .append(StringHelper.tab(3)).append(TAG_INCLUDE(SqlId.selectCondition)).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_END).append(StringHelper.ln())
         .append(StringHelper.tab(2)).append(TAG_IF_END).append(StringHelper.ln())
        //.append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
         .append("<select id=\""+TAG_ID_AVG+"\" resultType=\"int\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select avg("+NAME_MAIN_TABLE+".$'{'"+NAME_COLUMN+"'}') from "+TAG_INCLUDE(SqlId.table.name())+" "+NAME_MAIN_TABLE+" ").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_IF_START(NAME_CONDITIDON+ " neq null")).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_CONDITIOM_START).append(StringHelper.ln())
        .append(StringHelper.tab(3)).append(TAG_INCLUDE(SqlId.selectCondition)).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_END).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_IF_END).append(StringHelper.ln())
        //.append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        .append("<select id=\""+TAG_ID_SUM+"\" resultType=\"int\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select sum("+NAME_MAIN_TABLE+".$'{'"+NAME_COLUMN+"'}') from "+TAG_INCLUDE(SqlId.table.name())+" "+NAME_MAIN_TABLE+" ").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_IF_START(NAME_CONDITIDON+ " neq null")).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_CONDITIOM_START).append(StringHelper.ln())
        .append(StringHelper.tab(3)).append(TAG_INCLUDE(SqlId.selectCondition)).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_END).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_IF_END).append(StringHelper.ln())
        //.append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        .append("<select id=\""+TAG_ID_MIN+"\" resultType=\"int\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select min("+NAME_MAIN_TABLE+".$'{'"+NAME_COLUMN+"'}') from "+TAG_INCLUDE(SqlId.table.name())+" "+NAME_MAIN_TABLE+" ").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_IF_START(NAME_CONDITIDON+ " neq null")).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_CONDITIOM_START).append(StringHelper.ln())
        .append(StringHelper.tab(3)).append(TAG_INCLUDE(SqlId.selectCondition)).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_END).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_IF_END).append(StringHelper.ln())
        //.append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        .append("<select id=\""+TAG_ID_MAX+"\" resultType=\"int\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select max("+NAME_MAIN_TABLE+".$'{'"+NAME_COLUMN+"'}') from "+TAG_INCLUDE(SqlId.table.name())+" "+NAME_MAIN_TABLE+" ").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_IF_START("model neq null")).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_CONDITIOM_START).append(StringHelper.ln())
        .append(StringHelper.tab(3)).append(TAG_INCLUDE(SqlId.selectCondition)).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_END).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_IF_END).append(StringHelper.ln())
        //.append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        .append("<insert id=\""+TAG_ID_INSERT+"\" flushCache=\"true\" >").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("insert into "+TAG_INCLUDE(SqlId.table)+"  ( "+getDbCols("")+" ) values  ").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("<foreach collection=\""+NAME_MODELS+"\" separator=\",\" item=\""+NAME_MODEL+"\">")
        .append(StringHelper.tab(1)).append("(").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_COMMA_START).append(StringHelper.ln())
        //.append(StringHelper.tab(3)).append(TAG_INCLUDE(SQL_ID_INSERT_COLS)).append(StringHelper.ln())
        .append(StringHelper.tab(3)).append("{4}").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_END).append(StringHelper.ln())
        .append(StringHelper.tab(1)).append(")").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append(TAG_FOREACH_END).append(StringHelper.ln())
        .append(TAG_INSERT_END).append(StringHelper.ln(2))
        
        .append("<update id=\""+TAG_ID_UPDATE+"\" flushCache=\"true\" >").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("update "+TAG_INCLUDE(SqlId.table)+"  set ").append(StringHelper.ln())        
        .append(StringHelper.tab(2)).append(TAG_TRIM_COMMA_START).append(StringHelper.ln())
        //.append(StringHelper.tab(3)).append(TAG_INCLUDE(SQL_ID_UPDATE_COLS)).append(StringHelper.ln())
        .append(StringHelper.tab(3)).append("{5}").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_END).append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("where "+TAG_INCLUDE(SqlId.pk)+"=#'{'"+NAME_MODEL+".{9}'}'").append(StringHelper.ln())
        .append(TAG_UPDATE_END).append(StringHelper.ln(2))
        
        .append("<delete id=\""+TAG_ID_DELETE_EQ_PK+"\" flushCache=\"true\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("delete from "+TAG_INCLUDE(SqlId.table)+"  where "+TAG_INCLUDE(SqlId.pk)+" =#'{'"+NAME_PK+"'}'").append(StringHelper.ln())
        .append(TAG_DELETE_END).append(StringHelper.ln(2))
        
        .append("<delete id=\""+TAG_ID_DELETE_IN_PK+"\" flushCache=\"true\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("delete from "+TAG_INCLUDE(SqlId.table)+" where "+TAG_INCLUDE(SqlId.pk)+" in (").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append("<foreach item=\""+NAME_PK+"\" collection=\""+NAME_PKS+"\"  separator=\",\" >#'{'"+NAME_PK+"'}'</foreach> ").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append(")").append(StringHelper.ln())
        .append(TAG_DELETE_END).append(StringHelper.ln(2))
        
       // .append("<delete id=\"deleteNotInPk\" flushCache=\"true\">").append(StringHelper.ln())
       // .append(StringHelper.tab(1)).append("delete from <include refid=\"table\"/> where <include refid=\"pk\" /> not in").append(StringHelper.ln())
       // .append(StringHelper.tab(2)).append("<foreach item=\"pk\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">#'{'pk'}'</foreach>").append(StringHelper.ln())
       // .append(TAG_DELETE_END).append(StringHelper.ln(4))      
        
        .append("<select id=\""+TAG_ID_SELECT_VIEW+"\" resultType=\"ArrayList\" resultMap=\""+TAG_ID_RESULT_VIEW+"\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select <include refid=\""+SqlId.columns+"\"/>  <include refid=\""+SqlId.joinCols+"\"/> from <include refid=\""+SqlId.table+"\" /> "+NAME_MAIN_TABLE+" "+TAG_INCLUDE(SqlId.join)+" ").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_TRIM_CONDITIOM_START).append(StringHelper.ln())
        .append(StringHelper.tab(3)).append(TAG_INCLUDE(SqlId.selectViewCondition)).append(StringHelper.ln())        
        .append(StringHelper.tab(2)).append(TAG_TRIM_END)
        .append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        .append("<select id=\""+TAG_ID_SELECT_VIEW_UNION+"\" resultType=\"List\" resultMap=\""+TAG_ID_RESULT_VIEW+"\">").append(StringHelper.ln())
        .append("  ").append("select "+NAME_MAIN_TABLE+".* from (").append(StringHelper.ln())
        //.append(StringHelper.tab(1)).append(TAG_TRIM_UNION_START).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append("<foreach collection=\""+NAME_CONDITIDONS+"\" item=\""+NAME_CONDITIDON+"\" separator=\" union \">").append(StringHelper.ln())
        .append(StringHelper.tab(3)).append(TAG_IF_START(NAME_CONDITIDON+" neq null")).append(StringHelper.ln())
        .append(StringHelper.tab(4)).append(" select "+TAG_INCLUDE(SqlId.queryCols)+" "+TAG_INCLUDE(SqlId.joinCols)+" from "+TAG_INCLUDE(SqlId.table)+" "+NAME_MAIN_TABLE+"  "+TAG_INCLUDE(SqlId.join)+" ").append(StringHelper.ln())
        .append(StringHelper.tab(5)).append(TAG_TRIM_CONDITIOM_START).append(StringHelper.ln())
        .append(StringHelper.tab(6)).append(TAG_INCLUDE(SqlId.selectViewCondition)).append(StringHelper.ln())       
        .append(StringHelper.tab(5)).append(TAG_TRIM_END).append(StringHelper.ln())
        .append(StringHelper.tab(3)).append(TAG_IF_END).append(StringHelper.ln())
        .append(StringHelper.tab(2)).append(TAG_FOREACH_END).append(StringHelper.ln())
        //.append(StringHelper.tab(1)).append(TAG_TRIM_END).append(StringHelper.ln())        
        .append("  ) "+NAME_MAIN_TABLE+" ").append(StringHelper.ln(2))
        .append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        

        .append("<select id=\""+TAG_ID_SELECT_VIEW_EQ_PK+"\"  resultMap=\""+TAG_ID_RESULT_VIEW+"\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select "+TAG_INCLUDE(SqlId.queryCols)+" "+TAG_INCLUDE(SqlId.joinCols)+"  from "+TAG_INCLUDE(SqlId.table)+" "+NAME_MAIN_TABLE+" "+TAG_INCLUDE(SqlId.join)+" where "+NAME_MAIN_TABLE+"."+TAG_INCLUDE(SqlId.pk)+" = #'{"+NAME_PK+"}'").append(StringHelper.ln())
        .append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
            
        .append("<select id=\""+IMapper.Methods.selectViewInPk+"\" resultType=\"List\" resultMap=\""+TAG_ID_RESULT_VIEW+"\" >").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select "+TAG_INCLUDE(SqlId.queryCols)+" "+TAG_INCLUDE(SqlId.joinCols)+" from "+TAG_INCLUDE(SqlId.table)+" "+NAME_MAIN_TABLE+" "+TAG_INCLUDE(SqlId.join)+" where "+NAME_MAIN_TABLE+"."+TAG_INCLUDE(SqlId.pk)+" in (").append(StringHelper.ln())
        .append(StringHelper.tab(2)).append("<foreach item=\""+NAME_PK+"\" collection=\""+NAME_PKS+"\"  separator=\",\" > #'{'"+NAME_PK+"'}' </foreach>").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append(")").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        
        /*
        .append("<select id=\"selectViewNotInPk\" resultType=\"ArrayList\" resultMap=\"viewMap\" >").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select m.* <include refid=\"joinColums\"/> from <include refid=\"table\" /> m <include refid=\"join\"/> where m.<include refid=\"pk\" /> not in").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("<foreach item=\"pk\" collection=\"list\" open=\"(\" separator=\",\" close=\")\"> #'{'pk'}' </foreach>").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append(TAG_INCLUDE(SQL_ID_SELECT_ORDERBY)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))
        */
        
        .append("<select  id=\""+TAG_ID_SELECT_VIEW_ALL+"\"  resultType=\"List\"  resultMap=\""+TAG_ID_RESULT_VIEW+"\">").append(StringHelper.ln())
        .append(StringHelper.tab(1)).append("select  "+TAG_INCLUDE(SqlId.queryCols)+" "+TAG_INCLUDE(SqlId.joinCols)+" from  "+TAG_INCLUDE(SqlId.table)+" "+NAME_MAIN_TABLE+" "+TAG_INCLUDE(SqlId.join)+"").append(StringHelper.ln())        
        .append(StringHelper.tab(1)).append(TAG_INCLUDE(SqlId.orderby)).append(StringHelper.ln())
        .append(TAG_SELECT_END).append(StringHelper.ln(2))

        
        .append("</mapper>");
     
    	
    	String lStrMapper=MessageFormat.format(pattern.toString(),
    			this.namespace.getName()  //0
    			,this.table    //1
    			,this.pk         //2
    			,this.getDbCols(NAME_MAIN_TABLE+".")  //3
    			,this.getInsertCols()  //4
    			,this.getUpdateCols()   //5
    			,this.getResultMapPols()   //6
    			,this.getConditions()  //7
    			,this.modelClass.getName() //8
    			,this.getPkCol()  //9
    			,this.getJavaColsContext() //10
    			,this.getEnum()  //11
    			,this.dialect.getClass().getName()  //12
    			,this.getQueryColsContext()  //13
    			,DateTimeHelper.Formatter.DATE_FORMAT_DB.format()
    	        ,this.getDbCols("") //15
    			);
		return lStrMapper;
	   
   }
    
    
    public Document getMapperDocument(){
    	Document document=DocumentHelper.createDocument();
    	document.setXMLEncoding("UTF-8");
    	StringBuffer strComment=new StringBuffer("");
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	strComment
    	.append(StringHelper.tab()).append("Create Date:").append(sdf.format(new Date())).append(StringHelper.ln(1))
        .append(StringHelper.tab()).append("MapperTemplate:").append(this.dialect.getClass().getName()).append(StringHelper.ln(1))
        .append(StringHelper.tab()).append("Model:").append(this.modelClass.getName()).append(StringHelper.ln(2))
    	.append(" public static enum F { \n  "+this.getEnum()+"  \n } ").append(StringHelper.ln(2))
        .append(this.getJavaColsContext()).append(StringHelper.ln(1))
        ;
        document.addComment(strComment.toString());
    	DocumentType documentType=new DOMDocumentType();
    	documentType.setName("mapper");
    	documentType.setPublicID("-//mybatis.org//DTD Mapper 3.0//EN");
    	documentType.setSystemID("http://mybatis.org/dtd/mybatis-3-mapper.dtd");
    	document.setDocType(documentType);
    	
    	Element elMapper=DocumentHelper.createElement("mapper");
    	elMapper.addAttribute("namespace", this.namespace.getName());
        
    	Element elCache=DocumentHelper.createElement("cache");
    	elCache.addAttribute("flushInterval", "30000");
    	elCache.addAttribute("readOnly", "true");
    	
    	Element elSqlTable=DocumentHelper.createElement("sql");
    	elSqlTable.addAttribute("id", "table");
    	elSqlTable.setText(this.table);
    	
    	Element elSqlPk=DocumentHelper.createElement("sql");
    	elSqlPk.addAttribute("id", "pk");
    	elSqlPk.setText(this.pk);
    	
    	Element elSqlColums=DocumentHelper.createElement("sql");
    	elSqlColums.addAttribute("id", "colums");
    	elSqlColums.setText(this.getDbCols(""));
    	
    	Element elSqlInsertCols=DocumentHelper.createElement("sql");
    	elSqlInsertCols.addAttribute("id", "insertCols");
    	elSqlInsertCols.setText(this.getInsertCols());
    	
    	Element elSqlUpdateCols=DocumentHelper.createElement("sql");
    	elSqlUpdateCols.addAttribute("id", "updateCols");
    	elSqlUpdateCols.setText(this.getUpdateCols());
    	
    	Element elSqlOrderby=DocumentHelper.createElement("sql");
    	elSqlOrderby.addAttribute("id", "orderby");
    	elSqlOrderby.setText("order by m."+this.pk+" desc");
    	
    	Element elSqlJoinColums=DocumentHelper.createElement("sql");
    	elSqlJoinColums.addAttribute("id", "joinColums");
    	
    	Element elSqlJoin=DocumentHelper.createElement("sql");
    	elSqlJoin.addAttribute("id", "join");
    	
    	
    	Element elSqlResultMap=DocumentHelper.createElement("resultMap");
    	elSqlResultMap.addAttribute("id","resultMap");
    	elSqlResultMap.addAttribute("type", this.modelClass.getName());
    	
    	elMapper.add(elCache);
    	elMapper.add(elSqlTable);
    	elMapper.add(elSqlPk);
    	elMapper.add(elSqlColums);
    	elMapper.add(elSqlInsertCols);
    	elMapper.add(elSqlUpdateCols);
    	elMapper.add(elSqlOrderby);
    	elMapper.add(elSqlJoinColums);
    	elMapper.add(elSqlJoin);
    	elMapper.add(elSqlResultMap);
  
    	
    	document.add(elMapper);  	
    	return document;
    }
    
    public StringBuffer contextBaseModel(String classSimpleName){
        StringBuffer context=new StringBuffer("");
    	
        
        context
    	.append(StringHelper.ln(3))
        .append("@OrmTable(name=\""+classSimpleName+"\",pk=\""+pk+"\" )").append("\n")
    	.append("public  class "+baseModelPrefix+classSimpleName+" {").append(StringHelper.ln(2))
   	//.append(StringHelper.tab()).append("public enum F { \n \t"+this.getEnum()+" \n\t }").append(StringHelper.ln(2))

   	.append(this.getJavaColsContext()).append(StringHelper.ln(3))
    	;
                
        for (ColumnTemplate columnTemplate : this.cols) {
    		
    		context
    		.append(getsetContext(columnTemplate)).append(StringHelper.ln(2))
			;
		}
           	context
    	//.append("public class Query {\n\n")
    	//.append(this.getQueryColsContext()).append(StringHelper.ln(2))
    	//.append("}  //end Query").append(StringHelper.ln(3))
    	.append("} //end "+classSimpleName);
        return context;
    }
    
    public boolean generalBaseModel(String srcPath){
    	
        boolean isSuccess=false;
    	StringBuffer context=new StringBuffer();
        Class[] importClss={List.class,Date.class,Timestamp.class,IModel.class,Resultable.class,Set.class,ObjectHelper.class};
    	context
    	.append("package "+this.modelClass.getPackage().getName()+";").append(StringHelper.ln(2));
    	for(Class importCls : importClss){
    		context.append("import "+importCls.getName()+";").append(StringHelper.ln())
        	;
    	}        
        context.append(contextBaseModel(this.modelClass.getSimpleName()));
    	
    	//System.out.println(context);
    	String path=srcPath+this.modelClass.getPackage().getName().replace('.','/')+"/"+baseModelPrefix+this.modelClass.getSimpleName()+".java";
    	File javaFile=new File(path);
 	    OutputStreamWriter osw=null;
 	   //BufferedOutputStream bos=new BufferedOutputStream(os);
 	    BufferedWriter bw=null;
 	    try {

 		if(javaFile.exists()&&javaFile.isFile())
 		{ 
 			javaFile.delete();
 			//File bakFile=new File(bakPath);
 			//if(bakFile.exists()){
 			//	bakFile.delete();
 			//}
 			//xmlFile.renameTo(bakFile);
 			isSuccess=true;
 		 }
 		javaFile.createNewFile();
		osw=new OutputStreamWriter(new FileOutputStream(javaFile),StringHelper.UrlCoding.utf8.name());
		bw=new BufferedWriter(osw);
		bw.write(context.toString());
		bw.flush();
		bw.close();
		osw.close();
		System.out.println(path);
 	    }catch(Exception ex){
 			ex.printStackTrace();
 		}
    	return isSuccess;
    }
    
    public boolean generalMapplerXML(String srcPath){
    	return this.generalMapplerXML(srcPath,this.dialect.database.name());
    }
    
    public boolean generalMapplerXML(String srcPath,String subPath){
    	String refPath= IOHelper.pathFormat(namespace);
    	return this.generalMapplerXML(srcPath, refPath,subPath);
    }
    
    
   public boolean generalMapplerXML(String srcPath,String refPath,String subPath){
	   boolean isSuccess=false;
	   subPath=ObjectHelper.<String>parse(subPath,"");
	   //String path= FileUtil.toPath(namespace);
	   //String xmlPath=srcPath+refPath+".xml";
	   String path=MessageFormat.format("{0}{1}/{2}.{3}",
		   srcPath
		   ,refPath
		   ,subPath
		   ,namespace.getSimpleName()
	   );
	   

	  
	   String xmlPath=path+".xml";
	   String bakPath=path+".bak";
	   
	   String strMapper=this.getMapperContext();
	   
	   System.err.println(xmlPath);
	   File xmlFile=new File(xmlPath);
	   
	   OutputStreamWriter osw=null;
	   //BufferedOutputStream bos=new BufferedOutputStream(os);
	   BufferedWriter bw=null;
	   try {

		if(xmlFile.exists()&&xmlFile.isFile())
		{ 
			xmlFile.delete();
			//File bakFile=new File(bakPath);
			//if(bakFile.exists()){
			//	bakFile.delete();
			//}
			//xmlFile.renameTo(bakFile);
			isSuccess=true;
		}
		xmlFile.createNewFile();
		osw=new OutputStreamWriter(new FileOutputStream(xmlFile),StringHelper.UrlCoding.utf8.name());
		bw=new BufferedWriter(osw);
		bw.write(strMapper);
		bw.flush();
		bw.close();
		osw.close();
		
		String xmlExtraPath=path+".extra.xml";
		File xmlExtraFile=new File(xmlExtraPath);
		String strExtraMapper=this.getExtraMapperContext();
		if(!xmlExtraFile.exists()){
			xmlExtraFile.createNewFile();
			osw=new OutputStreamWriter(new FileOutputStream(xmlExtraFile),StringHelper.UrlCoding.utf8.name());
			bw=new BufferedWriter(osw);
			bw.write(strExtraMapper);
			bw.flush();
			bw.close();
			osw.close();
		}else if(isOverWriterExtra){
			String bakExtraPath=path+".extra.bak";
            File bakExtraFile=new File(bakExtraPath);
            if(bakExtraFile.exists()){
            	bakExtraFile.delete();
            }
            xmlExtraFile.renameTo(bakExtraFile);
			xmlExtraFile.delete();
			xmlExtraFile.createNewFile();
			osw=new OutputStreamWriter(new FileOutputStream(xmlExtraFile),StringHelper.UrlCoding.utf8.name());
			bw=new BufferedWriter(osw);
			bw.write(strExtraMapper);
			bw.flush();
			bw.close();
			osw.close();
			

            
		}
	   
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return isSuccess;
   }

   public static void parseXml(String xmlPath){
	   SAXReader saxReader=new SAXReader();
	   try {
	   Document document=saxReader.read(new File(xmlPath));
	   Element element=document.getRootElement();
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   private String getterContext(ColumnTemplate columnTemplate){
	   
        return getterContext(columnTemplate.getColumn().getJavaType().getSimpleName(), columnTemplate.getJavaName());
   }
   
   private String getterContext(String typeName,String proName){
	    String getterPattern="public {1} get{0}()'{' return this.{2}; '}'";
	    if(Byte.class.getSimpleName().equalsIgnoreCase(typeName)){
	    	getterPattern="public byte[] get{0}()'{' return this.{2}; '}'";
	    }
       return MessageFormat.format(getterPattern,
     		  StringHelper.format(proName, StringHelper.Format.upcaseFirstChar)
			  ,typeName
			  ,proName
			  ,StringHelper.format(proName, StringHelper.Format.lowcaseFirstChar)
			
       );
  }
   
   private String setterContext(ColumnTemplate columnTemplate){
	   
        return setterContext(columnTemplate.getColumn().getJavaType().getSimpleName(), columnTemplate.getJavaName());
   }
   
   private String setterContext(String typeName,String proName){
	    String setterPattern="public void set{0}({1} {3})'{' this.{2}={3}; '}'";
	    if(Byte.class.getSimpleName().equalsIgnoreCase(typeName)){
	    	setterPattern="public void set{0}(byte[] {3})'{' this.{2}={3}; '}'";
	    }
      return MessageFormat.format(setterPattern,
    		  StringHelper.format(proName, StringHelper.Format.upcaseFirstChar)
			  ,typeName
			  ,proName
			  ,StringHelper.format(proName, StringHelper.Format.lowcaseFirstChar)
			
      );
 }
   
   private String getsetContext(String typeName,String proName){
	   
	   return getterContext(typeName,proName)+StringHelper.ln()+setterContext(typeName,proName);
   }
	
   private String getsetContext(ColumnTemplate columnTemplate){
	   
	   return getsetContext(columnTemplate.getColumn().getJavaType().getSimpleName(), columnTemplate.getJavaName());
   }
   
   private String implementIModelContext(){
	   StringBuffer contextPattern=new StringBuffer("");

	   contextPattern.append(StringHelper.tab()).append("public Object getPk()'{' return {0};'}'").append(StringHelper.ln(2))
	   .append(StringHelper.tab()).append("public void setPk(Object pk) throws Exception '{' {0}={1};'}'").append(StringHelper.ln(2))
	   .append(StringHelper.tab()).append("public void genPk() throws Exception '{'{3} '}'").append(StringHelper.ln(2))
	   .append(StringHelper.tab()).append("public {2} validate()'{' return new {2}(); '}'").append(StringHelper.ln(2))
	   .append(StringHelper.tab()).append("public "+Enum.class.getSimpleName()+"[] enumFields()'{' return "+baseModelPrefix+this.modelClass.getSimpleName()+".F.values(); '}'").append(StringHelper.ln(2))
	   ;
	   String setContext="pk.toString()";
	   if(LinqHelper.isIn(this.pkColumn.getColumn().getJavaType(), Integer.class)){
		   setContext= Integer.class.getSimpleName()+".parseInt(pk.toString())";         
	   }else if(LinqHelper.isIn(this.pkColumn.getColumn().getJavaType(), Long.class)){
		   setContext= Long.class.getSimpleName()+".parseLong(pk.toString())"; 
	   }
	   String genPkContext="";
	   if(LinqHelper.isIn(this.pkColumn.getColumn().getJavaType(), String.class)){
		   genPkContext= this.pkColumn.getJavaName()+"="+ObjectHelper.class.getSimpleName()+".GeneralPK();";         
	   }else if(LinqHelper.isIn(this.pkColumn.getColumn().getJavaType(), Long.class)){
		   genPkContext= this.pkColumn.getJavaName()+"=new Date().getTime();";    
	   } 
       return MessageFormat.format(contextPattern.toString(), 
       this.pkColumn.getJavaName()
        ,setContext
        ,Resultable.class.getSimpleName()
        ,genPkContext
	   );
	   
   }
   
	public static void main(String[] args){
           String xmlPath="h:\\project2\\smartken-kia\\kia-core\\src\\main\\java\\com\\smartken\\kia\\core\\plugin\\mybatis\\IMapper.xml";
		   SAXReader saxReader=new SAXReader();
		   try {
		   Document document=saxReader.read(new File(xmlPath));
		   System.out.println(document.getDocType().getName());
		   Element element=document.getRootElement();
		   for(Iterator<Element> it=element.elementIterator("sql");it.hasNext();){
			   Element tempElement=it.next();
			   if(tempElement.attributeValue("id").equals("pk")){
				   System.out.println(tempElement.asXML());
			   }
			  
		   }
		   
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
