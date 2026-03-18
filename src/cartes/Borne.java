package cartes;

public class Borne extends Carte {
	private int km;

	public Borne(int km) {
		this.km = km;
	}
	public String toString() {
		return (km+"KM");
	}
	
	public int getKm() {
		return this.km;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			if (obj instanceof Borne borne) {
				return km == borne.getKm();
			}
			return false;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 31 * super.hashCode() * km;
	}
}
