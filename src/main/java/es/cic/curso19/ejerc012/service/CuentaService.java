package es.cic.curso19.ejerc012.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.repository.CuentaRepository;

@Service
@Transactional
public class CuentaService {
	

	@Autowired
	private CuentaRepository cuentaRepository;
	
//	@Autowired
//	private SesionUtil sesionUtil;
//	
//	@Autowired
//	private SalaUtil salaUtil;

	public Cuenta create(Cuenta cuenta) {

		return cuentaRepository.create(cuenta);
	}

	public Cuenta read(Long id) {
		
		return cuentaRepository.read(id);
	}

	public List<Cuenta> read() {
		
		return cuentaRepository.read();
	}

	public void update(Cuenta cuenta) {
		
		this.cuentaRepository.update(cuenta);
	}

	public void delete(Long id) {
		this.cuentaRepository.delete(id);
	}
}
