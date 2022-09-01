package es.cic.curso19.ejerc012.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.repository.CuentaRepository;
import es.cic.curso19.ejerc012.util.CuentaUtil;


@Service
@Transactional
public class CuentaService {
	

	@Autowired
	private CuentaRepository cuentaRepository;
	private CuentaUtil cuentaUtil = new CuentaUtil();

	public Cuenta create(Cuenta cuenta) {
		
		cuentaUtil.cuentaValida(cuenta);
		
		return cuentaRepository.create(cuenta);
	}

	public void delete(Long id) {
		this.cuentaRepository.delete(id);
	}
}
