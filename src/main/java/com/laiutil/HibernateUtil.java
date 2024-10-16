package com.laiutil;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory = creataSessionFactory();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	private static SessionFactory creataSessionFactory() {
		try {
			registry = new StandardServiceRegistryBuilder().configure().build();

			SessionFactory sessionFactory = new MetadataSources(registry)
					.buildMetadata()
					.buildSessionFactory();

			return sessionFactory;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}

	}
	public static void Release() {

        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }

	}


}
