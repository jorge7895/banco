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

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.model.Operacion;
import es.cic.curso19.ejerc012.model.TipoOperacion;
import es.cic.curso19.ejerc012.model.Transferencia;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TransferenciaIntegrationTest {
	@Autowired
	private MockMvc mvc;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Cuenta cuenta01;
	private Cuenta cuenta02;
	
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
	}

	@Test
	void testCreateTransferenciaInterna() throws Exception {
		
		Operacion operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
		operacion.setCantidad(200);
		operacion.setActiva(true);
		
		Transferencia transferencia = new Transferencia();
		transferencia.setActiva(true);
		transferencia.setOperacion(operacion);
		transferencia.setCuentaAjenaInterna(cuenta02);
		
		mvc.perform(post("/operacion/transferencia")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transferencia)))
		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.operacion.cuenta.importe", is(800.0)));
	}
	
	@Test
	void testCreateTransferenciaInternaCuentaMal() throws Exception {
		
		Operacion operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
		operacion.setCantidad(200);
		operacion.setActiva(true);
		
		Transferencia transferencia = new Transferencia();
		transferencia.setActiva(true);
		transferencia.setOperacion(operacion);
		cuenta02.setNumeroCuenta("esta cuenta no vale");
		transferencia.setCuentaAjenaInterna(cuenta02);
		
		mvc.perform(post("/operacion/transferencia")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transferencia)))
		.andDo(print())
		.andExpect(status().is5xxServerError());
	}
	
	@Test
	void testCreateTransferenciaExterna() throws Exception {
		
		Operacion operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
		operacion.setCantidad(200);
		operacion.setActiva(true);
		
		Transferencia transferencia = new Transferencia();
		transferencia.setActiva(true);
		transferencia.setOperacion(operacion);
		transferencia.setCuentaAjenaExterna("12345678912345678913");
		
		mvc.perform(post("/operacion/transferencia")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transferencia)))
		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.operacion.cuenta.importe", is(800.0)));
	}
	
	@Test
	void testCreateTransferenciaExternaCuentaMal() throws Exception {
		
		Operacion operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
		operacion.setCantidad(200);
		operacion.setActiva(true);
		
		Transferencia transferencia = new Transferencia();
		transferencia.setActiva(true);
		transferencia.setOperacion(operacion);
		transferencia.setCuentaAjenaExterna("esta cuenta no vale");
		
		mvc.perform(post("/operacion/transferencia")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transferencia)))
		.andDo(print())
		.andExpect(status().is5xxServerError());
	}
	
	@Test
	void testCreateTransferenciaCuentaMal() throws Exception {
		
		Operacion operacion = new Operacion();
		cuenta01.setNumeroCuenta("esta cuenta esta mal");
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
		operacion.setCantidad(200);
		operacion.setActiva(true);
		
		Transferencia transferencia = new Transferencia();
		transferencia.setActiva(true);
		transferencia.setOperacion(operacion);
		transferencia.setCuentaAjenaExterna("12345678912345678913");
		
		mvc.perform(post("/operacion/transferencia")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transferencia)))
		.andDo(print())
		.andExpect(status().is5xxServerError());
	}
	
	@Test
	void testCreateTransferenciaNegativa() throws Exception {
		
		Operacion operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
		operacion.setCantidad(-200);
		operacion.setActiva(true);
		
		Transferencia transferencia = new Transferencia();
		transferencia.setActiva(true);
		transferencia.setOperacion(operacion);
		transferencia.setCuentaAjenaExterna("12345678912345678913");
		
		mvc.perform(post("/operacion/transferencia")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transferencia)))
		.andDo(print())
		.andExpect(status().is5xxServerError());
	}
	
	@Test
	void testRecibirTransferencia() throws Exception {
		
		Operacion operacion = new Operacion();
		operacion.setCuenta(cuenta01);
		operacion.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
		operacion.setCantidad(200);
		operacion.setActiva(true);
		
		Transferencia transferencia = new Transferencia();
		transferencia.setActiva(true);
		transferencia.setOperacion(operacion);
		transferencia.setCuentaAjenaInterna(cuenta02);
		
		mvc.perform(post("/operacion/ingreso/transferencia")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transferencia)))
		.andDo(print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.operacion.cuenta.importe", is(1200.0)));
	}
}