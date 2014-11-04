/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.action.wx;

import cn.songxinqiang.weixin4j.request.EventBaseMessage;
import cn.songxinqiang.weixin4j.request.RequestImageMessage;
import cn.songxinqiang.weixin4j.request.RequestLinkMessage;
import cn.songxinqiang.weixin4j.request.RequestLocationMessage;
import cn.songxinqiang.weixin4j.request.RequestVideoMessage;
import cn.songxinqiang.weixin4j.request.RequestVoiceMessage;
import cn.songxinqiang.weixin4j.response.ResponseBaseMessage;
import com.kull.common.model.KullCommonDBML;
import com.kull.web.struts.weixin.CliMPActionSupport;

/**
 *
 * @author lin
 */
public class MP_LSFAction extends CliMPActionSupport{

    public MP_LSFAction() {
       this.token="kull";
    }

    
    
    @Override
    protected void _main(String[] args) {
        String cmd=args[0];
        KullCommonDBML.mp_driver driver=new KullCommonDBML().new mp_driver();

        this.type=Type.valueOf(driver.getType());
        this.handler_name=driver.getHandler_name();
        this.image_url=driver.getImage_url();
        this.text_param=driver.getText_param();
        this.text_pattern=driver.getText_pattern();
        
        if("text".equals(cmd)){
            this.type=Type.text;
             this.text_param="sdfs,sferwqw";
             this.text_pattern="test mesage {0} {1}";
        }else if("handler".equals(cmd)){
            this.type=Type.handler;
        }
        
    }

    @Override
    protected ResponseBaseMessage _handleImage(RequestImageMessage msg) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ResponseBaseMessage _handleVoice(RequestVoiceMessage msg) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ResponseBaseMessage _handleVideo(RequestVideoMessage msg) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ResponseBaseMessage _handleLocation(RequestLocationMessage msg) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ResponseBaseMessage _handleEvent(EventBaseMessage event) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ResponseBaseMessage _handleLink(RequestLinkMessage msg) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
