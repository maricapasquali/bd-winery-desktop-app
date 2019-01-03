package Model.builder;

import Model.PhaseProduction;
import Model.Product;
import Model.enumeration.TypeProduct;

public interface ProductBuilder {

	Product build();

	ProductBuilder setPhaseProd(PhaseProduction idPhase);

	ProductBuilder setName(TypeProduct name);

	ProductBuilder setQuantity(double quantity);

	ProductBuilder setQuantityActual(double quantityActual);

	ProductBuilder setCask(Integer cask);

}
