package com.winery.Model.builder;

import com.winery.Model.Grape;

public class GrapeBuilderImpl implements GrapeBuilder {
	
	private Grape grape;
	
	public GrapeBuilderImpl() {
		grape = new Grape();
	}

	@Override
	public Grape build() {
		return grape;
	}

	@Override
	public GrapeBuilder setName(final String name) {
		grape.setName(name);
		return this;
	}

	@Override
	public GrapeBuilder setType(final String type) {
		grape.setType(type);
		return this;
	}

	@Override
	public GrapeBuilder setPriceBottle(final double priceBottle) {
		grape.setPriceBottle(priceBottle);
		return this;
	}

	@Override
	public GrapeBuilder setPriceLiter(final double priceLiter) {
		grape.setPriceLiter(priceLiter);
		return this;
	}

}
