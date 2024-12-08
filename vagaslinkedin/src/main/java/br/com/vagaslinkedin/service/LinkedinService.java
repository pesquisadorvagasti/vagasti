package br.com.vagaslinkedin.service;

import java.util.ArrayList;
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
import br.com.vagaslinkedin.domain.model.enumerator.CategoriaTI;
import br.com.vagaslinkedin.domain.model.enumerator.ModalidadeTrabalhoEnum;
import br.com.vagaslinkedin.util.ScrappingUtil;

@Service
public class LinkedinService {

	record CargosOuTecnologias(String descricao, String descricaoPesquisa, Integer ordem, boolean isCargo) {
	};

	record ModalidadeTrabalho(Integer codigo, String descricaoModalidadeTrabalho, Integer ordem) {
	};

	record PaginaDisponivel(boolean foiPossivelVerificarPaginasDisponiveis, int quantidadePaginas) {
	};

	List<String> listaLinguagensProgramacao = new ArrayList<>();

	public LinkedinService() {
		this.listaLinguagensProgramacao = obterCargosOuLinguagens().stream().map(CargosOuTecnologias::descricao)
				.toList();
	}

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

		List<CargosOuTecnologias> listaCargosOuLinguagens = obterCargosOuLinguagens();

		List<ModalidadeTrabalho> modalidadesTrabalho = obterModalidadesTrabalho();
		while (true) {
			for (ModalidadeTrabalho modalidadeTrabalho : modalidadesTrabalho) {
				for (CargosOuTecnologias cargoOuLinguagem : listaCargosOuLinguagens) {
					System.out.println("Processando vagas de " + cargoOuLinguagem.descricao
							+ " - Modalidade de trabalho: " + modalidadeTrabalho.descricaoModalidadeTrabalho);
					boolean procurarProximaLinguagem = false;

					if (naoTemMaisVagas(page)) {
						page.navigate(obterUrlPaginaVagasLinkedin());
						continue;
					}

					String urlPaginaVagasLinkedin = obterUrlPaginaVagasLinkedin(cargoOuLinguagem.descricaoPesquisa,
							modalidadeTrabalho.codigo);

					Page paginaVagasLinkedin = abrirPaginaVagasLinkedinComParametros(page, urlPaginaVagasLinkedin);
					ScrappingUtil.aguardarEmSegundos(2);
					PaginaDisponivel paginaDisponivel = obterQuantidadePaginasDisponiveis(page);

					if (!paginaDisponivel.foiPossivelVerificarPaginasDisponiveis) {
						procurarProximaLinguagem = true;
						break;
					}

					int qtdPaginasDisponiveis = paginaDisponivel.quantidadePaginas;

					boolean foiPossivelClicarPrimeiraPagina = clicarPrimeiraPagina(page);

					if (!foiPossivelClicarPrimeiraPagina) {
						abrirPaginaVagasLinkedinComParametros(page, urlPaginaVagasLinkedin);
					}

					int contador = 0;

					for (int i = 0; i <= qtdPaginasDisponiveis; i++) {
						if (i > 0) {
							clicarBotaoProximaPagina(page);
							ScrappingUtil.aguardarEmSegundos(2);
						}
						boolean temPrimeiraVaga = clicarPrimeiraVaga(page);

						if (!temPrimeiraVaga) {
							procurarProximaLinguagem = true;
							break;
						}

						ScrappingUtil.rolarParaFinalPagina(page);

						Locator vagas = paginaVagasLinkedin.locator("[data-job-id]");

						for (int indice = 0; indice < vagas.count(); indice++) {
							try {

								Optional<VagaRequestDto> vagaRequestDto = obterInformacoesVagaAposClique(
										paginaVagasLinkedin, vagas.nth(indice), cargoOuLinguagem.descricao,
										cargoOuLinguagem.isCargo, modalidadeTrabalho.descricaoModalidadeTrabalho);

								vagaRequestDto.ifPresent(v -> cadastroVagaService.postarVaga(v));

								contador++;
								System.out.println("Capturadas " + contador + " vagas");

							} catch (Exception e) {

								System.err.println("Erro ao processar vaga: " + e.getMessage());
							}
						}
					}
					if (procurarProximaLinguagem) {
						continue;
					}
				}
			}
		}
	}

	private boolean naoTemMaisVagas(Page page) {
		Locator naoTemMaisVagas = page.locator("[class='t-24 t-black t-normal text-align-center']");
		boolean naoTem = naoTemMaisVagas.count() > 0;
		if (naoTem)
			System.out.println("Não tem mais vagas disponíveis, indo para a próxima");
		return naoTem;
	}

	private void clicarBotaoProximaPagina(Page page) {
		Locator botaoProximaPagina = page.locator("[aria-label='Ver próxima página']");
		if (botaoProximaPagina.isVisible())
			botaoProximaPagina.click();
	}

	private PaginaDisponivel obterQuantidadePaginasDisponiveis(Page page) {

		try {
			int qtdPaginas = 0;

			boolean temProximaPagina = true;

			while (temProximaPagina) {
				Locator botaoProximaPagina = page.locator("[aria-label='Ver próxima página']");
				if (botaoProximaPagina.count() > 0 && botaoProximaPagina.isVisible()) {
					botaoProximaPagina.click();
					ScrappingUtil.aguardarEmSegundos(2);
					qtdPaginas++;
					temProximaPagina = true;
				} else {
					temProximaPagina = false;
				}
			}

			return new PaginaDisponivel(true, qtdPaginas);
		} catch (Exception e) {
			return new PaginaDisponivel(false, 0);
		}

	}

	private boolean clicarPrimeiraPagina(Page page) {

		try {
			Locator primeiraPagina = page.locator("[class='jobs-search-pagination__indicator-button']").nth(0);

			if (primeiraPagina.count() > 0 && primeiraPagina.isVisible()) {
				primeiraPagina.nth(0).click();
				return true;
			}
		} catch (Exception e) {
			System.out.println("Não foi possível clicar na primeira página, vamos redirecionar");
		}
		return false;

	}

	private boolean clicarPrimeiraVaga(Page page) {
		try {
			ElementHandle primeiraVaga = page.waitForSelector("[data-job-id]");

			if (Objects.nonNull(primeiraVaga) && primeiraVaga.isVisible()) {
				primeiraVaga.click();
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	private List<CargosOuTecnologias> obterCargosOuLinguagens() {
		List<CargosOuTecnologias> linguagens = Stream.of(CategoriaTI.values()).map(
				l -> new CargosOuTecnologias(l.getDescricao(), l.getDescricaoPesquisa(), l.ordinal(), l.getIsCargo()))
				.collect(Collectors.toList());
		Collections.sort(linguagens, Comparator.comparing(CargosOuTecnologias::ordem));
		return linguagens;
	}

	private List<ModalidadeTrabalho> obterModalidadesTrabalho() {
		List<ModalidadeTrabalho> modalidades = Stream.of(ModalidadeTrabalhoEnum.values())
				.map(m -> new ModalidadeTrabalho(m.getCodigo(), m.getDescricao(), m.ordinal()))
				.collect(Collectors.toList());
		Collections.sort(modalidades, Comparator.comparing(ModalidadeTrabalho::ordem));
		return modalidades;
	}

	private Page abrirPaginaVagasLinkedinComParametros(Page page, String urlPaginaVagasLinkedin) {

		page.navigate(urlPaginaVagasLinkedin);
		page.waitForSelector("body");
		return page;
	}

	private Optional<VagaRequestDto> obterInformacoesVagaAposClique(Page page, Locator vaga, String linguagemOuCargo,
			boolean isCargo, String modalidadeTrabalho) {

		if (vaga == null || vaga.locator("strong").count() == 0) {
			return Optional.empty();
		}

		vaga.click();

		page.waitForSelector("#job-details");

		ScrappingUtil.aguardarEmSegundos(1);

		String idVaga = obterIdVaga(page.url());
		String linkVaga = "https://www.linkedin.com/jobs/view/" + idVaga;

		String tituloVaga = vaga.locator("strong").textContent();

		String descricaoVaga = page.locator("#job-details").innerHTML();

		String empresa = null;

		Locator elementoEmpresa = page.locator("[class='job-details-jobs-unified-top-card__company-name']");

		empresa = elementoEmpresa.count() > 0
				? elementoEmpresa.textContent().replace("\n                  ", "").replace("\n", "")
				: null;

		empresa = elementoEmpresa.textContent().replace("\n                  ", "").replace("\n", "");

		Optional<InformacaoInicialVagaDto> informacaoInicialVagaDto = obterInformacoesIniciasVaga(page);
		String localizacao = null;
		String postadoEm = null;
		Integer quantidadeCandidaturas = null;
		Boolean candidaturaSimplificada = Boolean.FALSE;

		String descricaoLinguagensVaga = obterDescricaoLinguagensVaga(isCargo, tituloVaga, descricaoVaga);

		if (informacaoInicialVagaDto.isPresent()) {
			InformacaoInicialVagaDto dto = informacaoInicialVagaDto.get();
			localizacao = dto.localizacao();
			postadoEm = dto.postadoEm();
			quantidadeCandidaturas = dto.quantidadeCandidaturas();
			candidaturaSimplificada = dto.candidaturaSimplificada();
		}

		return Optional.of(new VagaRequestDto(null, Long.valueOf(idVaga), localizacao, postadoEm,
				quantidadeCandidaturas, 1, linkVaga, linguagemOuCargo, descricaoLinguagensVaga, empresa, tituloVaga,
				descricaoVaga, candidaturaSimplificada, modalidadeTrabalho));
	}

	private String obterDescricaoLinguagensVaga(boolean isCargo, String tituloVaga, String descricaoVaga) {
		String descricaoLinguagensVaga = null;

		if (isCargo) {
			return tituloVaga;
		}

		descricaoLinguagensVaga = listaLinguagensProgramacao.stream()
				.filter(linguagem -> ScrappingUtil.palavraExisteNoTexto(linguagem, descricaoVaga))
				.collect(Collectors.joining(","));

		if (!descricaoLinguagensVaga.isEmpty()) {
			return tituloVaga;
		}
		return tituloVaga;

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

	private String obterUrlPaginaVagasLinkedin() {
		return "https://www.linkedin.com/jobs";
	}
}
