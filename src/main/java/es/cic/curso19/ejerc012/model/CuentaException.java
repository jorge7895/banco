package es.cic.curso19.ejerc012.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CuentaException extends RuntimeException {

	public CuentaException(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() { 
		return super.getMessage();
	}
}