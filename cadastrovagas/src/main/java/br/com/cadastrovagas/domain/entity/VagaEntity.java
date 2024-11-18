package br.com.cadastrovagas.domain.entity;

import br.com.cadastrovagas.domain.enumerator.SiteVagaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "VAGA", schema = "VAGASTI")
public class VagaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sqVaga;

	@Column(name = "ID_VAGA")
	private Long idVaga;
	@Column(name = "ID_SITE_VAGA")
	private SiteVagaEnum site;
	@Column(name = "LINK_VAGA")
	private String linkVaga;
	@Column(name = "LINGUAGEM")
	private String linguagem;
	@Column(name = "DS_EMPRESA")
	private String empresa;
	@Column(name = "DS_TITULO_VAGA")
	private String tituloVaga;
	@Column(name = "DS_VAGA", columnDefinition = "TEXT")
	private String descricao;

	public VagaEntity() {

	}

	public VagaEntity(Long sqVaga, Long idVaga, SiteVagaEnum site, String linkVaga, String linguagem, String empresa,
			String tituloVaga, String descricao) {
		super();
		this.sqVaga = sqVaga;
		this.idVaga = idVaga;
		this.site = site;
		this.linkVaga = linkVaga;
		this.linguagem = linguagem;
		this.empresa = empresa;
		this.tituloVaga = tituloVaga;
		this.descricao = descricao;
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

}
