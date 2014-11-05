/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.orm.dialect;

import com.kull.StringHelper;
import com.mysql.jdbc.jmx.LoadBalanceConnectionGroupManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lin
 */
public class Column {

    protected final static Map<Integer,Class> TYPES_REF_JCLS=new HashMap<Integer,Class>();
    
    static{
        TYPES_REF_JCLS.put(Types.BIT, Byte.class);
        TYPES_REF_JCLS.put(Types.TINYINT, Integer.class);
        TYPES_REF_JCLS.put(Types.SMALLINT, Integer.class);
        TYPES_REF_JCLS.put(Types.INTEGER, Integer.class);
        TYPES_REF_JCLS.put(Types.BIGINT, Integer.class);
        TYPES_REF_JCLS.put(Types.FLOAT, Float.class);
        TYPES_REF_JCLS.put(Types.REAL, Double.class);
        TYPES_REF_JCLS.put(Types.DOUBLE, Double.class);
        TYPES_REF_JCLS.put(Types.NUMERIC, Double.class);
        TYPES_REF_JCLS.put(Types.DECIMAL, Double.class);
        TYPES_REF_JCLS.put(Types.CHAR, String.class);
        TYPES_REF_JCLS.put(Types.VARCHAR, String.class);
        TYPES_REF_JCLS.put(Types.LONGVARCHAR, String.class);
        TYPES_REF_JCLS.put(Types.DATE, Date.class);
        TYPES_REF_JCLS.put(Types.TIME, Time.class);
        TYPES_REF_JCLS.put(Types.TIMESTAMP,Long.class);
        TYPES_REF_JCLS.put(Types.BINARY, Byte.class);
        TYPES_REF_JCLS.put(Types.VARBINARY, Byte.class);
        TYPES_REF_JCLS.put(Types.LONGVARBINARY, Byte.class);
        
        TYPES_REF_JCLS.put(Types.BLOB, Byte.class);
        TYPES_REF_JCLS.put(Types.CLOB, Byte.class);
        TYPES_REF_JCLS.put(Types.BOOLEAN, Integer.class);
    }
    
    
    protected String name, typeName, label;
    protected int type, pk = 0, precisions = 100;
    protected Class cls;

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        if(TYPES_REF_JCLS.containsKey(type))this.cls=TYPES_REF_JCLS.get(type);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getPrecisions() {
        return precisions;
    }

    public void setPrecisions(int precisions) {
        this.precisions = precisions;
    }

    public void from(ResultSetMetaData mtdata, int index) throws SQLException {
        this.setName(mtdata.getColumnName(index));
        this.setTypeName(mtdata.getColumnTypeName(index));
        this.setType(mtdata.getColumnType(index));
        this.setLabel(mtdata.getColumnLabel(index));

        try {
            this.setPrecisions(mtdata.getPrecision(index));
        } catch (NumberFormatException nfe) {
            this.setPrecisions(100);
        }
    }
 
    public String protectedScript(){
        String jcls = cls.getSimpleName();
        if (Byte.class.equals(cls)) {
            jcls = "byte[]";
        }
         return MessageFormat.format("protected {0} {1};", jcls,name);
    }
    
    public String getterScript() {
        String getterPattern = "public {1} get{0}()'{' return this.{2}; '}'";
        String jcls = cls.getSimpleName();
        if (Byte.class.equals(cls)) {
            jcls = "byte[]";
        }

        return MessageFormat.format(getterPattern,
                StringHelper.format(name, StringHelper.Format.upcaseFirstChar),
                jcls,
                name
        );
    }

    public String setterScript() {
        String setterPattern = "public void set{0}({1} {2})'{' this.{2}={2}; '}'";
        String jcls = cls.getSimpleName();
        if (Byte.class.equals(cls)) {
            jcls = "byte[]";
        }

        return MessageFormat.format(setterPattern,
                StringHelper.format(name, StringHelper.Format.upcaseFirstChar),
                jcls,
                name
        );
    }
    
    
    
}
