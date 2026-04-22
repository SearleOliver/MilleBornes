package strategies;

import jeu.Coup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public interface Strategie {

	default List<Coup> trierCoups(Set<Coup> coups) {
        List<Coup> copie = new ArrayList<>(coups);
        Random random = new Random();
        copie.sort((c1, c2) -> {
            if (c1.equals(c2)) return 0;
            return random.nextBoolean() ? -1 : 1;
        });
        return copie;
    }

	default Coup selectionnerCoup(Set<Coup> coups) {
        return trierCoups(coups).get(0);
    }

    default Coup selectionnerDefausse(Set<Coup> coups) {
        List<Coup> tries = trierCoups(coups);
        return tries.get(tries.size() - 1);
    }
}