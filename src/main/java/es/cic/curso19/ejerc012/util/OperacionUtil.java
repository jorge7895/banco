package es.cic.curso19.ejerc012.util;

import org.springframework.stereotype.Component;

import es.cic.curso19.ejerc012.model.Operacion;
import es.cic.curso19.ejerc012.model.OperacionException;
import es.cic.curso19.ejerc012.model.TipoOperacion;


@Component
public class OperacionUtil {
	
	public <S extends Operacion> void comprobarCantidad(S operacion) {
		if (operacion.getCantidad()<= 0) {
			throw new OperacionException(operacion.getId(),"El importe de la transferencia no es válido");
		}
		if (operacion.getCuenta().getImporte() < operacion.getCantidad()) {
			throw new OperacionException(operacion.getId(),"El saldo es insuficiente");
		}
	}
	
	public void cuentasDistintas(String cuentaOrigen, String cuentaDestino) {
		
		if (cuentaOrigen.equalsIgnoreCase(cuentaDestino)) {
		}
	}
	
	public <S extends Operacion> void actualizarSaldo(S operacion) {
		
		double nuevoSaldo;
		
		if (operacion.getTipoOperacion().equals(TipoOperacion.EXTRACCION) | operacion.getTipoOperacion().equals(TipoOperacion.TRANSFERENCIA)) {
			
			nuevoSaldo = (operacion.getCuenta().getImporte() - operacion.getCantidad());
			
		}else {
			
			nuevoSaldo = (operacion.getCuenta().getImporte() + operacion.getCantidad());	
		}
		
		operacion.getCuenta().setImporte(nuevoSaldo);
		
	}
}
