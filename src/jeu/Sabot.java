package jeu;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import cartes.Carte;
import cartes.JeuDeCartes;

public class Sabot implements Iterable<Carte>{
	Carte[] cartes;
	private int nbCartes = JeuDeCartes.getNbCartesMax();
	private int nbrOperations = 0;

	public Sabot(Carte[] cartes) {
		this.cartes= cartes;
	}
	
	public Boolean estVide() {
		return nbCartes == 0;
	}
	
	public void ajouterCarte(Carte carte) {
		if (nbCartes<JeuDeCartes.getNbCartesMax()) {
			cartes[nbCartes++]=carte;
			nbrOperations++;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	public Carte piocher() {
		Iterator<Carte> iter = iterator();
		Carte carte = iter.next();
		iter.remove();
		return carte;
	}

	@Override
	public Iterator<Carte> iterator() {
		return new Iterateur();
	}
	
	private class Iterateur implements Iterator<Carte> {
		private Boolean nextEffectue = false;
		private int nbrOpRef = nbrOperations;
		private int indiceIterateur = 0;
		
		private void verificationConcurrence() {
			if (nbrOperations != nbrOpRef)
				throw new ConcurrentModificationException();
		}
		
		@Override
		public Carte next() {
			verificationConcurrence();
			if (hasNext()) {
				Carte carte = cartes[indiceIterateur];
				indiceIterateur++;
				nextEffectue = true;
				return carte;
			} else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public boolean hasNext() {
			return indiceIterateur < nbCartes;
		}
		
		@Override
		public void remove() {
			verificationConcurrence();
			if (nbCartes < 1 || !nextEffectue) {
				throw new IllegalStateException();
			}
			
			for (int i = indiceIterateur - 1; i < nbCartes - 1; i++) {
				cartes[i] = cartes[i+1];
			}
			nextEffectue = false;
			indiceIterateur--;
			nbCartes--;
			nbrOperations++; nbrOpRef++;
		}
		
	}
	
	

}
