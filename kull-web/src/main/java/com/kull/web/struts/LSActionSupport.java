/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts;

import com.kull.web.anntation.DwrAjax;

import java.io.IOException;
import java.util.Map;


/**
 *
 * @author lin
 */
public abstract class LSActionSupport extends AwareActionSupport {

    

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
