package br.com.cadastrovagas.domain.enumerator;

public enum SiteVagaEnum {

	LINKEDIN(1, "LinkedIn"), INDEED(2, "Indeed"), GLASSDOOR(3, "Glassdoor");

	private final int codigo;
	private final String descricao;

	SiteVagaEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static SiteVagaEnum obterPorCodigo(int codigo) {
		for (SiteVagaEnum site : SiteVagaEnum.values()) {
			if (site.getCodigo() == codigo) {
				return site;
			}
		}
		throw new IllegalArgumentException("Código do site de vagas não encontrado: " + codigo);
	}

	public static SiteVagaEnum obterPorDescricao(String descricao) {
		for (SiteVagaEnum site : SiteVagaEnum.values()) {
			if (site.getDescricao().equalsIgnoreCase(descricao)) {
				return site;
			}
		}
		throw new IllegalArgumentException("Descrição do site de vagas não encontrada: " + descricao);
	}
}
