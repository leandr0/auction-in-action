/**
 * 
 */
package com.fiap.leilao.business.mail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.activation.DataSource;

/**
 * Classe customizada de Stream para envio de email com relatorio pdf
 * 
 * @author Leandro
 *
 */
public class InputStreamDataSource implements Serializable ,DataSource {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7895895011553913312L;
	
	private String name;
	private String contentType;
	private ByteArrayOutputStream baos;

	public InputStreamDataSource(String name, String contentType,InputStream inputStream) throws IOException {
		int read;			
		this.name = name;
		this.contentType = contentType;
		
		baos = new ByteArrayOutputStream();
		
		byte[] buff = new byte[256];			
		while ((read = inputStream.read(buff)) != -1) {
			baos.write(buff, 0, read);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.activation.DataSource#getContentType()
	 */
	public String getContentType() {
		return contentType;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.activation.DataSource#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(baos.toByteArray());
	}

	/*
	 * (non-Javadoc)
	 * @see javax.activation.DataSource#getName()
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.activation.DataSource#getOutputStream()
	 */
	public OutputStream getOutputStream() throws IOException {
		throw new IOException("Somente leitura");
	}
}