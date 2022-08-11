package es.cic.curso19.ejerc012.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.cic.curso19.ejerc012.model.acciones.Extraccion;
import es.cic.curso19.ejerc012.model.acciones.Ingreso;
import es.cic.curso19.ejerc012.model.acciones.Transferencia;
import es.cic.curso19.ejerc012.model.cuenta.Cuenta;
import es.cic.curso19.ejerc012.model.excepciones.OperacionException;
import es.cic.curso19.ejerc012.model.operacion.Operacion;
import es.cic.curso19.ejerc012.service.ExtraccionService;
import es.cic.curso19.ejerc012.service.IngresoService;
import es.cic.curso19.ejerc012.service.OperacionService;
import es.cic.curso19.ejerc012.service.TransferenciaService;

@RestController
@RequestMapping(path = "/operacion")
public class OperacionController {

	@Autowired
	private OperacionService operacionService;

	@Autowired
	private IngresoService ingresoService;

	@Autowired
	private ExtraccionService extraccionService;

	@Autowired
	private TransferenciaService transferenciaService;

	@PostMapping("/ingreso")
	public ResponseEntity<Ingreso> crearIngreso(@Validated @RequestBody Ingreso ingreso, BindingResult errors) {

		try {
			
			if (errors.hasErrors()) {
				throw new OperacionException(ingreso.getId(), ingreso.toString());
			}
			ingreso = ingresoService.crear(ingreso);

			return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(ingreso);
			

		} catch (RuntimeException Re) {

			StringBuilder mensaje = new StringBuilder();
			mensaje.append("Ingreso fallido. ");
			mensaje.append(Re.getMessage());

			throw new ResponseStatusException(1000, mensaje.toString(), Re);

		}

	}

	@PostMapping("/extraccion")
	public ResponseEntity<Extraccion> crearExtraccion(@Validated @RequestBody Extraccion extraccion, BindingResult errors) {
		try {
			
			if (errors.hasErrors()) {
				throw new OperacionException(extraccion.getId(), extraccion.toString());
			}
			extraccion = extraccionService.crear(extraccion);

			return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(extraccion);

		} catch (RuntimeException Re) {

			StringBuilder mensaje = new StringBuilder();
			mensaje.append("Extracción fallida. ");
			mensaje.append(Re.getMessage());

			throw new ResponseStatusException(1001, mensaje.toString(), Re);

		}
	}

	@PostMapping("/transferencia")
	public ResponseEntity<Transferencia> crearTransferencia(@Validated @RequestBody Transferencia transferencia, BindingResult errors) {

		try {
			if (errors.hasErrors()) {
				throw new OperacionException(transferencia.getId(), transferencia.toString());
			}
			transferencia = transferenciaService.crear(transferencia);

			return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(transferencia);
			
		}catch (RuntimeException Re) {

			StringBuilder mensaje = new StringBuilder();
			mensaje.append("Transferencia fallida. ");
			mensaje.append(Re.getMessage());

			throw new ResponseStatusException(1002, mensaje.toString(), Re);

		}
	}

	@PostMapping("/ingreso/transferencia")
	public ResponseEntity<Transferencia> recibirTransferencia(@Validated @RequestBody Transferencia transferencia, BindingResult errors) {

		try {
			if (errors.hasErrors()) {
				throw new OperacionException(transferencia.getId(), transferencia.toString());
			}
			transferencia = transferenciaService.recibir(transferencia);

			return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(transferencia);
			
		} catch (RuntimeException Re) {

			StringBuilder mensaje = new StringBuilder();
			mensaje.append("Transferencia entrante fallida. ");
			mensaje.append(Re.getMessage());

			throw new ResponseStatusException(1003, mensaje.toString(), Re);

		}
	}

	@GetMapping("/movimientos/{cuenta}")
	public ResponseEntity<List<Operacion>> movimientosCuenta(@Validated @RequestBody Cuenta cuenta, BindingResult errors) {

		try {
			if (errors.hasErrors()) {
				throw new OperacionException(cuenta.getId(), cuenta.toString());
			}
			List<Operacion> resultados = operacionService.movimientosCuenta(cuenta);
	
			return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(resultados);
			
		} catch (RuntimeException Re) {

			StringBuilder mensaje = new StringBuilder();
			mensaje.append("Error al recuperar movimientos. ");
			mensaje.append(Re.getMessage());

			throw new ResponseStatusException(1004, mensaje.toString(), Re);

		}
	}

	@GetMapping("/cuentas")
	public ResponseEntity<List<Cuenta>> cuentasRelevantes() {

		try {
			List<Cuenta> resultados = operacionService.cuentasRelevantes();
	
			return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(resultados);
			
		} catch (RuntimeException Re) {

			StringBuilder mensaje = new StringBuilder();
			mensaje.append("Error al recuperar las cuentas. ");
			mensaje.append(Re.getMessage());

			throw new ResponseStatusException(1003, mensaje.toString(), Re);

		}
	}
}
