package com.winery.Model.search;

import java.sql.Date;
import java.util.Optional;

public class BuyingSearch {

	private Date dateBuying;
	private String wine;
	private Optional<Integer> num_bottle;
	private Optional<Double> num_liter;

	public BuyingSearch() {
	}

	public Date getDateBuying() {
		return dateBuying;
	}

	public void setDateBuying(Date dateBuying) {
		this.dateBuying = dateBuying;
	}

	public String getWine() {
		return wine;
	}

	public void setWine(String wine) {
		this.wine = wine;
	}

	public Optional<Integer> getNum_bottle() {
		return num_bottle;
	}

	public void setNum_bottle(Integer num_bottle) {
		this.num_bottle = Optional.ofNullable(num_bottle);
	}

	public Optional<Double> getNum_liter() {
		return num_liter;
	}

	public void setNum_liter(Double num_liter) {
		this.num_liter = Optional.ofNullable(num_liter);
	}

}
