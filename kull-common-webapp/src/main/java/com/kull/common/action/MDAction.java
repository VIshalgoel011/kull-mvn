/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.action;


import com.kull.bean.JdbcBean;
import com.kull.common.Utils;
import com.kull.web.struts.OrmMDActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.sql.Connection;
import org.javaclub.jorm.Jorm;
import org.javaclub.jorm.Session;

/**
 *
 * @author lin
 */
public abstract class MDAction<M> extends OrmMDActionSupport<M> implements Preparable{

    
    @Override
    public void prepare() throws Exception {
        Connection conn=Utils.dbmeta();
        setJdbcBean(new JdbcBean(conn));
    }

    
    
    
}
