package es.cic.curso19.ejerc012.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.service.CuentaService;

@RestController
@RequestMapping(path = "/cuentas")
public class CuentaController {

	
	@Autowired
	private CuentaService cuentaService;

	@PostMapping
	public ResponseEntity<Cuenta> create(@RequestBody Cuenta cuenta) {
		
		
		cuenta = cuentaService.create(cuenta);
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(cuenta);
	}

	@DeleteMapping("/borrar/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void delete(@PathVariable(name = "id") long id) {
		cuentaService.delete(id);
	}
}
