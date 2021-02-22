package sn.graim.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.graim.entities.Metiers;
import sn.graim.entities.Offres;
import sn.graim.entities.Region;

public interface OffresRepository extends JpaRepository<Offres, Long> {
	@Query("select o from Offres o where o.titre like :x")
	public Page<Offres> rechercheParTitre(@Param("x") String mc, Pageable pageable);
	@Query("select count(o) from Offres o")
	public Long nombreOffres();
	@Query("select count(o) from Offres o join Metiers m on o.metiers = m.secteur")
	public Long offresParSecteur();
	@Query("select o from Offres o where o.titre like :x and o.region =:y and o.metiers =:z")
	public Page<Offres> moteurRecherche(@Param("x") String titre, @Param("y") Region region, @Param("z") Metiers metier, Pageable pageable);
}
