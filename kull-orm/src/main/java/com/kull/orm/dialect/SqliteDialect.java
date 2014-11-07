/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.orm.dialect;

import com.kull.Linq;
import com.kull.able.Foreachable;
import com.kull.orm.Session;
import com.kull.orm.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

/**
 *
 * @author lin
 */
public class SqliteDialect extends Dialect{

    
    
    
    
 public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
        if (offset > 0) {   
        	return sql + " limit "+offsetPlaceholder+","+limitPlaceholder; 
        } else {   
            return sql + " limit "+limitPlaceholder;
        }  
	}

	




	public String methodStrToDate(String regexp) {
		// TODO Auto-generated method stub
		String pattern="str_to_date({0},''{1}'')";
		return MessageFormat.format(pattern,regexp,dateRegexp);
	}   

    @Override
    public Set<String> showTables(Connection conn) throws SQLException {
        String sql="select name from sqlite_master where type='table' and name not in ('sqlite_sequence')  order by name;";
        
        Set<String> tables=new HashSet<>();
        PreparedStatement ps= conn.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
           tables.add(rs.getString(1));
        }
        Session.close(null, ps, rs);
        return tables;
    }

    @Override
    public Set<String> showViews(Connection conn) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Column> showColumns(Connection conn, String talble) throws SQLException {
        String sql="pragma table_info ('"+talble+"')";
        
        List<Column> cols= new ArrayList<>();
        List<Map<String,Object>> list=qr.query(conn,sql, new MapListHandler());
        
        for (Map<String, Object> map : list) {
            Column col=new Column();
            String colname=map.get("name").toString()
            , typeName=map.get("type").toString();
            int pk = (int)map.get("pk"),type;
            
            if("datetime".equals(typeName)){
               typeName="date";
            }else if("int".equals(typeName)){
               typeName=Integer.class.getSimpleName();
            }
            
            try {
             int i=typeName.indexOf("(");
             if(i>-1){
                 type=Utils.refJdbcTypesBy(typeName.substring(0,i));
             }else{
                 type=Utils.refJdbcTypesBy(typeName);
             }
             
            } catch (Exception ex) {
                throw new SQLException(ex);
            } 
            
            col.setName(colname);
            col.setPk(pk);
            col.setType(type);
            col.setTypeName(typeName);
            cols.add(col);
        }
        
        return cols;
    }
}
