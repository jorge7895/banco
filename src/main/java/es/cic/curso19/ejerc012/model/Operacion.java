package es.cic.curso19.ejerc012.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity
public class Operacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Cuenta cuenta;
	
	@Min(0)
	private double cantidad;
	
	private TipoOperacion tipoOperacion;
	private boolean activa;
	
	public Operacion() {
		super();
	}

	public Operacion(long id, Cuenta cuenta, double cantidad) {
		super();
		this.id = id;
		this.cuenta = cuenta;
		this.cantidad = cantidad;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
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
		Operacion other = (Operacion) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Operacion [id=" + id + ", cuenta=" + cuenta + ", cantidad=" + cantidad + ", tipoOperacion="
				+ tipoOperacion + ", activa=" + activa + "]";
	}
	
}
