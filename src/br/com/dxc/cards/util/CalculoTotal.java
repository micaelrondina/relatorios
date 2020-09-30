package br.com.dxc.cards.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class CalculoTotal {

	public static boolean validarRegistros(int comercioAnterior, int comercioAtual, int produtoAnterior,
			int produtoAtual) {

		if (comercioAnterior == comercioAtual && produtoAnterior == produtoAtual) {
			return true;
		}
		return false;
	}

	public static String Total(String valorAnterior, String valorAtual) throws ParseException {

		if (valorAnterior.contains(",")) {
			valorAnterior = valorAnterior.replace(".", "");
			valorAnterior = valorAnterior.replace(",", ".");
			valorAnterior = truncar(valorAnterior);
		} else {
			valorAnterior = truncar(valorAnterior);
		}
		valorAtual = truncar(valorAtual);
		String novoValor = calcular(valorAnterior, valorAtual);
		return novoValor;
	}

	private static String truncar(String valor) throws ParseException {

		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setParseBigDecimal(true);
		decimalFormat.parse(valor);
		BigDecimal bi = new BigDecimal(valor);
		String x = bi.toString();
		System.out.println("Valor foi truncado: " + x);
		return x;
	}

	private static String calcular(String x, String y) {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "Brazil"));
		dfs.setDecimalSeparator(',');
		dfs.setGroupingSeparator('.');
		BigDecimal big1 = new BigDecimal(x);
		BigDecimal big2 = new BigDecimal(y);
		BigDecimal bigResult = big1.add(big2);
		DecimalFormat df = new DecimalFormat("#,##0.00", dfs);
		String xxx = df.format(bigResult);
		System.out.println("Valor formatado, vamos retornar: " + xxx);
		return xxx;
	}

}
