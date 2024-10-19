package com.laiutil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cipher.model.WebDataVo;
import com.laiutil.json.JsonDeserializerInterface;

import redis.clients.jedis.JedisPool;

@WebFilter("/*")
public class AuthFilterServlet implements Filter, JsonDeserializerInterface {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		WebUtil.accessAllallow(req, resp);

		if ("OPTIONS".equals(req.getMethod())) {
			chain.doFilter(request, response);
			return;
		}
		else {

			CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(req);

	        WebDataVo data = readJsonFromBufferedReader(cachedRequest.getReader(), WebDataVo.class);

	        if ("auth".equals(data.getAction())) {
	            AuthService aService = new AuthService();
	            JedisPool pool = (JedisPool) req.getServletContext().getAttribute("redis");

	            if (!aService.authCheck(pool, data)) {
	                // 如果认证失败，立即返回 401 错误
	                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                return;
	            }
	        }

	        // 在验证通过后才调用 chain.doFilter
	        chain.doFilter(cachedRequest, response);
		}
	}
}
