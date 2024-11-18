package br.com.vagaslinkedin.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import br.com.vagaslinkedin.domain.model.dto.VagaRequestDto;
import br.com.vagaslinkedin.domain.model.enumerator.LinguagensProgramacaoEnum;
import br.com.vagaslinkedin.util.ScrappingUtil;

@Service
public class LinkedinService {

	@Autowired
	private CadastroVagaService cadastroVagaService;

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

		List<String> listaLinguagensProgramacao = Stream.of(LinguagensProgramacaoEnum.values())
				.map(LinguagensProgramacaoEnum::getDescricao).toList();

		listaLinguagensProgramacao = listaLinguagensProgramacao.stream().sorted().toList();

		int quantidadeVagasInicioCaptura = 0;
		String linguagemInicioCaptura = "";
		for (String linguagemProgramacao : listaLinguagensProgramacao) {

			String urlPaginaVagasLinkedin = obterUrlPaginaVagasLinkedin(linguagemProgramacao);
			Page paginaVagasLinkedin = abrirPaginaVagasLinkedinComParametros(page, urlPaginaVagasLinkedin, null);

			int quantidadeVagasDisponiveis = obterQuantidadeVagasDisponiveis(paginaVagasLinkedin);

			quantidadeVagasInicioCaptura = quantidadeVagasDisponiveis;
			linguagemInicioCaptura = linguagemProgramacao;

			int quantidadePaginasPossiveis = (int) Math.ceil((double) quantidadeVagasDisponiveis / 25);

			int quantidadeItensMostrarPagina = -25;
			int quantidadeVagasCapturadas = 0;

			for (int i = 0; i < quantidadePaginasPossiveis; i++) {

				int quantidadeVagasDisponiveisMomento = obterQuantidadeVagasDisponiveis(paginaVagasLinkedin);

				if (linguagemProgramacao.equals(linguagemInicioCaptura)
						&& quantidadeVagasDisponiveisMomento < quantidadeVagasInicioCaptura) {
					quantidadePaginasPossiveis = (int) Math.ceil((double) quantidadeVagasDisponiveisMomento / 25);
				}
				System.out.println("Quantidade de páginas " + quantidadePaginasPossiveis);
				quantidadeItensMostrarPagina += 25;

				if (quantidadeItensMostrarPagina > 0) {
					page = abrirPaginaVagasLinkedinComParametros(paginaVagasLinkedin, urlPaginaVagasLinkedin,
							quantidadeItensMostrarPagina);
					ScrappingUtil.aguardarEmSegundos(3);
				}

				ElementHandle primeiraVaga = paginaVagasLinkedin.waitForSelector("[data-job-id]");
				primeiraVaga.click();

				ScrappingUtil.rolarParaFinalPagina(page);
				List<ElementHandle> vagas = paginaVagasLinkedin.querySelectorAll("[data-job-id]");

				for (ElementHandle vaga : vagas) {

					try {
						Optional<VagaRequestDto> vagaRequestDto = obterInformacoesVagaAposClique(paginaVagasLinkedin,
								vaga, linguagemProgramacao);

						vagaRequestDto.ifPresent(v -> cadastroVagaService.postarVaga(v));

						quantidadeVagasCapturadas++;
						System.out.println("Capturadas " + quantidadeVagasCapturadas + " vagas");
					} catch (Exception e) {

						System.err.println("Erro ao processar vaga: " + e.getMessage());
					}
				}
			}
		}
	}

	private int obterQuantidadeVagasDisponiveis(Page page) {

		ElementHandle elementoQtdItensPagina = page.waitForSelector(".jobs-search-results-list__subtitle");

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

	private Page abrirPaginaVagasLinkedinComParametros(Page page, String urlPaginaVagasLinkedin,
			Integer quantidadeItensMostrarPagina) {

		if (Objects.nonNull(quantidadeItensMostrarPagina)) {
			urlPaginaVagasLinkedin = urlPaginaVagasLinkedin + "&start=" + quantidadeItensMostrarPagina;
		}

		page.navigate(urlPaginaVagasLinkedin);
		page.waitForSelector("body");
		return page;
	}

	private Optional<VagaRequestDto> obterInformacoesVagaAposClique(Page page, ElementHandle vaga,
			String linguagemProgramacao) {

		if (Objects.isNull(vaga) || Objects.isNull(vaga.querySelector("Strong")))
			return Optional.empty();

		vaga.click();

		page.waitForSelector("#job-details");

		ScrappingUtil.aguardarEmSegundos(1);

		String idVaga = obterIdVaga(page.url());

		String linkVaga = "https://www.linkedin.com/jobs/search/?currentJobId=" + idVaga;

		String tituloVaga = vaga.waitForSelector("strong").textContent();

		String descricaoVaga = page.waitForSelector("#job-details").innerHTML();

		String empresa = vaga.waitForSelector(".job-card-container__primary-description").textContent()
				.replace("\n                  ", "").replace("\n", "");

		return Optional.of(new VagaRequestDto(null, Long.valueOf(idVaga), 1, linkVaga, linguagemProgramacao, empresa,
				tituloVaga, descricaoVaga));
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

	private String obterUrlPaginaVagasLinkedin(String linguagemProgramacao) {
		return "https://www.linkedin.com/jobs/search/?currentJobId=4079049333&distance=100.0&f_TPR=r86400&f_WT=2&geoId=106057199&keywords="
				+ linguagemProgramacao + "&origin=JOB_SEARCH_PAGE_JOB_FILTER";
	}
}
