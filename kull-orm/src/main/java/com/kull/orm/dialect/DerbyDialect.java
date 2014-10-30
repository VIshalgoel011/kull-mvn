package com.kull.orm.dialect;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;




public class DerbyDialect extends Dialect{

	public boolean supportsLimit() {
		return false;
	}

	public boolean supportsLimitOffset() {
		return false;
	}

	public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
		throw new UnsupportedOperationException( "paged queries not supported" );
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
    public Set<String> showViews(Connection conn) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
