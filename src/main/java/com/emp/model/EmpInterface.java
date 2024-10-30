package com.emp.model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.outherutil.HibernateUtil;

public interface EmpInterface{
	public default void insert(EmpVo emp) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.save(emp);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	/**
	 * 只更新名子
	 */
	public default void update(int id, EmpVo emp) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			session.beginTransaction();
			EmpVo oldEmp = session.get(EmpVo.class, id);
			if(oldEmp!=null) {
				oldEmp.setName(emp.getName());
				session.update(oldEmp);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	public default List<EmpVo> getAll(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		NativeQuery<EmpVo> query = session.createNativeQuery("SELECT * FROM employee_data", EmpVo.class);
		List<EmpVo> list = query.list();
		session.getTransaction().commit();
		return list;
	}
	public default EmpVo getByAccount(String account){

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		NativeQuery<EmpVo> query = session.createNativeQuery("SELECT * FROM employee_data where empo_account = ?0", EmpVo.class);
		query.setParameter(0, account);
		List<EmpVo> list = query.list();
		session.getTransaction().commit();
		return list.size()>0? list.get(0): null;
	}
}
