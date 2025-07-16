package com.winery.Model;

import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.winery.Utility.Utility;

public class Cask {
	
	private int IDBotte;
	private double capacity;
	private int winery;
	
	public Cask() {
	}
	
	public Cask(final int IDBotte, final double capacity, final int winery) {
		super();
		this.IDBotte = IDBotte;
		this.capacity = capacity;
		this.winery = winery;
	}
	
	public int getIDBotte() {
		return IDBotte;
	}

	public void setIDBotte(final int iDBotte) {
		Utility.checkNumber(iDBotte);
		IDBotte = iDBotte;
	}
	
	public double getCapacity() {
		return capacity;
	}
	
	public void setCapacity(final double capacity) {
		Utility.checkNumber(capacity);
		this.capacity = capacity;
	}
	
	public int getWinery() {
		return winery;
	}
	
	public void setWinery(final int winery) {
		Utility.checkNumber(winery);
		this.winery = winery;
	}

	@Override
	public String toString() {
		return "Botte [IDBotte=" + IDBotte + ", capacity=" + capacity + ", winery=" + winery + "]";
	}	
	
	public static Cask find(final Collection<Cask> collection, final String id_capacity) {
		if(id_capacity==null){
			throw new NullPointerException();
		}
		final String[] caskSelected = id_capacity.split(Pattern.quote(Utility.getSplit()));
		return collection.stream().filter(c -> c.getIDBotte()==Integer.parseInt(caskSelected[0])).collect(Collectors.toList()).get(0);
	}
	
	public String string() {
		return this.getIDBotte() + Utility.getSplit() + this.getCapacity();
	}

}
