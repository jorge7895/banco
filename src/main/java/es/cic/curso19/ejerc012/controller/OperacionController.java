package es.cic.curso19.ejerc012.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Ingreso> crearIngreso(@RequestBody Ingreso ingreso) {

		try {
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
	public ResponseEntity<Extraccion> crearExtraccion(@RequestBody Extraccion extraccion) {
		try {
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
	public ResponseEntity<Transferencia> crearTransferencia(@RequestBody Transferencia transferencia) {

		try {
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
	public ResponseEntity<Transferencia> recibirTransferencia(@RequestBody Transferencia transferencia) {

		try {
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
	public ResponseEntity<List<Operacion>> movimientosCuenta(@RequestBody Cuenta cuenta) {

		try {
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
