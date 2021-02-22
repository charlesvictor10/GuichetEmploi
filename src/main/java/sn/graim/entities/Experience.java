package sn.graim.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Experience implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Experience
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@DateTimeFormat(pattern = "MM-yyyy")
	private Date debut;
	@DateTimeFormat(pattern = "MM-yyyy")
	private Date fin;
	private String intitule;
	private String entreprise;
	private String description;
	/**
	 * Relation entre Experience et Utilisateur
	 */
	@ManyToOne
	@JoinColumn(name="username")
	private Utilisateur utilisateur;
	
	/**
	 * Constructeurs de la class Experience
	 */
	public Experience() {
		super();
	}
	
	public Experience(Date debut, Date fin, String intitule, String entreprise, String description) {
		super();
		this.debut = debut;
		this.fin = fin;
		this.intitule = intitule;
		this.entreprise = entreprise;
		this.description = description;
	}
	
	/**
	 * Genertaion des getters et setters
	 * @return
	 */
	public Long getId() {
		return id;
	}
	
	public Date getDebut() {
		return debut;
	}
	
	public Date getFin() {
		return fin;
	}
	
	public String getIntitule() {
		return intitule;
	}
	
	public String getEntreprise() {
		return entreprise;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setDebut(Date debut) {
		this.debut = debut;
	}
	
	public void setFin(Date fin) {
		this.fin = fin;
	}
	
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	
	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
}
