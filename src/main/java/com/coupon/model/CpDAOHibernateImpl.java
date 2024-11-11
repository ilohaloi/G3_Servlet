package com.coupon.model;

import org.hibernate.Session;
import org.hibernate.query.Query;
import com.coupon.myutil.HibernateUtil;
import java.sql.Timestamp;
import java.util.List;

public class CpDAOHibernateImpl implements CpDAO {

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

	// 查找符合發放條件的優惠券
	@Override
	public List<Cp> findCouponsToBeIssued(Timestamp today) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			String hql = "FROM Cp WHERE coup_issue_date <= :today AND coup_expiry_date >= :today";
			Query<Cp> query = session.createQuery(hql, Cp.class);
			query.setParameter("today", today);
			List<Cp> resultList = query.list();
			session.getTransaction().commit();
			System.out.println("自動發放範圍內的優惠券數量: " + resultList.size());
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	// 新增：根據 coup_id 查找 Cp
	@Override
	public List<Cp> findByCoupId(Integer coupId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			String hql = "FROM Cp WHERE coup_id = :coupId";
			Query<Cp> query = session.createQuery(hql, Cp.class);
			query.setParameter("coupId", coupId);
			List<Cp> resultList = query.list();
			session.getTransaction().commit();
			System.out.println("查詢到的優惠券數量: " + resultList.size());
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}
}
