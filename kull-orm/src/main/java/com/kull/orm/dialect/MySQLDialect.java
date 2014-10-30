package com.kull.orm.dialect;

import java.text.MessageFormat;

import com.kull.orm.Database;
import com.kull.orm.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;







/**
 * @author badqiu
 */
public class MySQLDialect extends Dialect{

	public MySQLDialect(){
		this.database=Database.mysql;
		this.dateRegexp=DATE_REGEXP_MYSQL;
	}
	
	public boolean supportsLimitOffset(){
		return true;
	}
	
    public boolean supportsLimit() {   
        return true;   
    }  
    
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
        String sql="show tables";
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
  
}
