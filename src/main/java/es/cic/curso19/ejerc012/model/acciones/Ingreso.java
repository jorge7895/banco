package es.cic.curso19.ejerc012.model.acciones;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import es.cic.curso19.ejerc012.model.operacion.Operacion;

@Entity
public class Ingreso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne(targetEntity = Operacion.class)
	private Operacion operacion;
	
	private boolean activa;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingreso other = (Ingreso) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Ingreso [id=" + id + ", activa=" + activa + "]";
	}

}
