/**
 * 
 */
package com.fiap.leilao.business.message;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.ejb3.annotation.ResourceAdapter;

import com.fiap.leilao.business.EnviarRelatorioBean;

/**
 * Beam MDB para processar as mensagens da fila de requisi��es de gerar relat�rio<p>
 * de leil�es finalizados e enviar e-mail
 * @author Leandro
 *
 */
@MessageDriven(
		activationConfig ={
				@ActivationConfigProperty(
						propertyName = "destinationType",
						propertyValue = "javax.jms.Queue"
				),
				@ActivationConfigProperty(
						propertyName = "destination",
						propertyValue = ResourceAdapterMessage.JNDI_QUEUE_RELATORIO_NAME
				)		
		}
)
/*
 * Como estamos utilizando um recurso externo de mensageria JMS ,
 * a JBoss nos disponibiliza uma anota��o para indicar qual recurso
 * ser� utlizado
 */
@ResourceAdapter(ResourceAdapterMessage.RESOURCE_ADAPTER) 
public class ManagerListenerMessageRelatorioBean implements MessageListener {

	/*Bean que gera o relat�rio e envia por e-mail */
	@EJB
	private EnviarRelatorioBean enviarRelatorioBean;
	
	private static final Log LOG = LogFactory.getLog(ManagerListenerMessageRelatorioBean.class);
	
	/**
	 * Recupera mensagem na fila e solicita que seja gerado um relat�rio 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		try{
			/*Recupera propriedade que cont�m o id do leil�o finalizado*/
			enviarRelatorioBean.enviarRelatorio(message.getLongProperty("ID_LEILAO"));	
			
		}catch (Exception e) {
			LOG.error("Erro ao processar mensagem para gerar ", e);
		}
	}

}
