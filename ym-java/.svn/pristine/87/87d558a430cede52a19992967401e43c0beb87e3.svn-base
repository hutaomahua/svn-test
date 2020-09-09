package com.lyht.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@WebFilter(urlPatterns = "/*", filterName = "allFilter")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		String origin = ((HttpServletRequest)request).getHeader("Origin");
		res.setHeader("Access-Control-Allow-Origin", origin);
		res.setHeader("Access-Control-Allow-Methods", "*");
		res.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Token,Accept,Connection,User-Agent,Cookie");
		res.setHeader("Access-Control-Max-Age", "3628800");
		res.addHeader("Access-Control-Allow-Credentials", "true");
//		res.setCharacterEncoding("UTF-8");
		chain.doFilter(request, res);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
