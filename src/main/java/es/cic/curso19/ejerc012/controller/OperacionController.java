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

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.model.Extraccion;
import es.cic.curso19.ejerc012.model.Ingreso;
import es.cic.curso19.ejerc012.model.Operacion;
import es.cic.curso19.ejerc012.model.Transferencia;
import es.cic.curso19.ejerc012.service.ExtraccionService;
import es.cic.curso19.ejerc012.service.IngresoService;
import es.cic.curso19.ejerc012.service.OperacionService;
import es.cic.curso19.ejerc012.service.TransferenciaService;

@RestController
@RequestMapping(path = "/operaciones")
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
		
		ingreso = ingresoService.crear(ingreso);
		
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(ingreso);
	}
	
	@PostMapping("/extraccion")
	public ResponseEntity<Extraccion> crearExtraccion(@RequestBody Extraccion extraccion) {
		
		extraccion = extraccionService.crear(extraccion);
		
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(extraccion);
	}
	
	@PostMapping("/transferencia")
	public ResponseEntity<Transferencia> crearTransferencia(@RequestBody Transferencia transferencia) {
		
		transferencia = transferenciaService.crear(transferencia); 
		
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(transferencia); 
	}
	
	@GetMapping("/movimientos/{cuenta}")
	public ResponseEntity<List<Operacion>> movimientosCuenta(@RequestBody Cuenta cuenta){
		
		List<Operacion> resultados = operacionService.movimientosCuenta(cuenta);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(resultados);
	}
	
	@GetMapping("/cuentas")
	public ResponseEntity<List<Cuenta>> cuentasRelevantes(){
		
		List<Cuenta> resultados = operacionService.cuentasRelevantes();
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(resultados);
	}
}
