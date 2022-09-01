package es.cic.curso19.ejerc012.integracion;

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

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.model.Extraccion;
import es.cic.curso19.ejerc012.model.Operacion;
import es.cic.curso19.ejerc012.util.TipoOperacion;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ExtraccionIntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Cuenta cuenta01;
	private Cuenta cuenta02;
	private Operacion operacion;
	private Extraccion extraccion;
	
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
		operacion.setTipoOperacion(TipoOperacion.EXTRACCION);
		operacion.setCantidad(200);
		operacion.setActiva(true);
		
		extraccion = new Extraccion();
		extraccion.setActiva(true);
		extraccion.setOperacion(operacion);
	}
	
	@Test
	void testCreateExtraccion() throws Exception {
		
		mvc.perform(post("/operacion/extraccion")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(extraccion)))
		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.operacion.cuenta.importe", is(800.0)));
	}
	
	@Test
	void testCreateExtraccionNumerosRojos() throws Exception {
		
		operacion.setCantidad(1049);
		extraccion.setOperacion(operacion);
		
		mvc.perform(post("/operacion/extraccion")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(extraccion)))
		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.operacion.cuenta.importe", is(-49.0)));
	}
	
	@Test
	void testCreateExtraccionSinFondos() throws Exception {
		
		operacion.setCantidad(1051);
		extraccion.setOperacion(operacion);
		
		mvc.perform(post("/operacion/extraccion")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(extraccion)))
		.andDo(print())
		.andExpect(status().is(1001));
	}
	
	@Test
	void testCreateExtraccionNegativa() throws Exception {
		
		operacion.setCantidad(-1051);
		extraccion.setOperacion(operacion);
		
		mvc.perform(post("/operacion/extraccion")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(extraccion)))
		.andDo(print())
		.andExpect(status().is(1001));
	}
	
	@Test
	void testCreateExtraccionCuentaNull() throws Exception {
		
		operacion.setCuenta(null);
		extraccion.setOperacion(operacion);
		
		mvc.perform(post("/operacion/extraccion")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(extraccion)))
		.andDo(print())
		.andExpect(status().is(1001));
	}
	
	@Test
	void testCreateExtraccionOperacionNull() throws Exception {
		
		extraccion.setOperacion(null);
		
		mvc.perform(post("/operacion/extraccion")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(extraccion)))
		.andDo(print())
		.andExpect(status().is(1001));
	}

}
