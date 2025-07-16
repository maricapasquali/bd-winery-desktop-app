package com.winery.desktop.Model.builder;

import com.winery.desktop.Model.PhaseProduction;
import com.winery.desktop.Model.Product;
import com.winery.desktop.Model.enumeration.TypeProduct;

public interface ProductBuilder {

	Product build();

	ProductBuilder setPhaseProd(PhaseProduction idPhase);

	ProductBuilder setName(TypeProduct name);

	ProductBuilder setQuantity(double quantity);

	ProductBuilder setQuantityActual(double quantityActual);

	ProductBuilder setCask(Integer cask);

}
