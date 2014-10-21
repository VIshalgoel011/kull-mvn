/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.action;


import com.kull.web.struts.DSActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.javaclub.jorm.Jorm;
import org.javaclub.jorm.jdbc.sql.Dialect;

/**
 *
 * @author lin
 */
public abstract class DSAction extends DSActionSupport implements Preparable{
  
    
    @Override
    protected String createPageSql(String dataSql, int start, int limit) {
         return dataSql;
    }

    @Override
    public void prepare() throws Exception {
        this.connection=Jorm.getConnection();
    }
    
    
    
}
