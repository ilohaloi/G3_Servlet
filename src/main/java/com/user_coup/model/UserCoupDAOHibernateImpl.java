package com.user_coup.model;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.coupon.myutil.HibernateUtil;

public class UserCoupDAOHibernateImpl implements UserCoupDAO {

	// Constants for coupon usage status
	public static final Integer USED = 1;
	public static final Integer UN_USED = 0;

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

	// 多條件查詢方法
	@Override
	public List<UserCoupon> getCoupons(Integer memberId, Integer couponId, Timestamp issueDateStart,
			Timestamp issueDateEnd, Boolean isUsed) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			StringBuilder hql = new StringBuilder("FROM UserCoupon WHERE 1=1");

			if (memberId != null)
				hql.append(" AND memb_id = :memberId");
			if (couponId != null)
				hql.append(" AND cp.coup_id = :couponId"); // 使用 cp.coup_id 關聯到 Cp 表
			if (issueDateStart != null && issueDateEnd != null) {
				hql.append(" AND coup_issue_date BETWEEN :issueDateStart AND :issueDateEnd");
			} else if (issueDateStart != null) {
				hql.append(" AND coup_issue_date >= :issueDateStart");
			} else if (issueDateEnd != null) {
				hql.append(" AND coup_expiry_date <= :issueDateEnd");
			}
			if (isUsed != null)
				hql.append(" AND coup_is_used = :isUsed");

			Query<UserCoupon> query = session.createQuery(hql.toString(), UserCoupon.class);
			if (memberId != null)
				query.setParameter("memberId", memberId);
			if (couponId != null)
				query.setParameter("couponId", couponId);
			if (issueDateStart != null)
				query.setParameter("issueDateStart", issueDateStart);
			if (issueDateEnd != null)
				query.setParameter("issueDateEnd", issueDateEnd);
			if (isUsed != null)
				query.setParameter("isUsed", isUsed ? USED : UN_USED);

			List<UserCoupon> resultList = query.list();
			session.getTransaction().commit();
			return resultList;

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}
}
