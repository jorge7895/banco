package es.cic.curso19.ejerc012.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.cic.curso19.ejerc012.model.cuenta.Cuenta;
import es.cic.curso19.ejerc012.model.operacion.Operacion;

@Repository
public class OperacionRepository{
	
	@PersistenceContext
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	public List<Operacion> movimientosCuenta(@Param("cuenta") Cuenta cuenta){
		
		Query query =em.createNativeQuery("SELECT * FROM operacion WHERE cuenta_id = :idCuenta", Operacion.class); 
		query.setParameter("idCuenta", cuenta.getId());
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Cuenta> cuentasRelevantes(){
		
		Query query2 = em.createQuery("Select c from Cuenta c where c.importe > 5000 OR "
				+ "(SELECT count(a) FROM Operacion a INNER JOIN Cuenta ca ON a.cuenta = ca.id WHERE ca.id = c.id) > 10");

		return query2.getResultList();
	}
}
