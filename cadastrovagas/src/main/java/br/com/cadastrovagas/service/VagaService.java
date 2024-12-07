package br.com.cadastrovagas.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cadastrovagas.domain.dto.VagaRequestDto;
import br.com.cadastrovagas.domain.entity.VagaEntity;
import br.com.cadastrovagas.domain.mapper.VagaMapper;
import br.com.cadastrovagas.exception.VagaNaoCadastradaException;
import br.com.cadastrovagas.repository.VagaRepository;

@Service
public class VagaService {

	private static final String ERRO_CADASTRAR_VAGA = "Erro ao cadastrar a vaga de id ";
	private static final String ERRO_EXCLUIR_VAGA = "Erro ao excluir a vaga!";
	@Autowired
	private VagaRepository vagaRepository;

	@Transactional
	public void cadastrar(VagaRequestDto vagaRequestDto) {
		try {
			if (vagaRepository.existsByIdVaga(vagaRequestDto.idVaga())) {
				Optional<VagaEntity> vagaRecuperadaBase = vagaRepository.findById(vagaRequestDto.idVaga());
				if (vagaRecuperadaBase.isPresent()) {
					VagaEntity vagaASerAtualizada = vagaRecuperadaBase.get();
					vagaASerAtualizada = VagaMapper.convertToEntity(vagaRequestDto);
					vagaASerAtualizada.setSqVaga(vagaRecuperadaBase.get().getSqVaga());
					vagaRepository.save(vagaASerAtualizada);
				}
			} else {
				//printStringFieldsWithSizes(vagaRequestDto);
				vagaRepository.save(VagaMapper.convertToEntity(vagaRequestDto));
			}

		} catch (VagaNaoCadastradaException e) {
			throw new VagaNaoCadastradaException(ERRO_CADASTRAR_VAGA + vagaRequestDto.idVaga() + " " + e);
		}
	}

	public void expurgarVagas(List<Long> idsVagasCadastradas) {

		List<Long> idsVagasASeremExpurgados = obterVagasNaoMaisExistentes(idsVagasCadastradas);

		if (!idsVagasCadastradas.isEmpty()) {
			vagaRepository.deleteAllByIdVagaIn(idsVagasASeremExpurgados);
		}

	}

	private List<Long> obterVagasNaoMaisExistentes(List<Long> idsVagasCadastradas) {

		List<Long> idsVagasExistentes = vagaRepository.findAll().stream().map(VagaEntity::getIdVaga).toList();

		if (idsVagasExistentes.isEmpty())
			return Collections.emptyList();

		List<Long> idsVagasASeremExpurgados = idsVagasExistentes.stream()
				.filter(idVagaExistente -> idsVagasCadastradas.contains(idVagaExistente)).toList();

		return idsVagasASeremExpurgados;

	}

	private static void printStringFieldsWithSizes(VagaRequestDto vagaRequestDto) {
		System.out.println("Campos String e seus tamanhos:");

		System.out.println("localidade - tamanho: "
				+ (vagaRequestDto.localidade() != null ? vagaRequestDto.localidade().length() : "null"));
		System.out.println("postadaEm - tamanho: "
				+ (vagaRequestDto.postadaEm() != null ? vagaRequestDto.postadaEm().length() : "null"));
		System.out.println("linkVaga - tamanho: "
				+ (vagaRequestDto.linkVaga() != null ? vagaRequestDto.linkVaga().length() : "null"));
		System.out.println("linguagem - tamanho: "
				+ (vagaRequestDto.linguagem() != null ? vagaRequestDto.linguagem().length() : "null"));
		System.out.println("descricaoLinguagensVaga - tamanho: "
				+ (vagaRequestDto.descricaoLinguagensVaga() != null ? vagaRequestDto.descricaoLinguagensVaga().length()
						: "null"));
		System.out.println("empresa - tamanho: "
				+ (vagaRequestDto.empresa() != null ? vagaRequestDto.empresa().length() : "null"));
		System.out.println("tituloVaga - tamanho: "
				+ (vagaRequestDto.tituloVaga() != null ? vagaRequestDto.tituloVaga().length() : "null"));
		System.out.println("descricao - tamanho: "
				+ (vagaRequestDto.descricao() != null ? vagaRequestDto.descricao().length() : "null"));
		System.out.println("modalidadeTrabalho - tamanho: "
				+ (vagaRequestDto.modalidadeTrabalho() != null ? vagaRequestDto.modalidadeTrabalho().length()
						: "null"));
	}

}
