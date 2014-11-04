/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts.weixin;

import cn.songxinqiang.weixin4j.request.RequestTextMessage;
import cn.songxinqiang.weixin4j.response.ResponseBaseMessage;
import cn.songxinqiang.weixin4j.response.ResponseTextMessage;
import cn.songxinqiang.weixin4j.response.ResponseType;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lin
 */
public abstract class CliMPActionSupport extends MPActionSupport{
    
    public enum Type{
        text,handler,Image,voice
    }
    
    protected Type type=Type.text;
    
    protected String handler_name,text_pattern="",text_param="",image_url;

    protected abstract void _main(String[] args);
    
    
    
    @Override
    protected ResponseBaseMessage _handleText(RequestTextMessage req) throws Exception{
        String[] args=req.getContent().split(" ");
        
        _main(args);
        
        switch(type){
            case text:{
             ResponseTextMessage res=new ResponseTextMessage();
            
             res.setContent(MessageFormat.format(text_pattern, text_param.split(",")));
             return res;
            }
            case  handler:{
               MessageHandler handler;
       
                handler = (MessageHandler)Class.forName(handler_name).newInstance();
                return handler.exec(req);
           
            }
            
            
            default: break;
        }
        
       
        return null;
    }
    
    
    
    
}
