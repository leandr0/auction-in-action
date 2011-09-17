/**
 * 
 */
package com.fiap.leilao.web.filter;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;

/**
 * @author Leandro
 *
 */
public class HibernateSessionRequestFilter implements Filter {

	private static Log log = LogFactory.getLog(HibernateSessionRequestFilter.class);
	
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain)throws IOException, ServletException {

		try {
			log.debug("Starting a database transaction");
			
			HibernateUtil.openSession();
			//local.get().begin();
			//userTransaction.begin();
			//HibernateUtil.currentSession().beginTransaction();  
			// Call the next filter (continue request processing)
			chain.doFilter(request, response);

			// Commit and cleanup
			log.debug("Committing the database transaction");
			
			//userTransaction.commit();

		} catch (Throwable ex) {
		
			try {
				//userTransaction.rollback();
				
			} catch (Throwable rbEx) {
				log.error("Could not rollback transaction after exception!", rbEx);
			}
			throw new ServletException(ex);
		}finally{
			//HibernateUtil.closeCurrentSession();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("Initializing filter...");
		log.debug("Obtaining SessionFactory from static HibernateUtil singleton");

		try{

			Context ctx = new InitialContext();

			//entityManager = (EntityManager) ctx.lookup("leilaoEntityManager");

/*			userTransaction = (UserTransaction) ctx.lookup("UserTransaction");
			
			local.set(userTransaction);*/
			
		}catch (Throwable e) {
			System.err.println(e.getMessage());
		}
	}

	public void destroy() {}
	


}