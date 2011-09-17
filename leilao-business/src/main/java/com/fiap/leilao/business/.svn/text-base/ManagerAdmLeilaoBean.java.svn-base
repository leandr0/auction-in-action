/**
 * 
 */
package com.fiap.leilao.business;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.domain.Item;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.bean.LanceBean;
import com.fiap.leilao.domain.bean.LeilaoBean;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.exception.LeilaoDomainException;

/**
 * Bean responsável em permitir que um usuário 
 * gerencie os leilões  
 * @author Leandro
 *
 */
@Stateless(mappedName = AdmLeilaoBean.JNDI_NAME)
@Local(AdmLeilaoBean.class)
public class ManagerAdmLeilaoBean implements AdmLeilaoBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4100723964364647042L;

	private static final Log LOG = LogFactory.getLog(ManagerAdmLeilaoBean.class);
	
	@EJB
	private LeilaoBean leilaoBean;
	
	@EJB
	private LanceBean lanceBean;
	
	/* (non-Javadoc)
	 * @see com.fiap.leilao.business.AdmLeilaoBean#atualizarLeilao(com.fiap.leilao.domain.Leilao)
	 */
	@Override
	public Leilao atualizarLeilao(Leilao leilao) throws LeilaoBusinessException {
		
		try {
			/*
			 *Ao atualizar um leilao é necessário verificar se existe algum item 
			 *novo a ser persistido 
			 */
			for(Item item : leilao.getProduto().getItens()){
				if(item.getProduto() == null)
					item.setProduto(leilao.getProduto());
			}
			
			LOG.info("Atualizando Leilao");
			
			leilao = leilaoBean.update(leilao);			
			
		} catch (IllegalArgumentException e) {
			LOG.error("Erro ao atualizar leilao ", e);
			throw new LeilaoBusinessException(e.getMessage());
		} catch (LeilaoDomainException e) {
			LOG.error("Erro ao atualizar leilao ", e);
			throw new LeilaoBusinessException(e.getMessage());
		}
		
		return leilao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.AdmLeilaoBean#atualizarStatusLeilao(com.fiap.leilao.domain.Leilao)
	 */
	@Override
	public Leilao atualizarStatusLeilao(Leilao leilao) throws LeilaoBusinessException {
		try {
			
			LOG.info("Atualizando status leilao");
			
			leilao = leilaoBean.update(leilao);
			
		} catch (IllegalArgumentException e) {
			LOG.error("Erro ao atualizar status leilao");
			throw new LeilaoBusinessException(e.getMessage());
		} catch (LeilaoDomainException e) {
			LOG.error("Erro ao atualizar status leilao");
			throw new LeilaoBusinessException(e.getMessage());
		}
		
		return leilao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.AdmLeilaoBean#isPermitidoEditarLeilao(com.fiap.leilao.domain.Leilao)
	 */
	@Override
	public boolean isPermitidoEditarLeilao(Leilao leilao) throws LeilaoBusinessException {
		
		try {
			
			LOG.info("Verificando permissao de editar leilao");
			
			return !lanceBean.contemLanceLeilao(leilao);
			
		} catch (LeilaoDomainArgumentException e) {
			LOG.error("Erro verificar permissao de editar leilao");
			throw new LeilaoBusinessException(e.getMessage());
		} catch (LeilaoDomainException e) {
			LOG.error("Erro verificar permissao de editar leilao");
			throw new LeilaoBusinessException(e.getMessage());
		}
	}
}