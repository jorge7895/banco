package es.cic.curso19.ejerc012.unitarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.cic.curso19.ejerc012.model.cuenta.Cuenta;
import es.cic.curso19.ejerc012.model.excepciones.OperacionException;
import es.cic.curso19.ejerc012.model.operacion.Operacion;
import es.cic.curso19.ejerc012.model.operacion.TipoOperacion;
import es.cic.curso19.ejerc012.util.OperacionUtil;

class OperacionUtilTest {
	
	private OperacionUtil cut;
	
	private Operacion operacion;
	private Cuenta cuenta;

	@BeforeEach
	void setUp() throws Exception {
		
		cuenta = new Cuenta();
		cuenta.setId(1l);
		cuenta.setImporte(1000);
		cuenta.setNumeroCuenta("12345678912345678900");
		cuenta.setTitular("jorge");
		
		operacion = new Operacion();
		operacion.setActiva(true);
		operacion.setCantidad(100);
		operacion.setId(1);
		operacion.setTipoOperacion(TipoOperacion.INGRESO);
		operacion.setCuenta(cuenta);
		
		cut = new OperacionUtil();
	}

	@Test
	void testComprobarCantidad() {
		cut.comprobarCantidad(operacion);
	}
	
	@Test
	void testComprobarCantidadMenorDeCero() {
		
		operacion.setCantidad(-10);
		
		assertThrowsExactly(OperacionException.class, () -> cut.comprobarCantidad(operacion));
	}
	
	@Test
	void testComprobarCantidadCero() {
		
		operacion.setCantidad(0);
		
		assertThrowsExactly(OperacionException.class, () -> cut.comprobarCantidad(operacion));
	}
	
	@Test
	void testComprobarSinFondos() {
		
		operacion.setCantidad(1100);
		
		assertThrowsExactly(OperacionException.class, () -> cut.comprobarCantidad(operacion));
	}
	
	@Test
	void testActualizarSaldoIngreso() {
		
		cut.actualizarSaldo(operacion);
		
		assertEquals(1100, operacion.getCuenta().getImporte());
	}
	
	@Test
	void testActualizarSaldoExtraccion() {
		
		operacion.setTipoOperacion(TipoOperacion.EXTRACCION);
		cut.actualizarSaldo(operacion);
		
		assertEquals(900, operacion.getCuenta().getImporte());
	}

}
