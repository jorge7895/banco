package es.cic.curso19.ejerc012;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import es.cic.curso19.ejerc012.model.acciones.Ingreso;
import es.cic.curso19.ejerc012.model.cuenta.Cuenta;
import es.cic.curso19.ejerc012.model.operacion.Operacion;
import es.cic.curso19.ejerc012.model.operacion.TipoOperacion;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IngresoIntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Cuenta cuenta01;
	private Cuenta cuenta02;
	private Operacion operacion;
	private Ingreso ingreso;
	
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
		
		ingreso = new Ingreso();
		ingreso.setActiva(true);
		ingreso.setOperacion(operacion);
	}
	
	@Test
	void testCreateIngreso() throws Exception {
		
		mvc.perform(post("/operacion/ingreso")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ingreso)))
		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.operacion.cuenta.importe", is(1200.0)));
	}
	
	@Test
	void testCreateIngresoNegativo() throws Exception {
		
		operacion.setCantidad(-200);
		
		mvc.perform(post("/operacion/ingreso")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ingreso)))
		.andDo(print())
		.andExpect(status().is(1000));
	}
	
	@Test
	void testCreateIngresoCuentaMal() throws Exception {
		
		cuenta01.setNumeroCuenta("cuenta no valida pa que casque");
		operacion.setCuenta(cuenta01);
		
		mvc.perform(post("/operacion/ingreso")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ingreso)))
		.andDo(print())
		.andExpect(status().is(1000));
	}

}
