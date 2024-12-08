package br.com.vagaslinkedin.domain.model.enumerator;

public enum CategoriaTI {
	ENGENHEIRO_DE_SOFTWARE("Engenheiro de software","Engenheiro de software"),
	ARQUITETO_DE_SOFTWARE("Arquiteto de software","Arquiteto de software"),
	TECNICO_EM_REDES("Técnico de centro de operações de rede","Técnico de centro de operações de rede"),
	ENGENHEIRO_EM_REDES("Engenheiro de centro de operações de rede","Engenheiro de centro de operações de rede"),
	NOC("NOC (Network Operations Center)","NOC (Network Operations Center)"),
	GERENTE_DE_TI("Gerente de TI","Gerente de TI"),
	SUPERVISOR_TI("Supervisor de TI","Supervisor de TI"),
	TECNICO_DE_SUPORTE("Tecnico de suporte","Tecnico de suporte"),
	ANALISTA_DE_SISTEMAS("Analista de sistemas","Analista de sistemas"),
	SQL("SQL","SQL"),
    DESENVOLVEDOR_WEB("Desenvolvedor web","Desenvolvedor web"),
    DESENVOLVEDOR_FRONT_END("Desenvolvedor de front-end","Desenvolvedor de front-end"),
    DESENVOLVEDOR_BACK_END("Desenvolvedor Back-end","Desenvolvedor Back-end"),
    DESENVOLVEDOR_FULL_STACK("Desenvolvedor full stack","Desenvolvedor full stack"),
    DESENVOLVEDOR_MOBILE("Desenvolvedor de aplicativos móveis","Desenvolvedor de aplicativos móveis"),
	JAVA( "Java", "Java"),
	SPRING_BOOT( "Spring Boot", "Spring Boot"),
	APACHE_KAFKA( "Apache Kafka", "Apache Kafka"),
	RABBITMQ( "RabbitMQ", "RabbitMQ"),	
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
