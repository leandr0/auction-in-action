/**
 * 
 */
package com.fiap.leilao.business.mail;

import java.io.Serializable;

import javax.enterprise.event.Observes;

import com.fiap.leilao.business.event.LeilaoEvent;
import com.fiap.leilao.business.qualifier.AprovarLeilao;
import com.fiap.leilao.business.qualifier.ReprovarLeilao;

/**
 * @author Leandro
 *
 */
public class MailHandler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3133330871993872107L;
	
	
	private LeilaoMail leilaoMail = new ManagerLeilaoMail();

	public void aprovarLeilao(@Observes @AprovarLeilao LeilaoEvent event){

		try{
			
			leilaoMail.addEmailTo(event.getLeilao().getVendedor().getEmail())
			.addSubject("LEILAO APROVADO ")
			.enviarMensagemHTML(event.getLeilao(), TemplateMessageMail.APROVAR_LEILAO_MAIL);
			
		}catch (Throwable e) {
			//TODO : LOG
			e.printStackTrace();
		}
	}
	
	public void reprovarLeilao(@Observes @ReprovarLeilao LeilaoEvent event){

		try{

			leilaoMail.addEmailTo(event.getLeilao().getVendedor().getEmail())
			.addSubject("LEILAO REPROVADO")
			.enviarMensagemHTML(event.getLeilao(), TemplateMessageMail.REJEITAR_LEILAO_MAIL);
			
		}catch (Throwable e) {
			//TODO : LOG
			e.printStackTrace();
		}
	}

}