package es.cic.curso19.ejerc012.repository;

import java.util.List;

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

	public Cuenta read(Long id) {
		return em.find(Cuenta.class, id);
	}

	public List<Cuenta> read() {
		return em.createQuery("SELECT c FROM Cuenta c", Cuenta.class).getResultList();
	}

	public Cuenta update(Cuenta cuenta) {
		return em.merge(cuenta);
	}

	public void delete(Long id) {
		em.remove(this.read(id));
	}

}