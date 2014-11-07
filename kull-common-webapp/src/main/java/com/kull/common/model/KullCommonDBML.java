/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.model;

import com.kull.orm.able.Ormable;
import com.kull.orm.annotation.OrmTable;


import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import com.kull.orm.able.Ormable;
import com.kull.able.Resultable;
import java.util.Set;
import com.kull.Clazz;
import com.kull.orm.annotation.OrmTable;
 public class KullCommonDBML {  



	public class dbmeta_conn { 



		protected Integer id;

		protected String name;

		protected String descp;

		protected String dialect;

		protected String driver;

		protected String user;

		protected String pwd;

		protected String url;

		protected Date lastcheck_at;

		protected Integer lastcheck_re;

		protected String lastcheck_msg;

		public Integer getId(){ return this.id; }
		public void setId(Integer id){ this.id=id; }

		public String getName(){ return this.name; }
		public void setName(String name){ this.name=name; }

		public String getDescp(){ return this.descp; }
		public void setDescp(String descp){ this.descp=descp; }

		public String getDialect(){ return this.dialect; }
		public void setDialect(String dialect){ this.dialect=dialect; }

		public String getDriver(){ return this.driver; }
		public void setDriver(String driver){ this.driver=driver; }

		public String getUser(){ return this.user; }
		public void setUser(String user){ this.user=user; }

		public String getPwd(){ return this.pwd; }
		public void setPwd(String pwd){ this.pwd=pwd; }

		public String getUrl(){ return this.url; }
		public void setUrl(String url){ this.url=url; }

		public Date getLastcheck_at(){ return this.lastcheck_at; }
		public void setLastcheck_at(Date lastcheck_at){ this.lastcheck_at=lastcheck_at; }

		public Integer getLastcheck_re(){ return this.lastcheck_re; }
		public void setLastcheck_re(Integer lastcheck_re){ this.lastcheck_re=lastcheck_re; }

		public String getLastcheck_msg(){ return this.lastcheck_msg; }
		public void setLastcheck_msg(String lastcheck_msg){ this.lastcheck_msg=lastcheck_msg; }

 	 } // end dbmeta_conn


	public class wx_driver { 



		protected Integer id;

		protected String project;

		protected String cmd;

		protected String respo;

		protected String respo_text_content;

		protected String respo_image_mediaid;

		protected Integer respo_news_articlecount;

		protected String respo_news_item0_title;

		protected String respo_news_item0_descp;

		protected String respo_news_item0_picurl;

		protected String respo_news_item0_url;

		protected String respo_news_item1_title;

		protected String respo_news_item1_descp;

		protected String respo_news_item1_picurl;

		protected String respo_news_item1_url;

		protected String respo_news_item2_title;

		protected String respo_news_item2_descp;

		protected String respo_news_item2_picurl;

		protected String respo_news_item2_url;

		protected String respo_news_item3_title;

		protected String respo_news_item3_descp;

		protected String respo_news_item3_picurl;

		protected String respo_news_item3_url;

		protected String respo_news_item4_title;

		protected String respo_news_item4_descp;

		protected String respo_news_item4_picurl;

		protected String respo_news_item4_url;

		protected String respo_news_item5_title;

		protected String respo_news_item5_descp;

		protected String respo_news_item5_picurl;

		protected String respo_news_item5_url;

		protected String respo_news_item6_title;

		protected String respo_news_item6_descp;

		protected String respo_news_item6_picurl;

		protected String respo_news_item6_url;

		protected String respo_news_item7_title;

		protected String respo_news_item7_descp;

		protected String respo_news_item7_picurl;

		protected String respo_news_item7_url;

		protected String respo_news_item8_title;

		protected String respo_news_item8_descp;

		protected String respo_news_item8_picurl;

		protected String respo_news_item8_url;

		protected String respo_news_item9_title;

		protected String respo_news_item9_descp;

		protected String respo_news_item9_picurl;

		protected String respo_news_item9_url;

		public Integer getId(){ return this.id; }
		public void setId(Integer id){ this.id=id; }

		public String getProject(){ return this.project; }
		public void setProject(String project){ this.project=project; }

		public String getCmd(){ return this.cmd; }
		public void setCmd(String cmd){ this.cmd=cmd; }

		public String getRespo(){ return this.respo; }
		public void setRespo(String respo){ this.respo=respo; }

		public String getRespo_text_content(){ return this.respo_text_content; }
		public void setRespo_text_content(String respo_text_content){ this.respo_text_content=respo_text_content; }

		public String getRespo_image_mediaid(){ return this.respo_image_mediaid; }
		public void setRespo_image_mediaid(String respo_image_mediaid){ this.respo_image_mediaid=respo_image_mediaid; }

		public Integer getRespo_news_articlecount(){ return this.respo_news_articlecount; }
		public void setRespo_news_articlecount(Integer respo_news_articlecount){ this.respo_news_articlecount=respo_news_articlecount; }

		public String getRespo_news_item0_title(){ return this.respo_news_item0_title; }
		public void setRespo_news_item0_title(String respo_news_item0_title){ this.respo_news_item0_title=respo_news_item0_title; }

		public String getRespo_news_item0_descp(){ return this.respo_news_item0_descp; }
		public void setRespo_news_item0_descp(String respo_news_item0_descp){ this.respo_news_item0_descp=respo_news_item0_descp; }

		public String getRespo_news_item0_picurl(){ return this.respo_news_item0_picurl; }
		public void setRespo_news_item0_picurl(String respo_news_item0_picurl){ this.respo_news_item0_picurl=respo_news_item0_picurl; }

		public String getRespo_news_item0_url(){ return this.respo_news_item0_url; }
		public void setRespo_news_item0_url(String respo_news_item0_url){ this.respo_news_item0_url=respo_news_item0_url; }

		public String getRespo_news_item1_title(){ return this.respo_news_item1_title; }
		public void setRespo_news_item1_title(String respo_news_item1_title){ this.respo_news_item1_title=respo_news_item1_title; }

		public String getRespo_news_item1_descp(){ return this.respo_news_item1_descp; }
		public void setRespo_news_item1_descp(String respo_news_item1_descp){ this.respo_news_item1_descp=respo_news_item1_descp; }

		public String getRespo_news_item1_picurl(){ return this.respo_news_item1_picurl; }
		public void setRespo_news_item1_picurl(String respo_news_item1_picurl){ this.respo_news_item1_picurl=respo_news_item1_picurl; }

		public String getRespo_news_item1_url(){ return this.respo_news_item1_url; }
		public void setRespo_news_item1_url(String respo_news_item1_url){ this.respo_news_item1_url=respo_news_item1_url; }

		public String getRespo_news_item2_title(){ return this.respo_news_item2_title; }
		public void setRespo_news_item2_title(String respo_news_item2_title){ this.respo_news_item2_title=respo_news_item2_title; }

		public String getRespo_news_item2_descp(){ return this.respo_news_item2_descp; }
		public void setRespo_news_item2_descp(String respo_news_item2_descp){ this.respo_news_item2_descp=respo_news_item2_descp; }

		public String getRespo_news_item2_picurl(){ return this.respo_news_item2_picurl; }
		public void setRespo_news_item2_picurl(String respo_news_item2_picurl){ this.respo_news_item2_picurl=respo_news_item2_picurl; }

		public String getRespo_news_item2_url(){ return this.respo_news_item2_url; }
		public void setRespo_news_item2_url(String respo_news_item2_url){ this.respo_news_item2_url=respo_news_item2_url; }

		public String getRespo_news_item3_title(){ return this.respo_news_item3_title; }
		public void setRespo_news_item3_title(String respo_news_item3_title){ this.respo_news_item3_title=respo_news_item3_title; }

		public String getRespo_news_item3_descp(){ return this.respo_news_item3_descp; }
		public void setRespo_news_item3_descp(String respo_news_item3_descp){ this.respo_news_item3_descp=respo_news_item3_descp; }

		public String getRespo_news_item3_picurl(){ return this.respo_news_item3_picurl; }
		public void setRespo_news_item3_picurl(String respo_news_item3_picurl){ this.respo_news_item3_picurl=respo_news_item3_picurl; }

		public String getRespo_news_item3_url(){ return this.respo_news_item3_url; }
		public void setRespo_news_item3_url(String respo_news_item3_url){ this.respo_news_item3_url=respo_news_item3_url; }

		public String getRespo_news_item4_title(){ return this.respo_news_item4_title; }
		public void setRespo_news_item4_title(String respo_news_item4_title){ this.respo_news_item4_title=respo_news_item4_title; }

		public String getRespo_news_item4_descp(){ return this.respo_news_item4_descp; }
		public void setRespo_news_item4_descp(String respo_news_item4_descp){ this.respo_news_item4_descp=respo_news_item4_descp; }

		public String getRespo_news_item4_picurl(){ return this.respo_news_item4_picurl; }
		public void setRespo_news_item4_picurl(String respo_news_item4_picurl){ this.respo_news_item4_picurl=respo_news_item4_picurl; }

		public String getRespo_news_item4_url(){ return this.respo_news_item4_url; }
		public void setRespo_news_item4_url(String respo_news_item4_url){ this.respo_news_item4_url=respo_news_item4_url; }

		public String getRespo_news_item5_title(){ return this.respo_news_item5_title; }
		public void setRespo_news_item5_title(String respo_news_item5_title){ this.respo_news_item5_title=respo_news_item5_title; }

		public String getRespo_news_item5_descp(){ return this.respo_news_item5_descp; }
		public void setRespo_news_item5_descp(String respo_news_item5_descp){ this.respo_news_item5_descp=respo_news_item5_descp; }

		public String getRespo_news_item5_picurl(){ return this.respo_news_item5_picurl; }
		public void setRespo_news_item5_picurl(String respo_news_item5_picurl){ this.respo_news_item5_picurl=respo_news_item5_picurl; }

		public String getRespo_news_item5_url(){ return this.respo_news_item5_url; }
		public void setRespo_news_item5_url(String respo_news_item5_url){ this.respo_news_item5_url=respo_news_item5_url; }

		public String getRespo_news_item6_title(){ return this.respo_news_item6_title; }
		public void setRespo_news_item6_title(String respo_news_item6_title){ this.respo_news_item6_title=respo_news_item6_title; }

		public String getRespo_news_item6_descp(){ return this.respo_news_item6_descp; }
		public void setRespo_news_item6_descp(String respo_news_item6_descp){ this.respo_news_item6_descp=respo_news_item6_descp; }

		public String getRespo_news_item6_picurl(){ return this.respo_news_item6_picurl; }
		public void setRespo_news_item6_picurl(String respo_news_item6_picurl){ this.respo_news_item6_picurl=respo_news_item6_picurl; }

		public String getRespo_news_item6_url(){ return this.respo_news_item6_url; }
		public void setRespo_news_item6_url(String respo_news_item6_url){ this.respo_news_item6_url=respo_news_item6_url; }

		public String getRespo_news_item7_title(){ return this.respo_news_item7_title; }
		public void setRespo_news_item7_title(String respo_news_item7_title){ this.respo_news_item7_title=respo_news_item7_title; }

		public String getRespo_news_item7_descp(){ return this.respo_news_item7_descp; }
		public void setRespo_news_item7_descp(String respo_news_item7_descp){ this.respo_news_item7_descp=respo_news_item7_descp; }

		public String getRespo_news_item7_picurl(){ return this.respo_news_item7_picurl; }
		public void setRespo_news_item7_picurl(String respo_news_item7_picurl){ this.respo_news_item7_picurl=respo_news_item7_picurl; }

		public String getRespo_news_item7_url(){ return this.respo_news_item7_url; }
		public void setRespo_news_item7_url(String respo_news_item7_url){ this.respo_news_item7_url=respo_news_item7_url; }

		public String getRespo_news_item8_title(){ return this.respo_news_item8_title; }
		public void setRespo_news_item8_title(String respo_news_item8_title){ this.respo_news_item8_title=respo_news_item8_title; }

		public String getRespo_news_item8_descp(){ return this.respo_news_item8_descp; }
		public void setRespo_news_item8_descp(String respo_news_item8_descp){ this.respo_news_item8_descp=respo_news_item8_descp; }

		public String getRespo_news_item8_picurl(){ return this.respo_news_item8_picurl; }
		public void setRespo_news_item8_picurl(String respo_news_item8_picurl){ this.respo_news_item8_picurl=respo_news_item8_picurl; }

		public String getRespo_news_item8_url(){ return this.respo_news_item8_url; }
		public void setRespo_news_item8_url(String respo_news_item8_url){ this.respo_news_item8_url=respo_news_item8_url; }

		public String getRespo_news_item9_title(){ return this.respo_news_item9_title; }
		public void setRespo_news_item9_title(String respo_news_item9_title){ this.respo_news_item9_title=respo_news_item9_title; }

		public String getRespo_news_item9_descp(){ return this.respo_news_item9_descp; }
		public void setRespo_news_item9_descp(String respo_news_item9_descp){ this.respo_news_item9_descp=respo_news_item9_descp; }

		public String getRespo_news_item9_picurl(){ return this.respo_news_item9_picurl; }
		public void setRespo_news_item9_picurl(String respo_news_item9_picurl){ this.respo_news_item9_picurl=respo_news_item9_picurl; }

		public String getRespo_news_item9_url(){ return this.respo_news_item9_url; }
		public void setRespo_news_item9_url(String respo_news_item9_url){ this.respo_news_item9_url=respo_news_item9_url; }

 	 } // end wx_driver

 } //end KullCommonDBM
