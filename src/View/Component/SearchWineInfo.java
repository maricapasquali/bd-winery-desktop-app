package View.Component;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;

import Controller.Controller;
import DataBaseConnections.QueriesSearch;
import Model.Grape;
import Model.Product;
import Model.tables.search.WineTable;

public class SearchWineInfo extends Search implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3426972667288867169L;

	public SearchWineInfo() {
		super("Vino", Controller.getInstance().getListGrapes().stream().map(Grape::getName));
	}

	@Override
	protected void searchButton() {
		final List<Product> w = QueriesSearch.searchWineInfo(super.getSelectedCombo());
		// Table
		super.setJViewPointTable(new WineTable(w).createTable());
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		SwingUtilities.invokeLater(() -> {
			try {
				super.addItemComboBox(((Grape) arg1).getName());
			} catch (ClassCastException ex) {
			}
		});
	}
}
