package Model;

import java.sql.Date;
import java.util.Optional;

import Model.enumeration.PhaseProductionWine;
import Utility.Utility;

public class PhaseProduction {
	
	private long id;
	private PhaseProductionWine ppw;
	private Date date;
	private Grape grape;
	private Optional<Double> quantity;
	
	public PhaseProduction() {
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public PhaseProductionWine getPpw() {
		return ppw;
	}

	public void setPpw(PhaseProductionWine ppw) {
		Utility.check(ppw==null);
		this.ppw = ppw;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		Utility.check(date==null);
		this.date = date;
	}

	public Grape getGrape() {
		return grape;
	}

	public void setGrape(Grape grape) {
		Utility.check(grape==null);
		this.grape = grape;
	}
	
	public Optional<Double> getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = Optional.ofNullable(quantity);
	}	
	
	public boolean isCollection() {
		return this.ppw.equals(PhaseProductionWine.RACCOLTA);
	}

	@Override
	public String toString() {
		return "PhaseProduction [id=" + id + ", ppw=" + ppw + ", date=" + date + ", grape=" + grape + ", quantity="
				+ quantity + "]";
	}
	
}
