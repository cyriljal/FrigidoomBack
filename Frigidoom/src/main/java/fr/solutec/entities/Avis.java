package fr.solutec.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data

@Entity 
public class Avis {
	@Id @GeneratedValue
	private Long id;
	private int note;
	private String commentaire;
	
	@ManyToOne
	private Utilisateur utilisateur;
	
	@ManyToOne
	private Recette recette;
}
