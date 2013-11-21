/**
 * 
 */
package com.fiap.leilao.business.message;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.business.exception.EnviarMensagemBusinessException;
import com.fiap.leilao.domain.Leilao;

/**
 * @author leandro
 *
 */
@Stateless(mappedName = EnviarMessageMailBean.JNDI_NAME)
@Local(EnviarMessageMailBean.class)
public class ManagerEnviarMessageMailBean implements EnviarMessageMailBean {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3248754156922018398L;

	private static final Log LOG = LogFactory.getLog(ManagerEnviarMessageMailBean.class);
	
	@Resource(mappedName = EnviarMessageMailBean.JNDI_CONNECTION_FACTORY)
	private ConnectionFactory factory;

	@Resource(mappedName = EnviarMessageMailBean.JNDI_QUEUE_APROVACAO_MAIL)	
	private Destination destinationApproved;
	

	@Resource(mappedName = EnviarMessageMailBean.JNDI_QUEUE_REPROVACAO_MAIL)	
	private Destination destinationUnapproved;
	

	@Override
	public void sendMailApproved(Leilao leilao) throws EnviarMensagemBusinessException{
	
		Connection connection 	= null;
		Session session 		= null;

		try{
			connection 	= factory.createConnection(); 
			session 	= connection.createSession(true,Session.SESSION_TRANSACTED);

			ObjectMessage objectMessage = session.createObjectMessage(leilao);
			
			LOG.info("Enviando mensagem para leilao aprovado");
			session.createProducer(destinationApproved).send(objectMessage);

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


	@Override
	public void sendMailUnapprived(Leilao leilao) throws EnviarMensagemBusinessException{
		
		Connection connection 	= null;
		Session session 		= null;

		try{
			connection 	= factory.createConnection(); 
			session 	= connection.createSession(true,Session.SESSION_TRANSACTED);

			ObjectMessage objectMessage = session.createObjectMessage(leilao);
			
			LOG.info("Enviando mensagem para leilao não aprovado");
			session.createProducer(destinationUnapproved).send(objectMessage);

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
