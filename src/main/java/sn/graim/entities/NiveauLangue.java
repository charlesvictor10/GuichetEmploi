package sn.graim.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class NiveauLangue implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Attributs de la class NiveauLangue
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_niveau_langue")
	private Long id;
	private String nom;
	/**
	 * Relation entre NiveauLangue et Langue
	 */
	@ManyToOne
	@JoinColumn(name="id_langue")
	private Langue langue;
	/**
	 * Relation entre NiveauLangue et Offres
	 */
	@OneToMany(mappedBy="niveauLangue")
	private Collection<Offres> offres;
	
	/**
	 * Constructeurs de la class NiveauLangue
	 */
	public NiveauLangue() {
		super();
	}

	public NiveauLangue(String nom) {
		super();
		this.nom = nom;
	}

	/**
	 * Generation getters et setters
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Langue getLangue() {
		return langue;
	}

	public void setLangue(Langue langue) {
		this.langue = langue;
	}

	@JsonIgnore
	public Collection<Offres> getOffres() {
		return offres;
	}

	public void setOffres(Collection<Offres> offres) {
		this.offres = offres;
	}

	@Override
	public String toString() {
		return "NiveauLangue [id=" + id + ", nom=" + nom + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		NiveauLangue other = (NiveauLangue) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
