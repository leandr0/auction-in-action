/**
 * 
 */
package com.fiap.leilao.business.mail;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.fiap.leilao.domain.Leilao;

/**
 * Classe que possibilita o envio de email , com o padrão Fluent Interface
 * @author Leandro
 *
 */
public class ManagerLeilaoMail implements LeilaoMail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8750498405160259888L;

	private  Multipart multipart;

	private MimeMessage mailMessage;

	public ManagerLeilaoMail() {

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.host", "smtp.mail.yahoo.com");

		multipart 	= new MimeMultipart();
		mailMessage = new MimeMessage(Session.getInstance(properties, new LeilaoMailAuthenticator()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.mail.LeilaoMail#addAnexo(java.lang.String)
	 */
	public LeilaoMail addAnexo(String fileName) throws MessagingException{

		MimeBodyPart anexo = new MimeBodyPart();

		FileDataSource fileDataSource = new FileDataSource(fileName);
		anexo.setDataHandler(new DataHandler(fileDataSource));
		anexo.setFileName(fileDataSource.getName());

		multipart.addBodyPart(anexo);

		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.mail.LeilaoMail#addAnexo(byte[], java.lang.String)
	 */
	public LeilaoMail addAnexo(byte[] stream , String fileName) throws MessagingException{

		try{
			
			MimeBodyPart anexo = new MimeBodyPart();

			ByteArrayInputStream bais = new ByteArrayInputStream(stream);

			InputStreamDataSource attach = new InputStreamDataSource(fileName,"application/octet-stream",bais); 

			anexo.setDataHandler(new DataHandler(attach));
			anexo.setFileName(attach.getName());

			multipart.addBodyPart(anexo);

			return this;
			
		}catch (Exception e) {
			throw new MessagingException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.mail.LeilaoMail#enviarMensagemHTML(java.lang.String)
	 */
	public void enviarMensagemHTML(String mensagemHTML) throws MessagingException{

		MimeBodyPart bodyMensagem = new MimeBodyPart();
		bodyMensagem.setDataHandler(new DataHandler(new HTMLDataSource(mensagemHTML)));
		multipart.addBodyPart(bodyMensagem);
		mailMessage.setContent(multipart);
		mailMessage.setSentDate(new Date());
		addEmailFrom("leilao12scj@yahoo.com");
		Transport.send(mailMessage);

		multipart 	= new MimeMultipart();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.mail.LeilaoMail#enviarMensagemHTML(com.fiap.leilao.domain.Leilao, com.fiap.leilao.business.mail.TemplateMessageMail)
	 */
	public void enviarMensagemHTML(Leilao leilao , TemplateMessageMail templateMessage) throws MessagingException{

		MimeBodyPart bodyMensagem = new MimeBodyPart();
		bodyMensagem.setDataHandler(new DataHandler(new HTMLDataSource(montarEmail(leilao, templateMessage))));
		multipart.addBodyPart(bodyMensagem);
		mailMessage.setContent(multipart);
		mailMessage.setSentDate(new Date());
		addEmailFrom("leilao12scj@yahoo.com");
		Transport.send(mailMessage);

		multipart 	= new MimeMultipart();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.mail.LeilaoMail#enviarMensagem(java.lang.String)
	 */
	public void enviarMensagem(String mensagem) throws MessagingException{

		MimeBodyPart bodyMensagem = new MimeBodyPart();
		bodyMensagem.setText(mensagem);
		multipart.addBodyPart(bodyMensagem);
		mailMessage.setContent(multipart);
		mailMessage.setSentDate(new Date());
		addEmailFrom("leilao12scj@yahoo.com");
		Transport.send(mailMessage);

		multipart 	= new MimeMultipart();
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.mail.LeilaoMail#addEmailFrom(java.lang.String)
	 */
	public LeilaoMail addEmailFrom(String from) throws AddressException, MessagingException{
		mailMessage.setFrom(new InternetAddress(from));
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.mail.LeilaoMail#addCopia(java.lang.String)
	 */
	public LeilaoMail addCopia(String copia) throws MessagingException{
		InternetAddress[] address = {new InternetAddress(copia)};
		mailMessage.setRecipients(Message.RecipientType.CC, address);
		return this;   
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.mail.LeilaoMail#addEmailTo(java.lang.String)
	 */
	public LeilaoMail addEmailTo(String to) throws MessagingException{

		InternetAddress[] address = {new InternetAddress(to)};
		mailMessage.setRecipients(Message.RecipientType.TO, address);

		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.mail.LeilaoMail#addSubject(java.lang.String)
	 */
	public LeilaoMail addSubject(String subject) throws MessagingException{
		mailMessage.setSubject(subject);
		return this;
	}

	/**
	 * Monta um e-mail HTML de acordo com os dados do Leilao e com o Template informado
	 * @param leilao {@link Leilao}
	 * @param templateMessage {@link TemplateMessageMail}
	 * @return {@link String}
	 */
	@SuppressWarnings("static-access")
	private String montarEmail(Leilao leilao , TemplateMessageMail templateMessage){
		
		String mensagem = templateMessage.APROVAR_LEILAO_MAIL.template;
		
		String nome = null;
		
		if(leilao.getComprador() != null)
			nome = leilao.getComprador().getNome();
		else
			nome = leilao.getVendedor().getNome();
		
		mensagem = mensagem.replace("$NOME", nome);
		mensagem = mensagem.replace("$DESCRICAO", leilao.getProduto().getDescricao());
		mensagem = mensagem.replace("$CODIGO", ""+leilao.getId());
		
		return mensagem;
	}
	
	/**
	 * Inner class para autenticação do e-mail
	 * @author Leandro
	 *
	 */
	static class LeilaoMailAuthenticator extends Authenticator {

		String senha = "!112scj!1";

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("leilao12scj", senha);
		}
	}

}
