package es.cic.curso19.ejerc012.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import es.cic.curso19.ejerc012.model.Cuenta;

@Repository
public class CuentaRepository {

	@PersistenceContext
	private EntityManager em;

	public Cuenta create(Cuenta cuenta) {
		em.persist(cuenta);
		return cuenta;
	}

	public Optional<Cuenta> findById(Long id) {

		return Optional.of(em.find(Cuenta.class, id));
	}

	public void delete(Long id) {
		Optional<Cuenta> cuenta = findById(id);

		if (cuenta.isPresent()) {
			em.remove(cuenta.get());
		}
	}

}