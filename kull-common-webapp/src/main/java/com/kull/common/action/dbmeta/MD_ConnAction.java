/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.action.dbmeta;

import com.kull.common.action.MDAction;
import com.kull.common.model.JormTable;

/**
 *
 * @author lin
 */
public class MD_ConnAction extends MDAction<JormTable.dbmeta_conn>{

    @Override
    protected JormTable.dbmeta_conn newModel() {
        return new JormTable().new dbmeta_conn();
    }
    
}
