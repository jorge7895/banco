package es.cic.curso19.ejerc012.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.cic.curso19.ejerc012.model.Cuenta;
import es.cic.curso19.ejerc012.model.Operacion;

@Repository
public class OperacionRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Operacion> movimientosCuenta(Cuenta cuenta) {
		
		Query jpqlQuery = entityManager.createQuery("SELECT o FROM Operacion o WHERE o.cuenta=:cuenta");
        jpqlQuery.setParameter("cuenta", cuenta);
        return jpqlQuery.getResultList();
	}
}
