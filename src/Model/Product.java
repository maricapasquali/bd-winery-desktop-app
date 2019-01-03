package Model;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import Model.enumeration.TypeProduct;
import Utility.Utility;

public class Product {
		
	private PhaseProduction phaseProduction;
	private TypeProduct name;
	private double quantity;
	private double quantityActual;
	private Optional<Integer> cask;
	
	public Product() {		
	}
		
	public PhaseProduction getPhaseProduction() {
		return phaseProduction;
	}

	public void setPhaseProduction(PhaseProduction phaseProduction) {
		this.phaseProduction = phaseProduction;
	}
	
	public TypeProduct getName() {
		return name;
	}

	public void setName(TypeProduct name) {
		this.name = name;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		Utility.checkNumber(quantity);
		this.quantity = quantity;
	}
	
	public double getQuantityActual() {
		return quantityActual;
	}

	public void setQuantityActual(double quantityActual) {
		this.quantityActual = quantityActual;
	}

	public Optional<Integer> getCask() {
		return cask;
	}

	public void setCask(Integer cask) {
		this.cask = Optional.ofNullable(cask);	
	}

	@Override
	public String toString() {
		return "Product [phaseProd=" + phaseProduction + ", name=" + name + ", quantity=" + quantity + ", quantityActual="
				+ quantityActual + ", cask=" + cask + "]";
	}
	
	public String string() {
		return phaseProduction.getGrape().getName() + Utility.getSplit() + Utility.dataPart(phaseProduction.getDate(), Calendar.YEAR);
	}

	public static Product find(final List<Product> wines, final String selectedItem) {
		if(selectedItem==null) {
			throw new NullPointerException();
		}
		final String[] wineSelected = selectedItem.split(Pattern.quote(Utility.getSplit()));
		return wines.stream().filter(w -> (w.getPhaseProduction().getGrape().getName().equals(wineSelected[0])
				&& Utility.dataPart(w.getPhaseProduction().getDate(), Calendar.YEAR) == Integer.parseInt(wineSelected[1])))
				.collect(Collectors.toList()).get(0);
	}
	
	public boolean isWine() {
		return name.equals(TypeProduct.VINO);
	}
	
	public boolean isStalks() {
		return name.equals(TypeProduct.RASPI);
	}
}
