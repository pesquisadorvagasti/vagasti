package br.com.vagaslinkedin.domain.model.enumerator;

public enum LinguagensProgramacaoEnum {
	/*JAVA(1, "Java", "Java",1),
	CSHARP(3, "C#", "C%23",2), 
	KOTLIN(2, "Kotlin", "Kotlin",3), 
	JAVASCRIPT(4, "JavaScript", "JavaScript",4),
	PYTHON(6, "Python", "Python",5),
	PHP(5, "PHP", "PHP",6),
	RUBY(10, "Ruby", "Ruby",7),*/
	GO(9, "Go", "Golang",8);

	private final Integer codigo;
	private final String descricao;
	private final String descricaoPesquisa;
	private final Integer ordem;

	LinguagensProgramacaoEnum(Integer codigo, String descricao, String descricaoPesquisa,Integer ordem) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.descricaoPesquisa = descricaoPesquisa;
		this.ordem = ordem;
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

	public Integer getOrdem() {
		return ordem;
	}

}
