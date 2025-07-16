package com.winery.View.Panels;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import org.apache.commons.lang3.Pair;

import com.winery.DataBaseConnections.QueriesSearch;
import com.winery.Model.Product;
import com.winery.Model.enumeration.PhaseProductionWine;
import com.winery.Utility.Components;
import com.winery.Utility.Utility;
import com.winery.View.tables.search.ProductsTable;

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
