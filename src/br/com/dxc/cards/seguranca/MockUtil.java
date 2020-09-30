package br.com.dxc.cards.seguranca;

import java.util.Arrays;
import java.util.List;

import br.com.dxc.cards.enuns.PermissaoDxcRelatorio;

public class MockUtil {
	public static List<String> getAllTasks() {
		return Arrays.asList(PermissaoDxcRelatorio.REL_EVENT_1386.getCodigo(),
				PermissaoDxcRelatorio.REL_GER_FILAS_ACQUIRER.getCodigo(),
				PermissaoDxcRelatorio.REL_GER_FILAS_EMISSOR.getCodigo(),
				PermissaoDxcRelatorio.REL_EXTRATO_EC.getCodigo());
	}
}