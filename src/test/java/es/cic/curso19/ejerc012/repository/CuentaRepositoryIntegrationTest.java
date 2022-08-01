package es.cic.curso19.ejerc012.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.repository.CuentaRepository;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CuentaRepositoryIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@PersistenceContext
	private EntityManager em;

	private Cuenta cuenta1;
	private Cuenta cuenta2;

	@BeforeEach
	void setUp()  {
		cuenta1 = new Cuenta();

		cuenta1.setTitular("Paco");
		cuenta1.setImporte(50);
		em.persist(cuenta1);

		cuenta2 = new Cuenta();

		cuenta2.setTitular("Pepe");
		cuenta2.setImporte(50000);
		em.persist(cuenta2);
	}

	@Test
	public void TestCreate() throws Exception {
		Cuenta cuenta = new Cuenta();
		cuenta.setTitular("NombreRandom");

		mvc.perform(post("/cuentas/create")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cuenta)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	void testRead() throws Exception {
		mvc.perform(get("/cuentas/{1}", cuenta1.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.titular", is("Paco")));
		;		
	}
	
	@Test
	void testReadAll() throws JsonProcessingException, Exception {
		mvc.perform(get("/cuentas")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.length()", is(2)));
		;
	}
	
	@Test
	void testUpdate() throws JsonProcessingException, Exception {
		
		Cuenta cuenta = cuenta1;
		cuenta.setImporte(1000);
		
		mvc.perform(put("/cuentas")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cuenta1)))
		.andDo(print())
		.andExpect(status().isOk());
		
		Cuenta cuentaLeida = em.find(Cuenta.class, cuenta1.getId());
		
		assertThat(cuentaLeida.getImporte() , is(1000.0));
		
	}

}
