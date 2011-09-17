/**
 * 
 */
package com.fiap.leilao.domain.bean;

import java.io.Serializable;

import com.fiap.leilao.domain.EntityBasic;
import com.fiap.leilao.domain.exception.LeilaoDomainException;

/**
 * Interface para operações básicas com as entidades de domínio
 * @author Leandro
 *
 */
public interface AbstractDomain <E extends EntityBasic<?>> extends Serializable{

	/**
	 * Persiste uma entidade na base de dados
	 * @param entity {@link EntityBasic}
	 * @return {@link EntityBasic}
	 * @throws IllegalArgumentException
	 * @throws LeilaoDomainException
	 */
	public E insert(E entity)throws IllegalArgumentException , LeilaoDomainException;
	
	/**
	 * Exclui uma entidade da base de dados
	 * @param entity {@link EntityBasic}
	 * @throws IllegalArgumentException
	 * @throws LeilaoDomainException
	 */
	public void delete(E entity)throws IllegalArgumentException , LeilaoDomainException;
	
	/**
	 * Pequisa um entidade pela chave primária
	 * @param entity {@link EntityBasic}
	 * @return {@link EntityBasic}
	 * @throws IllegalArgumentException
	 * @throws LeilaoDomainException
	 */
	public E find(E entity)throws IllegalArgumentException , LeilaoDomainException;
	
	/**
	 * Atualiza uma entidade pela chave primária
	 * @param entity {@link EntityBasic}
	 * @return {@link EntityBasic}
	 * @throws IllegalArgumentException
	 * @throws LeilaoDomainException
	 */
	public E update(E entity)throws IllegalArgumentException , LeilaoDomainException;
}