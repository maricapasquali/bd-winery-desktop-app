package View.Component;

import java.awt.GridBagConstraints;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import org.apache.commons.lang3.Pair;

import DataBaseConnections.QueriesSearch;
import Model.Product;
import Model.enumeration.PhaseProductionWine;
import Model.tables.search.ProductsTable;

public class SearchProduct extends Search {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4279259935395656346L;
	private final JSpinner tYear = Components.createSpinner();

	public SearchProduct() {
		super("Fase", Stream.of(PhaseProductionWine.values()));

		GridBagConstraints constraints = getConstraints();
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel year = Components.createLabel("Anno");

		Components.addInCenterPanel(super.getPaneSearch(), constraints, year, tYear);
		super.getComboBox().removeKeyListener(super.getKeyL());
	}

	@Override
	protected void searchButton() {
		if (((Number) tYear.getValue()).intValue() == 0) {
			throw new NullPointerException();
		}
		final Map<Long, Pair<Product, Product>> p = QueriesSearch.searchProductsOfPhase(
				PhaseProductionWine.conversion(super.getSelectedCombo()), ((Number) tYear.getValue()).intValue());
		p.entrySet().stream().forEach(System.out::println);
		// Table
		super.setJViewPointTable(new ProductsTable(p).createTable());
	}
}
