package sn.graim.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Candidat implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Candidat
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_candidat")
	private Long id;
	private String cv;
	private String photo;
	private String etude;
	private String experience;
	private String[] langue;
	private String[] niveauLangue;
	/**
	 * Relation entre Candidat et Utilisateur
	 */
	@OneToOne
	@JoinColumn(name="username")
	private Utilisateur utilisateur;
	
		
	/**
	 * Constructeurs de la class Candidat
	 */
	public Candidat() {
		super();
	}

	public Candidat(String cv, String photo, String etude, String experience) {
		super();
		this.cv = cv;
		this.photo = photo;
		this.etude = etude;
		this.experience = experience;
	}

	/**
	 * Generation des getters et setters
	 */
	public Long getId() {
		return id;
	}

	public String getCv() {
		return cv;
	}

	public String getPhoto() {
		return photo;
	}

	public String getEtude() {
		return etude;
	}

	public String getExperience() {
		return experience;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setEtude(String etude) {
		this.etude = etude;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String[] getLangue() {
		return langue;
	}

	public String[] getNiveauLangue() {
		return niveauLangue;
	}

	public void setLangue(String[] langue) {
		this.langue = langue;
	}

	public void setNiveauLangue(String[] niveauLangue) {
		this.niveauLangue = niveauLangue;
	}
}
