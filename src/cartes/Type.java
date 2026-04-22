package cartes;

public enum Type {
	ESSENCE("Panne d'essence", "Essence", "Citerne d'essence"),
	CREVAISON("Crevaison","Roue de Secours","Increvable"),
	ACCIDENT("Accident","Reparations","As du volant"),
	FEU("FeuRouge","FeuVert","VehiculePrioritaire");
	
	private final String attaque;
	private final String parade;
	private final String botte;
	Type(String attaque, String parade, String botte) {
		this.attaque = attaque;
		this.parade = parade;
		this.botte = botte;
	}
	public String getAttaque() {
		return attaque;
	}
	
	public String getParade() {
		return parade;
	}
	
	public String getBotte() {
		return botte;
	}
}
