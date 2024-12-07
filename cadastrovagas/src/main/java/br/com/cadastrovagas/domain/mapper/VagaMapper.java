package br.com.cadastrovagas.domain.mapper;

import br.com.cadastrovagas.domain.dto.VagaRequestDto;
import br.com.cadastrovagas.domain.entity.VagaEntity;
import br.com.cadastrovagas.domain.enumerator.SiteVagaEnum;

public class VagaMapper {

	public static VagaEntity convertToEntity(VagaRequestDto vagaRequestDto) {
		return new VagaEntity(vagaRequestDto.sqVaga(),
				vagaRequestDto.idVaga(),
				vagaRequestDto.localidade(),
				vagaRequestDto.postadaEm(), 
				vagaRequestDto.quantidadeCandidaturas(),
				SiteVagaEnum.obterPorCodigo(vagaRequestDto.idSite()), 
				vagaRequestDto.linkVaga(),
				vagaRequestDto.linguagem(), 
				vagaRequestDto.descricaoLinguagensVaga(), 
				vagaRequestDto.empresa(),
				vagaRequestDto.tituloVaga(), 
				vagaRequestDto.descricao(), 
				vagaRequestDto.candidaturaSimplificada(),
				vagaRequestDto.modalidadeTrabalho());
	}
}
