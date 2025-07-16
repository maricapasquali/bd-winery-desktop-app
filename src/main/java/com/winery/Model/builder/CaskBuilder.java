package com.winery.Model.builder;

import com.winery.Model.Cask;

public interface CaskBuilder {

	Cask build();
	
	CaskBuilder setIDBotte(int iDBotte);

	CaskBuilder setCapacity(double capacity);
	
	CaskBuilder setWinery(int winery);
}