package cartes;

public abstract class Probleme extends Carte {
	private Type type;

	public Probleme(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			if (obj instanceof Probleme pb) {
				return type.equals(pb.getType());
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 31 * super.hashCode() * type.hashCode();
	}

}
