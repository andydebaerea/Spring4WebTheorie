package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "werknemers")
public class Werknemer implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String voornaam;
	private String familienaam;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "filiaalId")
	private Filiaal filiaal;
	private BigDecimal wedde;
	private long rijksregisterNr;

	protected Werknemer() {
	}

	
	public long getId() {
		return id;
	}


	public String getVoornaam() {
		return voornaam;
	}


	public String getFamilienaam() {
		return familienaam;
	}


	public Filiaal getFiliaal() {
		return filiaal;
	}


	public BigDecimal getWedde() {
		return wedde;
	}


	public long getRijksregisterNr() {
		return rijksregisterNr;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (rijksregisterNr ^ (rijksregisterNr >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Werknemer other = (Werknemer) obj;
		if (rijksregisterNr != other.rijksregisterNr)
			return false;
		return true;
	}


	public String getNaam() {
		return String.format("%s %s", voornaam, familienaam);
	}

	@Override
	public String toString() {
		return String.format("%s %s:%d", voornaam, familienaam, id);
	}
}
