package cartes;

public abstract class Carte {
	private Type type;

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			return getClass().equals(obj.getClass());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 31 * getClass().hashCode();
	}
	
}
