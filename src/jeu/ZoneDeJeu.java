package jeu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cartes.Attaque;
import cartes.Bataille;
import cartes.Borne;
import cartes.Botte;
import cartes.Carte;
import cartes.Cartes;
import cartes.DebutLimite;
import cartes.FinLimite;
import cartes.Limite;
import cartes.Parade;

public class ZoneDeJeu {

	private List<Limite> pileLimites = new ArrayList<>();
	private List<Bataille> pileBataille = new ArrayList<>();
	private List<Borne> pileBorne = new ArrayList<>();
	private Set<Botte> bottes = new HashSet<>();
	
	public Set<Botte> getBottes() {
		return bottes;
	}
	
	public List<Limite> getLimites() {
		return pileLimites;
	}
	
	public List<Bataille> getBataille() {
		return pileBataille;
	}
	
	
	public int donnerLimitationVitesse() {
		if (pileLimites.isEmpty() || (donnerSommet(pileLimites) instanceof FinLimite) || estPrioritaire()) {
			return 200;
		}
		return 50;
	}
	
	public int donnerKmParcours() {
		int total = 0;
		for (Borne borne : pileBorne) {
			total += borne.getKm();
		}
		return total;
	}
	
	public boolean estPrioritaire() {
		return bottes.contains(Cartes.PRIORITAIRE);
	}

	public <E> E donnerSommet(List<E> pile) {
		if (!pile.isEmpty()) {
			return pile.get(pile.size() - 1);
		}
		return null;
	}
	
	public void deposer(Carte c) {
		if (c instanceof Borne borne) {
			pileBorne.add(borne);
		} else if (c instanceof Limite limite)  {
			pileLimites.add(limite);
		} else if (c instanceof Bataille bataille) {
			pileBataille.add(bataille);
		} else if (c instanceof Botte botte) {
			bottes.add(botte);
		}
	}
	
	public boolean peutAvancer() {
		if (pileBataille.isEmpty() && estPrioritaire()) {
			return true;
		} else if (!pileBataille.isEmpty()) {
			Bataille c = donnerSommet(pileBataille);
			return (c.equals(Cartes.FEU_VERT) 
					|| ((c instanceof Parade) && estPrioritaire()) 
					|| ((c instanceof Attaque) && c.equals(Cartes.FEU_ROUGE) && estPrioritaire())
					|| ((c instanceof Attaque) && !c.equals(Cartes.FEU_ROUGE) && bottes.contains(new Botte(c.getType())) && estPrioritaire()));
		}
		return false;
	}
	
	private boolean estDepotFeuVertAutorise() {
		if (estPrioritaire()) {
			return false;
		} else if (pileBataille.isEmpty()) {
			return true;
		} else {
			Bataille s = donnerSommet(pileBataille);
			return s.equals(Cartes.FEU_ROUGE)
					|| (s instanceof Parade && !(s.equals(Cartes.FEU_VERT)))
					|| (s instanceof Attaque && bottes.contains(new Botte(s.getType())));
		}
	}
	
	private boolean estDepotBorneAutorise(Borne b) {
		return peutAvancer()
				&& b.getKm() <= donnerLimitationVitesse()
				&& b.getKm() + donnerKmParcours() <= 1000;
	}
	
	private boolean estDepotLimiteAutorise(Limite limite) {
		if (estPrioritaire()) {
			return false;
		}
		if (limite instanceof DebutLimite) {
			return pileLimites.isEmpty() || (donnerSommet(pileLimites) instanceof FinLimite);
		} else {
			return donnerSommet(pileLimites) instanceof DebutLimite;
		}
	}
	
	private boolean estDepotBatailleAutorise(Bataille bataille) {
		if (bottes.contains(new Botte(bataille.getType()))) {
			return false;
		}
		else if (bataille instanceof Attaque) {
			return peutAvancer();
		}
		if (bataille instanceof Parade) {
			if (bataille.equals(Cartes.FEU_VERT)) {
				return estDepotFeuVertAutorise();
			} else {
				Bataille s = donnerSommet(pileBataille);
				return (s != null) && (s instanceof Attaque) && s.getType().equals(bataille.getType());
			}
		}
		return false;
	}
	
	public boolean estDepotAutorise(Carte carte) {
		if (carte instanceof Borne borne) {
			return estDepotBorneAutorise(borne);
		} else if (carte instanceof Limite limite) {
			return estDepotLimiteAutorise(limite);
		} else if (carte instanceof Bataille bataille) {
			return estDepotBatailleAutorise(bataille);
		} else {
			return true;
		}
	}
	

}
