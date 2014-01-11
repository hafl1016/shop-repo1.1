package de.shop.kundenverwaltung.domain;



public enum HobbyType {
	SPORT("S"),
	LESEN("L"),
	REISEN("R");
	
	private String internal;
	
	private HobbyType(String internal) {
		this.internal = internal;
	}
	
	public String getInternal() {
		return internal;
	}
	
	public static HobbyType build(String internal) {
		if (internal == null) {
			return null;
		}
		
		switch (internal) {
			case "S":
				return SPORT;
			case "L":
				return LESEN;
			case "R":
				return REISEN;
			default:
				throw new RuntimeException(internal + " ist kein gueltiger Wert fuer HobbyType");
		}
	}
}
