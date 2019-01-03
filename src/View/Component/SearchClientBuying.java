package View.Component;

import java.util.List;

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
	
	public SearchClientBuying() {	
		super("Cliente", clients.stream().map(c -> c.getName()+Utility.getSplit()+c.getLastName()));
	}

	@Override
	protected void searchButton() {

		final List<Buying> b = QueriesSearch.searchBuyingClient(Client.find(clients, super.getSelectedCombo()));
		// Table
		super.setJViewPointTable(new BuyTable(b).createTable());
	}

}
