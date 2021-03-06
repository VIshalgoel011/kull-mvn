package com.kull.web.servlet;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;

public class GZipOutputStream extends ServletOutputStream {

	
	private HttpServletResponse response;
	
	private GZIPOutputStream gzipOutputStream;
	
	private ByteArrayOutputStream byteArrayOutputStream;
	
	public GZipOutputStream(HttpServletResponse response) throws IOException{
		this.response=response;
		this.byteArrayOutputStream=new ByteArrayOutputStream();
		gzipOutputStream=new GZIPOutputStream(this.byteArrayOutputStream);
		
	}
	
	@Override
	public void write(int arg0) throws IOException {
		// TODO Auto-generated method stub
		gzipOutputStream.write(arg0);
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		gzipOutputStream.flush();
		byte[] content=byteArrayOutputStream.toByteArray();
		response.addHeader("Content-Encoding", "gzip");
		response.addHeader("Content-Length",Integer.toString(content.length));
		ServletOutputStream out=response.getOutputStream();
		out.write(content);
		out.close();
	}

    @Override
    public boolean isReady() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setWriteListener(WriteListener wl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	
	

}
