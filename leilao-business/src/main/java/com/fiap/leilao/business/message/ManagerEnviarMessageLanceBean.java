/**
 * 
 */
package com.fiap.leilao.business.message;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.ejb3.annotation.ResourceAdapter;
//import org.jboss.annotation.ejb.ResourceAdapter;

import com.fiap.leilao.business.exception.EnviarMensagemBusinessException;

/**
 * Bean que permite Enviar um relatorio por e-mail
 * @author Leandro
 *
 */
/*
 * A classe é assíncrona , pois como o envio do lance
 * para um leilão é assícrono não e necessário aguardar por todo o processo
 * de envio da mensagem
 */
//@Asynchronous
@Remote(EnviarMessageLanceBean.class)
@Stateless(mappedName = EnviarMessageLanceBean.JNDI_NAME)
/*
 * Como estamos utilizando um recurso externo de mensageria JMS ,
 * a JBoss nos disponibiliza uma anotação para indicar qual recurso
 * será utlizado
 */
//@ResourceAdapter(EnviarMessageLanceBean.RESOURCE_ADAPTER)        
public class ManagerEnviarMessageLanceBean implements EnviarMessageLanceBean {

	@Resource(mappedName = EnviarMessageLanceBean.JNDI_CONNECTION_FACTORY)
	private ConnectionFactory factory;

	@Resource(mappedName = EnviarMessageLanceBean.JNDI_QUEUE_LANCE_NAME)	
	private Destination destination;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2148941833086393469L;

	private static final Log LOG = LogFactory.getLog(ManagerEnviarMessageLanceBean.class);
	
	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.message.EnviarMessageLanceBean#enviarTextMessage(java.lang.String)
	 */
	@Override
	public void enviarTextMessage(String message) throws EnviarMensagemBusinessException{

		Connection connection 	= null;
		Session session 		= null;

		try{
			connection 	= factory.createConnection(); 
			session 	= connection.createSession(true,Session.SESSION_TRANSACTED);

			TextMessage textMessage = session.createTextMessage(message);
			
			LOG.info("Enviando mensagem de lance para leilao");
			session.createProducer(destination).send(textMessage);

		}catch (Exception e) {
			LOG.error("Erro ao enviar mensagem JMS", e);
			throw new EnviarMensagemBusinessException(e);
		}
		finally{
			try{
				session.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar sessao JMS",e);
			}
			try{
				connection.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar conexao JMS",e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.message.EnviarMessageLanceBean#enviarObjectMessage(java.io.Serializable)
	 */
	@Override
	public <M extends Serializable> void enviarObjectMessage(M message) throws EnviarMensagemBusinessException{
		

		Connection connection 	= null;
		Session session 		= null;

		try{
			connection 	= factory.createConnection(); 
			session 	= connection.createSession(true,Session.SESSION_TRANSACTED);

			ObjectMessage objectMessage = session.createObjectMessage(message);
			LOG.info("Enviando mensagem de lance para leilao");
			session.createProducer(destination).send(objectMessage);

		}catch (Exception e) {
			LOG.error("Erro ao enviar mensagem JMS", e);
			throw new EnviarMensagemBusinessException(e);
		}
		finally{
			try{
				session.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar sessao JMS",e);
			}
			try{
				connection.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar conexao JMS",e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.message.EnviarMessageLanceBean#enviarTextMessageQuerySelector(java.lang.String, com.fiap.leilao.business.message.EnviarMessageLanceBean.MessageSelect)
	 */
	@Override
	public void enviarTextMessageQuerySelector(String message,MessageSelect messageSelect) throws EnviarMensagemBusinessException{


		Connection connection 	= null;
		Session session 		= null;

		try{
			connection 	= factory.createConnection(); 
			session 	= connection.createSession(true,Session.SESSION_TRANSACTED);

			TextMessage textMessage = session.createTextMessage(message);
			textMessage.setStringProperty(messageSelect.QUERYSELECTOR, messageSelect.VALUESELECTOR);
			LOG.info("Enviando mensagem de lance para leilao com parametro de selecao");
			session.createProducer(destination).send(textMessage);

		}catch (Exception e) {
			LOG.error("Erro ao enviar mensagem JMS", e);
			throw new EnviarMensagemBusinessException(e);
		}
		finally{
			try{
				session.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar sessao JMS",e);
			}
			try{
				connection.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar conexao JMS",e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.message.EnviarMessageLanceBean#enviarObjectMessageQuerySeletor(java.io.Serializable, com.fiap.leilao.business.message.EnviarMessageLanceBean.MessageSelect)
	 */
	@Override
	public <M extends Serializable> void enviarObjectMessageQuerySeletor(M message, MessageSelect messageSelect) throws EnviarMensagemBusinessException{


		Connection connection 	= null;
		Session session 		= null;

		try{
			connection 	= factory.createConnection(); 
			session 	= connection.createSession(true,Session.SESSION_TRANSACTED);

			ObjectMessage objectMessage = session.createObjectMessage(message);
			objectMessage.setStringProperty(messageSelect.QUERYSELECTOR, messageSelect.VALUESELECTOR);
			
			LOG.info("Enviando mensagem de lance para leilao com parametro de selecao");
			session.createProducer(destination).send(objectMessage);

		}catch (Exception e) {
			LOG.error("Erro ao enviar mensagem JMS", e);
			throw new EnviarMensagemBusinessException(e);
		}
		finally{
			try{
				session.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar sessao JMS",e);
			}
			try{
				connection.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar conexao JMS",e);
			}
		}
	}
}