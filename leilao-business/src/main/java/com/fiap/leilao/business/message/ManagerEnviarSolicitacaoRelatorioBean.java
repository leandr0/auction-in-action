/**
 * 
 */
package com.fiap.leilao.business.message;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.ejb3.annotation.ResourceAdapter;

/**
 * @author Leandro
 *
 */
/*
 * O bean é anotado com @Asynchronous, pois para as ações que enviam mensagens JMS
 * podem demorar , devido a disponibilidade da rede ou do broker JMS
 * Assim não prendendo as requisições
 */
//@Asynchronous
@Remote(EnviarSolicitacaoRelatorioBean.class)
@Stateless(mappedName = EnviarSolicitacaoRelatorioBean.JNDI_NAME)
/*
 * Como estamos utilizando um recurso externo de mensageria JMS ,
 * a JBoss nos disponibiliza uma anotação para indicar qual recurso
 * será utlizado
 */
//@ResourceAdapter(EnviarSolicitacaoRelatorioBean.RESOURCE_ADAPTER)  
public class ManagerEnviarSolicitacaoRelatorioBean implements EnviarSolicitacaoRelatorioBean {

	
	@Resource(mappedName = EnviarMessageLanceBean.JNDI_CONNECTION_FACTORY)
	private ConnectionFactory factory;

	@Resource(mappedName = EnviarMessageLanceBean.JNDI_QUEUE_RELATORIO_NAME)	
	private Destination destination;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6508485036335241228L;
	
	private static final Log LOG = LogFactory.getLog(ManagerEnviarSolicitacaoRelatorioBean.class);

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.message.EnviarSolicitacaoRelatorioBean#enviarSolicitacaoRelatorioEmail(java.lang.Long)
	 */
	@Override
	public void enviarSolicitacaoRelatorioEmail(Long idLeilao) {
		
		Connection connection 	= null;
		Session session 		= null;

		try{
		
			connection 	= factory.createConnection(); 
			session 	= connection.createSession(true,Session.SESSION_TRANSACTED);

			TextMessage textMessage = session.createTextMessage();
			/*Enviando mensagem com ID do leilao para fila que gera os relatórios*/
			textMessage.setLongProperty("ID_LEILAO", idLeilao);
			
			session.createProducer(destination).send(textMessage);

		}catch (Exception e) {
			LOG.error("Erro ao enviar mensagem jms",e);
			throw new RuntimeException(e);
		}
		finally{
			try{
				session.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar sessao jms",e);
			}
			try{
				connection.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar conexao jms",e);
			}
		}
		
	}

}