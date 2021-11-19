package it.prova.cartellaesattoriale.web.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartellaEsattorialeNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CartellaEsattorialeNotFoundException(String message) {
		super(message);
	}

}
