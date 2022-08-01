package es.cic.curso19.ejerc012.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class OperacionException extends RuntimeException{
	
	private final long Id;

	public OperacionException(long Id ,String message, Throwable cause) {
		super(message, cause);
		this.Id=Id;
	}

	public OperacionException(long Id,String message) {
		super(message);
		this.Id = Id;
	}

	public long getId() {
		return Id;
	}

	@Override
	public String getMessage() { 

		return String.format("La operacion %d %s", Id, super.getMessage());
	}
}