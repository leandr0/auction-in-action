/**
 * 
 */
package com.fiap.leilao.business;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.business.exception.LeilaoBusinessArgumentException;
import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.domain.Item;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.bean.LeilaoBean;
import com.fiap.leilao.domain.type.PerfilUsuario;
import com.fiap.leilao.domain.type.StatusLeilao;

/**
 * Bean que permite criar Leilões
 * @author Leandro
 *
 */
@Stateless(mappedName = CriarLeilaoBean.JNDI_NAME)
@Remote(CriarLeilaoBean.class)
public class ManagerCriarLeilaoBean implements CriarLeilaoBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4647706789163695523L;
	
	/*Bean da camada de domínio*/
	@EJB
	private LeilaoBean leilaoBean;
	
	private static final Log LOG = LogFactory.getLog(ManagerCriarLeilaoBean.class);
	
	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.CriarLeilaoBean#criarLeilao(com.fiap.leilao.domain.Leilao, com.fiap.leilao.domain.Usuario)
	 */
	@Override
	/**
	 * A configuração <p>@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	 * indica que o método suporta uma transação e solicita uma nova trasnsação ao container
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long criarLeilao(Leilao leilao, Usuario usuario) throws LeilaoBusinessException , LeilaoBusinessArgumentException{
		
		/*Se o usuário for nulo ou se não tiver o perfil de VENDEDOR não podera criar um leilão*/
		if(usuario == null || !usuario.getPerfil().equals(PerfilUsuario.VENDEDOR) )
			throw new LeilaoBusinessArgumentException("Usuario inválido para a operação.");
		
		/*Valida se o usuário não esta tentando gravar um leilão com a lisata de itens vazia*/
		if(leilao.getProduto().getItens().isEmpty())
			throw new LeilaoBusinessArgumentException("É necessário adicionar ítens para o leilão.");
		
		try{
			
			leilao.setVendedor(usuario);
			leilao.setStatus(StatusLeilao.AGUARDANDO_AUTORIZACAO);
			leilao.getProduto().setLeilao(leilao);
			leilao.setDataCadastro(Calendar.getInstance().getTime());
			
			for(Item item : leilao.getProduto().getItens()){
				item.setProduto(leilao.getProduto());
			}
			
			leilao = leilaoBean.insert(leilao);
			
			return leilao.getId();
			
		}catch (Exception e) {
			LOG.error("Erro a salva item ", e);
			throw new LeilaoBusinessException(e);
		}

	}
}