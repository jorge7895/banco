package es.cic.curso19.ejerc012.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso19.ejerc012.model.acciones.Ingreso;
import es.cic.curso19.ejerc012.repository.IngresoRepository;
import es.cic.curso19.ejerc012.util.CuentaUtil;
import es.cic.curso19.ejerc012.util.OperacionUtil;

@Service
public class IngresoService {
	
	Logger LOGGER = LogManager.getLogger(IngresoService.class);

	@Autowired
	private IngresoRepository ingresoRepository;
	
	private OperacionUtil operacionUtil = new OperacionUtil();
	private CuentaUtil cuentaUtil = new CuentaUtil();
	
	public Ingreso crear(Ingreso ingreso) {
		
		operacionUtil.comprobarCantidad(ingreso.getOperacion());
		cuentaUtil.cuentaValida(ingreso.getOperacion().getCuenta());
		operacionUtil.actualizarSaldo(ingreso.getOperacion());
		
		LOGGER.trace("Ingreso creado con id: {}",ingreso.getId());
		
		return ingresoRepository.crear(ingreso);
		
	}
}
