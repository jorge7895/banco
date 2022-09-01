package es.cic.curso19.ejerc012.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.cic.curso19.ejerc012.model.Extraccion;

@Repository
public interface ExtraccionRepository extends JpaRepository<Extraccion, Long>{
	
//	@PersistenceContext
//	private EntityManager em;
//
//	public Extraccion crear(Extraccion extraccion) {
//		em.persist(extraccion);
//		return extraccion;
//	}
//	
//	public Optional<Extraccion> findById(Long id) {
//
//		return Optional.of(em.find(Extraccion.class, id));
//	}
}
