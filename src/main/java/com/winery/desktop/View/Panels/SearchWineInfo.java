package com.winery.desktop.View.Panels;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;

import com.winery.desktop.Controller.Controller;
import com.winery.desktop.DataBaseConnections.QueriesSearch;
import com.winery.desktop.Model.Grape;
import com.winery.desktop.Model.Product;
import com.winery.desktop.Utility.Components;
import com.winery.desktop.Utility.Utility;
import com.winery.desktop.View.tables.search.WineTable;

public class SearchWineInfo extends Search implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3426972667288867169L;

	private List<Product> w = new ArrayList<>();
	public SearchWineInfo() {
		super("Vino", Controller.getInstance().getListGrapes().stream().map(Grape::getName));
		
		super.setJViewPointTable(new WineTable(w).createTable());
	}

	@Override
	protected void searchButton() {
		try {
			w = QueriesSearch.searchWineInfo(super.getSelectedCombo());
			Utility.check(w.isEmpty());
			// Table
			super.setJViewPointTable(new WineTable(w).createTable());
		}catch (NullPointerException ex) {
			Components.errorPane(super.getSelectedCombo() + " non è stato inserito", this);
		}
		
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
