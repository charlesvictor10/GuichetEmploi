package sn.graim.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributs de la class Utilisateur
	 */
	@Id
	@Column(name = "username", length = 250, unique = true, nullable = false)
	private String username;
	private String civilite;
	private String prenom;
	private String nom;
	@Column(name = "tel_mobile", length = 17, unique = true, nullable = false)
	private String mobile;
	@Column(name = "carte_identite", length = 15, unique = true, nullable = false)
	private String cni;
	private String password;
	private boolean active;
	/**
	 * Relation entre Utilisateur et Role
	 */
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles")
    private Set<Role> roles = new HashSet<>();	
	/**
	 * Relation entre Utilisateur et Formation
	 */
	@OneToMany(mappedBy="utilisateur", fetch = FetchType.LAZY)
	private Collection<Formation> formations;
	/**
	 * Relation entre Utilisateur et Critere
	 */
	@OneToMany(mappedBy="utilisateur", fetch = FetchType.LAZY)
	private Collection<Critere> criteres;
	/**
	 * Relation entre Utilisateur et Experience
	 */
	@OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
	private Collection<Experience> experiences;
	
	/**
	 * Constructeurs de la class Utilisateur
	 */
	public Utilisateur() {
		super();
	}

	public Utilisateur(String civilite, String prenom, String nom, String mobile, String cni, String username,
			String password) {
		super();
		this.civilite = civilite;
		this.prenom = prenom;
		this.nom = nom;
		this.mobile = mobile;
		this.cni = cni;
		this.username = username;
		this.password = password;
	}

	public Utilisateur(String prenom, String nom, String mobile, String username, String password) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.mobile = mobile;
		this.username = username;
		this.password = password;
	}

	public Utilisateur(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	/**
	 * Generation des getters et setters
	 */
	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getCni() {
		return cni;
	}

	public void setCni(String cni) {
		this.cni = cni;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@JsonIgnore
	public Collection<Formation> getFormations() {
		return formations;
	}

	public void setFormations(Collection<Formation> formations) {
		this.formations = formations;
	}

	@JsonIgnore
	public Collection<Critere> getCriteres() {
		return criteres;
	}

	public void setCriteres(Collection<Critere> criteres) {
		this.criteres = criteres;
	}

	@JsonIgnore
	public Collection<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(Collection<Experience> experiences) {
		this.experiences = experiences;
	}
}
