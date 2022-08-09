package es.cic.curso19.ejerc012.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import es.cic.curso19.ejerc012.model.Extraccion;

@Repository
public class ExtraccionRepository {
	
	@PersistenceContext
	private EntityManager em;

	public Extraccion crear(Extraccion extraccion) {
		em.persist(extraccion);
		return extraccion;
	}
	
	public Optional<Extraccion> findById(Long id) {

		return Optional.of(em.find(Extraccion.class, id));
	}
}
