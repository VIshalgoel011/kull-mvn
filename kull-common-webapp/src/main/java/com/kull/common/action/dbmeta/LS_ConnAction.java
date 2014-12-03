/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.action.dbmeta;

import com.kull.common.action.LSAction;
import com.kull.web.Utils;
import java.io.IOException;

/**
 *
 * @author lin
 */
public class LS_ConnAction extends LSAction{

    @Override
    protected String viewName() {
        return "dbmeta_conn";//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String title() {
        return "数据库连接管理"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void pk() throws IOException{
        Utils.writeJson(response, this.pk);
    }
}
