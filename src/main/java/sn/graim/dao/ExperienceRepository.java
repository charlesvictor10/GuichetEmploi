package sn.graim.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.graim.entities.Experience;
import sn.graim.entities.Utilisateur;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
	public Experience findByUtilisateur(Utilisateur u);
}
