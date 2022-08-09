package es.cic.curso19.ejerc012.util;

import es.cic.curso19.ejerc012.model.CuentaException;

public class CuentaUtil {
	
	public void cuentaValida(String numeroCuenta) {

		if (!numeroCuenta.matches("[0-9]{20}")) {
			throw new CuentaException("El número de cuenta es erroneo");
		}
	}
	
	
	public void cuentasDistintas(String cuentaOrigen, String cuentaDestino) {
		
		if (cuentaOrigen.equalsIgnoreCase(cuentaDestino)) {
			throw new CuentaException("Cuenta de origen y destino son iguales");
		}
	}
}
