package es.cic.curso19.ejerc012.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso19.ejerc012.model.Extraccion;
import es.cic.curso19.ejerc012.repository.ExtraccionRepository;
import es.cic.curso19.ejerc012.util.CuentaUtil;
import es.cic.curso19.ejerc012.util.OperacionUtil;

@Service
public class ExtraccionService {
	
	@Autowired
	private ExtraccionRepository extraccionRepository;
	
	private OperacionUtil operacionUtil = new OperacionUtil();
	private CuentaUtil cuentaUtil = new CuentaUtil();
	
	public Extraccion crear(Extraccion extraccion) {
		
		operacionUtil.comprobarCantidad(extraccion.getOperacion());
		cuentaUtil.cuentaValida(extraccion.getOperacion().getCuenta().getNumeroCuenta());
		operacionUtil.actualizarSaldo(extraccion.getOperacion());
		
		return extraccionRepository.crear(extraccion);
		
	}
}
