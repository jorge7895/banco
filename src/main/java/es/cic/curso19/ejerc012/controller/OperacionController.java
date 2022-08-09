package es.cic.curso19.ejerc012.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public Ingreso crearIngreso(@RequestBody Ingreso ingreso) {
		return ingresoService.crear(ingreso);
	}
	
	@PostMapping("/extraccion")
	public Extraccion crearExtraccion(@RequestBody Extraccion extraccion) {
		return extraccionService.crear(extraccion);
	}
	
	@PostMapping("/transferencia")
	public Transferencia crearTransferencia(@RequestBody Transferencia transferencia) {
		return transferenciaService.crear(transferencia);
	}
	
	@GetMapping("/movimientos/{cuenta}")
	public List<Operacion> movimientosCuenta(@RequestBody Cuenta cuenta){
		return operacionService.movimientosCuenta(cuenta);
	}
}
