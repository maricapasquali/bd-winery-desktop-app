package View.Component;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import Controller.Controller;
import DataBaseConnections.QueriesSearch;
import Model.Buying;
import Model.Client;
import Model.tables.search.BuyTable;
import Utility.Components;
import Utility.Utility;

public class SearchClientBuying extends Search implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3490299764708216598L;
	private List<Buying> b;

	private JButton compute = Components.createButton("Calcola");
	private JLabel priceLabel = Components.createLabel();

	public SearchClientBuying() {
		super("Cliente", Controller.getInstance().getListClients().stream()
				.map(c -> c.getName() + Utility.getSplit() + c.getLastName()));
		super.addInPaneSouth(compute, priceLabel);
		compute.addActionListener(e -> {
			try {
				priceLabel.setText("Prezzo globale: " + b.stream().map(Buying::getPriceTot)
						.collect(Collectors.summarizingDouble(Double::doubleValue)).getSum());
				priceLabel.setVisible(true);
				((JButton) (e.getSource())).setEnabled(false);
			} catch (NullPointerException ex) {
				Components.errorPane(Components.getFieldNotSet(), this);
			}
		});
		
		
		
	}

	@Override
	protected void searchButton() {
		super.reset(compute, priceLabel);
		b = null;
		b = QueriesSearch
				.searchBuyingClient(Client.find(Controller.getInstance().getListClients(), super.getSelectedCombo()));
		// Table
		super.setJViewPointTable(new BuyTable(b).createTable());
	}

	@Override
	public void update(Observable o, Object arg) {

		try {
			final Client c = (Client) arg;	
			SwingUtilities.invokeLater(() -> {
				super.addItemComboBox(c.getName() + Utility.getSplit() + c.getLastName());
			});
		} catch (ClassCastException ex) {
		}

	}
}
