package br.com.vagaslinkedin.domain.model.enumerator;

public enum LinguagensProgramacaoEnum {
	JAVA(1, "Java", "Java"), KOTLIN(2, "Kotlin", "Kotlin"), CSHARP(3, "C#", "C%23"),
	JAVASCRIPT(4, "JavaScript", "JavaScript"), PHP(5, "PHP", "PHP"), PYTHON(6, "Python", "Python"),
	C_MAIS_MAIS(7, "C++", "C%2B%2B"), SWIFT(8, "Swift", "Swift"), GO(9, "Go", "Go"), RUBY(10, "Ruby", "Ruby"),
	FLUTTER(1, "Flutter", "Flutter");

	private final Integer codigo;
	private final String descricao;
	private final String descricaoPesquisa;

	LinguagensProgramacaoEnum(Integer codigo, String descricao, String descricaoPesquisa) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.descricaoPesquisa = descricaoPesquisa;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDescricaoPesquisa() {
		return descricaoPesquisa;
	}

}
