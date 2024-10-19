package com.laiutil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.laiutil.redis.RedisUtil;
import com.laiutil.vault.VaultUtil;


@WebListener
public class UtilInitServlet implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("redis", RedisUtil.getPool());
		System.out.println("redis 建立成功");
		if(VaultUtil.getVault()!=null) {
			sce.getServletContext().setAttribute("vault", VaultUtil.getVault());
			System.out.println("vault 建立成功");
		}


	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		RedisUtil.Release();
		sce.getServletContext().removeAttribute("redis");
		sce.getServletContext().removeAttribute("vault");
		HibernateUtil.Release();
	}
}
