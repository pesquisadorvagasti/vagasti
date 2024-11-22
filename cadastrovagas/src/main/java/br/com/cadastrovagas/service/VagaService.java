package br.com.cadastrovagas.service;

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
				vagaRepository.save(VagaMapper.convertToEntity(vagaRequestDto));
			}

		} catch (VagaNaoCadastradaException e) {
			throw new VagaNaoCadastradaException(ERRO_CADASTRAR_VAGA + vagaRequestDto.idVaga() + " " + e);
		}
	}

}
