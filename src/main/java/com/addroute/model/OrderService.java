package com.addroute.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class OrderService {
    private SessionFactory sessionFactory;

    public OrderService() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public <TravelOrder> void saveOrder(TravelOrder order) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(order);

        session.getTransaction().commit();
        session.close();
    }

    public void close() {
        sessionFactory.close();
    }

	public void processOrder(com.addroute.model.TravelOrder order) {
		// TODO Auto-generated method stub
		
	}

	public ShipSchedule findShipScheduleByJourney(String journey) {
		// TODO Auto-generated method stub
		return null;
	}
}
