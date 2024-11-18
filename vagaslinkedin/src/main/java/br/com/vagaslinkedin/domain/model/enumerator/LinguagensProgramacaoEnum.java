package br.com.vagaslinkedin.domain.model.enumerator;

public enum LinguagensProgramacaoEnum {
	JAVASCRIPT("JavaScript", "JavaScript"), PYTHON("Python", "Python"), JAVA("Java", "Java"), CSHARP("C#", "C%23"),
	C_MAIS_MAIS("C++", "C%2B%2B"), PHP("PHP", "PHP"), SWIFT("Swift", "Swift"), KOTLIN("Kotlin", "Kotlin"),
	GO("Go", "Go"), RUBY("Ruby", "Ruby");

	private final String descricao;
	private final String descricaoPesquisa;

	LinguagensProgramacaoEnum(String descricao, String descricaoPesquisa) {
		this.descricao = descricao;
		this.descricaoPesquisa = descricaoPesquisa;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDescricaoPesquisa() {
		return descricaoPesquisa;
	}

}
