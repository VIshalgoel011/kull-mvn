/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.jdbc;

import java.text.MessageFormat;

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
}
