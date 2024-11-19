package br.com.vagaslinkedin.domain.model.dto;

public record VagaRequestDto(Long sqVaga, Long idVaga, String localidade, String postadaEm,
		Integer quantidadeCandidaturas, Integer idSite, String linkVaga, String linguagem, String empresa,
		String tituloVaga, String descricao, Boolean candidaturaSimplificada, String modalidadeTrabalho) {

}
