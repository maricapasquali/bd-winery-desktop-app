package com.winery.desktop.Model.builder;

import com.winery.desktop.Model.Cask;

public interface CaskBuilder {

	Cask build();
	
	CaskBuilder setIDBotte(int iDBotte);

	CaskBuilder setCapacity(double capacity);
	
	CaskBuilder setWinery(int winery);
}