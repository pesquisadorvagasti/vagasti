package br.com.vagaslinkedin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import br.com.vagaslinkedin.service.LinkedinService;
import br.com.vagaslinkedin.util.ScrappingUtil;

@SpringBootApplication
public class VagaslinkedinApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(VagaslinkedinApplication.class, args);

		LinkedinService linkedinService = applicationContext.getBean(LinkedinService.class);

		try (Playwright playwright = Playwright.create()) {

			Page page = ScrappingUtil.abrirBrowserMaximizado(playwright);

			linkedinService.efetuarLoginGoogle(page);

			ScrappingUtil.aguardarEmSegundos(2);

			linkedinService.abrirPaginaVagasLinkedin(page);

			linkedinService.visualizarVagasLinkedin(page);

			ScrappingUtil.aguardarEmSegundos(60);

		}

	}

}
