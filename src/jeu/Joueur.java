package jeu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;

import cartes.Bataille;
import cartes.Botte;
import cartes.Carte;
import cartes.DebutLimite;
import strategies.Strategie;

public class Joueur {
    private String nom;
    private ZoneDeJeu zoneDeJeu = new ZoneDeJeu();
    private MainJoueur main = new MainJoueur();
    private Random seed = new Random();
    private Strategie strategie;

    public int compareTo(Joueur joueur) {
    	if (this.donnerKmParcourus()==joueur.donnerKmParcourus()) {
    		return this.nom.compareTo(joueur.nom);
    	}
    	if (this.donnerKmParcourus()>joueur.donnerKmParcourus()) {
    		return 1;
    	}return -1;
    }
    public Joueur(String nom) {
        this.nom = nom;
        this.strategie = new Strategie() {
		};
    }
    

    public Joueur(String nom, Strategie strat) {
        this.nom = nom;
        this.strategie = strat;
    }

    public Strategie getStrategie() {
        return strategie;
    }

    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
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
        if (sabot.estVide()) return null;
        Carte cartePiochee = sabot.piocher();
        donner(cartePiochee);
        return cartePiochee;
    }

    public void retirerDeLaMain(Carte carte) {
        main.jouer(carte);
    }

    @Override
    public String toString() { return nom; }

    @Override
    public int hashCode() { return 31 * nom.hashCode(); }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Joueur other) return nom.equals(other.getNom());
        return false;
    }

    public int donnerKmParcourus() { return zoneDeJeu.donnerKmParcours(); }

    public void deposer(Carte c) { zoneDeJeu.deposer(c); }

    public boolean estDepotAutorise(Carte carte) { return zoneDeJeu.estDepotAutorise(carte); }

    public Set<Coup> coupsPossibles(Set<Joueur> participants) {
        Set<Coup> coups = new HashSet<>();
        for (Iterator<Joueur> joueursCible = participants.iterator(); joueursCible.hasNext();) {
            Joueur cible = joueursCible.next();
            for (ListIterator<Carte> cartes = this.main.getListeCartes().listIterator(); cartes.hasNext();) {
                Carte cartejouee = cartes.next();
                Coup coup = new Coup(this, cible, cartejouee);
                if (coup.estValide()) coups.add(coup);
            }
        }
        return coups;
    }

    public Set<Coup> coupsDefausse() {
        Set<Coup> coups = new HashSet<>();
        for (ListIterator<Carte> itCartes = this.main.getListeCartes().listIterator(); itCartes.hasNext();) {
            coups.add(new Coup(this, null, itCartes.next()));
        }
        return coups;
    }

    private Coup coupAleatoire(Set<Coup> coups) {
        int index = seed.nextInt(coups.size());
        Iterator<Coup> it = coups.iterator();
        Coup coupChoisit = null;
        for (; it.hasNext() && index >= 0; index--) coupChoisit = it.next();
        return coupChoisit;
    }

    public Coup choisirCoup(Set<Joueur> participants) {
        Set<Coup> coupsLegaux = coupsPossibles(participants);

        if (!coupsLegaux.isEmpty()) {
            return strategie.selectionnerCoup(coupsLegaux);
        } else {
            return strategie.selectionnerDefausse(coupsDefausse());
        }
    }
    
    public Carte donnerSommetPile() {
    	return zoneDeJeu.donnerSommet(zoneDeJeu.getBataille());
    }

    public String afficherEtatJoueur() {
        StringBuilder string = new StringBuilder("INFOS " + nom + '\n');
        string.append("- Ensemble des bottes: [");
        Set<Botte> ensemblesBottes = zoneDeJeu.getBottes();
        Iterator<Botte> it = ensemblesBottes.iterator();
        while (it.hasNext()) string.append(it.next().toString() + ", ");
        string.append("]\n");
        string.append("- Présence d'une limitation de vitesse? : "
            + (zoneDeJeu.getLimites().contains(new DebutLimite()) ? "oui\n" : "non\n"));
        Bataille sommet = zoneDeJeu.donnerSommet(zoneDeJeu.getBataille());
        string.append("- Sommet de la pile de bataille: " + (sommet != null ? sommet.toString() + "\n" : "null\n"));
        string.append("- Contenu de la main: " + main.toString());
        return string.toString();
    }

	public Set<Botte> donnerBottes() {
		return zoneDeJeu.getBottes();
	}
}