/**
 * 
 */
package com.fiap.leilao.business.report;

import com.fiap.leilao.business.exception.GerarRelatorioBusinessException;

/**
 * @author Leandro
 *
 */
public interface GerarRelatorio {

	/**
	 * 
	 * @return
	 * @throws GerarRelatorioBusinessException
	 */
	public byte[] gerarRelatorio(Long leilaoId)throws GerarRelatorioBusinessException;
}
