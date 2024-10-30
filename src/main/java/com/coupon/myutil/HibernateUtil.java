package com.coupon.myutil;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
	private static StandardServiceRegistry registry;
	private static final SessionFactory sessionFactory = createSessionFactory();

	// 獲取 SessionFactory 的方法
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	// 創建 SessionFactory 的靜態方法
	private static SessionFactory createSessionFactory() {
		try {
			registry = new StandardServiceRegistryBuilder().configure() // 讀取 Hibernate 設定檔
					.build();

			return new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}

	// 關閉 Hibernate 的 Registry
	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
