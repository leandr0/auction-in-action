/**
 * 
 */
package com.fiap.leilao.service.bean;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.business.EnviarLanceBean;
import com.fiap.leilao.business.FechamentoLeilaoBean;
import com.fiap.leilao.domain.Item;
import com.fiap.leilao.domain.Lance;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.bean.ItemBean;
import com.fiap.leilao.domain.bean.LeilaoBean;
import com.fiap.leilao.domain.bean.SegurancaBean;
import com.fiap.leilao.domain.bean.UsuarioBean;
import com.fiap.leilao.service.exception.LeilaoServiceException;
import com.fiap.leilao.service.model.ItemModel;
import com.fiap.leilao.service.model.LeilaoModel;


/**
 * @author Leandro
 *
 */
@Stateless(mappedName = ControllerServiceLeilao.JNDI_NAME)
@Remote(ControllerServiceLeilao.class)
public class ManagerControllerServiceLeilaoBean implements ControllerServiceLeilao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6277596281495172397L;

	@EJB
	private UsuarioBean usuarioBean; 

	@EJB
	private ItemBean itemBean;

	@EJB
	private LeilaoBean leilaoBean;

	@EJB
	private EnviarLanceBean enviarLanceBean;

	@EJB
	private SegurancaBean segurancaBean;

	@EJB
	private FechamentoLeilaoBean fechamentoLeilaoBean;

	private static final Log LOG = LogFactory.getLog(ManagerControllerServiceLeilaoBean.class);


	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.service.ListarLeilao#listarLeiloes(java.lang.String)
	 */
	@Override
	public List<LeilaoModel> listarLeiloes(String chaveSeguranca) throws LeilaoServiceException{

		try {

			segurancaBean.validaChaveServico(chaveSeguranca);

			LOG.info("Pesquisando usuario");
			LOG.info("dados de pesquisa : "+chaveSeguranca);
			Usuario usuario = usuarioBean.pesquisaChaveSeguranca(chaveSeguranca);

			List<Leilao> leiloes = leilaoBean.pesquisaLeilaoEnviarLance(usuario); 

			List<LeilaoModel> result = new LinkedList<LeilaoModel>();

			for (Leilao ll : leiloes) {

				List<Item> itens = itemBean.getItensProduto(ll.getProduto());

				List<ItemModel> itensModel = new LinkedList<ItemModel>();

				for (Item item : itens){
					itensModel.add(new ItemModel(item.getDescricao(), item.getQuantidade()));
				}	

				result.add(new LeilaoModel(ll.getId(), ll.getProduto().getDescricao()
						,ll.getValorInicial(), itensModel));
			}

			return result;

		} catch (Throwable e) {
			throw new LeilaoServiceException(e.getMessage());
		}
	}

	@Override
	public void enviarLance(Long codLeilao, Double valorLance, String chaveSeguranca) throws LeilaoServiceException {

		try{

			segurancaBean.validaChaveServico(chaveSeguranca);

			Usuario usuario = usuarioBean.pesquisaChaveSeguranca(chaveSeguranca);

			Lance lance = new Lance();

			Leilao leilao = new Leilao();
			leilao.setId(codLeilao);

			leilao = leilaoBean.find(leilao);

			lance.setLeilao(leilao);
			lance.setValor(valorLance);

			enviarLanceBean.enviarLance(lance, usuario);

		}catch (Throwable e) {
			throw new LeilaoServiceException(e.getMessage());
		}

	}

	@Override
	public void finalizarLeilao() throws LeilaoServiceException {
		try{

			fechamentoLeilaoBean.finalizarLeilao();
			
		}catch (Throwable e) {
			throw new LeilaoServiceException(e.getMessage());
		}

	}
}