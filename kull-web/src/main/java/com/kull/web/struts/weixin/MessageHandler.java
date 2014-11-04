/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts.weixin;

import cn.songxinqiang.weixin4j.request.RequestBaseMessage;
import cn.songxinqiang.weixin4j.response.ResponseBaseMessage;

/**
 *
 * @author lin
 */
public interface MessageHandler {
    
    
    public ResponseBaseMessage exec(RequestBaseMessage req ) throws Exception;
    
}
