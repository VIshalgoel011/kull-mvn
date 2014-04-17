package com.kull.bean;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;

import com.kull.StringHelper;


public class HttpClientBean  {
	
	
	
	protected HttpHost targetHost; 
	 protected BasicHttpContext localcontext = new BasicHttpContext();
	protected DefaultHttpClient defaultHttpClient;
	 
	 
	public HttpClientBean(DefaultHttpClient defaultHttpClient){
		this.defaultHttpClient=defaultHttpClient;
	}
	 
	 public String get(String url) throws MalformedChallengeException{	
		HttpGet httpGet=new HttpGet(url);
		String responseText="";
		try {
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			responseText = this.defaultHttpClient.execute(targetHost, httpGet,responseHandler, localcontext);
			this.defaultHttpClient.getConnectionManager().closeExpiredConnections();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return responseText;
		}	
		
	}
	
	 public String post(String url,List<NameValuePair> nvps) throws MalformedChallengeException{	
		HttpPost httpPost=new HttpPost(url);
		String responseText="";
		try {
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			responseText = this.defaultHttpClient.execute(targetHost, httpPost,responseHandler, localcontext);
			this.defaultHttpClient.getConnectionManager().closeExpiredConnections();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return responseText;
		}	
		
	}
	
	 public String post(String url,List<NameValuePair> nvps, Map<String, File> postFiles){
		HttpPost httpPost=new HttpPost(url);
		String responseText="";
		MultipartEntity entity = new MultipartEntity();
		
		for(NameValuePair nvp:nvps){
			try {
				String encodeVal=StringHelper.UrlCoding.utf8.encode(nvp.getValue());
				StringBody stringBody=new StringBody(encodeVal);
				entity.addPart(nvp.getName(),stringBody);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(Iterator<String> it=postFiles.keySet().iterator();it.hasNext();){
			String key=it.next();
			File file=postFiles.get(key);
			if(!file.exists()){continue;}
			FileBody fb = new FileBody(file);
			entity.addPart(key,fb );
		}
		try {
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			
			httpPost.setEntity(entity);
			responseText = this.defaultHttpClient.execute(targetHost, httpPost,responseHandler, localcontext);
			this.defaultHttpClient.getConnectionManager().closeExpiredConnections();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return responseText;
		}	
}
}
