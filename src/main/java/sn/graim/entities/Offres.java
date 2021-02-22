package sn.graim.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Offres implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Offres
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_offre")
	private Long id;
	private String titre;
	private String type_contrat;
	@Column(name = "niveau_experience", length = 250, nullable = false)
	private String experience;
	@Column(name = "niveau_etude", length = 250, nullable = false)
	private String etudes;
	private int nombre_poste;
	private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime date_publication;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime date_expiration;
	@ManyToOne
	@JoinColumn(name="id_niveau_langue")
	private NiveauLangue niveauLangue;
	/**
	 * Relation entre Emploi et Recruteur
	 */
	@ManyToOne
	@JoinColumn(name="id_recruteur")
	private Recruteur recruteur;
	/**
	 * Relation entre Emploi et Metiers
	 */
	@ManyToOne
	@JoinColumn(name="id_metier")
	private Metiers metiers;
	/**
	 * Relation entre Emploi et Region
	 */
	@ManyToOne
	@JoinColumn(name="id_region")
	public Region region;
	
	/**
	 * Constructeurs de la class Offres
	 */
	public Offres() {
		super();
	}

	public Offres(String titre, String type_contrat, String experience, String etudes, int nombre_poste,
			String description) {
		super();
		this.titre = titre;
		this.type_contrat = type_contrat;
		this.experience = experience;
		this.etudes = etudes;
		this.nombre_poste = nombre_poste;
		this.description = description;
	}

	/**
	 * Generation des getters et setters
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public String getTitre() {
		return titre;
	}

	public String getType_contrat() {
		return type_contrat;
	}

	public String getExperience() {
		return experience;
	}

	public String getEtudes() {
		return etudes;
	}

	public int getNombre_poste() {
		return nombre_poste;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getDate_publication() {
		return date_publication;
	}

	public LocalDateTime getDate_expiration() {
		return date_expiration;
	}

	public NiveauLangue getNiveauLangue() {
		return niveauLangue;
	}

	public void setNiveauLangue(NiveauLangue niveauLangue) {
		this.niveauLangue = niveauLangue;
	}

	public void setDate_publication(LocalDateTime date_publication) {
		this.date_publication = date_publication;
	}

	public void setDate_expiration(LocalDateTime date_expiration) {
		this.date_expiration = date_expiration;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setType_contrat(String type_contrat) {
		this.type_contrat = type_contrat;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public void setEtudes(String etudes) {
		this.etudes = etudes;
	}

	public void setNombre_poste(int nombre_poste) {
		this.nombre_poste = nombre_poste;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Recruteur getRecruteur() {
		return recruteur;
	}

	public Region getRegion() {
		return region;
	}

	public void setRecruteur(Recruteur recruteur) {
		this.recruteur = recruteur;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Metiers getMetiers() {
		return metiers;
	}

	public void setMetiers(Metiers metiers) {
		this.metiers = metiers;
	}
}
