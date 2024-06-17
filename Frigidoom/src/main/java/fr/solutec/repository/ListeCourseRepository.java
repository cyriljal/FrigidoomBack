package fr.solutec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.solutec.entities.ListeCourse;


public interface ListeCourseRepository extends CrudRepository<ListeCourse, Long> {

	@Query("SELECT lc FROM ListeCourse lc WHERE lc.utilisateur.id = ?1 AND valide = false")
	public List<ListeCourse> findAllByUtilisateurId(Long utilisateurId);

	@Query("SELECT lc FROM ListeCourse lc WHERE lc.utilisateur.id = ?1 AND valide = true")
	public List<ListeCourse> findAllArchiveByUtilisateurId(Long utilisateurId);


	
}

