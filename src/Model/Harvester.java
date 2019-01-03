package Model;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import Utility.Utility;

public class Harvester {

	private Long IDVend;
	private String brand;
	private String model;
	private double cost;
	private double hourlyRate;
	
	public Harvester() {	
	}
	
	public Harvester(final String brand, final String model, final double cost, final double hourlyRate) {
		super();
		this.brand = brand;
		this.model = model;
		Utility.check(cost==0 && hourlyRate==0);
		this.cost = cost;
		this.hourlyRate = hourlyRate;
	}
	
	public Harvester(final Long id, final String brand, final String model) {
		super();
		this.IDVend = id;
		this.brand = brand;
		this.model = model;
	}

	public long getIDVend() {
		return IDVend;
	}

	public void setIDVend(final Long iDVend) {
		IDVend = iDVend;
	}

	public String getBrand() {		
		return brand;
	}

	public void setBrand(final String brand) {
		Utility.checkLength(brand);
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(final String model) {
		Utility.checkLength(model);
		this.model = model;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(final double cost) {
		this.cost = cost;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(final double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	@Override
	public String toString() {
		return "Vendemmiatrice [IDVend=" + IDVend + ", brand=" + brand + ", model=" + model + ", cost=" + this.getCost() 
				+ ", hourlyRate=" + this.getHourlyRate() + "]" ;
	}
	
	public String string() {
		return this.getIDVend() + Utility.getSplit() + this.getBrand() + Utility.getSplit() + this.getModel();
	}
	
	public boolean isBorrowed() {
		return getHourlyRate() > 0;
	}

	public static Long find(List<Harvester> harve, Object string) {
		if(string!=null) {
			final String[] workerSelected = String.valueOf(string).split(Pattern.quote(Utility.getSplit()));
			return harve.stream().filter(h -> h.getIDVend()==Long.parseLong(workerSelected[0])).collect(Collectors.toList()).get(0).getIDVend();
		}
		return null;
	}
}
