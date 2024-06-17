package fr.solutec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.solutec.entities.Utilisateur;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

	//Pour la connexion
	@Query (value = "SELECT u FROM Utilisateur u WHERE u.login= ?1 AND u.mdp =?2")
	public Optional <Utilisateur> getPersonByLoginAndMdp(String login, String mdp);
	
	@Query (value = "SELECT u FROM Utilisateur u WHERE u.login = ?1")
	public Optional<Utilisateur> findByLogin(String login); 
}
