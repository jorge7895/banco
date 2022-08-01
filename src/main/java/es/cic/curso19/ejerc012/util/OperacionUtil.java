package es.cic.curso19.ejerc012.util;

import org.springframework.stereotype.Component;

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.model.Operacion;
import es.cic.curso19.ejerc012.model.TipoOperacion;


@Component
public class OperacionUtil {
	
	public boolean isOperacionValida(Cuenta cuenta, Operacion operacion) {

		return cuenta.getImporte() < 0 && operacion.getTipo() == TipoOperacion.Retirada;

	}
}
