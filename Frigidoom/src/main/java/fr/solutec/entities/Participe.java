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
public class Participe {
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private Evenement evenement;
	
	@ManyToOne 
	private Utilisateur participant;
	

	private boolean accesFrigo = false;

	public Utilisateur getParticipants() {
	    return participant;
	}
	
	public void setParticipants(Utilisateur participants) {
	    this.participant = participants;
	}

}
