/**
 * 
 */
package com.fiap.leilao.business;

import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.business.event.LeilaoEvent;
import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.business.qualifier.AprovarLeilao;
import com.fiap.leilao.business.qualifier.ReprovarLeilao;
import com.fiap.leilao.domain.Item;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Produto;
import com.fiap.leilao.domain.bean.ItemBean;
import com.fiap.leilao.domain.bean.LeilaoBean;
import com.fiap.leilao.domain.type.StatusLeilao;

/**
 * Bean responsável por permitir que um usuário com o perfil ADMINISTRADOR<p>
 * tenha as possibilidades de autorizar e rejeitar um leilão
 * @author Leandro
 *
 */
@Stateless(mappedName = AutorizarLeilaoBean.JNDI_NAME)
@Remote(AutorizarLeilaoBean.class)
/*
 * O bean é anotado com @Asynchronous, pois para as ações que enviam e-mail
 * podem demorar , devido a disponibilidade da rede ou mesmo do provedor de e-mail.
 * Assim não prendendo as requisições
 */
@Asynchronous
public class ManagerAutorizarLeilaoBean implements AutorizarLeilaoBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8478803356735916111L;

	private static final Log LOG = LogFactory.getLog(ManagerAutorizarLeilaoBean.class);
	
	@EJB
	private LeilaoBean leilaBean;
	
	@EJB
	private ItemBean itemBean;

	/*
	 * @Inject - Anotação CDI do JEE 6 que permite injetar
	 * Um uma classe que que envia e-mail para leilões aprovados
	 * 
	 * @AprovarLeilao - Anotação CDI JEE 6 que permite criar qualificadores 
	 * (@Qualifier) de implemtentações de uma mesma Interface
	 * 
	 * Event<?> - cria um observer disponibilizado pelo container para injeção de 
	 * dependência de acordo com o @Qualifier 
	 */
	@Inject
	@AprovarLeilao
	private Event<LeilaoEvent> aprovarLeilao;
	
	/*
	 * @Inject - Anotação CDI do JEE 6 que permite injetar
	 * Um uma classe que que envia e-mail para leilões aprovados
	 * 
	 * @ReprovarLeilao - Anotação CDI JEE 6 que permite criar qualificadores 
	 * (@Qualifier) de implemtentações de uma mesma Interface
	 * 
	 * Event<?> - cria um observer disponibilizado pelo container para injeção de 
	 * dependência de acordo com o @Qualifier 
	 */
	@Inject
	@ReprovarLeilao
	private Event<LeilaoEvent> reprovarLeilao;
	
	/* (non-Javadoc)
	 * @see com.fiap.leilao.business.AutorizarLeilaoBean#atutorizarLeilao(com.fiap.leilao.domain.Leilao)
	 */
	@Override
	public void atutorizarLeilao(Leilao leilao) throws LeilaoBusinessException , IllegalArgumentException{

		if(leilao == null || !leilao.getStatus().equals(StatusLeilao.AGUARDANDO_AUTORIZACAO))
			throw new IllegalArgumentException("Status do leilao invalido");

		try{

			leilao.setStatus(StatusLeilao.INICADO);
			
			LOG.info("Atualizando status leilao para INICIADO");
			leilaBean.update(leilao);
			
			LOG.info("Chamando Observer para enviar email de aprovacao");
			aprovarLeilao.fire(new LeilaoEvent(leilao));
			
		}catch (Throwable e) {
			LOG.error("Erro ao autorizar leilao", e);
			throw new LeilaoBusinessException(e);
		}


	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.AutorizarLeilaoBean#buscarLeiloesPendentes()
	 */
	@Override
	public List<Leilao> buscarLeiloesPendentes() throws LeilaoBusinessException {
		try{
			LOG.info("Buscando leiloes pendentes");
			return leilaBean.pesquisaLeilaoStatus(StatusLeilao.AGUARDANDO_AUTORIZACAO);
		}catch (Exception e) {
			LOG.error("Erro ao buscar leiloes pendentes", e);
			throw new LeilaoBusinessException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.AutorizarLeilaoBean#buscarItensLeilao()
	 */
	@Override
	public List<Item> buscarItensLeilao(Produto produto) throws LeilaoBusinessException {
		try{
			LOG.info("Buscando itens do leilao");
			return itemBean.getItensProduto(produto);
		}catch (Exception e) {
			LOG.error("Erro ao buscar itens do leilao",e);
			throw new LeilaoBusinessException(e);
		}
	}

	@Override
	public void cancelarLeilao(Leilao leilao) throws LeilaoBusinessException {

		if(leilao == null || !leilao.getStatus().equals(StatusLeilao.AGUARDANDO_AUTORIZACAO))
			throw new IllegalArgumentException("Status do leilao invalido");

		try{

			leilao.setStatus(StatusLeilao.CANCELADO);
			
			LOG.info("Atualizando leilao para status de cancelado");
			leilaBean.update(leilao);
			
			LOG.info("Chamando Observer para enviar email de rejeicao");
			reprovarLeilao.fire(new LeilaoEvent(leilao));
			
		}catch (Throwable e) {
			LOG.error("Erro ao cancelar leilao",e);
			throw new LeilaoBusinessException(e);
		}
	}
}