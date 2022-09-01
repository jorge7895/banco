package es.cic.curso19.ejerc012.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import es.cic.curso19.ejerc012.model.Transferencia;

@Repository
public class TransferenciaRepository {
	
	@PersistenceContext
	private EntityManager em;

	public Transferencia crear(Transferencia transferencia) {
		em.persist(transferencia);
		return transferencia;
	}
	
	public Optional<Transferencia> findById(Long id) {

		return Optional.of(em.find(Transferencia.class, id));
	}
}
