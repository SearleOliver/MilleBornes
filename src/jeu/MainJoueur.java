package jeu;

import java.util.ArrayList;
import java.util.List;

import cartes.Carte;

public class MainJoueur {
	
	private List<Carte> main = new ArrayList<>();

	public MainJoueur() {
		
	}
	
	public void prendre(Carte c) {
		main.add(c);
	}
	
	public void jouer(Carte c) {
		assert main.contains(c);
		main.remove(c);
	}
	
	public List<Carte> getListeCartes() {
		return main;
	}
	
	public String afficherMain() {
		int nbCartes = main.size();
		StringBuilder string = new StringBuilder("[");
		
		for (int i = 0; i < nbCartes; i++) {
			if (i < nbCartes - 1)
				string.append(main.get(i).toString() + ", ");
			else
				string.append(main.get(i).toString());
		}
		string.append("]\n");
		return string.toString();
	}
	
	@Override
	public String toString() {
		return afficherMain();
	}

}