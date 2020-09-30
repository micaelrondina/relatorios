package br.com.dxc.cards.model;

public class Arquivo {
	
	private Long idRel;
	private String nomeRelat;
	private byte[] conteudo;
	
	public Arquivo() {}
	
	public Arquivo(Long idRel, String nomeRelat, byte[] conteudoArquivo) {
		this.idRel = idRel;
		this.nomeRelat = nomeRelat;
		this.conteudo = conteudoArquivo;
	}
	
	public Long getIdRel() {
		return idRel;
	}
	public void setIdRel(Long idRel) {
		this.idRel = idRel;
	}
	public String getNomeRelat() {
		return nomeRelat;
	}
	public void setNomeRelat(String nomeRelat) {
		this.nomeRelat = nomeRelat;
	}
	public byte[] getConteudo() {
		return conteudo;
	}
	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}
}
