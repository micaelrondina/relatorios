package br.com.dxc.cards.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.dxc.cards.model.Arquivo;
import br.com.dxc.cards.util.Utils;

public class ArquivoDAO {
	
	public List<Arquivo> getListRelEvent1386() throws SQLException {
		return getListRelGeneric("Rel_Eventos_1386_%");
	}
	
	public List<Arquivo> getListRelGerFilasEmissor() throws SQLException {
		List<Arquivo> retorno = new ArrayList<>();
		retorno.addAll(getListRelGeneric("Gerencial_Filas_Emissor_Dt_Abertura_%"));
		retorno.addAll(getListRelGeneric("Gerencial_Filas_Emissor_Dt_Tratamento_%"));
		return retorno;
	}
	
	public List<Arquivo> getListRelGerFilasAcquirer() throws SQLException {
		List<Arquivo> retorno = new ArrayList<>();
		retorno.addAll(getListRelGeneric("Gerencial_Filas_Acquirer_Dt_Abertura_%"));
		retorno.addAll(getListRelGeneric("Gerencial_Filas_Acquirer_Dt_Tratamento_%"));
		return retorno;
	}
	
	private List<Arquivo> getListRelGeneric(String filtro) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		List<Arquivo> arquivos = new ArrayList<>();

		try {
			con = Utils.getConnection();
			String query = "SELECT ID_REL, NM_ARQUIVO FROM DXC_RELATORIOS\r\n"
					+ "WHERE UPPER(NM_ARQUIVO) LIKE UPPER('" + filtro + "')\r\n"
					+ "ORDER BY ID_REL DESC";

			ps = con.prepareStatement(query);
			rs = Utils.executeQuery(ps, query); // executa query e print query no log
			while (rs.next()) {
				Arquivo arq = new Arquivo();
				arq.setIdRel(rs.getLong("ID_REL"));
				arq.setNomeRelat(rs.getString("NM_ARQUIVO"));
				arquivos.add(arq);
			}
			return arquivos;
		} finally {
			Utils.fechaConexao(rs, ps, con);
		}		
	}

	public Arquivo getFileDownload(Long idRel) throws SQLException, IOException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		
		try {
			con = Utils.getConnection();
			String query = "SELECT * FROM DXC_RELATORIOS WHERE ID_REL = ?";
		
			ps = con.prepareStatement(query);
			ps.setLong(1, idRel);
			rs = Utils.executeQuery(ps, query);
			
			if (rs.next()) {
				InputStream is = rs.getBinaryStream("ARQUIVO"); //conteudo em si do arquivo
				
				//convertendo InputStream para byte[]
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				int nRead;
				byte[] data = new byte[16384];
				while ((nRead = is.read(data, 0, data.length)) != -1) {
				  buffer.write(data, 0, nRead);
				}
				buffer.close();
				is.close();

				return new Arquivo(rs.getLong("ID_REL"), rs.getString("NM_ARQUIVO"), buffer.toByteArray());
			}
			return null;
			
		} finally {
			Utils.fechaConexao(rs, ps, con);
		}
	}
}
