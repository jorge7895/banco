package es.cic.curso19.ejerc012.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import es.cic.curso19.ejerc012.model.acciones.Ingreso;

@Repository
public class IngresoRepository {
	
	@PersistenceContext
	private EntityManager em;

	public Ingreso crear(Ingreso ingreso) {
		em.persist(ingreso);
		return ingreso;
	}
	
	public Optional<Ingreso> findById(Long id) {

		return Optional.of(em.find(Ingreso.class, id));
	}

}
