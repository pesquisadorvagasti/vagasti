package br.com.vagaslinkedin;

import java.util.List;
import java.util.Objects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

@SpringBootApplication
public class VagaslinkedinApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(VagaslinkedinApplication.class, args);

		try (Playwright playwright = Playwright.create()) {

			Page page = abrirBrowserMaximizado(playwright);

			efetuarLoginGoogle(page);

			aguardarEmSegundos(3);

			visualizarVagasLinkedin(page);

			aguardarEmSegundos(60);

		}

	}

	private static void aguardarEmSegundos(Integer segundos) {
		try {
			if (Objects.isNull(segundos))
				throw new RuntimeException("Informe os segundos que deseja aguardar!");
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void efetuarLoginGoogle(Page page) {

		// Acesse a página de login do Gmail
		page.navigate("https://accounts.google.com/signin");

		// Preencha o email
		page.fill("input[type='email']", "pesquisadorvagasti@gmail.com");
		page.click("button:has-text('Próxima')");

		// Aguarde o campo de senha ser exibido
		page.waitForSelector("input[type='password']");

		// Preencha a senha
		page.fill("input[type='password']", "51791542aA@15421542aA@");
		page.click("button:has-text('Próxima')");

		// Aguarde o login ser concluído
		page.waitForURL("https://myaccount.google.com/*");
	}

	private static Page abrirPaginaVagasLinkedin(Page page) {

		page.navigate("https://www.linkedin.com/jobs");

		page.waitForSelector("body");
		aguardarEmSegundos(20);

		return page;
	}

	private static Page abrirPaginaVagasLinkedinComParametros(Page page, Integer quantidadeItensMostrarPagina) {

		page.waitForSelector("body");

		String urlPaginaVagasLinkedin = "https://www.linkedin.com/jobs/search/?distance=100&f_WT=2&&keywords=Java&origin=JOB_SEARCH_PAGE_JOB_FILTER&refresh=true&spellCorrectionEnabled=true";

		if (Objects.nonNull(quantidadeItensMostrarPagina)) {
			urlPaginaVagasLinkedin = urlPaginaVagasLinkedin + "&start=" + quantidadeItensMostrarPagina;
		}

		page.navigate(urlPaginaVagasLinkedin);
		return page;
	}

	private static Page abrirBrowserMaximizado(Playwright playwright) {
		Browser browser = playwright.chromium()
				.launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(List.of("--start-maximized")));
		BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
		return context.newPage();
	}

	private static String obterIdVaga(String url) {

		String regex = "currentJobId=([^&]*)";
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(url);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	public static void visualizarVagasLinkedin(Page page) {

		Page paginaVagasLinkedin = abrirPaginaVagasLinkedinComParametros(page, null);
		aguardarEmSegundos(20);
		int quantidadeVagasDisponiveis = obterQuantidadeVagasDisponiveis(paginaVagasLinkedin);
		int quantidadePaginasPossiveis = obterQuantidadePaginasPossiveis(quantidadeVagasDisponiveis);

		int quantidadeItensMostrarPagina = -25;

		for (int i = 0; i < quantidadePaginasPossiveis; i++) {
			quantidadeItensMostrarPagina += 25;

			if (quantidadeItensMostrarPagina > 0) {
				page = abrirPaginaVagasLinkedinComParametros(paginaVagasLinkedin, quantidadeItensMostrarPagina);
			}

			ElementHandle primeiraVaga = paginaVagasLinkedin.querySelector("[data-job-id]");
			primeiraVaga.click();
			aguardarEmSegundos(1);

			rolarParaFinalPagina(page);
			List<ElementHandle> vagas = paginaVagasLinkedin.querySelectorAll("[data-job-id]");

			for (ElementHandle vaga : vagas) {

				try {
					clicarEmVagaPorVagaParaObterInformacoes(paginaVagasLinkedin, vaga);

				} catch (Exception e) {

					System.err.println("Erro ao processar vaga: " + e.getMessage());
				}
			}

		}
	}

	private static void clicarEmVagaPorVagaParaObterInformacoes(Page page, ElementHandle vaga) {
		vaga.click();

		aguardarEmSegundos(2);
		page.waitForSelector(
				".disabled.ember-view.job-card-container__link.job-card-list__title.job-card-list__title--link strong");

		String idVaga = obterIdVaga(page.url());

		String tituloVaga = vaga.querySelector(
				".disabled.ember-view.job-card-container__link.job-card-list__title.job-card-list__title--link strong")
				.textContent();

		String empresa = vaga.querySelector(".job-card-container__primary-description").textContent()
				.replace("\n                  ", "").replace("\n", "");

		page.waitForSelector("#job-details");
		// String descricaoVaga = page.querySelector("#job-details").innerHTML();

		System.out.println("Id da vaga: " + idVaga);
		System.out.println("Título: " + tituloVaga);
		System.out.println("Empresa: " + empresa);
		// System.out.println("Descrição: " + descricaoVaga);
		System.out.println("-------------------------");
	}

	public static void rodarRodinhaMouseParaBaixo(Page page) {
		page.mouse().wheel(0, 500);
	}

	public static void rodarRodinhaMouseParaCima(Page page) {
		page.mouse().wheel(0, -500);
	}

	public static void rolarParaFinalPagina(Page page) {

		for (int i = 0; i <= 10; i++) {
			rodarRodinhaMouseParaBaixo(page);
			aguardarEmSegundos(1);
		}

	}

	public static int obterQuantidadeVagasDisponiveis(Page page) {

		ElementHandle elementoQtdItensPagina = page.querySelector(".jobs-search-results-list__subtitle");

		if (elementoQtdItensPagina != null) {

			String textoQtdItensPagina = elementoQtdItensPagina.textContent();

			textoQtdItensPagina = textoQtdItensPagina.replace("\n                  \n                    ", "");
			String[] partes = textoQtdItensPagina.split(" ");
			String qtdItensString = partes[0].replace(".", "");

			try {
				return Integer.parseInt(qtdItensString);
			} catch (NumberFormatException e) {
				System.err.println("Erro ao converter a quantidade de itens para número: " + e.getMessage());
			}
		} else {
			System.err.println("Elemento não encontrado na página!");
		}

		return 0;
	}

	public static int obterQuantidadePaginasPossiveis(int quantidadeVagasDisponiveis) {

		int quantidadePaginasDisponiveis = quantidadeVagasDisponiveis / 25;
		return quantidadePaginasDisponiveis - 25;
	}
}
