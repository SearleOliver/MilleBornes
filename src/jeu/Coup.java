package jeu;

import cartes.Attaque;
import cartes.Carte;
import cartes.DebutLimite;
import cartes.Limite;

public class Coup  {
	private Joueur joueurCourant;
	private Joueur joueurCible;
	private Carte carteJouee;
	
	public Coup(Joueur joueurCourant, Joueur joueurCible, Carte carteJouee) {
		this.joueurCourant = joueurCourant;
		this.joueurCible = joueurCible;
		this.carteJouee = carteJouee;
	}
	
	public int compareTo(Coup coup) {
	Joueur cibleDuCoup = coup.getJoueurCible();
	if (joueurCourant.equals(this.joueurCible) &&
	joueurCourant.equals(cibleDuCoup)) {
		return 0;
	}
	if (joueurCible == null && cibleDuCoup == null) {
		return 0;
	}
	if (joueurCourant.equals(joueurCible)) {
		return 1;
	}
	if (joueurCourant.equals(cibleDuCoup)) {
		return -1;
	}
		return joueurCible.compareTo(cibleDuCoup);
	}

	public Joueur getJoueurCourant() {
		return joueurCourant;
	}

	public Joueur getJoueurCible() {
		return joueurCible;
	}

	public Carte getCarteJouee() {
		return carteJouee;
	}
	public boolean estValide() {
		if (carteJouee instanceof Attaque || carteJouee instanceof DebutLimite) {
			return joueurCourant != null && joueurCible != null && !joueurCourant.equals(joueurCible);
		} else {
			return joueurCourant != null && joueurCourant.equals(joueurCible);
		}
	}
	
	

}
