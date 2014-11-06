/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.orm;

import com.kull.LinqHelper;
import com.kull.ObjectHelper;
import com.kull.StringHelper;
import com.kull.cache.MapCacheAccess;
import com.kull.orm.annotation.OrmTable;
import com.kull.orm.dbutils.InOutBeanHandler;
import com.kull.orm.dbutils.InOutBeanListHandler;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.jcs.access.exception.CacheException;

/**
 *
 * @author lin
 */
public class Session {

    private Connection conn;
    private QueryRunner queryRunner;

    public Session(Connection conn) {
        this.conn = conn;
        queryRunner = new QueryRunner();
        try {
            SQL_CACHE= new MapCacheAccess(Session.class.getName()+":sql");
        } catch (CacheException ex) {}
    }

    //private static Map<String, String> SQL_CACHE = new HashMap<String, String>();
    
    private   MapCacheAccess<String,String> SQL_CACHE=null;
    
    
   

    public <T> T load(Class<T> cls, Object pk) throws SQLException, NullPointerException, InstantiationException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        OrmTable table = cls.getAnnotation(OrmTable.class);
        return (T) load(cls, table.pk(), pk);
    }
    
   

//    private <T> T load(Class<T> cls, String column, Object pk) throws SQLException, NullPointerException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        T t=ObjectHelper.newInstance(cls);
//        
//        OrmTable table = t.getClass().getAnnotation(OrmTable.class);
//        String sql = MessageFormat.format("select * from {0} where {1}= ?", table.name(), column);
//        Field pkField = t.getClass().getDeclaredField(table.pk());
//	PreparedStatement  preparedStatement=conn.prepareStatement(sql);
//		if(pkField.getType().equals(String.class)){
//			preparedStatement.setString(1, pk.toString());
//		}else if(pkField.getType().equals(Integer.class)){
//			preparedStatement.setInt(1, Integer.parseInt(pk.toString()));
//		}
//	ResultSet  resultSet=preparedStatement.executeQuery();
//		   
//	    if(resultSet.next()){
//	    	t=toBean(t, resultSet);
//	    }else{
//                  String errormsg="can't select a model wiht sql= "+sql+ " , pk ="+pk;
//                    throw new SQLException(errormsg);
//            }
//		
//         close(null,preparedStatement,resultSet);
//        return t;
//    }
    
    private <T> T load(Class<T> cls, String column, Object pk) throws SQLException, NullPointerException, NoSuchFieldException {
        OrmTable table = cls.getAnnotation(OrmTable.class);
        String sql = MessageFormat.format("select * from {0} where {1}= ?", table.name(), column);
        
	ResultSetHandler<T> ht=new InOutBeanHandler<T>(cls);
        return queryRunner.query(this.conn, sql,ht,pk);
    }

    public <T> int insert(T... objs) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {

        int success = 0;
        if (ObjectHelper.isEmpty(objs)) {
            return success;
        }

        Class<T> cls = (Class<T>) objs[0].getClass();
        String sqlPattern = "insert into {0} ({1}) values ({2})", sql = "",
                sqlCacheKey = cls.getSimpleName() + ":insert", cols = "", vals = "";
        //Field[] fields = null;
        final OrmTable table = cls.getAnnotation(OrmTable.class);
        List<Field> insertFields = new ArrayList<Field>();

        for (Field f : cls.getDeclaredFields()) {
            String fname = f.getName();
            if (ObjectHelper.isThis0(f) || (LinqHelper.isIn(fname, table.excludeColumns())
                    || (!table.insertPk() && fname.equalsIgnoreCase(table.pk())))) {
                continue;
            }
            insertFields.add(f);
        }

        if (SQL_CACHE.containsKey(sqlCacheKey)) {
            sql = SQL_CACHE.get(sqlCacheKey);
        } else {

            for (Field field : insertFields) {

                String fname = field.getName();

                cols += MessageFormat.format(" `{0}`,", fname);
                vals += " ?,";

            }
            cols = StringHelper.trim(cols, ",");
            vals = StringHelper.trim(vals, ",");
            sql = MessageFormat.format(sqlPattern, table.name(), cols, vals);
            try {
                SQL_CACHE.put(sqlCacheKey, sql);
                //System.out.println(sql);
            } catch (CacheException ex) {
                throw new SQLException(ex);
            }
        }
        int insertsize = insertFields.size();
        Object[][] params = new Object[objs.length][insertsize];
        for (int i = 0; i < objs.length; i++) {
            T obj = objs[i];
            for (int j = 0; j < insertsize; j++) {
                Field field = insertFields.get(j);
                String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method m = obj.getClass().getDeclaredMethod(getterName);
                params[i][j] = m.invoke(obj);
            }
        }
        int[] res = queryRunner.batch(this.conn, sql, params);
        for (int re : res) {
            success += re;
        }
        return success;
    }

    public <T> int delete(T... objs) throws IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, SQLException {

        int success = 0;
        if (ObjectHelper.isEmpty(objs)) {
            return success;
        }
        Class<T> cls = (Class<T>) objs[0].getClass();
        //PreparedStatement preparedStatement = null;
        OrmTable table = cls.getAnnotation(OrmTable.class);
        String sqlPattern = "delete from {0} where {1}=? ", sql = "",
                sqlCacheKey = cls.getSimpleName() + ":delete";

        if (SQL_CACHE.containsKey(sqlCacheKey)) {
            sql = SQL_CACHE.get(sqlCacheKey);
        } else {
            sql = MessageFormat.format(sqlPattern, table.name(), table.pk());
            try {
                SQL_CACHE.put(sqlCacheKey, sql);
            } catch (CacheException ex) {
               throw new SQLException(ex);
            }
        }
        int dlength = objs.length;
        Object[][] params = new Object[dlength][1];
        for (int i = 0; i < dlength; i++) {
            Object obj = objs[i];
            if (obj == null) {
                continue;
            }

            String getterName = "get" + table.pk().substring(0, 1).toUpperCase() + table.pk().substring(1);;
            Method method = obj.getClass().getDeclaredMethod(getterName);
            params[i][1] = method.invoke(obj);

        }
        int[] res = queryRunner.batch(this.conn, sql, params);
        for (int re : res) {
            success += re;
        }
        return success;
    }

    public <T> int update(Object... objs) throws NoSuchFieldException, NoSuchMethodException, SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        int success = 0;
        if (ObjectHelper.isEmpty(objs)) {
            return success;
        }
        Class<T> cls = (Class<T>) objs[0].getClass();
        OrmTable table = cls.getAnnotation(OrmTable.class);
        String sqlPattern = "update {0} set {1} where {2}=? ", key = "", sql = "",
                sqlCacheKey = cls.getSimpleName() + ":update";

        Field pkField = cls.getDeclaredField(table.pk());
        List<Field> updateFields = new ArrayList<Field>();

        for (Field f : cls.getDeclaredFields()) {
            String fname = f.getName();
            if (ObjectHelper.isThis0(f) || LinqHelper.isIn(fname, table.excludeColumns())
                    || fname.equalsIgnoreCase(pkField.getName()))  {
                continue;
            }
            updateFields.add(f);
        }

        if (SQL_CACHE.containsKey(sqlCacheKey)) {
            sql = SQL_CACHE.get(sqlCacheKey);
        } else {

            int i = 0;
            for (Field field : updateFields) {
                String fname = field.getName();

                key += MessageFormat.format(" `{0}` =? ,", fname);
                i++;
            }
            key = StringHelper.trim(key, ",");
            sql = MessageFormat.format(sqlPattern, table.name(), key, table.pk());
            try {
                SQL_CACHE.put(sqlCacheKey, sql);
            } catch (CacheException ex) {
              throw new SQLException(ex);
            }
        }
        int updatesize = updateFields.size();
        Object[][] params = new Object[objs.length][updatesize + 1];
        for (int i = 0; i < objs.length; i++) {
            Object obj = objs[i];
            if (obj == null) {
                continue;
            }

            for (int j = 0; j < updatesize; j++) {
                Field field = updateFields.get(j);
                String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method m = obj.getClass().getDeclaredMethod(getterName);
                params[i][j] = m.invoke(obj);

            }
            String getterName = "get" + pkField.getName().substring(0, 1).toUpperCase() + pkField.getName().substring(1);
            Method m = obj.getClass().getDeclaredMethod(getterName);
            params[i][updatesize] = m.invoke(obj);


        }
        int[] res = queryRunner.batch(this.conn, sql, params);
        for (int re : res) {
            success += re;
        }
        return success;
    }

    

   

    

   

    public <T> List<T> query(Class<T> cls, String sql, Object... params) throws SQLException {
        List<T> list = new ArrayList<>();

        OrmTable table = cls.getAnnotation(OrmTable.class);
        if (table != null) {
            sql = MessageFormat.format(sql, table.name(), table.pk());
        }
        list = queryRunner.query(this.conn, sql, new InOutBeanListHandler<>(cls), params);
        return list;
    }



    public List<Map<String, Object>> query(String sql, Object... params) throws SQLException {
          return queryRunner.query(this.conn,sql,new MapListHandler(),params);
    }

    public int update(String sql, Object... params) throws SQLException {
        
        return queryRunner.update(this.conn, sql,params);
    }
    
     public int[] batch(String sql, Object[][] params) throws SQLException {
        return queryRunner.batch(this.conn, sql,params);
    }

    public  int selectInt(String sql, Object... params) throws SQLException {
        return selectScalar(sql, params);
    }
    
      public  <T> T selectScalar(String sql, Object... params) throws SQLException {
        T t= (T)queryRunner.query(this.conn,sql, new ScalarHandler());
        return t;
    }

    /**
     * 安全关闭数据库连接
     *
     * @param conn1 Connection
     */
    public static void close(Connection conn, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }

        } catch (Exception e) {

        }
    }

   
}
