/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.orm;

import com.kull.Clazz;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lin
 */
public class Utils {
    /**
     *
     * @param cls
     * @param resultSet
     * @return 将resutlSet映射到泛型集合中
     * @throws Exception
     */
    public static <T> List<T> toBeanList(Class<T> cls, ResultSet resultSet) throws Exception {
        List<T> list = new ArrayList<T>();
        while (resultSet.next()) {
            list.add(toBean(cls, resultSet));
        }
        return list;
    }

        /**
     *
     * @param t
     * @param resultSet
     * @return 将resutlSet的第一条记录映射如新对象中并返回
     * @throws Exception
     */
    public static <T> T toBean(Class<T> cls, ResultSet resultSet) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        T t=Clazz.newInstance(cls);
     
        Map<String,Method> setters= Clazz.settersBy(cls);
        ResultSetMetaData rsmd=resultSet.getMetaData();
        int columncount= rsmd.getColumnCount();
        for (int i = 1; i <= columncount; i++) {
            String colname=rsmd.getColumnName(i);
            if(setters.containsKey(colname))continue;
            setters.get(colname).invoke(t, resultSet.getObject(colname));
        } 
        return t;
    }
    
    public static int refJdbcTypesBy(String typeName) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        Field field = Types.class.getField(typeName.toUpperCase());
        return field.getInt(Types.class);
    }
}
