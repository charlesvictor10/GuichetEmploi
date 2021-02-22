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
public class Formation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Formation
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date debut;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fin;
	private String titre;
	private String etablissement;
	private String description;
	/**
	 * Relation entre Critere et Utilisateur
	 */
	@ManyToOne
	@JoinColumn(name="username")
	private Utilisateur utilisateur;
	
	/**
	 * Constructeurs de la class Formation
	 */
	public Formation() {
		super();
	}
	
	public Formation(Date debut, Date fin, String titre, String etablissement, String description) {
		super();
		this.debut = debut;
		this.fin = fin;
		this.titre = titre;
		this.etablissement = etablissement;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public Date getDebut() {
		return debut;
	}

	public Date getFin() {
		return fin;
	}

	public String getTitre() {
		return titre;
	}

	public String getEtablissement() {
		return etablissement;
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

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
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
