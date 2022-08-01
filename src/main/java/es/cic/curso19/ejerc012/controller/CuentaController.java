package es.cic.curso19.ejerc012.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.service.CuentaService;

@RestController
@RequestMapping(path = "/cuentas")
public class CuentaController {

	@Autowired
	private CuentaService cuentaService;

	@PostMapping("/create")
	public Cuenta create(@RequestBody Cuenta cuenta) {
		return cuentaService.create(cuenta);
	}

	@GetMapping("/{id}")
	public Cuenta read(@PathVariable("id") long id) {
		return this.cuentaService.read(id);
	}

	@GetMapping
	public List<Cuenta> read() {
		return this.cuentaService.read();
	}

	@PutMapping
	public void update(Cuenta cuenta) {
		this.cuentaService.update(cuenta);
	}

	@DeleteMapping("/{id}")
	public void delete(long id) {
		this.cuentaService.delete(id);
	}
}
