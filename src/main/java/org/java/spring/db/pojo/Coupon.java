package org.java.spring.db.pojo;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private LocalDate dataDiInzio;
	private LocalDate dataDiFine;
	private String titolo;
	
	@ManyToOne
	private Pizza pizza;
	public Coupon() {
	}
	public Coupon(String titolo, LocalDate dataDiInizio, LocalDate dataDiFine,Pizza pizza) {
		setTitolo(titolo);
		setDataDiInzio(dataDiInizio);
		setDataDiFine(dataDiFine);
		setPizza(pizza);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public LocalDate getDataDiInzio() {
		return dataDiInzio;
	}

	public void setDataDiInzio(LocalDate dataDiInzio) {
		this.dataDiInzio = dataDiInzio;
	}

	public LocalDate getDataDiFine() {
		return dataDiFine;
	}

	public void setDataDiFine(LocalDate dataDiFine) {
		this.dataDiFine = dataDiFine;
	}

	public Pizza getPizza() {
		return pizza;
	}
	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}
	
	@Override
	public String toString() {
		return "[" + getId() + "]" + getTitolo() + " - " + getDataDiInzio() + " - " + getDataDiFine();
	}
}
