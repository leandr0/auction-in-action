/**
 * 
 */
package com.fiap.leilao.business.mail;

import java.io.Serializable;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.fiap.leilao.domain.Leilao;

/**
 * Interface que possibilita o envio de email , com o padrão Fluent Interface
 * @author Leandro
 *
 */
public interface LeilaoMail extends Serializable{

	/**
	 * Adiciona um anexo
	 * @param fileName Path do arquivo
	 * @return {@link LeilaoMail}
	 * @throws MessagingException
	 */
	public LeilaoMail addAnexo(String fileName) throws MessagingException;

	/**
	 * Adiciona um anexo com array de bytes
	 * @param stream {@code byte[]}
	 * @param fileName {@link String}
	 * @return {@link LeilaoMail}
	 * @throws MessagingException
	 */
	public LeilaoMail addAnexo(byte[] stream ,String fileName) throws MessagingException;
	
	/**
	 * Envia e-mail
	 * @param mensagem {@link String}
	 * @throws MessagingException
	 */
	public void enviarMensagem(String mensagem) throws MessagingException;
	
	/**
	 * Envia e-mail em formato HTML
	 * @param mensagem {@link String}
	 * @throws MessagingException
	 */
	public void enviarMensagemHTML(String mensagem) throws MessagingException;

	/**
	 * Envia e-mail com mensagem montada em HTML de acordo com o Template informado 
	 * @param leilao {@link Leilao}
	 * @param templateMessage {@link TemplateMessageMail}
	 * @throws MessagingException
	 */
	public void enviarMensagemHTML(Leilao leilao , TemplateMessageMail templateMessage) throws MessagingException;
	
	/**
	 * Adiciona o e-mail que enviará a mensagem
	 * 
	 * @param from {@link String}
	 * @return {@link LeilaoMail}
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public LeilaoMail addEmailFrom(String from) throws AddressException, MessagingException;

	/**
	 * Adiciona e-mail para cópia
	 * @param copia {@link String} e-mail a ser copiado
	 * @return {@link LeilaoMail}
	 * @throws MessagingException
	 */
	public LeilaoMail addCopia(String copia) throws MessagingException;
	
	/**
	 * Adicioca destinatátio
	 * @param to {@link String} destinatátio
	 * @return {@link LeilaoMail}
	 * @throws MessagingException
	 */
	public LeilaoMail addEmailTo(String to) throws MessagingException;
	
	/**
	 * Adiciona Subject do e-mail
	 * @param subject {@link String}
	 * @return {@link LeilaoMail}
	 * @throws MessagingException
	 */
	public LeilaoMail addSubject(String subject) throws MessagingException;
}