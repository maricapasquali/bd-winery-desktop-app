package View.Component;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JLabel;
import DataBaseConnections.QueriesEmployee;
import DataBaseConnections.QueriesSearch;
import Model.Buying;
import Model.Client;
import Model.tables.search.BuyTable;
import Utility.Utility;

public class SearchClientBuying extends Search {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3490299764708216598L;
	private final static List<Client> clients = QueriesEmployee.listOfClients();
	private List<Buying> b;

	private JButton compute = Components.createButton("Calcola");
	private JLabel priceLabel = Components.createLabel();

	public SearchClientBuying() {
		super("Cliente", clients.stream().map(c -> c.getName() + Utility.getSplit() + c.getLastName()));
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
		b = QueriesSearch.searchBuyingClient(Client.find(clients, super.getSelectedCombo()));
		// Table
		super.setJViewPointTable(new BuyTable(b).createTable());
	}
}
