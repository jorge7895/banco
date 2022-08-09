package es.cic.curso19.ejerc012.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.model.Operacion;
import es.cic.curso19.ejerc012.repository.OperacionRepository;

@Service
@Transactional
public class OperacionService {

	@Autowired
	private OperacionRepository operacionRepository;

	
	public List<Operacion> movimientosCuenta(Cuenta cuenta){
		
		return operacionRepository.movimientosCuenta(cuenta);
	}
}
