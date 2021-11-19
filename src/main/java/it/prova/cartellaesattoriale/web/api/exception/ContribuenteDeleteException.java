package it.prova.cartellaesattoriale.web.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ContribuenteDeleteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ContribuenteDeleteException(String message) {
		super(message);
	}

}
