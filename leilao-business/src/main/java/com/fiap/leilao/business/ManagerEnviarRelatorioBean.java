/**
 * 
 */
package com.fiap.leilao.business;

import java.sql.Connection;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.business.mail.ManagerLeilaoMail;
import com.fiap.leilao.business.mail.TemplateMessageMail;
import com.fiap.leilao.business.report.GerarRelatorio;
import com.fiap.leilao.business.report.GerarRelatorioBoleto;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.bean.LeilaoBean;

/**
 * Bean que possibilita o envio de relatórios por e-mail
 * 
 * @author Leandro
 *
 */
@Stateless(mappedName = EnviarRelatorioBean.JNDI_NAME)
@Local(EnviarRelatorioBean.class)
public class ManagerEnviarRelatorioBean implements EnviarRelatorioBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8149832463831036153L;
	
	private static final Log LOG = LogFactory.getLog(ManagerEnviarRelatorioBean.class);

	@Resource(mappedName = "java:LeilaoDS")
	protected DataSource dataSource;

	/*Bean da camada de domínio*/
	@EJB
	private LeilaoBean leilaoBean;
	
	/**
	 * O bean é anotado com @Asynchronous, pois para as ações que geram relatório 
	 * demoram e também o envio de e-mails podem demorar , 
	 * devido a disponibilidade da rede
	 * Assim não prendendo as requisições
	 * @see com.fiap.leilao.business.GerarRelatorioBean#gravarRelatorio()
	 */
	@Override
	@Asynchronous
	public void enviarRelatorio(Long idLeilao) {

		Connection connection = null;
		
		try{

			Leilao leilao = leilaoBean.pesquisarLeilaoGanhador(idLeilao);
			
			connection = dataSource.getConnection();
			
			/*Criando uma instância para gerar relatótio passando um data source*/
			GerarRelatorio gerarRelatorio = new GerarRelatorioBoleto(dataSource);
			
			/*Gerando relatório com o ID do leilão informado*/
			byte[] report = gerarRelatorio.gerarRelatorio(idLeilao);
			
			/*
			 * Enviando e-mail através da classe de enviar e-mail
			 * com padrão Fluent Interface
			 */
			new ManagerLeilaoMail()
				.addEmailTo(leilao.getComprador().getEmail())
				.addAnexo(report,"Leilao-Cod-"+idLeilao+".pdf")
				.addSubject("GANHADOR LEILÃO FIAP")
				.enviarMensagemHTML(leilao, TemplateMessageMail.APROVAR_LEILAO_MAIL);


		}catch (Exception e) {
			LOG.error("Erro ao enviar relatorio", e);
		}finally{
			try{
				connection.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar conexao"+e);
			}
		}
	}
}