package es.cic.curso19.ejerc012.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso19.ejerc012.model.Ingreso;
import es.cic.curso19.ejerc012.repository.IngresoRepository;
import es.cic.curso19.ejerc012.util.CuentaUtil;
import es.cic.curso19.ejerc012.util.OperacionUtil;

@Service
public class IngresoService {

	@Autowired
	private IngresoRepository ingresoRepository;
	
	private OperacionUtil operacionUtil = new OperacionUtil();
	private CuentaUtil cuentaUtil = new CuentaUtil();
	
	public Ingreso crear(Ingreso ingreso) {
		
		operacionUtil.comprobarCantidad(ingreso.getOperacion());
		cuentaUtil.cuentaValida(ingreso.getOperacion().getCuenta().getNumeroCuenta());
		operacionUtil.actualizarSaldo(ingreso.getOperacion());
		
		return ingresoRepository.crear(ingreso);
		
	}
}
