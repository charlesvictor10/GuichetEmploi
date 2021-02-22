package sn.graim.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Langue implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Attributs de la class Langue
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	/**
	 * Relation entre Langue et NiveauLangue
	 */
	@OneToMany(mappedBy="langue")
	private Collection<NiveauLangue> niveauLangues;
	
	/**
	 * Construteurs de la class Langue
	 */
	public Langue() {
		super();
	}

	public Langue(String nom) {
		super();
		this.nom = nom;
	}

	/**
	 * Generation des getters et setters
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

	@JsonIgnore
	public Collection<NiveauLangue> getNiveauLangues() {
		return niveauLangues;
	}

	public void setNiveauLangues(Collection<NiveauLangue> niveauLangues) {
		this.niveauLangues = niveauLangues;
	}

	@Override
	public String toString() {
		return "Langue [id=" + id + ", nom=" + nom + "]";
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
		Langue other = (Langue) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
