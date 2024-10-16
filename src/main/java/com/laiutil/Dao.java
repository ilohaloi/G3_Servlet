package com.laiutil;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

public interface Dao {

	public default void insert(Object data) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.save(data);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public <T> void update(int id, T data);

	public default <T> List<T> getAll(String tablename, Class<T> clazz){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		NativeQuery<T> query = session.createNativeQuery("SELECT * FROM "+ tablename ,clazz);
		List<T> list = query.list();

		session.getTransaction().commit();

		return list;
	}
	public default <T> T getByPk(int pk,Class<T> clazz) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		var prod =  session.get(clazz, pk);
		session.getTransaction().commit();
		return prod;
	}
}
