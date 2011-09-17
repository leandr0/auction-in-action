/**
 * 
 */
package com.fiap.leilao.domain.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.fiap.leilao.domain.Item;
import com.fiap.leilao.domain.Produto;
import com.fiap.leilao.domain.exception.LeilaoDomainException;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;

/**
 * @author Leandro
 *
 */
@Stateless
public class ManagerItemBean extends AbstractDomainBean<Item> implements ItemBean {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3251222197028821194L;

	/* (non-Javadoc)
	 * @see com.fiap.leilao.domain.bean.ItemBean#getItensProduto(com.fiap.leilao.domain.Produto)
	 */
	@Override
	public List<Item> getItensProduto(Produto produto)throws LeilaoDomainArgumentException, LeilaoDomainException {

		if(produto == null || produto.getId() == null)
			throw new LeilaoDomainArgumentException("Produto invalido para pesquisa");

		try{
			CriteriaBuilder 		criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Item> 	criteriaQuery 	= criteriaBuilder.createQuery(Item.class);
			Root<Item> 				root			= criteriaQuery.from(Item.class);
			Predicate				condition		= criteriaBuilder.equal(root.get("produto"),produto);

			criteriaQuery.where(condition);
			TypedQuery<Item> typedQuery = entityManager.createQuery(criteriaQuery);

			return typedQuery.getResultList();
		}catch (Exception e) {
			throw new LeilaoDomainException(e);
		}
	}

}
