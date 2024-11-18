package br.com.cadastrovagas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cadastrovagas.domain.dto.VagaRequestDto;
import br.com.cadastrovagas.domain.mapper.VagaMapper;
import br.com.cadastrovagas.exception.VagaNaoCadastradaException;
import br.com.cadastrovagas.repository.VagaRepository;

@Service
public class VagaService {

	private static final String ERRO_CADASTRAR_VAGA = "Erro ao cadastrar a vaga de id ";
	@Autowired
	private VagaRepository vagaRepository;

	@Transactional
	public void cadastrar(VagaRequestDto vagaRequestDto) {
		try {
			if (!vagaRepository.existsByIdVaga(vagaRequestDto.idVaga()))
				vagaRepository.save(VagaMapper.convertToEntity(vagaRequestDto));

		} catch (VagaNaoCadastradaException e) {
			throw new VagaNaoCadastradaException(ERRO_CADASTRAR_VAGA + vagaRequestDto.idVaga() + " " + e);
		}
	}
}
