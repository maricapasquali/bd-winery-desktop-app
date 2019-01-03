package View.Component;

import java.util.List;

import DataBaseConnections.QueriesPartTime;
import DataBaseConnections.QueriesSearch;
import Model.Grape;
import Model.Product;
import Model.tables.search.WineTable;

public class SearchWineInfo extends Search {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3426972667288867169L;

	public SearchWineInfo() {
		super("Vino", QueriesPartTime.listOfGrapes().stream().map(Grape::getName));

	}

	@Override
	protected void searchButton() {
		final List<Product> w = QueriesSearch.searchWineInfo(super.getSelectedCombo());
		// Table
		super.setJViewPointTable(new WineTable(w).createTable());
	}

}
