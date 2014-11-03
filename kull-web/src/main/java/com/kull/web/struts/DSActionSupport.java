/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts;

import com.kull.ObjectHelper;
import com.kull.StringHelper;
import com.kull.orm.Session;

import com.kull.script.Html;

import com.kull.web.Utils;
import com.kull.web.anntation.DwrAjax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import net.sf.json.JSONObject;


/**
 *
 * @author lin
 */
public abstract class DSActionSupport extends AwareActionSupport {

    

    protected Integer start=0, limit=Integer.MAX_VALUE ;
    protected final String rowsName="rows",totalName="total",errTypeName="errtype",errMsgName="errmsg";

    public void setStart(Integer start) {
        this.start = start;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }



    protected abstract String title();

    
    
    protected Map<String,Object> rowEach(Map<String,Object> row) {
		return row;
   }

   

    

    @DwrAjax(method = DwrAjax.Method.post)
    public abstract void grid() throws IOException;
    
    	

        
       
}
