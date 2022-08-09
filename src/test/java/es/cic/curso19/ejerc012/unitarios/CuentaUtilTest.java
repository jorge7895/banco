package es.cic.curso19.ejerc012.unitarios;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.cic.curso19.ejerc012.model.CuentaException;
import es.cic.curso19.ejerc012.util.CuentaUtil;

class CuentaUtilTest {

	private CuentaUtil cut;
	
	@BeforeEach
	void setUp() throws Exception {
		cut = new CuentaUtil();
	}

	@Test
	void testCuentaValida() {
		cut.cuentaValida("12345678912345678900");
	}
	
	@Test
	void testCuentaInvalida() {
		
		assertThrowsExactly(CuentaException.class, () -> cut.cuentaValida("1234567891234567890"));
	}
	
	@Test
	void testCuentasDistintas() {
		
		cut.cuentasDistintas("12345678912345678900", "12345678912345678901");
	}
	
	@Test
	void testCuentasDistintasIguales() {
		
		assertThrowsExactly(CuentaException.class, () -> cut.cuentasDistintas("12345678912345678900", "12345678912345678900"));
	}

}
