package br.com.dxc.cards.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.dxc.cards.controller.LoggedUserController;
import br.com.dxc.cards.enuns.PermissaoDxcRelatorio;
import br.com.dxc.cards.model.LoggedUserBean;

public class Utils {

	private static final Logger LOG = LogManager.getLogger(LoggedUserController.class);

	private static Context iniCtx;
	private static DataSource ds;
	private static Connection con;

	public static Connection getConnection() {
		try {
			if (con == null || con.isClosed()) {
				iniCtx = new InitialContext();
				ds = (DataSource) iniCtx.lookup("java:/jdbc/OracleDS/DXC_RELAT");
				return ds.getConnection();
			}
		} catch (SQLException | NamingException e) {
			LOG.error("Erro obtendo conexão com o Banco de Dados!", e);
		}
		return con;
	}

	public static Connection getConexaoACQR() {
		try {
			if (con == null || con.isClosed()) {
				iniCtx = new InitialContext();
				ds = (DataSource) iniCtx.lookup("java:/ACQUIRER_DS");
				return ds.getConnection();
			}
		} catch (SQLException | NamingException e) {
			LOG.error("Erro obtendo conexão com o Banco de Dados!", e);
		}
		return con;
	}

	public static void fechaConexao(ResultSet result, PreparedStatement ps, Connection con) {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException e) {// [fortify] Aplicacao pequena, tratamento simplificado para excecao
			LOG.error("Erro ao fechar conexao - ResultSet", e);
		}

		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {// [fortify] Aplicacao pequena, tratamento simplificado para excecao
			LOG.error("Erro ao fechar conexao - PreparedStatement", e);
		}
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {// [fortify] Aplicacao pequena, tratamento simplificado para excecao
			LOG.error("Erro ao fechar conexao - Connection", e);
		}
	}

	public static ResultSet executeQuery(PreparedStatement s, String query) {
		try {
			if (query != null) {
				LOG.info("SQL : " + query);
			}
			return s.executeQuery();
		} catch (SQLException e) {
			LOG.error("Erro ao executar Query", e);// [Fortify] System Information Leak - alternativa ao
													// printStackTrace()
			throw new RuntimeException(e);
		}
	}

	public static void execute(PreparedStatement ps, String command) {
		try {
			if (command != null) {
				LOG.info("SQL : " + command);
			}
			if (ps == null) {
				ps = getConnection().prepareStatement(command);// [fortify] Sql baseado em uma variavel constante - nao
																// oferece risco
			}
			ps.execute();
		} catch (SQLException e) {
			LOG.error("Erro ao executar Query", e);// [Fortify] System Information Leak - alternativa ao
													// printStackTrace()
			throw new RuntimeException(e);
		}
	}

	public static boolean usuarioTemAcesso(HttpServletRequest request, PermissaoDxcRelatorio permissaoNecessaria) {
		boolean temAcesso = false;
		LoggedUserBean user = (LoggedUserBean) request.getSession().getAttribute("loggedUserBean");
		List<String> tasks = user.getTasks();
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).equals(permissaoNecessaria.getCodigo())) {
				temAcesso = true;
			}
		}
		return temAcesso;
	}

	public static String getyyyyMMddFromIsoDate(String isoDate) {
		String data = isoDate.substring(0, 10);
		data = data.replaceAll("-", "");
		return data;
	}
}
