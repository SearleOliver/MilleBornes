package jeu;

import cartes.Carte;

public class Joueur {
	private String nom;
	private MainJoueur main = new MainJoueur();

	public Joueur(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return nom;
	}
	
	public MainJoueur getMain() {
		return main;
	}
	
	public void donner(Carte c) {
		main.prendre(c);
	}
	
	public Carte prendreCarte(Sabot sabot) {
		if (sabot.estVide()) {
			return null;
		}
		Carte cartePiochee = sabot.piocher();
		donner(cartePiochee);
		return cartePiochee;
	}
	
	public void retirerDeLaMain(Carte carte) {
		main.jouer(carte);
	}

	@Override
	public String toString() {
		return nom;
	}
	
	@Override
	public int hashCode() {
		return 31 * nom.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Joueur other) {
			return nom.equals(other.getNom());
		}
		return false;
	}

}