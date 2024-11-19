package br.com.vagaslinkedin.domain.model.enumerator;

public enum ModalidadeTrabalhoEnum {
	PRESENCIAL(1, "Presencial", 2), REMOTO(2, "Remoto", 1), HIBRIDO(3, "Híbrido", 3);

	private final Integer codigo;
	private final String descricao;
	private final Integer ordem;

	private ModalidadeTrabalhoEnum(Integer codigo, String descricao, Integer ordem) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.ordem = ordem;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getOrdem() {
		return ordem;
	}

}
