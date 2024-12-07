package br.com.cadastrovagas.domain.dto;

public record VagaRequestDto(Long sqVaga, Long idVaga, String localidade, String postadaEm,
		Integer quantidadeCandidaturas, Integer idSite, String linkVaga, String linguagem,
		String descricaoLinguagensVaga, String empresa, String tituloVaga, String descricao,
		Boolean candidaturaSimplificada, String modalidadeTrabalho) {

}
