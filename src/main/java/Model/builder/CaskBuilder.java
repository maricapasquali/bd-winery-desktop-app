package Model.builder;

import Model.Cask;

public interface CaskBuilder {

	Cask build();
	
	CaskBuilder setIDBotte(int iDBotte);

	CaskBuilder setCapacity(double capacity);
	
	CaskBuilder setWinery(int winery);
}