package it.polito.tdp.extflightdelays.model;

public class Adiacenza {

	Airport a1;
	Airport a2;
	Integer peso;
	public Adiacenza(Airport a1, Airport a2, Integer peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	public Airport getA1() {
		return a1;
	}
	public void setA1(Airport a1) {
		this.a1 = a1;
	}
	public Airport getA2() {
		return a2;
	}
	public void setA2(Airport a2) {
		this.a2 = a2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return a2 + " - " + peso;
	}
	
	
}
