package cartes;

import java.util.LinkedHashMap;
import java.util.Map;

public class JeuDeCartes {
    private static final int NB_CARTES_MAX = 106;

    private Map<Carte, Integer> typesDeCartes;

    public JeuDeCartes() {
        typesDeCartes = new LinkedHashMap<>();
        typesDeCartes.put(new Borne(25),              10);
        typesDeCartes.put(new Borne(50),              10);
        typesDeCartes.put(new Borne(75),              10);
        typesDeCartes.put(new Borne(100),             12);
        typesDeCartes.put(new Borne(200),              4);
        typesDeCartes.put(new DebutLimite(),           4);
        typesDeCartes.put(new FinLimite(),             6);
        typesDeCartes.put(new Parade(Type.FEU),       14);
        typesDeCartes.put(new Parade(Type.ESSENCE),    6);
        typesDeCartes.put(new Parade(Type.CREVAISON),  6);
        typesDeCartes.put(new Parade(Type.ACCIDENT),   6);
        typesDeCartes.put(new Attaque(Type.FEU),       5);
        typesDeCartes.put(new Attaque(Type.ESSENCE),   3);
        typesDeCartes.put(new Attaque(Type.CREVAISON), 3);
        typesDeCartes.put(new Attaque(Type.ACCIDENT),  3);
        typesDeCartes.put(new Botte(Type.FEU),         1);
        typesDeCartes.put(new Botte(Type.ESSENCE),     1);
        typesDeCartes.put(new Botte(Type.CREVAISON),   1);
        typesDeCartes.put(new Botte(Type.ACCIDENT),    1);
    }

    public String affichageJeuCartes() {
        StringBuilder string = new StringBuilder();
        for (Map.Entry<Carte, Integer> entry : typesDeCartes.entrySet()) {
            string.append(entry.getValue() + " " + entry.getKey().toString() + "\n");
        }
        return string.toString();
    }

    public static int getNbCartesMax() {
        return NB_CARTES_MAX;
    }

    private int getCount() {
        int count = 0;
        for (Map.Entry<Carte, Integer> entry : typesDeCartes.entrySet()) {
            count += entry.getValue();
        }
        return count;
    }

    public Carte[] donnerCartes() {
        Carte[] cartes = new Carte[getCount()];
        int i = 0;
        for (Map.Entry<Carte, Integer> entry : typesDeCartes.entrySet()) {
            for (int numCarte = 0; numCarte < entry.getValue(); numCarte++) {
                cartes[i++] = entry.getKey();
            }
        }
        return cartes;
    }

    private int count(Carte carte, Carte[] cartes) {
        int cpt = 0;
        for (int i = 0; i < cartes.length; i++) {
            if (cartes[i].equals(carte)) {
                cpt++;
            }
        }
        return cpt;
    }

    public boolean checkCount() {
        Carte[] cartes = donnerCartes();
        for (Map.Entry<Carte, Integer> entry : typesDeCartes.entrySet()) {
            int cpt = count(entry.getKey(), cartes);
            if (entry.getValue() != cpt) {
                return false;
            }
        }
        return true;
    }
}

//package cartes;
//
//import cartes.JeuDeCartes;
//
//public class JeuDeCartes {
//	private static final int NB_CARTES_MAX = 106;
//	
//	private static class Configuration {
//
//		private int nbExemplaires;
//		private Carte carte;
//
//		private Configuration(Carte carte, int nbExemplaires) {
//			this.carte = carte;
//			this.nbExemplaires = nbExemplaires;
//		}
//
//		public Carte getCarte() {
//			return carte;
//		}
//
//		public int getNbExemplaires() {
//			return nbExemplaires;
//		}
//
//	}
//
//	private Configuration[] typesDeCartes = new Configuration[] { new Configuration(new Borne(25), 10),
//			new Configuration(new Borne(50), 10), new Configuration(new Borne(75), 10),
//			new Configuration(new Borne(100), 12), new Configuration(new Borne(200), 4),
//			new Configuration(new DebutLimite(), 4), new Configuration(new FinLimite(), 6),
//			new Configuration(new Parade(Type.FEU), 14), new Configuration(new Parade(Type.ESSENCE), 6),
//			new Configuration(new Parade(Type.CREVAISON), 6), new Configuration(new Parade(Type.ACCIDENT), 6),
//			new Configuration(new Attaque(Type.FEU), 5), new Configuration(new Attaque(Type.ESSENCE), 3),
//			new Configuration(new Attaque(Type.CREVAISON), 3), new Configuration(new Attaque(Type.ACCIDENT), 3),
//			new Configuration(new Botte(Type.FEU), 1), new Configuration(new Botte(Type.ESSENCE), 1),
//			new Configuration(new Botte(Type.CREVAISON), 1), new Configuration(new Botte(Type.ACCIDENT), 1) };
//
//	
//	public String affichageJeuDeCartes() {
//		StringBuilder string = new StringBuilder();
//		string.append("JEU : \n");
//		for (Configuration config : typesDeCartes) {
//			string.append(config.getNbExemplaires()+ " " + config.getCarte()+ "\n" );
//		}
//		return string.toString();
//	}
//	
//	public int getCount() {
//		int count = 0;
//		for (Configuration config: typesDeCartes) {
//			count+=config.getNbExemplaires();
//		}
//		return count;
//	}
//	
//	public Carte[] donnerCartes() {
//		Carte[] cartes = new Carte[getCount()];
//
//		for (int i = 0, j = 0; j < 19; j++) {
//			Configuration config = typesDeCartes[j];
//			for (int numCarte = 0; numCarte < config.getNbExemplaires(); numCarte++) {
//				cartes[i++] = config.getCarte();
//			}
//		}
//		return cartes;
//	}
//	
//	public static int getNbCartesMax() {
//		return NB_CARTES_MAX;
//	}
//	
//	private int count(Carte carte, Carte[] cartes) {
//		int cpt = 0;
//		for (int i = 0; i < cartes.length; i++) {
//			if (cartes[i].equals(carte)) {
//				cpt++;
//			}
//		}
//		return cpt;
//	}
//	
//	public boolean checkCount() {
//		Carte[] cartes = donnerCartes();
//		for (int i = 0; i < 19; i++) {
//			Carte c = typesDeCartes[i].getCarte();
//			int cpt = count(c, cartes);
//			if (typesDeCartes[i].getNbExemplaires() != cpt) {
//				return false;
//			}
//		}
//		return true;
//	}
//	
//	private static class Configuration {
//
//		private int nbExemplaires;
//		private Carte carte;
//
//		private Configuration(Carte carte, int nbExemplaires) {
//			this.carte = carte;
//			this.nbExemplaires = nbExemplaires;
//		}
//
//		public Carte getCarte() {
//			return carte;
//		}
//
//		public int getNbExemplaires() {
//			return nbExemplaires;
//		}
//
//	}
//}
