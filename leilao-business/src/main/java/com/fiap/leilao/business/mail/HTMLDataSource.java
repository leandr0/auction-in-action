/**
 * 
 */
package com.fiap.leilao.business.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.activation.DataSource;

/**
 * Classe customizada para envio de e-mails com teplate html
 * 
 * @author Leandro
 *
 */
public class HTMLDataSource implements DataSource , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2435325646477543092L;
	
	private String html;

	/**
	 * 
	 * @param htmlString
	 */
	public HTMLDataSource(String htmlString) {
		html = htmlString;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.activation.DataSource#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {
		if (html == null)
			throw new IOException("Parâmetro Inválido");
		return new ByteArrayInputStream(html.getBytes());
	}

	/*
	 * (non-Javadoc)
	 * @see javax.activation.DataSource#getOutputStream()
	 */
	public OutputStream getOutputStream() throws IOException {
		throw new IOException("Não é possivel escrever um HTML");
	}

	public String getContentType() {
		return "text/html";
	}

	public String getName() {
		return "text/html dataSource para enviar e-mail";
	}
}
