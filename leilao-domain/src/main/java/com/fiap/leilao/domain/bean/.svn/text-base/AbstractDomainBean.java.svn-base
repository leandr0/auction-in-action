/**
 * 
 */
package com.fiap.leilao.domain.bean;

import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import com.fiap.leilao.domain.EntityBasic;
import com.fiap.leilao.domain.exception.LeilaoDomainException;

/**
 * @author Leandro
 *
 */
public abstract class AbstractDomainBean<E extends EntityBasic<?>> implements AbstractDomain<E>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4282295776128437957L;

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Resource(mappedName = "java:LeilaoDS")
	protected DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.domain.bean.AbstractDomain#insert(com.fiap.leilao.domain.EntityBasic)
	 */
	@Override
	public E insert(E entity) throws IllegalArgumentException , LeilaoDomainException{

		if(entity == null)
			throw new IllegalArgumentException("Entidade nula");
		try{
			entityManager.persist(entity);
			return entity;
		}catch (Throwable e) {
			throw new LeilaoDomainException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.domain.bean.AbstractDomain#delete(com.fiap.leilao.domain.EntityBasic)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(E entity) throws IllegalArgumentException , LeilaoDomainException{

		if(entity == null || entity.getId() == null)
			throw new IllegalArgumentException("Entidade inválida");

		try{
			entity = find(entity);
			entityManager.remove(entity);
		}catch (Throwable e) {
			throw new LeilaoDomainException(e);
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.domain.bean.AbstractDomain#find(com.fiap.leilao.domain.EntityBasic)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E find(E entity) throws IllegalArgumentException , LeilaoDomainException{
		if(entity == null || entity.getId() == null)
			throw new IllegalArgumentException("Entidade inválida");
		try{
			return (E) entityManager.find(entity.getClass(), entity.getId());
		}catch (Exception e) {
			throw new LeilaoDomainException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.domain.bean.AbstractDomain#update(com.fiap.leilao.domain.EntityBasic)
	 */
	@Override
	public E update(E entity) throws IllegalArgumentException , LeilaoDomainException{

		if(entity == null || entity.getId() == null)
			throw new IllegalArgumentException("Entidade inválida");
		try{
			return entityManager.merge(entity);
		}catch (Exception e) {
			throw new LeilaoDomainException(e);
		}
	}

	/**
	 * 
	 * @param entityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}