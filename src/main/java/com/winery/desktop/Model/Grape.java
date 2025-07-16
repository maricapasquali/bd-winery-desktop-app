package com.winery.desktop.Model;

import java.util.List;
import java.util.stream.Collectors;

import com.winery.desktop.Model.enumeration.TypeGrape;
import com.winery.desktop.Utility.Utility;

public class Grape {

	private String name;
	private TypeGrape type;
	private double priceBottle;
	private double priceLiter;

	public Grape() {
	}
	
	public Grape(final String name, final TypeGrape type, final double priceBottle, final double priceLiter) {
		super();
		this.name = name;
		this.type = type;
		this.priceBottle = priceBottle;
		this.priceLiter = priceLiter;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		Utility.checkLength(name);
		this.name = name;
	}

	public TypeGrape getType() {
		return type;
	}

	public void setType(final String type) {
		Utility.checkLength(type);
		this.type = type.equals(TypeGrape.BIANCO.toString())? TypeGrape.BIANCO: TypeGrape.ROSSO;
	}

	public double getPriceBottle() {
		return priceBottle;
	}

	public void setPriceBottle(final double priceBottle) {
		Utility.checkNumber(priceBottle);
		this.priceBottle = priceBottle;
	}

	public double getPriceLiter() {
		return priceLiter;
	}

	public void setPriceLiter(final double priceLiter) {
		Utility.checkNumber(priceLiter);
		this.priceLiter = priceLiter;
	}

	@Override
	public String toString() {
		return "Uva [name = " + name + ", type = " + type + ", price bottle = " + priceBottle + ", price liter = "
				+ priceLiter + "]";
	}

	public static Grape find(final List<Grape> grapes,final String name) {
		return grapes.stream().filter(g -> g.getName().equals(name)).collect(Collectors.toList()).get(0);
	}
}
