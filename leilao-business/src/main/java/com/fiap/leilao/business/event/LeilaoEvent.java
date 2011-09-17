/**
 * 
 */
package com.fiap.leilao.business.event;

import java.io.Serializable;

import com.fiap.leilao.domain.Leilao;

/**
 * Classe para implementação do Padrão Observer
 * para envio de email
 * @author Leandro
 *
 */
public class LeilaoEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5905325513678610472L;
	
	private final Leilao leilao;
	
	public LeilaoEvent(Leilao leilao) {
		this.leilao = leilao;
	}

	public Leilao getLeilao() {
		return leilao;
	}
}