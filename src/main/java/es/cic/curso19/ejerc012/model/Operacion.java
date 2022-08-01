package es.cic.curso19.ejerc012.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Operacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "id_cuenta", nullable = false)
	private long idCuenta;
	
	@Column(name = "tipo", nullable = false)
	private TipoOperacion tipo;
	
	@Min(0)
	@Column(name = "cantidad", nullable = false)
	private double cantidad;
	
	public Operacion() {
		super();
	}

	public Operacion(long id, long idCuenta, TipoOperacion tipo, double cantidad) {
		super();
		this.id = id;
		this.idCuenta = idCuenta;
		this.tipo = tipo;
		this.cantidad = cantidad;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public TipoOperacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoOperacion tipo) {
		this.tipo = tipo;
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
		return "Operacion [id=" + id + ", idCuenta=" + idCuenta + ", tipo=" + tipo + ", cantidad=" + cantidad + "]";
	}
	
}
