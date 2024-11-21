package br.com.vagaslinkedin.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import br.com.vagaslinkedin.domain.model.dto.InformacaoInicialVagaDto;
import br.com.vagaslinkedin.domain.model.dto.VagaRequestDto;
import br.com.vagaslinkedin.domain.model.enumerator.LinguagensProgramacaoEnum;
import br.com.vagaslinkedin.domain.model.enumerator.ModalidadeTrabalhoEnum;
import br.com.vagaslinkedin.util.ScrappingUtil;

@Service
public class LinkedinService {
	record LinguagemProgramacao(Integer codigo, String descricao, String descricaoPesquisa, Integer ordem) {
	};

	record ModalidadeTrabalho(Integer codigo, String descricaoModalidadeTrabalho, Integer ordem) {
	};

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

		List<LinguagemProgramacao> listaLinguagensProgramacao = obterListaLinguagensProgramacao();

		List<ModalidadeTrabalho> modalidadesTrabalho = obterModalidadesTrabalho();

		for (ModalidadeTrabalho modalidadeTrabalho : modalidadesTrabalho) {
			for (LinguagemProgramacao linguagemProgramacao : listaLinguagensProgramacao) {

				String urlPaginaVagasLinkedin = obterUrlPaginaVagasLinkedin(linguagemProgramacao.descricaoPesquisa,
						modalidadeTrabalho.codigo);
				Page paginaVagasLinkedin = abrirPaginaVagasLinkedinComParametros(page, urlPaginaVagasLinkedin);
				ScrappingUtil.aguardarEmSegundos(2);

				int qtdPaginasDisponiveis = obterQuantidadePaginasDisponiveis(page);
				clicarPrimeiraPagina(page);
				int contador = 0;

				for (int i = 0; i <= qtdPaginasDisponiveis; i++) {
					if (i > 0) {
						clicarBotaoProximaPagina(page);
						ScrappingUtil.aguardarEmSegundos(2);
					}
					clicarPrimeiraVagaERolarPaginaParaFinal(page);

					List<ElementHandle> vagas = paginaVagasLinkedin.querySelectorAll("[data-job-id]");

					for (ElementHandle vaga : vagas) {

						try {

							Optional<VagaRequestDto> vagaRequestDto = obterInformacoesVagaAposClique(
									paginaVagasLinkedin, vaga, linguagemProgramacao.descricao,
									modalidadeTrabalho.descricaoModalidadeTrabalho);

							vagaRequestDto.ifPresent(v -> cadastroVagaService.postarVaga(v));

							contador++;
							System.out.println("Capturadas " + contador + " vagas");
							/*
							 * if (contador == vagas.size() - 1) {
							 * System.err.println("Próxima página, capturados " + vagas.size() + " itens ");
							 * ScrappingUtil.aguardarEmSegundos(5);
							 * clicarPrimeiraVagaERolarPaginaParaFinal(page); vagas =
							 * paginaVagasLinkedin.querySelectorAll("[data-job-id]"); contador = 0; }
							 */

						} catch (Exception e) {

							System.err.println("Erro ao processar vaga: " + e.getMessage());
						}
					}
				}
			}
		}

	}

	private void clicarBotaoProximaPagina(Page page) {
		Locator botaoProximaPagina = page.locator("[aria-label='Ver próxima página']");
		if (botaoProximaPagina.isVisible())
			botaoProximaPagina.click();
	}

	private int obterQuantidadePaginasDisponiveis(Page page) {

		int qtdPaginas = 0;

		boolean temProximaPagina = true;
		while (temProximaPagina) {
			Locator botaoProximaPagina = page.locator("[aria-label='Ver próxima página']");
			if (botaoProximaPagina.isVisible()) {
				botaoProximaPagina.click();
				qtdPaginas++;
				temProximaPagina = true;
			} else {
				temProximaPagina = false;
			}
		}

		return qtdPaginas;

	}

	private void clicarPrimeiraPagina(Page page) {
		Locator primeiraPagina = page.locator("[class='jobs-search-pagination__indicator-button']").nth(0);

		if (primeiraPagina.isVisible() ) {
			primeiraPagina.nth(0).click();
		}
	}

	private void clicarPrimeiraVagaERolarPaginaParaFinal(Page page) {
		ElementHandle primeiraVaga = page.waitForSelector("[data-job-id]");

		if (primeiraVaga.isVisible()) {
			primeiraVaga.click();
			ScrappingUtil.rolarParaFinalPagina(page);
		}
	}

	private List<LinguagemProgramacao> obterListaLinguagensProgramacao() {
		List<LinguagemProgramacao> linguagens = Stream.of(LinguagensProgramacaoEnum.values()).map(
				l -> new LinguagemProgramacao(l.getCodigo(), l.getDescricao(), l.getDescricaoPesquisa(), l.getOrdem()))
				.collect(Collectors.toList());
		Collections.sort(linguagens, Comparator.comparing(LinguagemProgramacao::ordem));
		return linguagens;
	}

	private List<ModalidadeTrabalho> obterModalidadesTrabalho() {
		List<ModalidadeTrabalho> modalidades = Stream.of(ModalidadeTrabalhoEnum.values())
				.map(m -> new ModalidadeTrabalho(m.getCodigo(), m.getDescricao(), m.getOrdem()))
				.collect(Collectors.toList());
		Collections.sort(modalidades, Comparator.comparing(ModalidadeTrabalho::ordem));
		return modalidades;
	}

	private Page abrirPaginaVagasLinkedinComParametros(Page page, String urlPaginaVagasLinkedin) {

		page.navigate(urlPaginaVagasLinkedin);
		page.waitForSelector("body");
		return page;
	}

	private Optional<VagaRequestDto> obterInformacoesVagaAposClique(Page page, ElementHandle vaga,
			String linguagemProgramacao, String modalidadeTrabalho) {

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

		Optional<InformacaoInicialVagaDto> informacaoInicialVagaDto = obterInformacoesIniciasVaga(page);
		String localizacao = null;
		String postadoEm = null;
		Integer quantidadeCandidaturas = null;
		Boolean candidaturaSimplificada = Boolean.FALSE;

		boolean linguagemPrincipalExisteNaVaga = ScrappingUtil.palavraExisteNoTexto(linguagemProgramacao,
				descricaoVaga);

		if (!linguagemPrincipalExisteNaVaga) {
			LinguagemProgramacao linguagemVaga = obterListaLinguagensProgramacao().stream()
					.filter(linguagem -> ScrappingUtil.palavraExisteNoTexto(linguagem.descricao, descricaoVaga))
					.findFirst().orElse(null);

			linguagemProgramacao = Objects.nonNull(linguagemVaga) ? linguagemVaga.descricao : null;
		}

		if (informacaoInicialVagaDto.isPresent()) {
			localizacao = informacaoInicialVagaDto.get().localizacao();
			postadoEm = informacaoInicialVagaDto.get().postadoEm();
			quantidadeCandidaturas = informacaoInicialVagaDto.get().quantidadeCandidaturas();
			candidaturaSimplificada = informacaoInicialVagaDto.get().candidaturaSimplificada();
		}

		return Optional.of(new VagaRequestDto(null, Long.valueOf(idVaga), localizacao, postadoEm,
				quantidadeCandidaturas, 1, linkVaga, linguagemProgramacao, empresa, tituloVaga, descricaoVaga,
				candidaturaSimplificada, modalidadeTrabalho));

	}

	private Optional<InformacaoInicialVagaDto> obterInformacoesIniciasVaga(Page page) {

		Locator detalheVaga = page
				.locator("[class='job-details-jobs-unified-top-card__primary-description-container']");

		Locator detalhesVaga = detalheVaga.locator("[class='tvm__text tvm__text--low-emphasis']");

		Locator elementoPostadoEm = detalheVaga.locator("[class = 'tvm__text tvm__text--positive']");

		boolean candidaturaSimplificada = false;

		Locator divBotaoCandidatura = page.locator("[class='jobs-apply-button--top-card']");

		Locator botaoCandidatura = divBotaoCandidatura.locator("button");

		if (Objects.isNull(divBotaoCandidatura) || botaoCandidatura.count() == 0) {
			candidaturaSimplificada = false;
		}

		String textoBotaoCandidaturaSimplificada = botaoCandidatura.first().textContent();
		textoBotaoCandidaturaSimplificada = textoBotaoCandidaturaSimplificada
				.replace("        \\n    \\n    \\n\\n\\n\\n\\n    ", "").replace("\n", "").trim();

		candidaturaSimplificada = textoBotaoCandidaturaSimplificada.equals("Candidatura simplificada");

		if (Objects.isNull(detalheVaga) || Objects.isNull(detalhesVaga))
			return Optional.empty();

		if (Objects.isNull(elementoPostadoEm) || Objects.isNull(detalhesVaga) || Objects.isNull(detalhesVaga.nth(0))
				|| Objects.isNull(detalhesVaga.nth(2)) || Objects.isNull(detalhesVaga.nth(4))) {

			return Optional.empty();
		}
		String localidade = detalhesVaga.nth(0).textContent().trim();
		String candidaturas = detalhesVaga.nth(3).textContent().trim();
		String postadoEm = elementoPostadoEm.textContent().trim();

		return Optional.of(new InformacaoInicialVagaDto(localidade, postadoEm,
				ScrappingUtil.extrairNumero(candidaturas), candidaturaSimplificada));

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

	private String obterUrlPaginaVagasLinkedin(String linguagemProgramacao, Integer codigoModalidadeTrabalho) {
		return "https://www.linkedin.com/jobs/search/?f_TPR=r86400&f_WT=" + codigoModalidadeTrabalho
				+ "&geoId=106057199&keywords=" + linguagemProgramacao
				+ "&origin=JOB_SEARCH_PAGE_KEYWORD_AUTOCOMPLETE&refresh=true";
	}
}
