package com.kull.web.servlet;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GZipResponseWrapper extends HttpServletResponseWrapper{

	protected HttpServletResponse response;
	
	protected GZipOutputStream gZipOutputStream;
	
	protected PrintWriter writer;
	

	
	public GZipResponseWrapper(HttpServletResponse response) {
		super(response);
		// TODO Auto-generated constructor stub
		this.response=response;
	}

	@Override
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub
		gZipOutputStream.flush();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return super.getOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		if(writer==null){
			this.writer=new PrintWriter(new OutputStreamWriter(new GZipOutputStream(response),"UTF-8"));
		}
		return this.writer;
	}

	@Override
	public void setContentLength(int len) {
		// TODO Auto-generated method stub
		
	}
	
	public void clear(){
		
			try {
				if(gZipOutputStream!=null){
				  gZipOutputStream.close();
				}
				if(this.writer!=null){
					writer.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		}
	}

}
