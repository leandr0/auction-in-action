/**
 * 
 */
package com.fiap.leilao.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.business.message.EnviarMessageMailBean;
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
//@Asynchronous
public class ManagerAutorizarLeilaoBean implements AutorizarLeilaoBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5444951532545265223L;

	private static final Log LOG = LogFactory.getLog(ManagerAutorizarLeilaoBean.class);
	
	@EJB
	private LeilaoBean leilaBean;
	
	@EJB
	private ItemBean itemBean;

	@EJB
	private EnviarMessageMailBean enviarMessageMailBean;
	
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
			
			//send to queue
			//in the mdb do a async method to send e-mail
			
			LOG.info("Chamando Observer para enviar email de aprovacao");
			enviarMessageMailBean.sendMailApproved(leilao);
			
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
			enviarMessageMailBean.sendMailUnapprived(leilao);
			
		}catch (Throwable e) {
			LOG.error("Erro ao cancelar leilao",e);
			throw new LeilaoBusinessException(e);
		}
	}
}