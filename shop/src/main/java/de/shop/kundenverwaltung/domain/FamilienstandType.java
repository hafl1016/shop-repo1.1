package de.shop.kundenverwaltung.domain;



public enum FamilienstandType {
	LEDIG("L"),
	VERHEIRATET("VH"),
	GESCHIEDEN("G"),
	VERWITWET("VW");
	
	private String internal;
	
	private FamilienstandType(String internal) {
		this.internal = internal;
	}
	
	public String getInternal() {
		return internal;
	}
	
	public static FamilienstandType build(String internal) {
		if (internal == null) {
			return null;
		}
		
		switch (internal) {
			case "L":
				return LEDIG;
			case "VH":
				return VERHEIRATET;
			case "G":
				return GESCHIEDEN;
			case "VW":
				return VERWITWET;
			default:
				throw new RuntimeException(internal + " ist kein gueltiger Wert fuer FamilienstandType");
		}
	}
}
