package com.winery.desktop.Model.builder;

import java.sql.Date;
import com.winery.desktop.Model.Grape;
import com.winery.desktop.Model.PhaseProduction;
import com.winery.desktop.Model.enumeration.PhaseProductionWine;

public interface PhaseProductionBuilder {

	PhaseProduction build();

	PhaseProductionBuilder setId(long id);

	PhaseProductionBuilder setPpw(PhaseProductionWine ppw);

	PhaseProductionBuilder setDate(Date date);

	PhaseProductionBuilder setGrape(Grape grape);

	PhaseProductionBuilder setQuantity(double quantity);

}
