package com.kull.mybatis;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;





public interface IMapper<M> {

   public static int INDEX_MODEL_CLASS=0;
   
   public enum Methods{
	   selectEqPk,selectInPk,select,selectUnion,selectAll,
	   selectViewEqPk,selectViewInPk,selectView,selectViewUnion,selectViewAll
	   ,update,insert,deleteEqPk,deleteInPk,count,countAll
	   ,sum,avg,min,max,selectGroupby,selectDistinct
   }
   
   public enum Params{
	   pk,dynamic,model,pks,conditions,models,condition, colunm
   }
	
   public M selectEqPk(@Param("pk")Object pObjPk) throws SQLException;
	
   public List<M> selectInPk(@Param("pks")Set pObjPk) throws SQLException;
	
	
	//public List<M> select(@Param("model") Map<String,Object> pModel,@Param("dynamic")DynamicQueryBound dynamicQueryBound) throws SQLException;
	
	
	public List<M> select(@Param("condition") Map<String,Object> pModel,RowBounds rowBounds,@Param("dynamic")DynamicQueryBound dynamicQueryBound) throws SQLException;
	
	
	//public List<M> selectUnion(@Param("querys")List<Map<String,Object>>  querys,@Param("dynamic")DynamicQueryBound dynamicQueryBound) throws SQLException;
	public List< M> selectUnion(@Param("conditions")List<Map<String,Object>>  querys,RowBounds rowBounds,@Param("dynamic")DynamicQueryBound dynamicQueryBound) throws SQLException;
	
	
	//public List<M> selectAll(@Param("dynamic")DynamicQueryBound dynamicQueryBound) throws SQLException;
	public List<M> selectAll(RowBounds rowBounds,@Param("dynamic")DynamicQueryBound dynamicQueryBound) throws SQLException;
	//public List<? extends M> selectAll(RowBounds rowBounds,List<OrderbyBound> orderbys) throws SQLException;

	
	public <T> T selectViewEqPk(@Param("pk")Object pObjPk) throws SQLException;
	public <T> List<T> selectViewInPk(@Param("pks")Set pObjPk) throws SQLException;

	//public <T> List<T> selectView(@Param("model") Map<String,Object> pModel,@Param("dynamic")DynamicQueryBound dynamicQueryBound) throws SQLException;
	
	public <T> List<T> selectView(@Param("condition") Map<String,Object> pModel,RowBounds rowBounds,@Param("dynamic")DynamicQueryBound dynamicQueryBound) throws SQLException;
	
	//public <T> List<T> selectViewUnion(@Param("querys")List<Map<String,Object>> querys,@Param("dynamic")DynamicQueryBound dynamicQueryBound) throws SQLException;
	public <T> List<T> selectViewUnion(@Param("conditions")List<Map<String,Object>> querys,RowBounds rowBounds,@Param("dynamic")DynamicQueryBound dynamicQueryBound) throws SQLException;

	
	//public <T> List<T> selectViewAll(@Param("dynamic")DynamicQueryBound dynamicQueryBound) throws SQLException;
	public <T> List<T> selectViewAll(RowBounds rowBounds,@Param("dynamic")DynamicQueryBound dynamicQueryBound) throws SQLException;
	

    
	public int update(@Param("model")M obj) throws SQLException;
    
	public int update(@Param("model")M obj,@Param("pk")Object newPk) throws SQLException;
	public int insert(@Param("models")M... obj) throws SQLException;

	public int deleteEqPk(@Param("pk")Object pObjPk) throws SQLException;
	
	
	public int deleteInPk(@Param("pks")Set  pArrPk) throws SQLException;
	

	
	public int countAll() throws SQLException;
	
	public int count(@Param("condition")Map<String,Object> condition) throws SQLException;  
	public int max(@Param("column")String column,@Param("condition")Map<String,Object> condition) throws SQLException;
	public int min(@Param("column")String column,@Param("condition")Map<String,Object> condition) throws SQLException;
	public int sum(@Param("column")String column,@Param("condition")Map<String,Object> condition) throws SQLException;
	public int avg(@Param("column")String column,@Param("condition")Map<String,Object> condition) throws SQLException;

	public List<M> selectGroupby(@Param("colunm")String column)throws SQLException; 
	
	public <T> List<T> selectDistinct(@Param("colunm")String column)throws SQLException; 
}
