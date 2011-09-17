/**
 * 
 */
package com.fiap.leilao.business;

import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.business.message.EnviarMessageLanceBean;
import com.fiap.leilao.domain.Item;
import com.fiap.leilao.domain.Lance;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Produto;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.bean.ItemBean;
import com.fiap.leilao.domain.bean.LeilaoBean;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;

/**
 * Bean que permite enviar lances aos leil�es
 * @author Leandro
 *
 */
/*
 * O bean � anotado com @Asynchronous, pois para as a��es que enviam mensagens JMS
 * podem demorar , devido a disponibilidade da rede ou do broker JMS
 * Assim n�o prendendo as requisi��es
 */
@Asynchronous
@Remote(EnviarLanceBean.class)
@Stateless(mappedName = EnviarLanceBean.JNDI_NAME)
public class ManagerEnviarLanceBean implements EnviarLanceBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2599603693648851218L;

	private static final Log LOG = LogFactory.getLog(ManagerEnviarLanceBean.class);
	
	/*Bean de negocio para comunica��o JMS*/
	@EJB
	private EnviarMessageLanceBean enviarMessageLanceBean;

	/*Bean da camada de dom�nio*/
	@EJB
	private LeilaoBean leilaoBean;
	
	/*Bean da camada de dom�nio*/
	@EJB
	private ItemBean itemBean;
	
	/* (non-Javadoc)
	 * @see com.fiap.leilao.business.EnviarLanceBean#enviarLance(com.fiap.leilao.domain.Lance, com.fiap.leilao.domain.Usuario)
	 */
	@Override
	public void enviarLance(Lance lance, Usuario usuario)throws LeilaoBusinessException , LeilaoDomainArgumentException{
		/*Valida��o do valor do lance */
		if(lance == null || lance.getValor().doubleValue() <= 0.0)
			throw new LeilaoDomainArgumentException("Lance inv�lido");
		/*Valida se o usu�rio que est� enviando o lance � o criandor do leil�o*/
		if(lance.getLeilao() == null || lance.getLeilao().getVendedor().getId().equals(usuario.getId()))
			throw new LeilaoDomainArgumentException("Usu�rio n�o pode efetuar o lance");
		/*Valida se o valor do lance � menor que o valor m�nimo do leil�o */
		if(lance.getLeilao() == null || lance.getLeilao().getValorInicial().doubleValue() > lance.getValor().doubleValue())
			throw new LeilaoDomainArgumentException("O valor do lance � menor que o valor m�nimo do leil�o");
		
		
		try{

			lance.setUsuario(usuario);
			/*Envia lance para a fila JMS*/
			enviarMessageLanceBean.enviarObjectMessage(lance);

		}catch (Exception e) {
			LOG.error("Erroa ao enviar lance", e);
			throw new LeilaoBusinessException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.EnviarLanceBean#buscarLeiloesAtivos(com.fiap.leilao.domain.Usuario)
	 */
	@Override
	public List<Leilao> buscarLeiloesAtivos(Usuario usuario) throws LeilaoBusinessException,LeilaoDomainArgumentException {
		try{
			return leilaoBean.pesquisaLeilaoEnviarLance(usuario);
		}catch (LeilaoDomainArgumentException e) {
			LOG.error("Erro ao buscar leiloes ativos" , e);
			throw e;
		}
		catch (Exception e) {
			LOG.error("Erro ao buscar leiloes ativos" , e);
			throw new LeilaoBusinessException(e.getMessage());
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.EnviarLanceBean#buscarItensProduto(com.fiap.leilao.domain.Produto)
	 */
	@Override
	public List<Item> buscarItensProduto(Produto produto)throws LeilaoBusinessException, LeilaoDomainArgumentException {
		try{
			return itemBean.getItensProduto(produto);
		}catch (LeilaoDomainArgumentException e) {
			LOG.error("Erro ao buscar itens do leilao" , e);
			throw e;
		}catch (Exception e) {
			LOG.error("Erro ao buscar itens do leilao" , e);
			throw new LeilaoBusinessException(e.getMessage());
		}
	}
}