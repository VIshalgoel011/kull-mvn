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
import com.kull.web.struts.weixin.MPActionSupport;

/**
 *
 * @author lin
 */
public class MP_LSFAction extends CliMPActionSupport{

    public MP_LSFAction() {
       this.token="kull";
    }

    
    
    @Override
    protected void init(String cmd) {
        KullCommonDBML.mp_driver driver=null;
        this.type=Type.valueOf(driver.getType());
        this.handler_name=driver.getHandler_name();
        this.image_url=driver.getImage_url();
        this.text_param=driver.getText_param();
        this.text_pattern=driver.getText_pattern();
    }

    @Override
    protected ResponseBaseMessage _handleImage(RequestImageMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ResponseBaseMessage _handleVoice(RequestVoiceMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ResponseBaseMessage _handleLink(RequestLinkMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ResponseBaseMessage _handleVideo(RequestVideoMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ResponseBaseMessage _handleLocation(RequestLocationMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ResponseBaseMessage _handleEvent(EventBaseMessage event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
