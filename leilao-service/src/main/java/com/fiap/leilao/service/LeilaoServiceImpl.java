/**
 * 
 */
package com.fiap.leilao.service;

import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.fiap.leilao.service.bean.ControllerServiceLeilao;
import com.fiap.leilao.service.model.LeilaoModel;

/**
 * @author Leandro
 *
 */
@Resource
@Path("/services/")
public class LeilaoServiceImpl implements LeilaoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4561838192642268610L;

	private Result result;

	private ControllerServiceLeilao controllesService;

	private static final Log LOG = LogFactory.getLog(LeilaoServiceImpl.class);
	
	public LeilaoServiceImpl(ControllerServiceLeilao controllesService , Result result) {
		this.result = result;
		this.controllesService = controllesService;
	}


	/* (non-Javadoc)
	 * @see com.fiap.leilao.service.ListarLeilaoService#listarLeiloes(java.lang.String)
	 */
	@Override
	@Get
	@Path("listarLeilaoLance/{chaveSeguranca}")
	public void listarLeiloes(String chaveSeguranca) {

		try{

			String paramChaveSeguranca = URLDecoder.decode(chaveSeguranca,"UTF-8");
			List<LeilaoModel> leiloes = controllesService.listarLeiloes(paramChaveSeguranca);
			result.use(Results.json()).from(leiloes,"leiloes").recursive().serialize();
			
		}catch (Exception e) {
			result.use(Results.json()).from(e.getMessage(),"error").serialize();
		}
	}

	@Post
	@Path("enviarLance")
	public void enviarLanceLeilao(String codLeilao, String vlrLance, String chaveSeguranca) {
		try{
			
			
			LOG.info("Enviar lance");
			
			String valorLance = vlrLance.replace(",", ".");
			
			controllesService.enviarLance(Long.valueOf(codLeilao), Double.valueOf(valorLance) , chaveSeguranca);
			
			result.use(Results.json()).from("Lance enviado com sucesso","message").serialize();
			
		}catch (Exception e) {
			result.use(Results.json()).from(e.getMessage(),"error").serialize();
		}
		
	}


	@Override
	@Path("finalizarLeilao")
	public void finalizarLeilao() {
		
		try{
			
			controllesService.finalizarLeilao();
			
			result.use(Results.json()).from("Solicitação enviada com sucesso","message").serialize();
			
		}catch (Exception e) {
			result.use(Results.json()).from(e.getMessage(),"error").serialize();
		}
	}
}
