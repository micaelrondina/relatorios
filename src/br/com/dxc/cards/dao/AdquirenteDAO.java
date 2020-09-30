package br.com.dxc.cards.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.dxc.cards.model.Parametro;
import br.com.dxc.cards.util.Utils;

public class AdquirenteDAO {

	public List<Parametro> getListAdquirentes() throws Exception {
		// public Map<Integer, String> getListAdquirentes() throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		List<Parametro> dados = new ArrayList<>();
		// Map<Integer, String> dados = new LinkedHashMap<>();

		try {
			con = Utils.getConexaoACQR();

			String query = "SELECT ACQUIRER_ID,DESCRIPCION FROM PRODACQR.ACQUIRERS ORDER BY ACQUIRER_ID ASC";

			ps = con.prepareStatement(query);
			rs = Utils.executeQuery(ps, query); // executa query e print query no log

			while (rs.next()) {

				Parametro adquirentes = new Parametro();
				adquirentes.setAcquirerId(rs.getInt("ACQUIRER_ID"));
				adquirentes.setDescricao(rs.getString("DESCRIPCION"));

				dados.add(adquirentes);
				// dados.put(rs.getInt("ACQUIRER_ID"), rs.getString("DESCRIPCION"));
			}
			return dados;
		} finally {
			Utils.fechaConexao(rs, ps, con);
		}
	}

}
