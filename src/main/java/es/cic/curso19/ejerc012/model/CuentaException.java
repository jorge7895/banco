package es.cic.curso19.ejerc012.model;

@SuppressWarnings("serial")
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