package com.user_coup.model;

import java.util.List;

import org.hibernate.Session;

import com.coupon.myutil.HibernateUtil;

public class UserCoupDAOHibernateImpl implements UserCoupDAO {

	@Override
	public int add(UserCoupon userCoup) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.save(userCoup);
			session.getTransaction().commit();
			return 1; // 成功
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return -1; // 失敗
	}

	@Override
	public int update(UserCoupon userCoup) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.update(userCoup);
			session.getTransaction().commit();
			return 1; // 成功
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return -1; // 失敗
	}

	@Override
	public int delete(Integer no) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			UserCoupon uc = session.get(UserCoupon.class, no);
			if (uc != null) {
				session.delete(uc);
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
	public UserCoupon findByPK(Integer no) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			UserCoupon uc = session.get(UserCoupon.class, no);
			session.getTransaction().commit();
			return uc;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null; // 找不到
	}

	@Override
	public List<UserCoupon> getAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			List<UserCoupon> list = session.createQuery("from UserCoupon", UserCoupon.class).list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null; // 找不到
	}

}
