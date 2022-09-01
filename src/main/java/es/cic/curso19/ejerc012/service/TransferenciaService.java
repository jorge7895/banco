package es.cic.curso19.ejerc012.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.model.Operacion;
import es.cic.curso19.ejerc012.model.OperacionException;
import es.cic.curso19.ejerc012.model.Transferencia;
import es.cic.curso19.ejerc012.repository.TransferenciaRepository;
import es.cic.curso19.ejerc012.util.CuentaUtil;
import es.cic.curso19.ejerc012.util.OperacionUtil;

@Service
public class TransferenciaService {
	
	@Autowired
	private TransferenciaRepository transferenciaRepository;
	
	private OperacionUtil operacionUtil = new OperacionUtil();
	private CuentaUtil cuentaUtil = new CuentaUtil();
	
	public Transferencia crear(Transferencia transferencia) {
		
		Operacion operacion = transferencia.getOperacion();
		Cuenta cuenta = transferencia.getOperacion().getCuenta();
		
		operacionUtil.comprobarCantidad(operacion);
		cuentaUtil.cuentaValida(cuenta);
		
		if (transferencia.getCuentaAjenaInterna() != null) {
			
			cuentaUtil.cuentaValida(transferencia.getCuentaAjenaInterna());
			cuentaUtil.cuentasDistintas(cuenta, transferencia.getCuentaAjenaInterna().getNumeroCuenta());
			
		}else if (transferencia.getCuentaAjenaExterna() != null){
			
			cuentaUtil.cuentasDistintas(cuenta, transferencia.getCuentaAjenaExterna());
			
		}else {
			throw new OperacionException(operacion.getId(), "las cuentas no son válidas");
		}
		
		operacionUtil.actualizarSaldo(operacion);
		
		return transferenciaRepository.crear(transferencia);
		
	}
	
	public Transferencia recibir(Transferencia transferencia) {
		
		Operacion operacion = transferencia.getOperacion();
		Cuenta cuenta = transferencia.getOperacion().getCuenta();
		
		operacionUtil.comprobarCantidad(operacion);
		cuentaUtil.cuentaValida(cuenta);
		
		if (transferencia.getCuentaAjenaInterna()!=null) {
			
			cuentaUtil.cuentaValida(transferencia.getCuentaAjenaInterna());
			cuentaUtil.cuentasDistintas(cuenta, transferencia.getCuentaAjenaInterna().getNumeroCuenta());
			
		}else {
			
			cuentaUtil.cuentasDistintas(cuenta, transferencia.getCuentaAjenaExterna());
		}
		
		double nuevoSaldo = (operacion.getCuenta().getImporte() + operacion.getCantidad());
		operacion.getCuenta().setImporte(nuevoSaldo);
		
		return transferenciaRepository.crear(transferencia);		
	}
}
