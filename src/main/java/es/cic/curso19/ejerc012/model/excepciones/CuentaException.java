package es.cic.curso19.ejerc012.model.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CuentaException extends RuntimeException {
	
	private final long id;
	
	public CuentaException(long id,String message) {
		super(message);
		this.id = id;
	}
	
	public CuentaException(long id ,String message, Throwable cause) {
		super(message, cause);
		this.id=id;
	}
	
	public long getId() {
		return id;
	}

	@Override
	public String getMessage() { 

		return String.format("Error con la cuenta %s", super.getMessage());
	}
}