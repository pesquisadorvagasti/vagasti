package br.com.vagaslinkedin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import br.com.vagaslinkedin.domain.model.dto.VagaRequestDto;

@Service
public class CadastroVagaService {

	@Autowired
	private RestClient restClient;

	public String postarVaga(VagaRequestDto vagaRequestDto) {
		return restClient.post().uri("http://localhost:8082/vagas")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(vagaRequestDto).retrieve()
				.body(String.class);
	}

}
