package com.fiap.leilao.web.filter;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.hibernate.Session;

public class HibernateUtil {

	private static UserTransaction userTransaction;

	private static EntityManager entityManager;
	
	private static ThreadLocal<Session> sessions = new ThreadLocal<Session>();

	public static EntityManager getEntityManager(){
		if(entityManager == null){
			try{
				Context ctx = new InitialContext();
				entityManager = (EntityManager) ctx.lookup("leilaoEntityManager");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		 
		return entityManager;
	}

	public static UserTransaction getUserTransaction(){
		if(userTransaction == null){
			try{
				Context ctx = new InitialContext();
				userTransaction = (UserTransaction) ctx.lookup("UserTransaction");

			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return userTransaction;
	}

	public static Session openSession(){

		Session session = (Session) getEntityManager().getDelegate(); 
		//sessionFactory.openSession();
		sessions.set(session);
		return sessions.get();
	}

	public static Session currentSession(){
		return sessions.get();
	}

	public static void closeCurrentSession(){
		sessions.get().close();
		sessions.set(null);
	}

	public static void clearSession(){
		sessions.get().clear();
	}

	public static void flushSession(){
		sessions.get().flush();
	}

	public static void rollback(){
		sessions.get().getTransaction().rollback();
		sessions.get().getTransaction().begin();
	}
}