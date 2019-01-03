package Model.builder;

import Model.PhaseProduction;
import Model.Product;
import Model.enumeration.TypeProduct;

public class ProductBuilderImpl implements ProductBuilder {

	private Product product;
	
	public ProductBuilderImpl() {
		product = new Product();
	}
	
	@Override
	public Product build() {
		return product;
	}

	@Override
	public ProductBuilder setPhaseProd(PhaseProduction phaseProduction) {
		product.setPhaseProduction(phaseProduction);
		return this;
	}

	@Override
	public ProductBuilder setName(TypeProduct name) {
		product.setName(name);
		return this;
	}

	@Override
	public ProductBuilder setQuantity(double quantity) {
		product.setQuantity(quantity);
		return this;
	}

	@Override
	public ProductBuilder setQuantityActual(double quantityActual) {
		product.setQuantityActual(quantityActual);
		return this;
	}

	@Override
	public ProductBuilder setCask(Integer cask) {
		product.setCask(cask);
		return this;
	}

}
