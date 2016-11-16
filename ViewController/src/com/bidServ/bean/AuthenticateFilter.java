package com.bidServ.bean;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticateFilter implements Filter {
    public AuthenticateFilter() {
        super();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Implement this method
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        // TODO Implement this method
       String username =(String)( (HttpServletRequest)servletRequest).getSession().getAttribute("user");
        System.out.println("====================doFilter "+username);
        System.out.println("====================doFilter "+( (HttpServletRequest)servletRequest).getPathInfo());
        if(username == null){
            ((HttpServletResponse) servletResponse).sendRedirect("/bidServ/faces/Login");
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
        

    }

    @Override
    public void destroy() {
        // TODO Implement this method
    }
}
