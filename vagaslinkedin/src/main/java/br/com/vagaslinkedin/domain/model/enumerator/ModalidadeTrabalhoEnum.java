package br.com.vagaslinkedin.domain.model.enumerator;

public enum ModalidadeTrabalhoEnum {
	PRESENCIAL(1, "Presencial"), REMOTO(2, "Remoto"), HIBRIDO(3, "Híbrido");

	private final Integer codigo;
	private final String descricao;

	private ModalidadeTrabalhoEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

}
