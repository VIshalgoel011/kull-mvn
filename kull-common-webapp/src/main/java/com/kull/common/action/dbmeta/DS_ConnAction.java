/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.action.dbmeta;

import com.kull.common.action.DSAction;

/**
 *
 * @author lin
 */
public class DS_ConnAction extends DSAction{

    @Override
    protected String viewName() {
        return "dbmeta_conn";//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String title() {
        return "数据库连接管理"; //To change body of generated methods, choose Tools | Templates.
    }
    
}
