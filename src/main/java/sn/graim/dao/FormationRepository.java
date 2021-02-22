package sn.graim.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.graim.entities.Formation;
import sn.graim.entities.Utilisateur;

public interface FormationRepository extends JpaRepository<Formation, Long> {
	public Formation findByUtilisateur(Utilisateur u);
}
