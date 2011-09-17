/**
 * 
 */
package com.fiap.leilao.web.application;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.business.CriarLeilaoBean;
import com.fiap.leilao.business.exception.LeilaoBusinessArgumentException;
import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.domain.Item;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Produto;
import com.fiap.leilao.domain.Usuario;

/**
 * Bean JSF com escopo de sessão para trata requisições de criar leilão
 * @author Leandro
 *
 */
@ManagedBean(name = "ui_criarLeilao_bean")
@SessionScoped
public class UICriarLeilaoBean extends UIAbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2036062251597247503L;

	private static final Log LOG = LogFactory.getLog(UICriarLeilaoBean.class);
	
	/*Bean de negócio para criação de leilão*/
	@EJB
	private CriarLeilaoBean criarLeilaoBean;

	/*
	 * Entidade Leilão que será utilizada para persistencia dos dados
	 */
	private Leilao leilao;

	/*
	 * Entidade Item que utilizada para adicionar ou remover um ítem na lista do Leilão
	 */
	private Item 	item;

	/** 
	 * Atributo que utilizado para identificar qual índice da lista de itens está sendo manipulado.<p>
	 * Isto ocorre pois o usuário pode editar , ou excluir um ítem antes de persisti-lo não tendo ainda<p>
	 * uma chave única como o ID , e também é mais performático verificar o índice ao invés da descrição
	 */
	private int indexItem;
	
	
	public UICriarLeilaoBean() {
		init();
	}

	/**
	 * Inicializa todas as variáveis do Bean
	 * e direciona para a página correspondente
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a página 
	 */
	public String init(){
		leilao 	= new Leilao();
		item 	= new Item();

		leilao.setProduto(new Produto());
		leilao.getProduto().setItens(new LinkedList<Item>());
		/*
		 * Navegação mapeada no faces-config.xml
		 */
		return "criar-leilao";
	}

	/**
	 * Valida dados do leilão e persiste a entidade
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a página
	 * @see {@link CriarLeilaoBean#criarLeilao()} 
	 * @see {@link UIAbstractBean#getUsuario() 
	 */
	public String criarLeilao(){

		/*
		 * Cria uma data vigente com o horário igual á 00:00:00
		 * para verificar se a data final do leilão não é menor 
		 * que a data atual
		 */
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		/*
		 * Verifica validade da data final e notifica o usuário caso a data seja inválida
		 */
		if(calendar.after(leilao.getDataFinal())){
			showMessageWarn("O leilão não pode conter uma anterior a data atual.");
			return null;
		}
		
		try{
			
			/*
			 * Recupera o usuário através do JAAS 
			 * @see {@link UIAbstractBean#getUsuario()} 
			 * 
			 */
			Usuario usuario = getUsuario();
			
			/*
			 * Persiste o leilão , atribuindo o usuário como proprietário do leilão
			 * @see {@link CriarLeilaoBean#criarLeilao()} 
			 */
			Long idLeilao = criarLeilaoBean.criarLeilao(leilao, usuario);
			
			/*
			 * Informa o número do ID do leilão criado ao Usuário
			 */
			showMessageInfo("Leilão código : "+idLeilao+" , criado com sucesso");

			init();

		}catch (LeilaoBusinessArgumentException e) {
			showMessageError(e.getMessage());
		}catch (LeilaoBusinessException e) {
			LOG.error("Erro ao salvar leilao ", e);
			showMessageError(e.getMessage());
		}catch (Throwable e) {
			LOG.error("Erro ao salvar leilao ", e);
			showMessageError("Erro ao criar um leilão.Por favor entre em contato com o administrador do sistema.");
			init();
		}

		/*
		 * Navegação mapeada no faces-config.xml
		 */
		return null;
	}

	/**
	 * Valida dados do ítem e adiciona na lista do leilão
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a página 
	 */
	public String adicionarItem(){

		try {

			LOG.info("Adicionando item");
			
			/*
			 * Clona o objeto item para não ter problema de referência de objetos 
			 * após incluído na lista de itens
			 */
			Item tmp =  (Item) BeanUtils.cloneBean(item);

			/*
			 * Percorre a lista de itens verificando se não existem itens com a descrição duplicada
			 * e notifica o usuário caso exista
			 */
			for(Item itm : leilao.getProduto().getItens()){
				if(tmp.getDescricao().trim().equalsIgnoreCase(itm.getDescricao().trim())){
					showMessageWarn("Já existem um ítem com a mesma descrição : "+itm.getDescricao()+".");
					return null;
				}
			}
			
			/*
			 * Valida descrição do Item e notifica o usuário de alguma irregularidade
			 */
			if(StringUtils.isBlank(tmp.getDescricao())){
				showMessageWarn("Item não pode ser adicionado.Descrição inválida.");
				return null;
			}
			
			/*
			 *Valida a quantidade do item e notifica o usuário de alguma irregularidade 
			 */
			if(tmp.getQuantidade() == null || tmp.getQuantidade().intValue() <= 0 ){
				showMessageWarn("Item não pode ser adicionado.Quantidade inválida.");
				return null;
			}
			
			/*
			 * Adiciona o ítem na lista
			 */
			leilao.getProduto().getItens().add(tmp);

			/*
			 * Criar uma nova instâcia do objeto , 
			 * para exibir dados limpos em tela caso o usuário inclua um novo ítem 
			 */
			item = new Item();

		} catch (Throwable e) {
			LOG.error("Erro ao adicionar um item na lista do leilao ", e);
			showMessageError("Erro ao adicionar o ítem na lista.");
		}

		/*
		 * Navegação mapeada no faces-config.xml
		 */
		return null;
	}

	/**
	 * Recupera os dados do item seleciona
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a página 
	 * @see UIAbstractBean#getParameterInRequest(String)
	 */
	public String selecionarItem(){

		/*
		 * Recupera o parâmetro passado pela camada de visão
		 */
		String descLeilao = getParameterInRequest("itemLeilao");
		
		/*
		 * Verifica os dados informados e atribui os dados do item recuperado
		 * a variável #item e recupera o index do ítem na lista e atribui a vairável #indexItem
		 */
		if(descLeilao != null){
			for(Item itm : leilao.getProduto().getItens()){
				if(descLeilao.trim().equals(itm.getDescricao().trim())){
					item = itm;
					indexItem = leilao.getProduto().getItens().indexOf(itm);
					break;
				}
			}
		}

		/*
		 * Navegação mapeada no faces-config.xml
		 */
		return null;
	}
	
	/**
	 * Valida os dados do item e o atualiza na lista de itens do leilão
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a página 
	 */
	public String atualizarItem(){
		
		/*
		 *Valida a descrição do ítem e notifica o usuário de alguma irregularidade 
		 */
		if(StringUtils.isBlank(item.getDescricao())){
			showMessageWarn("Item não atualizado.Descrição inválida.");
			return null;
		}
		
		/*
		 * Validade a quantidade do ítem e notifica o usuário de alguma irregularidade 
		 */
		if(item.getQuantidade() == null || item.getQuantidade().intValue() <= 0 ){
			showMessageWarn("Item não atualizado.Quantidade inválida.");
			return null;
		}
		/*Remove o item da lista de acordo com o indice da mesma*/
		leilao.getProduto().getItens().remove(indexItem);
		/*Adiciona o item na lista com o mesmo indece removido*/
		leilao.getProduto().getItens().add(indexItem, item);
		
		/*
		 * Criar uma nova instâcia do objeto , 
		 * para exibir dados limpos em tela caso o usuário inclua um novo ítem 
		 */
		item = new Item();
		
		/*
		 * Navegação mapeada no faces-config.xml
		 */
		return null;
	}
	
	/**
	 * Remove um ítem selecionado da lista de itens do leilão
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a página 
	 */
	public String removerItemLeilao(){
		
		/*Lista que armazena os itens selecionados para serem removidos*/
		List<Integer> indxs = new LinkedList<Integer>();
		
		/*Percorre a lista de itens e adiciona o indice dos itens selecionados na lista de indices*/
		for(Item itm : leilao.getProduto().getItens()){
			if(itm.isSelect()){
				indxs.add(leilao.getProduto().getItens().indexOf(itm));
			}
		}
		
		/*Percorre a lista de indices e remove os itens pelo indice*/
		for(Integer indx : indxs){
			leilao.getProduto().getItens().remove(indx.intValue());
		}
		
		/*
		 * Navegação mapeada no faces-config.xml
		 */
		return null;
	}
	
	/**
	 * Limpa as vaiáveis {@link #indexItem}e {@link #item} crianod novas referências
	 * @return {@link String} nome mapeado no faces-config.xml que corresponde a página 
	 */
	public String clear(){
		
		LOG.info("Limpando dados");
		
		item = new Item();
		indexItem = 0;
		
		/*
		 * Navegação mapeada no faces-config.xml
		 */
		return null;
	}
	
	public Leilao getLeilao() {
		return leilao;
	}


	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}


	public Item getItem() {
		return item;
	}


	public void setItem(Item item) {
		this.item = item;
	}

	public int getIndexItem() {
		return indexItem;
	}

	public void setIndexItem(int indexItem) {
		this.indexItem = indexItem;
	}
}