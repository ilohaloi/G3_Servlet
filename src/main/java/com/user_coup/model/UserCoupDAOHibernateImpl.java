package com.user_coup.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Transient;
import javax.sound.midi.VoiceStatus;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.resource.beans.internal.FallbackBeanInstanceProducer;
import org.hibernate.stat.SecondLevelCacheStatistics;

import com.coupon.myutil.HibernateUtil;
import com.emp.model.EmpVo;

public class UserCoupDAOHibernateImpl implements UserCoupDAO {

	//Coupon
	public final Integer USED = 1;
	public final Integer UN_USED = 0;

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

	// 新增多條件查詢方法
	@Override
	public List<UserCoupon> getCoupons(Integer memberId, Integer couponId, Timestamp issueDateStart,
			Timestamp issueDateEnd, Boolean isUsed) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			// 使用 UserCoupon 類中對應的數據庫欄位名稱
			String hql = "FROM UserCoupon WHERE 1=1";

			if (memberId != null) {
				hql += " AND memb_id = :memberId";
				System.out.println("查詢條件 - memberId: " + memberId);
			}
			if (couponId != null) {
				hql += " AND coup_id = :couponId";
				System.out.println("查詢條件 - couponId: " + couponId);
			}

			if (issueDateStart != null && issueDateEnd != null) {
				hql += " AND coup_issue_date BETWEEN :issueDateStart AND :issueDateEnd"; // 包含開始與結束日期
				System.out.println("查詢條件 - issueDate (包含當日): " + issueDateStart + " 至 " + issueDateEnd);
			} else if (issueDateStart != null) {
				hql += " AND coup_issue_date >= :issueDateStart"; // 包含開始日期
				System.out.println("查詢條件 - issueDateStart (包含當日): " + issueDateStart);
			} else if (issueDateEnd != null) {
				hql += " AND coup_expiry_date <= :issueDateEnd"; // 包含結束日期
				System.out.println("查詢條件 - issueDateEnd (包含當日): " + issueDateEnd);
			}

			if (isUsed != null) {
				hql += " AND coup_is_used = :isUsed";
				System.out.println("查詢條件 - isUsed: " + isUsed);
			}

			System.out.println("最終查詢語句: " + hql);

			Query<UserCoupon> query = session.createQuery(hql, UserCoupon.class);

			// 設置參數
			if (memberId != null)
				query.setParameter("memberId", memberId);
			if (couponId != null)
				query.setParameter("couponId", couponId);
			if (issueDateStart != null)
				query.setParameter("issueDateStart", issueDateStart);
			if (issueDateEnd != null)
				query.setParameter("issueDateEnd", issueDateEnd);
			if (isUsed != null)
				query.setParameter("isUsed", isUsed ? 1 : 0);

			List<UserCoupon> resultList = query.list();
			session.getTransaction().commit();

			System.out.println("查詢結果數量: " + resultList.size());
			return resultList;

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public void updateCouponUsed(Integer no) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			UserCoupon coupon = session.get(UserCoupon.class, no);
			if(coupon!=null) {
				coupon.setCoup_is_used(USED);
				session.update(coupon);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

}