package fr.solutec.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data

@Entity 
public class ListeCourse {
	@Id @GeneratedValue
	private Long id;
	private String titre;
	private LocalDate dateCreation;
	private LocalDate dateModification;
	
	@ManyToOne //
	private Utilisateur utilisateur;
	
	private boolean valide =false;
	
	@PrePersist
	protected void onCreate() {
	    this.dateCreation = LocalDate.now(); // permet de mettre par défaut la date de création à aujourd'hui
	}





	
}


