/**
 * 
 */
package com.fiap.leilao.business.message;

import java.io.Serializable;

/**
 * @author Leandro
 *
 */
public interface ResourceAdapterMessage extends Serializable {

	/**
	 * 
	 */
	public static final String RESOURCE_ADAPTER = "activemq.rar"; 
	
	/**
	 * 
	 */
	public static final String JNDI_CONNECTION_FACTORY = "java:/ConnectionFactory";
	
	/**
	 * 
	 */
	public static final String JNDI_QUEUE_LANCE_NAME = "java:jboss/queue/recebeLanceLeilao";
	
	/**
	 * 
	 */
	public static final String JNDI_QUEUE_RELATORIO_NAME = "java:jboss/queue/solicitacaoRelatorioLeilao";
	
	public static final String JNDI_QUEUE_APROVACAO_MAIL = "java:jboss/queue/emailAprovacaoLeilao";
	
	public static final String JNDI_QUEUE_REPROVACAO_MAIL = "java:jboss/queue/emailReprovacaoLeilao";
}
