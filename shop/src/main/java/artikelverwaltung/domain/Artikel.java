package artikelverwaltung.domain;

public class Artikel {

	private Long id;
	private String bezeichnung;
	private long preis;
	public Long getId() {
		return id;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public long getPreis() {
		return preis;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public void setPreis(long preis) {
		this.preis = preis;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (int) (preis ^ (preis >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artikel other = (Artikel) obj;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		} else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (preis != other.preis)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Artikel [id=" + id + ", bezeichnung=" + bezeichnung
				+ ", preis=" + preis + "]";
	}
	
	
}
