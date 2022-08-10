package es.cic.curso19.ejerc012.util;

import es.cic.curso19.ejerc012.model.cuenta.Cuenta;
import es.cic.curso19.ejerc012.model.excepciones.CuentaException;

public class CuentaUtil {
		
	public void cuentaValida(Cuenta cuenta) {

		if (!cuenta.getNumeroCuenta().matches("[0-9]{20}")) {
			throw new CuentaException(cuenta.getId(), "El número de cuenta es erroneo");
		}
	}
	
	
	public void cuentasDistintas(Cuenta cuentaOrigen, String cuentaDestino) {
		
		if (cuentaOrigen.getNumeroCuenta().equalsIgnoreCase(cuentaDestino)) {
			throw new CuentaException(cuentaOrigen.getId(), "Cuenta de origen y destino son iguales");
		}
	}
}
