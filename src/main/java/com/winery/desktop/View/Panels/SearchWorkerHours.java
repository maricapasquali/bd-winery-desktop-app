package com.winery.desktop.View.Panels;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.winery.desktop.Utility.Components;
import org.apache.commons.lang3.Pair;

import com.winery.desktop.Controller.Controller;
import com.winery.desktop.DataBaseConnections.QueriesSearch;
import com.winery.desktop.Model.PersonCompany;
import com.winery.desktop.Utility.Utility;
import com.winery.desktop.View.tables.search.HoursPartTimeTable;

public class SearchWorkerHours extends Search implements Observer  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4823710556368989815L;
	private final static List<PersonCompany> partTimes = Controller.getInstance().getListWorkers().stream()
			.filter(w -> w.isPartTime()).collect(Collectors.toList());
	
	private Map<Pair<String, Date>, Integer> h = new HashMap<>();
	private JButton compute = Components.createButton("Calcola");
	private JLabel hoursLabel = Components.createLabel();

	public SearchWorkerHours() {
		super("Operaio Part-Time", partTimes.stream().map(i -> i.getName() + Utility.getSplit() + i.getLastName()));
		super.addInPaneSouth(compute, hoursLabel);
		super.setJViewPointTable(new HoursPartTimeTable(h).createTable()) ;
		compute.addActionListener(e -> {
			hoursLabel.setText("Ore Totali: "
					+ h.values().stream().collect(Collectors.summarizingInt(Integer::intValue)).getSum());
			hoursLabel.setVisible(true);
			((JButton) (e.getSource())).setEnabled(false);

		});
	}

	@Override
	protected void searchButton() {

		try {
			h = QueriesSearch.searchHoursPartTime(PersonCompany.findPartTime(partTimes, super.getSelectedCombo()));
			Utility.check(h.isEmpty());
			super.reset(compute, hoursLabel);
			// Table
			super.setJViewPointTable(new HoursPartTimeTable(h).createTable());
		} catch (NullPointerException ex) {
			Components.errorPane("Operaio Part-time non ha ancora lavorato", this);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		try {		
			final PersonCompany w = (PersonCompany)arg;
			if(w.isPartTime()) {
				SwingUtilities.invokeLater(() -> {
					super.addItemComboBox(w.getName() + Utility.getSplit() + w.getLastName());
				});	
			}				
		} catch (ClassCastException ex) {
		}
	}
}
