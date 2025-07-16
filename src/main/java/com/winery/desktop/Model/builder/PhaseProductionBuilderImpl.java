package com.winery.desktop.Model.builder;

import java.sql.Date;

import com.winery.desktop.Model.Grape;
import com.winery.desktop.Model.PhaseProduction;
import com.winery.desktop.Model.enumeration.PhaseProductionWine;

public class PhaseProductionBuilderImpl implements PhaseProductionBuilder {

	private PhaseProduction pp;
	
	public PhaseProductionBuilderImpl() {
		pp = new PhaseProduction();
	}
	
	@Override
	public PhaseProduction build() {
		return pp;
	}

	@Override
	public PhaseProductionBuilder setId(long id) {
		pp.setId(id);
		return this;
	}

	@Override
	public PhaseProductionBuilder setPpw(PhaseProductionWine ppw) {
		pp.setPpw(ppw);
		return this;
	}

	@Override
	public PhaseProductionBuilder setDate(Date date) {
		pp.setDate(date);
		return this;
	}

	@Override
	public PhaseProductionBuilder setGrape(Grape grape) {
		pp.setGrape(grape);
		return this;
	}

	@Override
	public PhaseProductionBuilder setQuantity(double quantity) {
		pp.setQuantity(quantity);
		return this;
	}
	
}
