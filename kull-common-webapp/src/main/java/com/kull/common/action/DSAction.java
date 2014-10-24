/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.action;


import com.kull.common.Utils;
import com.kull.orm.dialect.Dialect;
import com.kull.orm.dialect.SqliteDialect;

import com.kull.web.struts.DSActionSupport;
import com.opensymphony.xwork2.Preparable;


/**
 *
 * @author lin
 */
public abstract class DSAction extends DSActionSupport implements Preparable{
  
    public final static  Dialect dialect=new SqliteDialect();
    
    protected String pk;

   

    
   
   
    
    
    
    
    @Override
    protected String createPageSql(String dataSql, int start, int limit) {
         return dialect.getLimitString(dataSql,start, limit);
    }

    @Override
    public void prepare() throws Exception {
        this.connection=Utils.dbmeta();
    }
    
    public void index(){
    }
    
}
