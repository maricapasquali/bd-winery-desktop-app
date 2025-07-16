package com.winery.Model.builder;

import com.winery.Model.PhaseProduction;
import com.winery.Model.Product;
import com.winery.Model.enumeration.TypeProduct;

public interface ProductBuilder {

	Product build();

	ProductBuilder setPhaseProd(PhaseProduction idPhase);

	ProductBuilder setName(TypeProduct name);

	ProductBuilder setQuantity(double quantity);

	ProductBuilder setQuantityActual(double quantityActual);

	ProductBuilder setCask(Integer cask);

}
