package sn.graim.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Critere implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Critere
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String disponibilite;
	private String type_contrat;
	private String renumeration;
	/** 
	 * Relation entre Critere et Metiers
	 */
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="criteres_metiers")
	private Set<Metiers> metiers = new HashSet<Metiers>(); 
	/** 
	 * Relation entre Critere et Secteurs
	 */
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="criteres_secteurs")
	private Set<Secteurs> secteurs = new HashSet<Secteurs>(); 
	/** 
	 * Relation entre Critere et Regions
	 */
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="criteres_regions")
	private Set<Region> regions = new HashSet<Region>(); 
	/**
	 * Relation entre Critere et Utilisateur
	 */
	@ManyToOne
	@JoinColumn(name="username")
	private Utilisateur utilisateur;
	
	/**
	 * Constructeurs de la class Critere
	 */
	public Critere() {
		super();
	}

	public Critere(String disponibilite, String type_contrat, String renumeration, Set<Metiers> metiers,
			Set<Secteurs> secteurs, Set<Region> regions, Utilisateur utilisateur) {
		super();
		this.disponibilite = disponibilite;
		this.type_contrat = type_contrat;
		this.renumeration = renumeration;
		this.metiers = metiers;
		this.secteurs = secteurs;
		this.regions = regions;
		this.utilisateur = utilisateur;
	}

	/**
	 * Generation des getters et setters
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public String getDisponibilite() {
		return disponibilite;
	}

	public String getType_contrat() {
		return type_contrat;
	}

	public String getRenumeration() {
		return renumeration;
	}

	public Set<Metiers> getMetiers() {
		return metiers;
	}

	public Set<Secteurs> getSecteurs() {
		return secteurs;
	}

	public Set<Region> getRegions() {
		return regions;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDisponibilite(String disponibilite) {
		this.disponibilite = disponibilite;
	}

	public void setType_contrat(String type_contrat) {
		this.type_contrat = type_contrat;
	}

	public void setRenumeration(String renumeration) {
		this.renumeration = renumeration;
	}

	public void setMetiers(Set<Metiers> metiers) {
		this.metiers = metiers;
	}

	public void setSecteurs(Set<Secteurs> secteurs) {
		this.secteurs = secteurs;
	}

	public void setRegions(Set<Region> regions) {
		this.regions = regions;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
}
