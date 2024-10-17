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

@WebFilter("/page/*")
public class AuthFilterServlet implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

    	HttpServletRequest req = (HttpServletRequest)request;
    	if("OPTIONS".equals(req.getMethod())) {
    		chain.doFilter(req, response);
    		return;
    	}
    	Cookie[] cookies = req.getCookies();

        System.out.println("Preprocessing request in AuthFilterServlet");

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 检查是否存在名为 "targetCookie" 的 Cookie
                if ("token".equals(cookie.getName())) {
                   System.out.println("have cookie");
                   break;
                }
            }
        }

        chain.doFilter(request, response);


        System.out.println("Postprocessing response in AuthFilterServlet");
    }
}
