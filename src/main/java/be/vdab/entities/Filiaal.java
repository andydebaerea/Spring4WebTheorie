package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import be.vdab.valueobjects.Adres;

@Entity
@Table(name = "filialen")
public class Filiaal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@NotNull
	@Size(min = 1, max = 50, message = "{Size.tekst}")
	private String naam;

	private boolean hoofdFiliaal;

	@NumberFormat(style = Style.NUMBER)
	@NotNull
	@Min(0)
	@Digits(integer = 10, fraction = 2)
	private BigDecimal waardeGebouw;

	@NotNull
	@DateTimeFormat(style = "S-")
	@Temporal(TemporalType.DATE)
	private Date inGebruikName;

	@Embedded
	private Adres adres;

	@OneToMany(mappedBy = "filiaal")
	private Set<Werknemer> werknemers;

	

	public Filiaal() {
		adres = new Adres();
	}

	public Filiaal(String naam, boolean hoofdFiliaal, BigDecimal waardeGebouw,
			Date inGebruikName, Adres adres) {
		setNaam(naam);
		setHoofdFiliaal(hoofdFiliaal);
		setWaardeGebouw(waardeGebouw);
		setInGebruikName(inGebruikName);
		setAdres(adres);
	}

	public Filiaal(long id, String naam, boolean hoofdFiliaal,
			BigDecimal waardeGebouw, Date inGebruikName, Adres adres) {
		this(naam, hoofdFiliaal, waardeGebouw, inGebruikName, adres);
		setId(id);
	}

	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public boolean isHoofdFiliaal() {
		return hoofdFiliaal;
	}

	public BigDecimal getWaardeGebouw() {
		return waardeGebouw;
	}

	public Date getInGebruikName() {
		return inGebruikName;
	}

	public Adres getAdres() {
		return adres;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void setHoofdFiliaal(boolean hoofdFiliaal) {
		this.hoofdFiliaal = hoofdFiliaal;
	}

	public void setWaardeGebouw(BigDecimal waardeGebouw) {
		this.waardeGebouw = waardeGebouw;
	}

	public void setInGebruikName(Date inGebruikName) {
		this.inGebruikName = inGebruikName;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}
	
	public Set<Werknemer> getWerknemers() {
		return Collections.unmodifiableSet(werknemers);
	}

	@Override
	public String toString() {
		return String.format("%s:%d", naam, id);
	}
}
