package com.winery.desktop.View.Panels;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import com.winery.desktop.DataBaseConnections.QueriesSearch;
import com.winery.desktop.Model.Product;
import com.winery.desktop.Model.enumeration.PhaseProductionWine;
import com.winery.desktop.Utility.Components;
import com.winery.desktop.Utility.Utility;
import com.winery.desktop.View.tables.search.ProductsTable;
import org.apache.commons.lang3.Pair;

public class SearchProduct extends Search {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4279259935395656346L;
	private final JSpinner tYear = Components.createSpinner();

	private Map<Long, Pair<Product, Product>> p = new HashMap<>();

	public SearchProduct() {
		super("Fase", Stream.of(PhaseProductionWine.values()));

		final GridBagConstraints constraints = getConstraints();
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel year = Components.createLabel("Anno");

		Components.addInCenterPanel(super.getPaneSearch(), constraints, year, tYear);
		super.getComboBox().removeKeyListener(super.getKeyL());
	}

	@Override
	protected void searchButton() {
		final int year = ((Number) tYear.getValue()).intValue();
		Utility.check(year == 0);
		try {
			final PhaseProductionWine phase = PhaseProductionWine.conversion(super.getSelectedCombo());
			p = QueriesSearch.searchProductsOfPhase(phase, year);
			p.entrySet().stream().forEach(System.out::println);
			super.setJViewPointTable(new ProductsTable(phase, p).createTable());
			Utility.check(p.isEmpty());
			// Table
		} catch (NullPointerException ex) {
			Components.errorPane(super.getSelectedCombo() + " del " + year + " non ha alcun prodotto", this);
		}
	}
}
