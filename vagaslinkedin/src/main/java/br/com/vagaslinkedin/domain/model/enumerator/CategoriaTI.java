package br.com.vagaslinkedin.domain.model.enumerator;

public enum CategoriaTI {
	JAVA( "Java", "Java"), 
	CSHARP( "C#", "C%23"),	
	ASP_NET( "ASP.NET", "ASP.NET"),
	DOT_NET_FRAMEWORK( ".NET Framework", ".NET Framework"),
	KOTLIN( "Kotlin", "Kotlin"),
	JAVASCRIPT( "JavaScript", "JavaScript"),
	PYTHON("Python", "Python"), 
	PHP( "PHP", "PHP"),
	RUBY( "Ruby", "Ruby"), 
	GO( "Go", "Go"),
	GOLANG( "Golang", "Golang"),
	ELIXIR( "Elixir", "Elixir"), 
	REACT( "React", "React"),
	REACT_NATIVE( "React Native", "React Native"), 
	FLUTTER( "Flutter", "React Native"),
	XAMARIN( "Xamarin", "Xamarin"), 
	ANGULAR( "Angular", "Angular"), 
	ANGULAR_JS( "Angular Js", "Angular Js"),
	EJB("EJB", "EJB"), 
	ENTERPRISE_JAVA_BEAN( "Enterprise Java Bean", "Enterprise Java Bean"),
	JSF("JSF", "JSF"),
	JAVA_SERVER_FACES("Java Server Faces", "Java Server Faces"),
	JSP("JSP", "JSP"),
	JAVA_SERVER_PAGES("Java Server Pages", "Java Server Pages");

	private final String descricao;
	private final String descricaoPesquisa;

	CategoriaTI(String descricao, String descricaoPesquisa) {

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
