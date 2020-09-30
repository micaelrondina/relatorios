package br.com.dxc.cards.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.dxc.cards.model.ExtratoEC;
import br.com.dxc.cards.util.CalculoTotal;
import br.com.dxc.cards.util.Utils;

public class ExtratoECDAO {

	private static String valorAnterior = "0";
	private static int comercioAnterior = 0;
	private static int produtoAnterior = 0;
	private static String saldo = "0";

	public List<ExtratoEC> getListClubSaude(int acquirerId, String dataInicio, String dataFim, int comercio,
			int rubrica, int produto, int filial) throws SQLException, ParseException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		List<ExtratoEC> dados = new ArrayList<>();

		try {
			con = Utils.getConexaoACQR();

			String dtPeriodoInicio = Utils.getyyyyMMddFromIsoDate(dataInicio);
			String dtPeriodoFim = Utils.getyyyyMMddFromIsoDate(dataFim);

			System.out.println("Data de Inicio: " + dtPeriodoInicio);
			System.out.println("Data de Fim: " + dtPeriodoFim);

			String query = "select m.comercio, m.fecha_movimiento, m.producto,"
					+ " m.rubro AS RUBRICA, r.descripcion AS DESCRICAO, m.FECHA_VALOR as DATA_TRANSACAO,"
					+ "	SUM(m.IMPORTE * ((2 * m.CODIGO_OPERACION) - 1)) AS VALOR,"
				//	+ "TO_CHAR(SUM(m.importe * ( (2 * m.codigo_operacion) - 1) ),'FM999G999G999D90','nls_numeric_characters='',.''') AS valor,"					
					+ "m.FECHA_LIQUIDACION as DATA_LIQUIDACAO, m.sucursal"
					+ "	from PRODACQR.movtos_comercios m, PRODACQR.rubros_comercios r" + "  where m.acquirer_id = ?"
					+ "	and m.FECHA_movimiento BETWEEN ? AND ? "
					+ " and m.comercio = ? and m.rubro = ? and m.producto = ? and m.sucursal = ? "
					+ " and r.acquirer_id = m.acquirer_id and r.rubro = m.rubro"
					+ "	group by m.fecha_movimiento, m.producto,m.rubro, r.descripcion, m.FECHA_VALOR , m.comercio,m.FECHA_LIQUIDACION, m.sucursal "
					// + " order by m.comercio asc";
					+ " order by m.comercio asc, m.producto asc, m.fecha_movimiento asc";
			// + ", m.producto,m.rubro";

			if (rubrica == -1) {
				query = query.replace("and m.rubro = ?", "");
			}

			if (comercio == -1) {
				query = query.replace("and m.comercio = ?", "");
			}

			if (produto == -1) {
				query = query.replace("and m.producto = ?", "");
			}

			if (filial == -1) {
				query = query.replace("and m.sucursal = ?", "");
			}

			System.out.println("Preparando conexao");

			ps = con.prepareStatement(query);

			ps.setInt(1, acquirerId);
			ps.setString(2, dtPeriodoInicio);
			ps.setString(3, dtPeriodoFim);

			if (comercio != -1) {
				ps.setInt(4, comercio);
				if (rubrica != -1) {
					ps.setInt(5, rubrica);
				}
				if (produto != -1 && rubrica == -1) {
					ps.setInt(5, produto);
				}

				if (produto == -1 && rubrica == -1 && filial != -1) {
					ps.setInt(5, filial);
				}

				if (produto != -1 && rubrica != -1) {
					ps.setInt(6, produto);
				}

				if (produto == -1 && rubrica != -1 && filial != -1) {
					ps.setInt(6, filial);
				}

				if (rubrica == -1 && produto != -1 && filial != -1) {
					ps.setInt(6, filial);
				}

				if (produto != -1 && rubrica != -1 && filial != -1) {
					ps.setInt(7, filial);
				}
			}

			if (rubrica != -1 && comercio == -1) {
				ps.setInt(4, rubrica);
				if (produto != -1) {
					ps.setInt(5, produto);
				}
				if (filial != -1 && produto == -1) {
					ps.setInt(5, filial);
				}

				if (produto != -1 && filial != -1) {
					ps.setInt(6, filial);
				}
			}

			if (rubrica == -1 && comercio == -1 && produto != -1) {
				ps.setInt(4, produto);
				if (filial != -1) {
					ps.setInt(5, filial);
				}
			}

			if (rubrica == -1 && comercio == -1 && produto == -1 && filial != -1) {
				ps.setInt(4, filial);
			}

			rs = Utils.executeQuery(ps, query); // executa query e print query no log
			while (rs.next()) {

				ExtratoEC relatorio = new ExtratoEC();
				int comercioAtual = rs.getInt(1);
				String dataVencimentoAtual = rs.getString(2);
				int produtoAtual = rs.getInt(3);
				
				String valorAtual = rs.getString(7);
				System.out.println("Valor atual: " +valorAtual);

				relatorio.setComercio(comercioAtual);

				relatorio.setDataVencimento(formataData(dataVencimentoAtual));

				relatorio.setProducto(produtoAtual);
				relatorio.setRubrica(rs.getInt(4));
				relatorio.setDescricao(rs.getString(5));
				relatorio.setDataTransacao(formataData(rs.getString(6)));
				
				DecimalFormatSymbols dfs = new DecimalFormatSymbols (new Locale("pt", "Brazil"));
				dfs.setDecimalSeparator(',');
				dfs.setGroupingSeparator('.');
				DecimalFormat df = new DecimalFormat("#,##0.00", dfs);
		        BigDecimal bi = new BigDecimal(valorAtual);
		        String valorFormatado = df.format(bi);
		        System.out.println("Inserindo valor formatado: " +valorFormatado);
				relatorio.setValor(valorFormatado);

				relatorio.setDataLiquidacao(formataData(rs.getString(8)));
				relatorio.setSucursal(rs.getInt(9));

				if (CalculoTotal.validarRegistros(comercioAnterior, rs.getInt(1), produtoAnterior,
						rs.getInt(3)) == true) {

				System.out.println(" /Registro validados, iniciando calculos /");

					saldo = CalculoTotal.Total(valorAnterior, valorAtual);
					relatorio.setSaldo(saldo);
					valorAnterior = saldo;

				} else {
					valorAnterior = valorAtual;
					relatorio.setSaldo(valorFormatado);
				}

				comercioAnterior = comercioAtual;
				produtoAnterior = produtoAtual;

				dados.add(relatorio);

			}
			saldo = "0";
			valorAnterior = "0";
			comercioAnterior = -1;
			System.out.println("Instrução realizada corretamente!");
			return dados;
		} finally

		{
			Utils.fechaConexao(rs, ps, con);
		}
	}

	/*
	 * public static void main(String args[]) throws ParseException { String f =
	 * formataData("20200402"); System.out.println("Data formatada: " + f); }
	 */

	public static String formataData(String data) {
		String ano = data.substring(0, 4);
		String mes = data.substring(4, 6) + "/";
		String dia = data.substring(6, 8) + "/";

		String dataFormatada = dia + mes + ano;
		return dataFormatada;
	}

}
