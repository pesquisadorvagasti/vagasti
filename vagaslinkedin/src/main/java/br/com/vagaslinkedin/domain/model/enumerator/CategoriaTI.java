package br.com.vagaslinkedin.domain.model.enumerator;

public enum CategoriaTI {
	ENGENHEIRO_DE_SOFTWARE("Engenheiro de software", "Engenheiro de software",true),
	ARQUITETO_DE_SOFTWARE("Arquiteto de software", "Arquiteto de software",true),
	TECNICO_EM_REDES("Técnico de centro de operações de rede", "Técnico de centro de operações de rede",true),
	ENGENHEIRO_EM_REDES("Engenheiro de centro de operações de rede", "Engenheiro de centro de operações de rede",true),
	NOC("NOC (Network Operations Center)", "NOC (Network Operations Center)",true),
	GERENTE_DE_TI("Gerente de TI", "Gerente de TI",true),
	SUPERVISOR_TI("Supervisor de TI", "Supervisor de TI",true),
	TECNICO_DE_SUPORTE("Tecnico de suporte", "Tecnico de suporte",true),
	ANALISTA_DE_SISTEMAS("Analista de sistemas", "Analista de sistemas",true), 
	SQL("SQL", "SQL",true),
	DESENVOLVEDOR_WEB("Desenvolvedor web", "Desenvolvedor web",true),
	DESENVOLVEDOR_FRONT_END("Desenvolvedor de front-end", "Desenvolvedor de front-end",true),
	DESENVOLVEDOR_BACK_END("Desenvolvedor Back-end", "Desenvolvedor Back-end",true),
	DESENVOLVEDOR_FULL_STACK("Desenvolvedor full stack", "Desenvolvedor full stack",true),
	DESENVOLVEDOR_MOBILE("Desenvolvedor de aplicativos móveis", "Desenvolvedor de aplicativos móveis",true),
	JAVA("Java", "Java",false), 
	SPRING_BOOT("Spring Boot", "Spring Boot",false),
	APACHE_KAFKA("Apache Kafka", "Apache Kafka",false),
	RABBITMQ("RabbitMQ", "RabbitMQ",false),
	CSHARP("C#", "C%23",false), 
	ASP_NET("ASP.NET", "ASP.NET",false),
	DOT_NET_FRAMEWORK(".NET Framework", ".NET Framework",false),
	KOTLIN("Kotlin", "Kotlin",false),
	JAVASCRIPT("JavaScript", "JavaScript",false),
	PYTHON("Python", "Python",false)
	, PHP("PHP", "PHP",false), RUBY("Ruby", "Ruby",false),
	GO("Go", "Go",false), 
	GOLANG("Golang", "Golang",false), 
	ELIXIR("Elixir", "Elixir",false), 
	REACT("React", "React",false),
	REACT_NATIVE("React Native", "React Native",false),
	FLUTTER("Flutter", "React Native",false), 
	XAMARIN("Xamarin", "Xamarin",false),
	ANGULAR("Angular", "Angular",false), 
	ANGULAR_JS("Angular Js", "Angular Js",false), 
	EJB("EJB", "EJB",false),
	ENTERPRISE_JAVA_BEAN("Enterprise Java Bean", "Enterprise Java Bean",false),
	JSF("JSF", "JSF",false),
	JAVA_SERVER_FACES("Java Server Faces", "Java Server Faces",false), 
	JSP("JSP", "JSP",false),
	JAVA_SERVER_PAGES("Java Server Pages", "Java Server Pages",false);

	private final String descricao;
	private final String descricaoPesquisa;
	private final boolean isCargo;

	CategoriaTI(String descricao, String descricaoPesquisa, boolean isCargo) {

		this.descricao = descricao;
		this.descricaoPesquisa = descricaoPesquisa;
		this.isCargo = isCargo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDescricaoPesquisa() {
		return descricaoPesquisa;
	}

	public boolean getIsCargo() {
		return isCargo;
	}

}
