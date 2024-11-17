package br.com.vagaslinkedin.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import br.com.vagaslinkedin.util.ScrappingUtil;

@Service
public class LinkedinService {

	public void efetuarLoginGoogle(Page page) {

		page.navigate("https://accounts.google.com/signin");

		page.fill("input[type='email']", "pesquisadorvagasti@gmail.com");
		page.click("button:has-text('Próxima')");

		page.waitForSelector("input[type='password']");

		page.fill("input[type='password']", "51791542aA@15421542aA@");
		page.click("button:has-text('Próxima')");

		page.waitForURL("https://myaccount.google.com/*");
	}

	public Page abrirPaginaVagasLinkedin(Page page) {

		page.navigate("https://www.linkedin.com/jobs");

		page.waitForSelector("body");
		ScrappingUtil.aguardarEmSegundos(20);

		return page;
	}

	public void visualizarVagasLinkedin(Page page) {

		Page paginaVagasLinkedin = abrirPaginaVagasLinkedinComParametros(page, null);
		ScrappingUtil.aguardarEmSegundos(7);
		int quantidadeVagasDisponiveis = obterQuantidadeVagasDisponiveis(paginaVagasLinkedin);
		int quantidadePaginasPossiveis = obterQuantidadePaginasPossiveis(quantidadeVagasDisponiveis);

		int quantidadeItensMostrarPagina = -25;
		int quantidadeVagasCapturadas = 0;

		for (int i = 0; i < quantidadePaginasPossiveis; i++) {
			quantidadeItensMostrarPagina += 25;

			if (quantidadeItensMostrarPagina > 0) {
				page = abrirPaginaVagasLinkedinComParametros(paginaVagasLinkedin, quantidadeItensMostrarPagina);
				ScrappingUtil.aguardarEmSegundos(7);
			}

			ElementHandle primeiraVaga = paginaVagasLinkedin.querySelector("[data-job-id]");
			primeiraVaga.click();
			ScrappingUtil.aguardarEmSegundos(1);

			ScrappingUtil.rolarParaFinalPagina(page);
			List<ElementHandle> vagas = paginaVagasLinkedin.querySelectorAll("[data-job-id]");

			for (ElementHandle vaga : vagas) {

				try {
					clicarEmVagaPorVagaParaObterInformacoes(paginaVagasLinkedin, vaga);
					quantidadeVagasCapturadas++;
					System.out.println("Capturadas " + quantidadeVagasCapturadas + " vagas");
				} catch (Exception e) {
					continue;
					// System.err.println("Erro ao processar vaga: " + e.getMessage());
				}
			}

		}
	}

	private int obterQuantidadeVagasDisponiveis(Page page) {

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

	private int obterQuantidadePaginasPossiveis(int quantidadeVagasDisponiveis) {

		int quantidadePaginasDisponiveis = quantidadeVagasDisponiveis / 25;
		return quantidadePaginasDisponiveis - 25;
	}

	private Page abrirPaginaVagasLinkedinComParametros(Page page, Integer quantidadeItensMostrarPagina) {

		page.waitForSelector("body");

		String urlPaginaVagasLinkedin = "https://www.linkedin.com/jobs/search/?distance=100&f_WT=2&&keywords=Java&origin=JOB_SEARCH_PAGE_JOB_FILTER&refresh=true&spellCorrectionEnabled=true";

		if (Objects.nonNull(quantidadeItensMostrarPagina)) {
			urlPaginaVagasLinkedin = urlPaginaVagasLinkedin + "&start=" + quantidadeItensMostrarPagina;
		}

		page.navigate(urlPaginaVagasLinkedin);
		return page;
	}

	private void clicarEmVagaPorVagaParaObterInformacoes(Page page, ElementHandle vaga) {
		vaga.click();

		ScrappingUtil.aguardarEmSegundos(2);
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

//		System.out.println("Id da vaga: " + idVaga);
//		System.out.println("Título: " + tituloVaga);
//		System.out.println("Empresa: " + empresa);
		// System.out.println("Descrição: " + descricaoVaga);
//		System.out.println("-------------------------");
	}

	private String obterIdVaga(String url) {

		String regex = "currentJobId=([^&]*)";
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(url);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

}
