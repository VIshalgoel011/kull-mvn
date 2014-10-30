package com.kull.orm.dialect;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;




/**
 * Dialect for HSQLDB
 * @author badqiu
 */
public class HSQLDialect extends Dialect{

	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsLimitOffset() {
		return true;
	}

	public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
		boolean hasOffset = offset>0;
		return new StringBuffer( sql.length() + 10 )
		.append( sql )
		.insert( sql.toLowerCase().indexOf( "select" ) + 6, hasOffset ? " limit "+offsetPlaceholder+" "+limitPlaceholder : " top "+limitPlaceholder )
		.toString();
	}

	



	@Override
	public String methodStrToDate(String regexp) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public Set<String> showTables(Connection conn) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<String> showViews(Connection conn)throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	
    
}
