package com.kull.orm.mybatis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

import com.kull.Linq;
import com.kull.Clazz;
import com.kull.Collectionz;






public class DynamicQueryBound {

	private ResultMap resultMap;
	private RowBounds rowBounds=new RowBounds();
	private Set<OrderbyBound> orderbyBounds;
	private Set<ResultMapping> colums=new HashSet<ResultMapping>();
	
    public enum Propertys{
    	orderbyBounds,colums
    }
	
	public DynamicQueryBound(ResultMap resultMap){
		this.resultMap=resultMap;
		this.colums=new HashSet<ResultMapping>(this.resultMap.getResultMappings());
	}
	
	

	public RowBounds getRowBounds() {
		
		return rowBounds;
	}
	public void setRowBounds(RowBounds rowBounds) {
		this.rowBounds = rowBounds;
	}

	public <T> int setJustIncludeFields(Set<T> include) {
		int count=0;
		Set<ResultMapping> tempColums=new HashSet<ResultMapping>();
		for(Iterator<T> it=include.iterator();it.hasNext();){
			T key=it.next();
			 if(key instanceof JdbcType){
				for(Iterator<ResultMapping> it1=this.resultMap.getResultMappings().iterator();it1.hasNext();){
					ResultMapping resultMapping=it1.next();
					JdbcType jdbcType=(JdbcType) key;
					if(!jdbcType.equals(resultMapping.getJdbcType())){
					  continue;
					}
					tempColums.add(resultMapping);
					count++;
				}
			}else if(key instanceof Class){
				for(Iterator<ResultMapping> it2=this.resultMap.getResultMappings().iterator();it2.hasNext();){
					ResultMapping resultMapping=it2.next();
					Class cls=(Class) key;
					if(!cls.equals(resultMapping.getJavaType())){
					  continue;
					}
					tempColums.add(resultMapping);
					count++;
				}
			}else  {
				ResultMapping tempResultMapping=this.getReusltMapping(key.toString());
				if(tempResultMapping==null)continue;
				tempColums.add(tempResultMapping);
				count++;
			}

		}
		this.colums=tempColums;
		this.fix();
		return count;
	}
	
	
	
	public <T> int setIncludeFields(Set<T> includeFieldNames) {
		int count=0;
		Set<ResultMapping> tempColums=this.colums;
		for(Iterator<T> it=includeFieldNames.iterator();it.hasNext();){
			T key=it.next();
			if(key instanceof JdbcType){
				for(Iterator<ResultMapping> it1=this.resultMap.getResultMappings().iterator();it1.hasNext();){
					ResultMapping resultMapping=it1.next();
					JdbcType jdbcType=(JdbcType) key;
					if(!jdbcType.equals(resultMapping.getJdbcType())){
					  continue;
					}
					tempColums.add(resultMapping);
					count++;
				}
			}else if(key instanceof Class){
				for(Iterator<ResultMapping> it2=this.resultMap.getResultMappings().iterator();it2.hasNext();){
					ResultMapping resultMapping=it2.next();
					Class cls=(Class) key;
					if(!cls.equals(resultMapping.getJavaType())){
					  continue;
					}
					tempColums.add(resultMapping);
					count++;
				}
			}else {
				ResultMapping tempResultMapping=this.getReusltMapping(key.toString());
				if(tempResultMapping==null)continue;
				tempColums.add(tempResultMapping);
				count++;
			} 

		}
		this.colums=tempColums;
		this.fix();
		return count;
	}
	
	public <T> int excludeBinaryFields(){
	   return	setExcludeFields(Collectionz.asSet(JdbcType.BINARY,JdbcType.BIT,JdbcType.BLOB,JdbcType.CLOB ,JdbcType.LONGVARBINARY,JdbcType.VARBINARY,JdbcType.NCLOB));
	}

	public <T> int setExcludeFields(Set<T> exclude) {
		int count=0;
		Set<ResultMapping> tempColums;
		if(Clazz.isEmpty(colums)){
			tempColums=new HashSet<ResultMapping>(this.resultMap.getResultMappings());
		}else{
			tempColums=this.colums;
		}
		
		for(Iterator<T> it=exclude.iterator();it.hasNext();){
			T key=it.next();
			 if(key instanceof JdbcType){
				for(Iterator<ResultMapping> it1=this.resultMap.getResultMappings().iterator();it1.hasNext();){
					ResultMapping resultMapping=it1.next();
					JdbcType jdbcType=(JdbcType) key;
					if(!jdbcType.equals(resultMapping.getJdbcType())){
					  continue;
					}
					tempColums.remove(resultMapping);
					count++;
				}
			}else if(key instanceof Class){
				for(Iterator<ResultMapping> it2=this.resultMap.getResultMappings().iterator();it2.hasNext();){
					ResultMapping resultMapping=it2.next();
					Class cls=(Class) key;
					if(!cls.equals(resultMapping.getJavaType())){
					  continue;
					}
					tempColums.remove(resultMapping);
					count++;
				}
			}else {
				ResultMapping tempResultMapping=this.getReusltMapping(key.toString());
				if(tempResultMapping==null)continue;
				tempColums.remove(tempResultMapping);
				count++;
			}

		}
		this.colums=tempColums;
		this.fix();
		return count;
	}
	

	public int setOrderbyBounds(Set<OrderbyBound> orderbyBounds) {
		int count=0;
		Set<OrderbyBound> tempOrderbyBounds=new HashSet<OrderbyBound>();
		for(Iterator<OrderbyBound> it=orderbyBounds.iterator();it.hasNext();){
			OrderbyBound tempOrderbyBound=it.next();
			ResultMapping tempResultMapping=this.getReusltMapping(tempOrderbyBound.getSort());
			if(tempResultMapping==null||
			Linq.isIn(tempResultMapping.getJdbcType(), 
					JdbcType.LONGVARBINARY
					,JdbcType.BINARY
					,JdbcType.BIT
					,JdbcType.BLOB
					,JdbcType.CLOB
					,JdbcType.LONGVARBINARY
					,JdbcType.LONGVARCHAR
					,JdbcType.NCLOB
			
			        )
					)continue;
			tempOrderbyBound.setColum(tempResultMapping.getColumn());
			tempOrderbyBounds.add(tempOrderbyBound);
			count++;
			//tempOrderbyBounds.add(new OrderbyBound(tempOrderbyBound.getName(),tempOrderbyBound.getType()));
		}
		this.orderbyBounds=tempOrderbyBounds;
		this.fix();
		return count;
	}
	
	
	public Set<OrderbyBound> getOrderbyBounds() {
		return orderbyBounds;
	}
	
	
	
	public ResultMap getResultMap() {
		return resultMap;
	}

	public Set<ResultMapping> getColums() {
		return colums;
	}

	private ResultMapping getReusltMapping(String proName){
		ResultMapping resultMapping=null;
		for(Iterator<ResultMapping> it=resultMap.getResultMappings().iterator();it.hasNext();){
			ResultMapping tempResultMapping=it.next();
			if(tempResultMapping.getProperty().equals(proName)){
			  resultMapping=tempResultMapping;
			break;}
		    
		}
		return resultMapping;
	}
	
	
	public  List<String> getFieldNames(){
		List<String> fieldNames=new ArrayList<String>();
		for(ResultMapping resultMaping :this.colums){
			fieldNames.add(resultMaping.getProperty());
		}
		return fieldNames;
	}
	
	private int fix(){
		int count=0;
		if(Clazz.isEmpty(this.getOrderbyBounds())
		){
			return 0;
		}else if(Clazz.isEmpty(this.getColums())){
			return 0;
		}
		Set<OrderbyBound> orderbys=new HashSet<OrderbyBound>();
		
		for(Iterator<OrderbyBound> it=this.orderbyBounds.iterator();it.hasNext();){
			OrderbyBound orderbyBound=it.next();
			for(Iterator<ResultMapping> it1=this.colums.iterator();it1.hasNext();){
				ResultMapping resultMapping=it1.next();
				if(!Clazz.isEquals(orderbyBound.getSort(),resultMapping.getProperty())){continue;}
			    orderbys.add(orderbyBound);
			    count++;
			}
		}
		this.orderbyBounds=orderbys;
		return count;
	}
	/*
	public void handle(EasyUiConfig easyUiConfig){
		if(easyUiConfig==null)return;
		if(!easyUiConfig.includeByte){
			this.setExcludeFields(ObjectHelper.toSet(JdbcType.BINARY,JdbcType.BIT,JdbcType.BLOB,JdbcType.CLOB));
		}
		if(ObjectHelper.isNotEmpty(easyUiConfig.datagridColumnFields())){
			this.setJustIncludeFields(ObjectHelper.toSet(easyUiConfig.datagridColumnFields()));
		}
	}
	*/
}
