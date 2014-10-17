package com.kull.web.servlet;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

	private String encoding;
    private	boolean enabled=true;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain fc) throws IOException, ServletException {
		// TODO Auto-generated method stub
		if(enabled){
			req.setCharacterEncoding(encoding);
			res.setCharacterEncoding(encoding);
		}
		fc.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {
		// TODO Auto-generated method stub
		encoding=fc.getInitParameter("encoding");
		encoding=encoding==null?"UTF-8":encoding;
	    enabled=!"false".equalsIgnoreCase(fc.getInitParameter("enabled"));
	}

}
