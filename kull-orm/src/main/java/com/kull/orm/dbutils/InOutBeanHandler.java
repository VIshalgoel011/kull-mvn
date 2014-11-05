/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.orm.dbutils;

import com.kull.orm.Session;
import com.kull.orm.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 *
 * @author lin
 */
public class InOutBeanHandler <T> extends BeanHandler<T> {

    protected Class<T> cls;
    
    public InOutBeanHandler(Class<T> type) {
        super(type);
        cls=type;
    }
    
     @Override
    public T handle(ResultSet rs) throws SQLException {
        if(cls.getName().contains("$")){
            try {
                return Utils.toBean(cls, rs);
            } catch (Exception ex) {
                throw new SQLException(ex);
            }
        }
        return super.handle(rs); //To change body of generated methods, choose Tools | Templates.
    }
    
}
