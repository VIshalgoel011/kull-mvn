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

public abstract class HttpServletFilter implements Filter {

	

	@Override
	public final void doFilter(ServletRequest req, ServletResponse res,
			FilterChain fc) throws IOException, ServletException {
		// TODO Auto-generated method stub
		doFilter((HttpServletRequest)req, (HttpServletResponse)res, fc);
	}
	
	
	public abstract void doFilter(HttpServletRequest req, HttpServletResponse res,
			FilterChain fc) throws IOException, ServletException;

	

}
