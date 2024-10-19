package com.laiutil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/page/*")
public class AuthFilterServlet implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("Preprocessing request in AuthFilterServlet");
        WebUtil.accessAllallow((HttpServletRequest)request, (HttpServletResponse)response);
        chain.doFilter(request, response);
        System.out.println("Postprocessing response in AuthFilterServlet");
    }
}
