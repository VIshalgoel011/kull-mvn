/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts.weixin;

import cn.songxinqiang.weixin4j.request.EventBaseMessage;
import cn.songxinqiang.weixin4j.request.RequestBaseMessage;
import cn.songxinqiang.weixin4j.request.RequestImageMessage;
import cn.songxinqiang.weixin4j.request.RequestLinkMessage;
import cn.songxinqiang.weixin4j.request.RequestLocationMessage;
import cn.songxinqiang.weixin4j.request.RequestTextMessage;
import cn.songxinqiang.weixin4j.request.RequestType;
import cn.songxinqiang.weixin4j.request.RequestVideoMessage;
import cn.songxinqiang.weixin4j.request.RequestVoiceMessage;
import cn.songxinqiang.weixin4j.response.ResponseBaseMessage;
import cn.songxinqiang.weixin4j.response.ResponseNewsMessage;
import cn.songxinqiang.weixin4j.response.ResponseTextMessage;
import cn.songxinqiang.weixin4j.util.WeixinMessageUtil;
import cn.songxinqiang.weixin4j.util.WeixinSignUtil;

import com.kull.web.struts.AwareActionSupport;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Map;

/**
 *
 * @author lin
 */
public abstract class MPActionSupport extends AwareActionSupport {

    protected String signature, timestamp, nonce, echostr, token;

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public void main() throws IOException {
        if (echostr != null) {
            _signature();
        } else {
            _handle();
        }
    }

    protected final void _signature() throws IOException {
        WeixinSignUtil signUtil = new WeixinSignUtil(token);
        boolean isOK = signUtil.checkSignature(signature, timestamp, nonce);
        if (isOK) {
            this.response.getWriter().write(echostr);
        }else{
           
        }
    }

    protected final void _handle() throws IOException {
        _WeixinMessageUtil wmu = new _WeixinMessageUtil();
        Map<String, String> xmlparam = null;
        String msgtype = "";
       
        ResponseBaseMessage res = null;
        try {
            xmlparam = wmu.parseXml(this.request.getInputStream());
            msgtype = xmlparam.get(RequestBaseMessage.MSG_FIELD_MsgType);
            RequestType requestType = RequestType.valueOf(msgtype);
            switch (requestType) {
                case text:
                    res = _handleText(wmu.parseTextMessage(xmlparam));
                    break;
                case image:
                    res = _handleImage(wmu.parseImageMessage(xmlparam));
                    break;
                case voice:
                    res = _handleVoice(wmu.parseVoiceMessage(xmlparam));
                    break;
                case video:
                    res = _handleVideo(wmu.parseVideoMessage(xmlparam));
                    break;
                case location:
                    res = _handleLocation(wmu.parseLocationMessage(xmlparam));
                    break;
                case link:
                    res = _handleLink(wmu.parseLinkMessage(xmlparam));
                    break;
                case event:
                    res = _handleEvent(wmu.parseSubscribeUnsubscribeEventMessage(xmlparam));
                    break;
                default:
                    break;
            }

        } catch (Exception ex) {
              ResponseTextMessage error=new ResponseTextMessage();
              error.setContent(MessageFormat.format("error:{0}", ex.getMessage()));
              res=error;
        }
        this.response.getWriter().write(wmu.messageToXml(res));
       
    }

    protected abstract ResponseBaseMessage _handleImage(RequestImageMessage msg);

    protected abstract ResponseBaseMessage _handleText(RequestTextMessage msg);

    protected abstract ResponseBaseMessage _handleVoice(RequestVoiceMessage msg);

    protected abstract ResponseBaseMessage _handleLink(RequestLinkMessage msg);

    protected abstract ResponseBaseMessage _handleVideo(RequestVideoMessage msg);

    protected abstract ResponseBaseMessage _handleLocation(RequestLocationMessage msg);

    protected abstract ResponseBaseMessage _handleEvent(EventBaseMessage event);

    
    
    private class _WeixinMessageUtil extends WeixinMessageUtil{

    
    
        public String  messageToXml(ResponseBaseMessage res){
            String xml="";
            if (res instanceof ResponseNewsMessage){
             xml= newsMessageToXml((ResponseNewsMessage)res);
            }
            else  {
            XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 瀵规墍鏈墄ml鑺傜偣鐨勮浆鎹㈤兘澧炲姞CDATA鏍囪
				boolean cdata = true;

				public void startNode(String name,
						@SuppressWarnings("rawtypes") Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
                xstream.alias("xml", res.getClass());
		xml= xstream.toXML(res);
            
            }
            
            
            
            return xml;
        }
    
    }
    
}
