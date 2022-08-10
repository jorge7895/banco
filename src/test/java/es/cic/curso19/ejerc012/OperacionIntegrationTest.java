package es.cic.curso19.ejerc012;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso19.ejerc012.model.cuenta.Cuenta;
import es.cic.curso19.ejerc012.model.operacion.Operacion;
import es.cic.curso19.ejerc012.model.operacion.TipoOperacion;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OperacionIntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Cuenta cuenta01;
	private Cuenta cuenta02;
	private Operacion operacion;
	private Operacion operacion1;
	
	@BeforeEach
	void setUp() throws Exception {
		cuenta01 = new Cuenta();
		cuenta01.setNumeroCuenta("12345678912345678912");
		cuenta01.setTitular("Jorge");
		cuenta01.setImporte(1000);
		entityManager.persist(cuenta01);
		
		cuenta02 = new Cuenta();
		cuenta02.setNumeroCuenta("12345678912345678913");
		cuenta02.setTitular("Manuel");
		cuenta02.setImporte(1000);
		entityManager.persist(cuenta02);
		
		operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.INGRESO);
		operacion.setCantidad(200);
		operacion.setActiva(true);
		
		entityManager.persist(operacion);
		
		operacion1 = new Operacion();
		operacion1.setCuenta(cuenta01);
		operacion1.setTipoOperacion(TipoOperacion.INGRESO);
		operacion1.setCantidad(500);
		operacion1.setActiva(true);
		
		entityManager.persist(operacion1);
	}
	
	@Test 
	void testMovimientosPorCuenta() throws Exception {
		
		mvc.perform(get("/operacion/movimientos/{1}",cuenta01)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cuenta01)))
		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.length()",is(2)))
		;
	}
	
	@Test 
	void testCuentasRelevantes() throws Exception {
		
		for (int i = 0; i < 11; i++) {
			Operacion operacion2 = new Operacion();
			operacion2.setCuenta(cuenta01);
			operacion2.setTipoOperacion(TipoOperacion.INGRESO);
			operacion2.setCantidad(5000 + i);
			operacion2.setActiva(true);
			
			entityManager.persist(operacion2);
		}
		
		mvc.perform(get("/operacion/cuentas"))
		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.length()",is(1)))
		;
	}
}
