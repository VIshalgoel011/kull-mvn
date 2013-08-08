package com.kull.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GZipFilter extends HttpServletFilter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	




	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}






	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse res,
			FilterChain fc) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String encoding=req.getHeader("Accept-Encoding");
		if(encoding==null||!encoding.contains("gzip")){fc.doFilter(req, res);return;};
		GZipResponseWrapper gZipResponseWrapper=new GZipResponseWrapper(res);
		fc.doFilter(req, gZipResponseWrapper);
		gZipResponseWrapper.clear();
	}

}
