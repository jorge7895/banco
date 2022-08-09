package es.cic.curso19.ejerc012;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import es.cic.curso19.ejerc012.model.Ingreso;
import es.cic.curso19.ejerc012.model.Operacion;
import es.cic.curso19.ejerc012.model.TipoOperacion;
import es.cic.curso19.ejerc012.model.Transferencia;

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
	
	@BeforeEach
	void setUp() throws Exception {
		cuenta01 = new Cuenta();
		cuenta01.setNumeroCuenta("12345678912345678912");
		cuenta01.setTitular("Jorge");
		cuenta01.setImporte(1000);
		entityManager.persist(cuenta01);
	}

	@Test
	void testCreateIngreso() throws Exception {
		
		Operacion operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.INGRESO);
		operacion.setCantidad(200);
		operacion.setActiva(true);
		
		Ingreso ingreso = new Ingreso();
		ingreso.setActiva(true);
		ingreso.setOperacion(operacion);
		
		mvc.perform(post("/operaciones/ingreso")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ingreso)))
		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.operacion.cuenta.importe", is(1200.0)));
	}
	
	@Test
	void testCreateExtraccion() throws Exception {
		
		Operacion operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.EXTRACCION);
		operacion.setCantidad(200);
		operacion.setActiva(true);
		
		Extraccion extraccion = new Extraccion();
		extraccion.setActiva(true);
		extraccion.setOperacion(operacion);
		
		mvc.perform(post("/operaciones/extraccion")
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
		
		Operacion operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.EXTRACCION);
		operacion.setCantidad(1049);
		operacion.setActiva(true);
		
		Extraccion extraccion = new Extraccion();
		extraccion.setActiva(true);
		extraccion.setOperacion(operacion);
		
		mvc.perform(post("/operaciones/extraccion")
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
		
		Operacion operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.EXTRACCION);
		operacion.setCantidad(1051);
		operacion.setActiva(true);
		
		Extraccion extraccion = new Extraccion();
		extraccion.setActiva(true);
		extraccion.setOperacion(operacion);
		
		mvc.perform(post("/operaciones/extraccion")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(extraccion)))
		.andDo(print())
		.andExpect(status().is5xxServerError());
	}

	
	@Test
	void testCreateTransferencia() throws Exception {
		
		Operacion operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
		operacion.setCantidad(200);
		operacion.setActiva(true);
		
		Transferencia transferencia = new Transferencia();
		transferencia.setActiva(true);
		transferencia.setOperacion(operacion);
		transferencia.setCuentaAjenaExterna("12345678912345678913");
		
		mvc.perform(post("/operaciones/transferencia")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transferencia)))
		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.operacion.cuenta.importe", is(800.0)));
	}
	
	@Test 
	void testMovimientosPorCuenta() throws Exception {
		
		Operacion operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.INGRESO);
		operacion.setCantidad(200);
		operacion.setActiva(true);
		
		entityManager.persist(operacion);
		
		Operacion operacion2 = new Operacion();
		operacion2.setCuenta(cuenta01);
		operacion2.setTipoOperacion(TipoOperacion.INGRESO);
		operacion2.setCantidad(500);
		operacion2.setActiva(true);
		
		entityManager.persist(operacion2);
		
		mvc.perform(get("/operaciones/movimientos/{1}",cuenta01)
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
		
		mvc.perform(get("/operaciones/cuentas"))
		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.length()",is(1)))
		;
	}
}
