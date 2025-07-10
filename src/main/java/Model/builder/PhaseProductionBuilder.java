package Model.builder;

import java.sql.Date;
import Model.Grape;
import Model.PhaseProduction;
import Model.enumeration.PhaseProductionWine;

public interface PhaseProductionBuilder {

	PhaseProduction build();

	PhaseProductionBuilder setId(long id);

	PhaseProductionBuilder setPpw(PhaseProductionWine ppw);

	PhaseProductionBuilder setDate(Date date);

	PhaseProductionBuilder setGrape(Grape grape);

	PhaseProductionBuilder setQuantity(double quantity);

}
