package br.com.cadastrovagas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class VagaNaoCadastradaException extends RuntimeException {

	private static final long serialVersionUID = 7336532078871242042L;

	public VagaNaoCadastradaException(String message) {
		super(message);
	}

	public VagaNaoCadastradaException(String message, Throwable cause) {
		super(message, cause);
	}
}
