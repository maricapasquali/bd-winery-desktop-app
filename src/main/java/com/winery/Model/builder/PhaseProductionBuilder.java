package com.winery.Model.builder;

import java.sql.Date;
import com.winery.Model.Grape;
import com.winery.Model.PhaseProduction;
import com.winery.Model.enumeration.PhaseProductionWine;

public interface PhaseProductionBuilder {

	PhaseProduction build();

	PhaseProductionBuilder setId(long id);

	PhaseProductionBuilder setPpw(PhaseProductionWine ppw);

	PhaseProductionBuilder setDate(Date date);

	PhaseProductionBuilder setGrape(Grape grape);

	PhaseProductionBuilder setQuantity(double quantity);

}
