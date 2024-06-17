package fr.solutec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.solutec.entities.IngredientCourse;
import fr.solutec.entities.ListeCourse;

public interface IngredientCourseRepository extends CrudRepository<IngredientCourse, Long> {
	
	@Query("SELECT ic FROM IngredientCourse ic WHERE ic.utilisateur.id = ?1")
	public List<IngredientCourse> findAllByUtilisateurId(Long utilisateurId);
	
	@Query ("SELECT ic FROM IngredientCourse ic WHERE ic.listeCourse.id = ?1")
	public List <IngredientCourse> findAllByListeCourseId (Long listeCourseId);

	@Query ("SELECT ic FROM IngredientCourse ic "
			+ "JOIN ListeCourse lc ON ic.listeCourse.id = lc.id "
			+ "JOIN Utilisateur u ON lc.utilisateur.id = u.id "
			+ "JOIN Participe p ON u.id = p.participant.id "
			+ "WHERE p.evenement.id = ?1 AND ic.disponible=1"
			)
	public List <IngredientCourse> findAllIngredientCourseEvenement (Long evenementId);
	
}
