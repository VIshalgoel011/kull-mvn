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
import cn.songxinqiang.weixin4j.request.RequestTextMessage;
import cn.songxinqiang.weixin4j.request.RequestVideoMessage;
import cn.songxinqiang.weixin4j.request.RequestVoiceMessage;
import cn.songxinqiang.weixin4j.response.ResponseBaseMessage;
import cn.songxinqiang.weixin4j.response.ResponseNewsMessage;
import cn.songxinqiang.weixin4j.response.model.Article;
import com.kull.Netz;
import com.kull.api.ItEbooks;
import com.kull.api.ItEbooks.Book;
import com.kull.web.struts.weixin.CliMPActionSupport;
import com.kull.web.struts.weixin.MPActionSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lin
 */
public class MP_GGYNAction extends MPActionSupport{
    
      public MP_GGYNAction() {
        this.token = "ggyn";
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

    @Override
    protected ResponseBaseMessage _handleText(RequestTextMessage msg) throws Exception {
        String[] args=msg.getContent().split(" ");
        String cmd=args[0];
        ResponseBaseMessage res=null;
        
        if("ebook".equals(cmd.toLowerCase())){
            String query=args[1];
            int page=args.length==3?Integer.valueOf(args[2]):1;
            ItEbooks.SearchResult sre=ItEbooks.search(query, page);
            ResponseNewsMessage newss=new ResponseNewsMessage();
            List<Article> articles=new ArrayList<Article>();
            for(int i=0;i<sre.getBooks().size()&&i<10;i++){
               Book book=sre.getBooks().get(i);
               String downloadurl=this.basePath()+"/"+this.namespace+"/"+this.action+"/downloadEbook/"+book.getID();
               Article article=new Article();
               article.setPicUrl(book.getImage());
               article.setUrl(downloadurl);
               article.setTitle(book.toString()+"\n"+downloadurl);
               article.setDescription(book.getSubTitle());
               articles.add(article);
            }
            newss.setArticles(articles);
            newss.setArticleCount(articles.size());
            res=newss;
        }
        
        return res;
    }

   

 
    protected String pk,namespace,action;

    public void setPk(String pk) {
        this.pk = pk;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    
    public void downloadEbook() throws IOException{
           Book book=ItEbooks.book(pk);
           response.sendRedirect(book.getDownload());
    }
    
}
