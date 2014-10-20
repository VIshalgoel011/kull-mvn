/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts;


import com.kull.LinqHelper;
import com.kull.ObjectHelper;
import com.kull.StringHelper;
import com.kull.util.Resultable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;



/**
 *
 * @author lin
 */
public abstract class DSMActionSupport<M> extends DSActionSupport{
    
    
    protected String rows,pk,pkname="id";

    public void setRows(String rows) {
        this.rows = rows;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    protected abstract void _create(M m);
   protected abstract void _delete(M m);
   protected abstract void _update(M m);


        protected  boolean onCreate( M m, Resultable re,List<M> sources,List<M> submits  ){return true;}
        protected boolean onUpdate( M m, Resultable re, List<M> sources, List<M> submits){return true;}
        protected  boolean onDelete( M m, Resultable re, List<M> sources, List<M> submits){return true;}
    
        
        protected abstract List<M> loadSource();

        protected  List<M> loadSubmit(String rows) throws Exception
        {
            JSONArray jarr = JSONArray.fromObject(rows);

            List<M> submits=new ArrayList<M>();
            for (int i=0;i<jarr.size();i++ )
            {
                
                    M m0 = newM(),m1 = toM(jarr.getJSONObject(i));
                    ObjectHelper.cp(m1,m0);
                    submits.add(m0);
                
            }
            return submits;

        } 
    
    public final void save() throws IOException{
         Resultable re=new Resultable();
          
            List<M> sources = null,submits=null; 
            try
            {
               sources = loadSource();
                submits = loadSubmit(rows);
                for (int i=0;i<sources.size();i++)
                {
                    M  source=sources.get(i),
                        target = selectBy(source, submits);
                    if (target == null)
                    {
                        if (!onDelete( source, re, sources, submits))
                            throw new Exception(re.getMsg());
                        _delete(source);
                    }
                    else
                    {
                        ObjectHelper.cp(target, source);
                        if (!onUpdate( source,  re, sources, submits))
                            throw new Exception(re.getMsg());
                        _update(target);
                    }
                }

                for (int i = 0; i < submits.size(); i++)
                {
                    M submit = submits.get(i);
                    
                    boolean isNew = this.isNew(submit);
                    if (isNew)
                    {
                        M nm=newM() ;
                        ObjectHelper.attr( submit, pk, null);
                        //ObjectHelper.attr( nm, submit);
                        ObjectHelper.cp(submit, nm);
                        if (!onCreate( nm, re,sources,submits))
                            throw new Exception(re.getMsg());
                        _create(nm);
                    }
                }

            }
            catch (Exception ex)
            {
                re.setMsg(ex.getMessage());
                re.setCode(Resultable.CODE_EXCEPTION);
            }
           
           this.response.getWriter().write(re.toString());
    }
    
    
    

    protected abstract M newM();
    
   protected  M toM(JSONObject json)
    {
       return (M)JSONSerializer.toJava(json);
    }
    
    protected  M selectBy(M condition, List<M> list) throws Exception
    {
            return LinqHelper.selectByPK(pkname,ObjectHelper.attr(condition, pk), list);
    }
    
   protected  int indexOf(M m, List<M> list) throws Exception
    {
            return LinqHelper.indexOfByPk(pkname,ObjectHelper.attr(m,pk), list);
   }
    
      
   protected  boolean isNew(M m) throws Exception
        {
            Object pkval = ObjectHelper.attr(m, pkname);
            boolean isNew = isUndefined(pkval);
            if (isNew) return isNew;
            try
            {
                int intpk = Integer.parseInt(pkval.toString());
                isNew = intpk <= 0;
            }
            catch(Exception ex)
            {
                isNew = false;
            }
            return isNew;
        }

    protected boolean isUndefined(Object pkval) {
        return pkval==null||StringHelper.isBlank(pkval.toString());
    }
   

   
}
