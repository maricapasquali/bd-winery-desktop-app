package com.winery.Model.builder;

import com.winery.Model.Grape;

public interface GrapeBuilder {

	Grape build();

	GrapeBuilder setName(String name);

	GrapeBuilder setType(String type);

	GrapeBuilder setPriceBottle(double priceBottle);

	GrapeBuilder setPriceLiter(double priceLiter);

}
