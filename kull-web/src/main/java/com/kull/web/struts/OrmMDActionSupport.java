/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts;

import com.kull.bean.JdbcBean;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lin
 */ 
public abstract class OrmMDActionSupport<M> extends MDActionSupport<M>{
    
    protected JdbcBean jdbcBean;

    public void setJdbcBean(JdbcBean jdbcBean) {
        this.jdbcBean = jdbcBean;
    }

    @Override
    protected M readByPk(String pk) throws Exception{
          return   jdbcBean.load(newModel(), pk); 
        
    }

    @Override
    protected void _create(M m) throws Exception {
       jdbcBean.insert(m);
    }

    @Override
    protected void _update(M m) throws Exception {
       jdbcBean.update(m);
    }

    @Override
    protected void _delete(M m) throws Exception {
        jdbcBean.delete(m);
    }
    
    
    
    
}
