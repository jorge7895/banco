package es.cic.curso19.ejerc012.model.acciones;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import es.cic.curso19.ejerc012.model.cuenta.Cuenta;
import es.cic.curso19.ejerc012.model.operacion.Operacion;

@Entity
public class Transferencia {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne(targetEntity = Operacion.class)
	private Operacion operacion;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Cuenta cuentaAjenaInterna;
	
	private String cuentaAjenaExterna;
	
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

	public Cuenta getCuentaAjenaInterna() {
		return cuentaAjenaInterna;
	}

	public void setCuentaAjenaInterna(Cuenta cuentaAjenaInterna) {
		this.cuentaAjenaInterna = cuentaAjenaInterna;
	}

	public String getCuentaAjenaExterna() {
		return cuentaAjenaExterna;
	}

	public void setCuentaAjenaExterna(String cuentaAjenaExterna) {
		this.cuentaAjenaExterna = cuentaAjenaExterna;
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
		Transferencia other = (Transferencia) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Transferencia [id=" + id + ", operacion=" + operacion + ", cuentaAjenaInterna=" + cuentaAjenaInterna
				+ ", cuentaAjenaExterna=" + cuentaAjenaExterna + ", activa=" + activa + "]";
	}
}
