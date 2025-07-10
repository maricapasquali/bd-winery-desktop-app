package Model.builder;

import java.sql.Date;

import Model.Buying;

public class BuyingBuilderImpl implements BuyingBuilder {

	private Buying buy;

	public BuyingBuilderImpl() {
		buy = new Buying();
	}

	@Override
	public Buying build() {
		return buy;
	}

	@Override
	public BuyingBuilder setDateBuying(final Date dateBuying) {
		buy.setDateBuying(dateBuying);
		return this;
	}

	@Override
	public BuyingBuilder setIdClient(final Long idClient) {
		buy.setIdClient(idClient);
		return this;
	}

	@Override
	public BuyingBuilder setIdCompany(final Long idCompany) {
		buy.setIdCompany(idCompany);
		return this;
	}

	@Override
	public BuyingBuilder setPriceTot(final Double priceTot) {
		buy.setPriceTot(priceTot);
		return this;
	}

}
