package it.polito.tdp.artsmia.model;

public class Artist {
	private Integer artistID;
	private String name;
	
	/**
	 * @param artistID
	 * @param name
	 */
	public Artist(Integer artistID, String name) {
		super();
		this.artistID = artistID;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getArtistID() {
		return artistID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artistID == null) ? 0 : artistID.hashCode());
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
		Artist other = (Artist) obj;
		if (artistID == null) {
			if (other.artistID != null)
				return false;
		} else if (!artistID.equals(other.artistID))
			return false;
		return true;
	}
	
	
}
