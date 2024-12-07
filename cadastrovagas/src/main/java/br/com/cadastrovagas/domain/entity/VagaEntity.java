package br.com.cadastrovagas.domain.entity;

import br.com.cadastrovagas.domain.enumerator.SiteVagaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "VAGA", schema = "VAGASTI")
public class VagaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sqVaga;

	@Column(name = "ID_VAGA")
	private Long idVaga;
	@Column(name = "LOCALIDADE")
	private String localidade;
	@Column(name = "POSTADA_EM")
	private String postadaEm;
	@Column(name = "QTD_CANDIDATURAS")
	private Integer quantidadeCandidaturas;
	private SiteVagaEnum site;
	@Column(name = "LINK_VAGA")
	private String linkVaga;
	@Column(name = "DS_LINGUAGEM")
	private String linguagem;
	@Column(name = "DS_LINGUAGENS_VAGA")
	private String descricaoLinguagensVaga;
	@Column(name = "DS_EMPRESA")
	private String empresa;
	@Column(name = "DS_TITULO_VAGA")
	private String tituloVaga;
	@Column(name = "DS_VAGA", columnDefinition = "TEXT")
	private String descricao;
	@Column(name = "CANDIDATURA_SIMPLIFICADA")
	private Boolean candidaturaSimplificada;
	@Column(name = "DS_MODALIDADE_TRABALHO")
	private String modalidadeTrabalho;

	public VagaEntity() {

	}

	public VagaEntity(Long sqVaga, Long idVaga, String localidade, String postadaEm, Integer quantidadeCandidaturas,
			SiteVagaEnum site, String linkVaga, String linguagem, String descricaoLinguagensVaga, String empresa,
			String tituloVaga, String descricao, Boolean candidaturaSimplificada, String modalidadeTrabalho) {
		super();
		this.sqVaga = sqVaga;
		this.idVaga = idVaga;
		this.localidade = localidade;
		this.postadaEm = postadaEm;
		this.quantidadeCandidaturas = quantidadeCandidaturas;
		this.site = site;
		this.linkVaga = linkVaga;
		this.linguagem = linguagem;
		this.descricaoLinguagensVaga = descricaoLinguagensVaga;
		this.empresa = empresa;
		this.tituloVaga = tituloVaga;
		this.descricao = descricao;
		this.candidaturaSimplificada = candidaturaSimplificada;
		this.modalidadeTrabalho = modalidadeTrabalho;

	}

	public Long getSqVaga() {
		return sqVaga;
	}

	public void setSqVaga(Long sqVaga) {
		this.sqVaga = sqVaga;
	}

	public Long getIdVaga() {

		return idVaga;
	}

	public void setIdVaga(Long idVaga) {
		this.idVaga = idVaga;
	}

	public SiteVagaEnum getSite() {
		return site;
	}

	public void setSite(SiteVagaEnum site) {
		this.site = site;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getTituloVaga() {
		return tituloVaga;
	}

	public void setTituloVaga(String tituloVaga) {
		this.tituloVaga = tituloVaga;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLinkVaga() {
		return linkVaga;
	}

	public void setLinkVaga(String linkVaga) {
		this.linkVaga = linkVaga;
	}

	public String getLinguagem() {
		return linguagem;
	}

	public void setLinguagem(String linguagem) {
		this.linguagem = linguagem;
	}

	public String getDescricaoLinguagensVaga() {
		return descricaoLinguagensVaga;
	}

	public void setDescricaoLinguagensVaga(String descricaoLinguagensVaga) {
		this.descricaoLinguagensVaga = descricaoLinguagensVaga;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getPostadaEm() {
		return postadaEm;
	}

	public void setPostadaEm(String postadaEm) {
		this.postadaEm = postadaEm;
	}

	public Integer getQuantidadeCandidaturas() {
		return quantidadeCandidaturas;
	}

	public void setQuantidadeCandidaturas(Integer quantidadeCandidaturas) {
		this.quantidadeCandidaturas = quantidadeCandidaturas;
	}

	public Boolean getCandidaturaSimplificada() {
		return candidaturaSimplificada;
	}

	public void setCandidaturaSimplificada(Boolean candidaturaSimplificada) {
		this.candidaturaSimplificada = candidaturaSimplificada;
	}

	public String getModalidadeTrabalho() {
		return modalidadeTrabalho;
	}

	public void setModalidadeTrabalho(String modalidadeTrabalho) {
		this.modalidadeTrabalho = modalidadeTrabalho;
	}

}
