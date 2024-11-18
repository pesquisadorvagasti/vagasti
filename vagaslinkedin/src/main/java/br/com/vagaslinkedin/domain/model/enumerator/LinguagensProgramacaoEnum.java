package br.com.vagaslinkedin.domain.model.enumerator;

public enum LinguagensProgramacaoEnum {
	JAVASCRIPT("JavaScript"), PYTHON("Python"), JAVA("Java"), CSHARP("C#"), CPP("C++"), PHP("PHP"), SWIFT("Swift"),
	KOTLIN("Kotlin"), GO("Go"), RUBY("Ruby");

	private final String descricao;

	LinguagensProgramacaoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
