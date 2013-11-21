/**
 * 
 */
package com.fiap.leilao.business.message;

import java.util.Calendar;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.ejb3.annotation.ResourceAdapter;

import com.fiap.leilao.domain.Lance;
import com.fiap.leilao.domain.bean.LanceBean;


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
						propertyValue = ResourceAdapterMessage.JNDI_QUEUE_LANCE_NAME
				)		
		}
)
/*
 * Como estamos utilizando um recurso externo de mensageria JMS ,
 * a JBoss nos disponibiliza uma anota��o para indicar qual recurso
 * ser� utlizado
 */
//@ResourceAdapter(ResourceAdapterMessage.RESOURCE_ADAPTER)        
public class ManagerListenerMessageLanceBean implements MessageListener {
	
	/*Bean da camada de dom�nio*/
	@EJB
	private LanceBean lanceBean;
	
	private static final Log LOG = LogFactory.getLog(ManagerListenerMessageLanceBean.class);
	
	/**
	 * Processa a mensagem recebida na fila,
	 * inserindo o lance na base de dados
	 */
	@Override
	public void onMessage(Message message) {
		try{
			
			ObjectMessage objectMessage = (ObjectMessage) message;
			
			Lance lance = (Lance) objectMessage.getObject();
			/*Atribui a data atual para o lance*/
			lance.setDataLance(Calendar.getInstance().getTime());
			
			lanceBean.insert(lance);
			
		}catch (Exception e) {
			LOG.error("Erro ao processar mansagem de lances ",e);
		}
	}
}
