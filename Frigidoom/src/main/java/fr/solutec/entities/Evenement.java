package fr.solutec.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data

@Entity 
public class Evenement {
	@Id @GeneratedValue
	private Long id;
	private String type;
	private LocalDate date;
	private String adresse;
	@Column(length = 10000) //permet d'augmenter le nombre de charactères 
	private String description;
	
	@ManyToOne
	private Utilisateur organisateur;
	
	@ManyToOne
	private Classement classement;
}
