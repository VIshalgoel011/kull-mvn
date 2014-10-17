package com.kull.web.servlet;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URLEncoder;


import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CacheFilter extends HttpServletFilter {

	
	private ServletContext servletContext;
	private File dir;
	private long timeout=Long.MAX_VALUE;
	private String encoding;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void init(FilterConfig fc) throws ServletException {
		// TODO Auto-generated method stub
		servletContext=fc.getServletContext();
		dir=(File)servletContext.getAttribute("javax.servlet.context.tempdir");
	    timeout=new Long(fc.getInitParameter("timeout"));
	    encoding=fc.getInitParameter("encoding");
	    encoding=encoding==null?"UTF-8":encoding;
	}
	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse res,
			FilterChain fc) throws IOException, ServletException {
		// TODO Auto-generated method stub
		if("post".equalsIgnoreCase(req.getMethod())){
			fc.doFilter(req, res);
			return;
		}
		String uri=req.getRequestURI();
		uri=uri.replace(req.getContextPath()+"/", "");
		uri=uri.trim().length()==0?"index.jsp":uri;
		uri=req.getQueryString()==null?uri:(uri+"?"+req.getQueryString());
		File cacheFile=new File(dir,URLEncoder.encode(uri, encoding));
		if(!cacheFile.exists()||cacheFile.length()==0||cacheFile.lastModified()<System.currentTimeMillis()-timeout){
			CacheResponseWrapper cacheResponseWrapper=new CacheResponseWrapper(res);
			fc.doFilter(req, cacheResponseWrapper);
			char[] chars=cacheResponseWrapper.getCacheWriter().toCharArray();
		    dir.mkdir();
		    cacheFile.createNewFile();
		    Writer writer=new OutputStreamWriter(new FileOutputStream(cacheFile),encoding);
		    writer.write(chars);
		    writer.close();
		}
		String mimeType=servletContext.getMimeType(req.getRequestURI());
		res.setContentType(mimeType);
		Reader reader=new InputStreamReader(new FileInputStream(cacheFile),encoding);
		StringBuffer buffer=new StringBuffer();
		char[] cbuf=new char[1024];
		int len;
		while((len=reader.read(cbuf))>-1){
			buffer.append(cbuf,0,len);
		}
		res.getWriter().write(buffer.toString());
	}
	
	

}
