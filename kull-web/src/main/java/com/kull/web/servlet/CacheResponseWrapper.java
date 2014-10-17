package com.kull.web.servlet;


import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CacheResponseWrapper extends HttpServletResponseWrapper {

	private CharArrayWriter cacheWriter=new CharArrayWriter();
	
	public CacheResponseWrapper(HttpServletResponse response) {
		super(response);
		// TODO Auto-generated constructor stub
	}

	public CharArrayWriter getCacheWriter() {
		// TODO Auto-generated method stub
		return cacheWriter;
	}
	
	

	

	@Override
	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return new PrintWriter(cacheWriter);
	}

	public void setCacheWriter(CharArrayWriter cachaWriter) {
		this.cacheWriter = cachaWriter;
	}

	@Override
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub
		super.flushBuffer();
		cacheWriter.flush();
	}
	
	public void clear(){
		this.cacheWriter.close();
	}

}
