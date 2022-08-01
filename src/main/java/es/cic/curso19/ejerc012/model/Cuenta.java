package es.cic.curso19.ejerc012.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
public class Cuenta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;

	// private String numeroCuenta; CADENA DE 20 CARACTERES????
	@Column(name = "titular", nullable = false)
	private String Titular;

	@Min(0)
	@Column(name = "importe", nullable = false)
	private double Importe;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getTitular() {
		return Titular;
	}

	public void setTitular(String titular) {
		Titular = titular;
	}

	public double getImporte() {
		return Importe;
	}

	public void setImporte(double importe) {
		Importe = importe;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta other = (Cuenta) obj;
		return Id == other.Id && Objects.equals(Titular, other.Titular);
	}

	@Override
	public String toString() {
		return "Cuenta [Id=" + Id + ", Titular=" + Titular + "]";
	}

}
