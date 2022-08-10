package es.cic.curso19.ejerc012.model.excepciones;

@SuppressWarnings("serial")
public class OperacionException extends RuntimeException{
	
	private final long id;

	public OperacionException(long Id ,String message, Throwable cause) {
		super(message, cause);
		this.id=Id;
	}

	public OperacionException(long Id,String message) {
		super(message);
		this.id = Id;
	}

	public long getId() {
		return id;
	}

	@Override
	public String getMessage() { 

		return String.format("Error en la operacion %d, %s", id, super.getMessage());
	}
}