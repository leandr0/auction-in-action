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
 * Bean JSF com escopo de sess�o para trata requisi��es de criar leil�o
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
	
	/*Lista com leil�es ativos*/
	private List<Leilao> leiloesAtivos;

	/*Entidade utilizada para recuperar dados de leil�o selecionado*/
	private Leilao leilao;

	/*Entidade para ser utilizada na persist�ncia dos dados*/
	private Lance lance = new Lance();
	
	/*Bean de neg�cio para cria��o de lance*/
	@EJB
	private EnviarLanceBean enviarLanceBean;

	/**
	 * Lista todos os leil�es ativos que o usu�rio pode enviar lance,ou seja ,<p>
	 * os leil�es criados pelo usu�rio logado n�o seram listados
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a p�gina 
	 */
	public String listarLeiloes(){

		try{
			/*Acessa bean de neg�cio para listar os leil�es permitidos aos
			 * envia lances 
			 */
			leiloesAtivos = enviarLanceBean.buscarLeiloesAtivos(getUsuario());
		}catch (Exception e) {
			LOG.error("Erro ao listar leiloes",e);
		}
		
		/*Navega��o mapeada no faces-config.xml*/
		return "enviar-lance-leilao";
	}

	/**
	 * Seleciona o leil�o na lista de leil�es
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a p�gina 
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
			/*Carrega os itens do leil�o */
			leilao.getProduto().setItens(
					enviarLanceBean.buscarItensProduto(leilao.getProduto()));
		} catch (LeilaoBusinessException e) {
			LOG.error("Erro ao selecionar leilao", e);
		} catch (LeilaoDomainArgumentException e) {
			LOG.error("Erro ao selecionar leilao", e);
		}
		
		/*Navega��o mapeada no faces-config.xml*/
		return null;
	}

	/**
	 *  Enviar lance para leil�o
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a p�gina 
	 */
	public String participarLeilao(){

		try{
			/*Valida valor do lance se � menor ou igual � zeroo
			 * e notifica o usu�rio caso a dados sejam inv�lidos
			 */
			if(lance.getValor().doubleValue() <= 0.0){
				showMessageWarn("O valor do lance deve ser maior que zero");
			}
			/*Valida valor do lance e notifica o usu�rio caso a dados sejam inv�lidos*/
			else if(lance.getValor().doubleValue() < leilao.getValorInicial().doubleValue()){
				showMessageWarn("O valor do lance deve ser maior ou igual ao valor inicial do leil�o");
			}else{
				
				/*
				 * Recupera o usu�rio atrav�s do JAAS 
				 * @see {@link UIAbstractBean#getUsuario()} 
				 * 
				 */
				Usuario usu = getUsuario();
				
				lance.setLeilao(leilao);
				
				/*Envia lance para camada de neg�cio*/
				enviarLanceBean.enviarLance(lance, usu);
				
				/*Notifica usu�rio em caso de sucesso*/
				showMessageInfo("Lance enviado com sucesso!");
			}
		}catch (LeilaoDomainArgumentException e) {
			LOG.error("Erro ao enviar lance",e);
			showMessageWarn(e.getMessage());
		}
		catch (Exception e) {
			LOG.error("Erro ao enviar lance",e);
			showMessageError("Ocorreu um erro ao enviar o lance ao leil�o.Por favor entre em contato com o Administrador do sistema");
		}finally{
			lance = new Lance();
		}

		/*Navega��o mapeada no faces-config.xml*/
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