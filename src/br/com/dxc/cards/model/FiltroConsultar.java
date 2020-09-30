package br.com.dxc.cards.model;

public class FiltroConsultar {

	private int acquirerId;
	private String dataInicio;
	private String dataFim;
	private int comercio;
	private int rubrica;
	private int produto;
	private int filial;

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public int getRubrica() {
		return rubrica;
	}

	public void setRubrica(int rubrica) {
		this.rubrica = rubrica;
	}

	public int getAcquirerId() {
		return acquirerId;
	}

	public void setAcquirerId(int acquirerId) {
		this.acquirerId = acquirerId;
	}

	public int getComercio() {
		return comercio;
	}

	public void setComercio(int comercio) {
		this.comercio = comercio;
	}

	public int getProduto() {
		return produto;
	}

	public void setProduto(int produto) {
		this.produto = produto;
	}

	public int getFilial() {
		return filial;
	}

	public void setFilial(int filial) {
		this.filial = filial;
	}

}