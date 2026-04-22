package jeu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;

import cartes.Carte;
import utils.GestionCartes;

public class Jeu {
	
	public static final int NBCARTES = 6;

	private Sabot sabot;
	private List<Carte> listeCartes;
	private Set<Joueur> joueurs = new LinkedHashSet<>();
	private Iterator<Joueur> itJoueurs = joueurs.iterator();
	
	public Jeu(Carte[] cartes) {
		listeCartes = new ArrayList<>();
		listeCartes.addAll(Arrays.asList(cartes));
		listeCartes = GestionCartes.melanger(listeCartes);
		sabot = new Sabot(listeCartes.toArray(new Carte[0]));
	}
	
	public void inscrire(Joueur...tabJoueurs) {
		for (Joueur joueur : tabJoueurs) {
			joueurs.add(joueur);
		}
	}
	
	public void distribuerCartes() {
		int nbJoueurs = joueurs.size();
		int i = 0;
		
		Iterator<Joueur> itJoueurs;
		
		while (i < nbJoueurs) {
			itJoueurs = joueurs.iterator();
			
			while (itJoueurs.hasNext()) {
				Joueur joueur = itJoueurs.next();
				joueur.donner(joueur.prendreCarte(sabot));
			}
			
			i++;
		}
	}
	
	public String jouerTour(Joueur joueur) {
		StringBuilder string = new StringBuilder();
		
		Carte carte = joueur.prendreCarte(sabot);
		string.append("Le joueur " + joueur.toString() + " a pioche " + carte.toString() + "\n");
		string.append("Il a dans sa main : " + joueur.getMain().toString());
		
		Coup coup = joueur.choisirCoup(joueurs);
		
		
		joueur.retirerDeLaMain(coup.getCarteJouee());
		
		if (coup.getJoueurCible() == null) {
			sabot.ajouterCarte(coup.getCarteJouee());
		} else if (coup.getJoueurCible().equals(coup.getJoueurCourant())) {
			joueur.deposer(coup.getCarteJouee());
		} else {
			coup.getJoueurCible().deposer(coup.getCarteJouee());
		}
		
		string.append(coup.toString());
		
		return string.toString();
	}
	
	public Joueur donnerJoueurSuivant() {
		
		if (itJoueurs.hasNext()) {
			return itJoueurs.next();
		} else {
			itJoueurs = joueurs.iterator();
			return itJoueurs.next();
		}
	}
	
	public String lancer() {
		StringBuilder string = new StringBuilder();
		Joueur joueurCourant = donnerJoueurSuivant();
		Joueur joueurPrecedent = joueurCourant;
		
		while (joueurPrecedent.donnerKmParcourus() < 1000 && !sabot.estVide()) {
			string.append(jouerTour(joueurCourant));
			joueurPrecedent = joueurCourant;
			joueurCourant = donnerJoueurSuivant();
		}
		
		NavigableSet<Joueur> clsmt = classement();
		List<Joueur> lstClassement = new ArrayList<Joueur>(clsmt);
		
		string.append("Vainqueur : " + lstClassement.get(0).toString() + "\n");
		string.append("Classement : \n");
		for(Joueur joueur : lstClassement) {
			string.append("Joueur : "+joueur.getNom()+" kilometrage : "+joueur.donnerKmParcourus()+"km. \n");
		}
		
		return string.toString();
	}
	
	public NavigableSet<Joueur> classement() {
		NavigableSet<Joueur> setJoueurs = new TreeSet<>(
			    Comparator
			        .comparingInt(Joueur::donnerKmParcourus)
			        .reversed()
			        .thenComparing(Joueur::getNom)
			);
		setJoueurs.addAll(joueurs);
		return setJoueurs;
	}

}