package jeu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cartes.Carte;
import cartes.JeuDeCartes;
import utils.GestionCartes;

public class Jeu {
	private Sabot sabot;
	private List<Carte> listeCartes;
	
	public Jeu(Carte[] cartes) {
		listeCartes = new ArrayList<>();
		listeCartes.addAll(Arrays.asList(cartes));
		listeCartes = GestionCartes.melanger(listeCartes);
		this.sabot = new Sabot(listeCartes.toArray(new Carte[0]));
	}

}
