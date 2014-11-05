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
import java.util.List;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 *
 * @author lin
 */
public class InOutBeanListHandler<T> extends BeanListHandler<T> {

    protected Class<T> cls;

    public InOutBeanListHandler(Class<T> type) {
        super(type);
        cls = type;
    }

    @Override
    public List<T> handle(ResultSet rs) throws SQLException {
        if (cls.getName().contains("$")) {
            try {
                return Utils.toBeanList(cls, rs);
            } catch (Exception ex) {
                throw new SQLException(ex);
            }
        }
        return super.handle(rs); //To change body of generated methods, choose Tools | Templates.
    }

}
