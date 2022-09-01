package es.cic.curso19.ejerc012.unitarios;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.model.CuentaException;
import es.cic.curso19.ejerc012.util.CuentaUtil;

class CuentaUtilTest {

	private CuentaUtil cut;
	private Cuenta cuenta;
	
	@BeforeEach
	void setUp() throws Exception {
		cut = new CuentaUtil();
		
		cuenta = new Cuenta();
		cuenta.setId(0l);
		cuenta.setImporte(1000);
		cuenta.setTitular("Jorge");
	}

	@Test
	void testCuentaValida() {
		cuenta.setNumeroCuenta("12345678912345678900");
		cut.cuentaValida(cuenta);
	}
	
	@Test
	void testCuentaInvalida() {
		
		cuenta.setNumeroCuenta("1234567891234567890");
		assertThrowsExactly(CuentaException.class, () -> cut.cuentaValida(cuenta));
	}
	
	@Test
	void testCuentasDistintas() {
		
		cuenta.setNumeroCuenta("12345678912345678900");
		cut.cuentasDistintas(cuenta, "12345678912345678901");
	}
	
	@Test
	void testCuentasDistintasIguales() {
		
		cuenta.setNumeroCuenta("12345678912345678900");
		assertThrowsExactly(CuentaException.class, () -> cut.cuentasDistintas(cuenta, "12345678912345678900"));
	}

}
