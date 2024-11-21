package br.com.cadastrovagas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cadastrovagas.domain.entity.VagaEntity;

public interface VagaRepository extends JpaRepository<VagaEntity, Long> {

	boolean existsByIdVaga(Long idVaga);

	void deleteByIdVaga(Long idVaga);

}
