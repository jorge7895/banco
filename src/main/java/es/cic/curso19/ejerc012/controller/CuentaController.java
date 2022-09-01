package es.cic.curso19.ejerc012.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.model.CuentaException;
import es.cic.curso19.ejerc012.service.CuentaService;

@RestController
@RequestMapping(path = "/cuentas")
public class CuentaController {

	@Autowired
	private CuentaService cuentaService;

	@PostMapping
	public ResponseEntity<Cuenta> create(@Validated @RequestBody Cuenta cuenta, BindingResult errors) {

		try {

			if (errors.hasErrors()) {
				throw new CuentaException(cuenta.getId(), cuenta.toString());
			}

			cuenta = cuentaService.create(cuenta);
			return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(cuenta);

		} catch (RuntimeException Re) {

			StringBuilder mensaje = new StringBuilder();
			mensaje.append("Error al crear la cuenta. ");
			mensaje.append(Re.getMessage());

			throw new ResponseStatusException(1100, mensaje.toString(), Re);

		}
	}

	@DeleteMapping("/borrar/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void delete(@PathVariable(name = "id") long id) {

		try {

			cuentaService.delete(id);

		} catch (RuntimeException Re) {

			StringBuilder mensaje = new StringBuilder();
			mensaje.append("Error al borrar la cuenta. ");
			mensaje.append(Re.getMessage());

			throw new ResponseStatusException(1101, mensaje.toString(), Re);

		}
	}
}
