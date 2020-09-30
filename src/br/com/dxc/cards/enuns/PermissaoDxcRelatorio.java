package br.com.dxc.cards.enuns;

public enum PermissaoDxcRelatorio {
	REL_EVENT_1386("dxcrel.eventos.1386", "Central de Atendimento - Relatorio de Eventos 1386"),
	REL_GER_FILAS_EMISSOR("dxcrel.ger.filas.emissor", "Relatorio Gerencial Filas Emissor"),
	REL_GER_FILAS_ACQUIRER("dxcrel.ger.filas.acquirer", "Relatorio Gerencial Filas Acquirer"),
	REL_EXTRATO_EC("dxcrel.extrato.ec", "Extrato do EC");        
	
	private String codigo;
	private String descricao;

	private PermissaoDxcRelatorio(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static PermissaoDxcRelatorio converterPeloCodigo(String codigo) {
		for (PermissaoDxcRelatorio permissao : PermissaoDxcRelatorio.values()) {
			if (permissao.getCodigo().equalsIgnoreCase(codigo)) {
				return permissao;
			}
		}
		return null;
	}
}
