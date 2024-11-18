package br.com.cadastrovagas.domain.dto;

public record VagaRequestDto(Long sqVaga, Long idVaga, Integer idSite, String linkVaga, String linguagem,
		String empresa, String tituloVaga, String descricao) {

}
