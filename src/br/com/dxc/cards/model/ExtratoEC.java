package br.com.dxc.cards.model;

public class ExtratoEC {

	private int comercio;
	private int sucursal;
	private String dataTransacao;
	private String dataVencimento;
	private String dataLiquidacao;
	private int producto;
	private int rubrica;
	private String descricao;
	private String valor;
	private String saldo;

	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public ExtratoEC() {
	}

	public int getProducto() {
		return producto;
	}

	public void setProducto(int producto) {
		this.producto = producto;
	}

	public int getRubrica() {
		return rubrica;
	}

	public void setRubrica(int rubrica) {
		this.rubrica = rubrica;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(String dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public int getComercio() {
		return comercio;
	}

	public void setComercio(int comercio) {
		this.comercio = comercio;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getSaldo() {
		return saldo;
	}

	public String getDataLiquidacao() {
		return dataLiquidacao;
	}

	public void setDataLiquidacao(String dataLiquidacao) {
		this.dataLiquidacao = dataLiquidacao;
	}

	public int getSucursal() {
		return sucursal;
	}

	public void setSucursal(int sucursal) {
		this.sucursal = sucursal;
	}

}
