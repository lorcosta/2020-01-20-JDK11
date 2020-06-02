package it.polito.tdp.artsmia.model;

public class Arco implements Comparable<Arco>{
	private Artist a1;
	private	Artist a2;
	private Integer peso;
	/**
	 * @param a1
	 * @param a2
	 * @param peso
	 */
	public Arco(Artist a1, Artist a2, Integer peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	public Artist getA1() {
		return a1;
	}
	public void setA1(Artist a1) {
		this.a1 = a1;
	}
	public Artist getA2() {
		return a2;
	}
	public void setA2(Artist a2) {
		this.a2 = a2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Arco other) {
		return -(this.peso-other.peso);
	}
	@Override
	public String toString() {
		return "Artista 1:"+a1.getName()+", Artista 2: "+a2.getName()+", num. esposizioni comuni: "+peso;
	}
	
	
}
