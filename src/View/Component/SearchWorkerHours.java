package View.Component;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JLabel;
import org.apache.commons.lang3.Pair;

import DataBaseConnections.QueriesSearch;
import Model.PersonCompany;
import Model.tables.search.HoursPartTimeTable;
import Utility.Utility;

public class SearchWorkerHours extends Search {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4823710556368989815L;
	private final static List<PersonCompany> partTimes = QueriesSearch.selectAllPartTime();
	private Map<Pair<String, Date>, Integer> h;
	private JButton compute = Components.createButton("Calcola");
	private JLabel hoursLabel = Components.createLabel();

	public SearchWorkerHours() {
		super("Operaio Part-Time", partTimes.stream().map(i -> i.getName() + Utility.getSplit() + i.getLastName()));
		super.addInPaneSouth(compute, hoursLabel);

		compute.addActionListener(e -> {
			try {
				hoursLabel.setText("Ore Totali: "
						+ h.values().stream().collect(Collectors.summarizingInt(Integer::intValue)).getSum());
				hoursLabel.setVisible(true);
				((JButton) (e.getSource())).setEnabled(false);
			} catch (NullPointerException ex) {
				Components.errorPane(Components.getFieldNotSet(), this);
			}
		});
	}

	@Override
	protected void searchButton() {
		super.reset(compute, hoursLabel);
		h = null;
		h = QueriesSearch.searchHoursPartTime(PersonCompany.findPartTime(partTimes, super.getSelectedCombo()));
		// Table
		super.setJViewPointTable(new HoursPartTimeTable(h).createTable());
	}
}
