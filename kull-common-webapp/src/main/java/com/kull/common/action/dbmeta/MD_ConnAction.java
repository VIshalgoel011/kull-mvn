/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.action.dbmeta;

import com.kull.common.action.MDAction;
import com.kull.common.model.KullCommonDBML;

/**
 *
 * @author lin
 */
public class MD_ConnAction extends MDAction<KullCommonDBML.dbmeta_conn>{

    @Override
    protected KullCommonDBML.dbmeta_conn newModel() {
        return new KullCommonDBML().new dbmeta_conn();
    }

    @Override
    protected Class<KullCommonDBML.dbmeta_conn> classM() {
       return KullCommonDBML.dbmeta_conn.class;
    }
    
}
