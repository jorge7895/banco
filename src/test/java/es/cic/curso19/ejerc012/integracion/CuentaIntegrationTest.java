package es.cic.curso19.ejerc012.integracion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CuentaIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@PersistenceContext
	private EntityManager em;

	private Cuenta cuenta;
	private Cuenta cuenta2;

	@BeforeEach
	void setUp()  {
		
		cuenta = new Cuenta();
		cuenta.setTitular("Paco");
		cuenta.setImporte(50);
		cuenta.setNumeroCuenta("12345123451234512345");

		cuenta2 = new Cuenta();
		cuenta2.setTitular("Pepe");
		cuenta2.setImporte(50000);
		cuenta2.setNumeroCuenta("67890678906789067890");
		em.persist(cuenta2);
	}

	@Test
	public void testCreate() throws Exception {

		mvc.perform(post("/cuentas")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cuenta)))
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.numeroCuenta", is("12345123451234512345")));
	}
	
	@Test
	public void testCreateNombreVacio() throws Exception {
		
		cuenta.setTitular(null);

		mvc.perform(post("/cuentas")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cuenta)))
				.andDo(print())
				.andExpect(status().is(1100));
	}
	
	@Test
	public void testCreateCuentaVacia() throws Exception {
		
		cuenta.setNumeroCuenta(null);

		mvc.perform(post("/cuentas")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cuenta)))
				.andDo(print())
				.andExpect(status().is(1100));
	}
	
	@Test
	void testDelete() throws Exception {
		
		mvc.perform(delete("/cuentas/borrar/{id}", cuenta2.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().is2xxSuccessful());
		
		Cuenta cuentaLeida = em.find(Cuenta.class, cuenta2.getId());
		assertThat(cuentaLeida, is(nullValue()));
	}

}
