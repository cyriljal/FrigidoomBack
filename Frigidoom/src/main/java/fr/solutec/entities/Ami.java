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
public class Ami {
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private Utilisateur idDemandeur;
	@ManyToOne
	private Utilisateur idReceveur;
	
	private boolean accepte;
	
}
