/**
 * 
 */
package com.greenfire.leilao.ui.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.business.EnviarLanceBean;
import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.domain.Lance;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;

/**
 * Bean JSF com escopo de sessão para trata requisições de criar leilão
 * @author Leandro
 *
 */
@ManagedBean(name = "ui_enviar_lance_bean")
@SessionScoped
public class UIEnviarLanceBean extends UIAbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3672735589571567484L;

	private static final Log LOG = LogFactory.getLog(UIEnviarLanceBean.class);
	
	/*Lista com leilões ativos*/
	private List<Leilao> leiloesAtivos;

	/*Entidade utilizada para recuperar dados de leilão selecionado*/
	private Leilao leilao;

	/*Entidade para ser utilizada na persistência dos dados*/
	private Lance lance = new Lance();
	
	/*Bean de negócio para criação de lance*/
	@EJB
	private EnviarLanceBean enviarLanceBean;

	/**
	 * Lista todos os leilões ativos que o usuário pode enviar lance,ou seja ,<p>
	 * os leilões criados pelo usuário logado não seram listados
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a página 
	 */
	public String listarLeiloes(){

		try{
			/*Acessa bean de negócio para listar os leilões permitidos aos
			 * envia lances 
			 */
			leiloesAtivos = enviarLanceBean.buscarLeiloesAtivos(getUsuario());
		}catch (Exception e) {
			LOG.error("Erro ao listar leiloes",e);
		}
		
		/*Navegação mapeada no faces-config.xml*/
		return "enviar-lance-leilao";
	}

	/**
	 * Seleciona o leilão na lista de leilões
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a página 
	 */
	public String selecionarLeilao(){

		String idLeilao = getParameterInRequest("leilaoAction");

		if(idLeilao != null){
			for (Leilao ll : leiloesAtivos) {
				if(Long.valueOf(idLeilao).longValue() == ll.getId().longValue()){
					leilao = ll;
					break;
				}
			}
		}

		try {
			/*Carrega os itens do leilão */
			leilao.getProduto().setItens(
					enviarLanceBean.buscarItensProduto(leilao.getProduto()));
		} catch (LeilaoBusinessException e) {
			LOG.error("Erro ao selecionar leilao", e);
		} catch (LeilaoDomainArgumentException e) {
			LOG.error("Erro ao selecionar leilao", e);
		}
		
		/*Navegação mapeada no faces-config.xml*/
		return null;
	}

	/**
	 *  Enviar lance para leilão
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a página 
	 */
	public String participarLeilao(){

		try{
			/*Valida valor do lance se é menor ou igual à zeroo
			 * e notifica o usuário caso a dados sejam inválidos
			 */
			if(lance.getValor().doubleValue() <= 0.0){
				showMessageWarn("O valor do lance deve ser maior que zero");
			}
			/*Valida valor do lance e notifica o usuário caso a dados sejam inválidos*/
			else if(lance.getValor().doubleValue() < leilao.getValorInicial().doubleValue()){
				showMessageWarn("O valor do lance deve ser maior ou igual ao valor inicial do leilão");
			}else{
				
				/*
				 * Recupera o usuário através do JAAS 
				 * @see {@link UIAbstractBean#getUsuario()} 
				 * 
				 */
				Usuario usu = getUsuario();
				
				lance.setLeilao(leilao);
				
				/*Envia lance para camada de negócio*/
				enviarLanceBean.enviarLance(lance, usu);
				
				/*Notifica usuário em caso de sucesso*/
				showMessageInfo("Lance enviado com sucesso!");
			}
		}catch (LeilaoDomainArgumentException e) {
			LOG.error("Erro ao enviar lance",e);
			showMessageWarn(e.getMessage());
		}
		catch (Exception e) {
			LOG.error("Erro ao enviar lance",e);
			showMessageError("Ocorreu um erro ao enviar o lance ao leilão.Por favor entre em contato com o Administrador do sistema");
		}finally{
			lance = new Lance();
		}

		/*Navegação mapeada no faces-config.xml*/
		return null;
	}

	public List<Leilao> getLeiloesAtivos() {
		return leiloesAtivos;
	}

	public void setLeiloesAtivos(List<Leilao> leiloesAtivos) {
		this.leiloesAtivos = leiloesAtivos;
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	public Lance getLance() {
		return lance;
	}

	public void setLance(Lance lance) {
		this.lance = lance;
	}
}