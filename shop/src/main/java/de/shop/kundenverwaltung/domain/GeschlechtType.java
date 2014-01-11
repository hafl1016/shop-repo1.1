package de.shop.kundenverwaltung.domain;



public enum GeschlechtType {
	MAENNLICH("M"),
	WEIBLICH("W");
	
	private String internal;
	
	private GeschlechtType(String internal) {
		this.internal = internal;
	}
	
	public String getInternal() {
		return internal;
	}
	
	public static GeschlechtType build(String internal) {
		if (internal == null) {
			return null;
		}
		
		switch (internal) {
			case "M":
				return MAENNLICH;
			case "W":
				return WEIBLICH;
			default:
				throw new RuntimeException(internal + " ist kein gueltiger Wert fuer GeschlechtType");
		}
	}
}
