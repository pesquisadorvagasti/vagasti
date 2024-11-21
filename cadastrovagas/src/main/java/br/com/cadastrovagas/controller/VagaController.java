package br.com.cadastrovagas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastrovagas.domain.dto.VagaRequestDto;
import br.com.cadastrovagas.service.VagaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vagas")
public class VagaController {

	@Autowired
	private VagaService vagaService;

	@PostMapping
	@Operation(summary = "Cadastrar uma nova vaga", description = "Recebe os dados de uma vaga via DTO e realiza o cadastro no banco de dados.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Vaga cadastrada com sucesso", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content) })

	public ResponseEntity<String> cadastrar(
			@ io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto contendo os dados da vaga a ser cadastrada", 
			required = true, content = @Content(mediaType = "application/json",
			schema = @Schema(implementation = VagaRequestDto.class))) 
			@Valid @RequestBody VagaRequestDto vagaRequestDto) {
		
		vagaService.cadastrar(vagaRequestDto);
		return ResponseEntity.ok("Vaga cadastrada com sucesso!");
	}
	
	@PatchMapping
	@Operation(summary = "Excluir uma nova vaga", description = "Recebe o id da vaga e exclui da base de dados.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Vaga cadastrada com sucesso", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content) })

	public ResponseEntity<String> excluir(
			@ io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto contendo os dados da vaga a ser cadastrada", 
			required = true, content = @Content(mediaType = "application/json",
			schema = @Schema(implementation = Long.class))) 
			@Valid @RequestParam Long idVaga) {
		
		vagaService.excluirPorId(idVaga);
		return ResponseEntity.ok("Vaga excluída com sucesso!");
	}
}
