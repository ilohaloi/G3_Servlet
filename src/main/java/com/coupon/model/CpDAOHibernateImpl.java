package com.coupon.model;

import org.hibernate.Session;
import com.coupon.myutil.HibernateUtil;
import com.outherutil.Dao;

import java.util.List;

public class CpDAOHibernateImpl implements Dao {

	@Override
	public <T> void update(int id, T data) {
		// TODO Auto-generated method stub


	}
	@Override
	public int add(Cp cp) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.save(cp);
			session.getTransaction().commit();
			return 1; // 成功
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return -1; // 失敗

	}

	@Override
	public int update(Cp cp) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.update(cp);
			session.getTransaction().commit();
			return 1; // 成功
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return -1; // 失敗
	}

	@Override
	public int delete(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Cp cp = session.get(Cp.class, id);
			if (cp != null) {
				session.delete(cp);
				session.getTransaction().commit();
				return 1; // 成功刪除
			} else {
				return 0; // 找不到要刪除的優惠券
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return -1; // 刪除過程中發生錯誤
	}

	@Override
	public Cp findByPK(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Cp cp = session.get(Cp.class, id);
			session.getTransaction().commit();
			return cp;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null; // 找不到
	}

	@Override
	public List<Cp> getAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			List<Cp> list = session.createQuery("from Cp", Cp.class).list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null; // 找不到
	}
}

