package Model.builder;

import java.sql.Date;

import Model.Buying;

public interface BuyingBuilder {

	Buying build();

	BuyingBuilder setDateBuying(Date dateBuying);

	BuyingBuilder setIdClient(Long idClient);

	BuyingBuilder setIdCompany(Long idCompany);
	
	BuyingBuilder setPriceTot(Double priceTot);
}
