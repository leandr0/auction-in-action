/**
 * 
 */
package com.fiap.leilao.business.report;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.fiap.leilao.business.exception.GerarRelatorioBusinessException;

/**
 * @author Leandro
 *
 */
public class GerarRelatorioBoleto implements GerarRelatorio {


	private final DataSource dataSource;

	public GerarRelatorioBoleto(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.business.report.GerarRelatorio#gerarRelatorio(java.lang.Long)
	 */
	@Override
	public byte[] gerarRelatorio(Long leilaoId) throws GerarRelatorioBusinessException {

		Connection connection = null;

		try{

			connection = dataSource.getConnection();

			HashMap<String, Object> parameterMap = new HashMap<String, Object>();

			ByteArrayOutputStream out = new ByteArrayOutputStream();		

			parameterMap.put("ID_LEILAO", leilaoId);

			InputStream stream =  getClass().getResourceAsStream("/reports/boleto-leilao.jasper");
			
			JasperPrint jp = JasperFillManager.fillReport(
								stream,
								parameterMap, connection);

			JasperExportManager.exportReportToPdfStream(jp,out);

			
			return out.toByteArray();

		}catch (Throwable e) {
			throw new GerarRelatorioBusinessException(e.getMessage());
		}finally{
			try{
				connection.close();
			}catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
}