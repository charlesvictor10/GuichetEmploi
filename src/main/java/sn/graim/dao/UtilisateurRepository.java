package sn.graim.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.graim.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {
	public Utilisateur findByUsername(String username);
}
