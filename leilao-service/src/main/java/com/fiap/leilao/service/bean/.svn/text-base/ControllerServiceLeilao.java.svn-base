/**
 * 
 */
package com.fiap.leilao.service.bean;

import java.io.Serializable;
import java.util.List;

import com.fiap.leilao.service.exception.LeilaoServiceException;
import com.fiap.leilao.service.model.LeilaoModel;

/**
 * @author Leandro
 *
 */
public interface ControllerServiceLeilao extends Serializable{
	
	public static final String JNDI_NAME = "serviceLeilaoBean";
	
	/**
	 * 
	 * @param chaveSeguranca
	 * @return
	 * @throws LeilaoServiceException
	 */
	public List<LeilaoModel> listarLeiloes(String chaveSeguranca)throws LeilaoServiceException;
	
	/**
	 * 
	 * @param codLeilao
	 * @param valorLance
	 * @throws LeilaoServiceException
	 */
	public void enviarLance(Long codLeilao , Double valorLance , String usuario)throws LeilaoServiceException;

	/**
	 * 
	 * @throws LeilaoServiceException
	 */
	public void finalizarLeilao()throws LeilaoServiceException;
	
}
