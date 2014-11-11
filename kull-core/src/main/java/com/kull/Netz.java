package com.kull;









import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;



import com.kull.able.Resultable;
import org.apache.commons.io.IOUtils;









public class Netz {

    
       private static InputStream get(String url,String... nvs) throws MalformedURLException, IOException{
           url+=url.contains("?")?"&":"?";
           for(int i=0,j=1;j<nvs.length;i+=2,j+=2){
               url+=nvs[i]+"="+nvs[j]+"&";
           }
           return new URL(url).openStream();
       }
       
        public static String getString(String url,String... nvs) throws MalformedURLException, IOException{
           InputStream in=get(url, nvs);
           String str= IOUtils.toString(in);
           IOUtils.closeQuietly(in);
           return str;
       }
	
	
		
 
        
        
	
	
	
	public static Resultable doPost(HttpPost post){
		DefaultHttpClient httpclient=new DefaultHttpClient();
		String context="";
		HttpResponse response=null;
		InputStream is=null;
		Resultable resultModel=new Resultable();
		try {
		    response = httpclient.execute(post);
		    is = response.getEntity().getContent();
			context=IOUtils.toString(is);
			httpclient.clearRequestInterceptors();
            httpclient.clearResponseInterceptors();
			is.close();
			resultModel.setCode(Resultable.CODE_SUCCESS);
			resultModel.setMsg(context);
		} catch (Exception e) {
			 resultModel=	Resultable.create(e);
		}
		return resultModel;
	}
	
	public static Resultable doPost(String url,Map<String, Object> params, Map<String, File> postFiles){
		List<NameValuePair> nvps=new ArrayList<NameValuePair>();
		for(Iterator<String> it=params.keySet().iterator();it.hasNext();){
			String key=it.next();
			Object object=params.get(key).toString();
			if(object==null)continue;
			nvps.add(new BasicNameValuePair(key, object.toString()));
		}
	   return doPost(url, nvps, postFiles);
	}
	
	public static Resultable doPost(String url,List<NameValuePair> nvps, Map<String, File> postFiles){
		
		HttpPost httpPost=new HttpPost(url);
		String responseText="";
		MultipartEntity entity = new MultipartEntity();
		
		for(NameValuePair nvp:nvps){
			try {
				//String encodeVal=StringUtil.encodeUtf8(nvp.getValue());
				StringBody stringBody=new StringBody(nvp.getValue());
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
		
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			
			httpPost.setEntity(entity);
			return doPost(httpPost);
	
		
		
	}
	
    public static InputStream getSoapInputStream(String host, String url)
    {
        InputStream is = null;

        try {
			URL U = new URL(url);
			URLConnection conn = U.openConnection();
			conn.setRequestProperty("Host", host);
			conn.connect();
			is = conn.getInputStream();
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        return is;
    }
	
   
	
}
