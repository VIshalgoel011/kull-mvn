/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaclub.jorm.jdbc.sql;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.javaclub.jorm.common.Strings;



/**
 *
 * @author lin
 */
public class Sqlite3Dialect extends Dialect{

    protected static final String LIMIT = "LIMIT";
    
    public boolean hasLimit(final String sql) {
		String regex = "(select\\s+)(.+\\s+)(from\\s+)(.+\\s+)([^\\(]+)(\\s+limit\\s+)([^\\)]+)";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sql);
		if(matcher.find()) {
			String matched = matcher.group();
			if(!matched.endsWith(")") && matched.equalsIgnoreCase(sql)) {
				return true;
			}
		} 
		
		String lower = Strings.lowerCase(sql);
		if (Strings.count(lower, "select") == 1
				&& Strings.count(lower, "limit") == 1) {
			return true;
		}
		return false;
	}
    
    @Override
    public String pageable(String sql, int start, int limit) {
        		if (hasLimit(sql)) {
			int lastLimitPos = Strings.upperCase(sql).lastIndexOf(LIMIT);
			return sql.substring(0, lastLimitPos) + " " + LIMIT + " " + start
					+ ", " + limit;
		}
		return sql + " " + LIMIT + " " + start + ", " + limit;
    }

    @Override
    public <T> SqlParams<T> pageable(SqlParams<T> sqlParams) {
        SqlParams<T> r = sqlParams.copy();
		final String sql = this.generateSql(sqlParams, true);
		if(this.supportsLimit() && sqlParams.getMaxResults() > 0) {
			r.setSql(pageable(sql, sqlParams.getFirstResult(), sqlParams.getMaxResults()));
		} else {
			r.setSql(sql);
		}
		return r;
    }
    
}
