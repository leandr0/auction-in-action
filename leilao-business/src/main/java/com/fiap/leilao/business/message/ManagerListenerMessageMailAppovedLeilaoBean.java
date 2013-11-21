/**
 * 
 */
package com.fiap.leilao.business.message;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.business.event.LeilaoEvent;
import com.fiap.leilao.business.qualifier.AprovarLeilao;
import com.fiap.leilao.domain.Leilao;


/**
 * Bean MDB que processa o itens na lista de lances para os leil�es
 * 
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
								propertyValue = ResourceAdapterMessage.JNDI_QUEUE_APROVACAO_MAIL
								)		
		}
		)
/*
 * Como estamos utilizando um recurso externo de mensageria JMS ,
 * a JBoss nos disponibiliza uma anota��o para indicar qual recurso
 * ser� utlizado
 */
//@ResourceAdapter(ResourceAdapterMessage.RESOURCE_ADAPTER)        
public class ManagerListenerMessageMailAppovedLeilaoBean implements MessageListener {


	private static final Log LOG = LogFactory.getLog(ManagerListenerMessageMailAppovedLeilaoBean.class);


	/*
	 * @Inject - Anota��o CDI do JEE 6 que permite injetar
	 * Um uma classe que que envia e-mail para leil�es aprovados
	 * 
	 * @AprovarLeilao - Anota��o CDI JEE 6 que permite criar qualificadores 
	 * (@Qualifier) de implemtenta��es de uma mesma Interface
	 * 
	 * Event<?> - cria um observer disponibilizado pelo container para inje��o de 
	 * depend�ncia de acordo com o @Qualifier 
	 */
	@Inject
	@AprovarLeilao
	private Event<LeilaoEvent> aprovarLeilao;
	
	
	/**
	 * Processa a mensagem recebida na fila,
	 * inserindo o lance na base de dados
	 */
	@Override
	public void onMessage(Message message) {
		try{
			
			ObjectMessage objectMessage = (ObjectMessage) message;

			Leilao leilao = (Leilao) objectMessage.getObject();

			LOG.info("Enviando email de n�o aprova��o para o Leilao : "+leilao.getId() );
			aprovarLeilao.fire(new LeilaoEvent(leilao));
			
		}catch (Exception e) {
			LOG.error("Erro ao processar mansagem de lances ",e);
		}
	}
}
