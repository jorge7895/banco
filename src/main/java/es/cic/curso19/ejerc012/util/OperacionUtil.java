package es.cic.curso19.ejerc012.util;

import org.springframework.stereotype.Component;

import es.cic.curso19.ejerc012.model.Operacion;
import es.cic.curso19.ejerc012.model.OperacionException;


@Component
public class OperacionUtil {
	
	public <S extends Operacion> void comprobarCantidad(S operacion) {
		if (operacion.getCantidad()<= 0) {
			throw new OperacionException(operacion.getId(),"el importe no es válido");
		}
		if (operacion.getCuenta().getImporte() < operacion.getCantidad() - 50) {
			throw new OperacionException(operacion.getId(),"el saldo es insuficiente");
		}
		if (operacion.getCuenta().getImporte() <= -50) {
			throw new OperacionException(operacion.getId(),"¡Está en números rojos!");
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
