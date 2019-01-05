package View.Panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import com.toedter.calendar.JDateChooser;

import Controller.Controller;
import DataBaseConnections.QueriesEmployee;
import Model.Buying;
import Model.Client;
import Model.PersonCompany;
import Model.Product;
import Model.builder.BuyingBuilderImpl;
import Model.builder.ClientBuilderImpl;
import Model.tables.BuyDetailsTable;
import Utility.Components;
import Utility.Utility;
import exception.InsertFailedException;
import exception.JustInsertException;
import exception.WineJustInsertInCart;
import exception.WineNotEnoughException;
import exception.WineNotInsertInCartException;

public class PanelsEmployee extends PanelsPartTime {

	private static JPanel paneBuying;
	private static Client client;
	private static JTextField tNameClient;
	private static JTextField tLastNameClient;
	private static JTextField tStreetClient;
	private static JSpinner tStreetNumberClient;
	private static JTextField tStreetCityClient;
	private static JTextField tPhoneClient;
	private static JButton addClient;
	private static JDateChooser tDateBuy;
	private static JComboBox<Object> tClient;
	private static JButton close;
	private static JSpinner tNumBottiglie;
	private static JSpinner tLitriDam;
	private static JComboBox<Object> tVino;
	private static JButton addBuy;
	private static JPanel details;
	private static JButton cancelBuy;

	private static JScrollPane cartTable = new JScrollPane();
	private static Buying buy;
	private static String PRICE_TOT;

	private static List<Product> wines = new ArrayList<>();

	private static final JPanel panelSales = Components.createPaneBorder("Acquisto");
	private static final JPanel paneClose = Components.createPaneBorder();
	private static final JLabel priceTot = Components.createLabel("Prezzo Totale : 0.0 €");

	// FUNZIONI PUBBLICHE
	public static JPanel createSales(final PersonCompany pers) {
		paneBuying = Components.createPaneBorder();
		// title
		paneBuying.add(Components.title("Vendite"), BorderLayout.NORTH);
		// Cliente
		paneBuying.add(operationPane(), BorderLayout.EAST);
		// Lista Vini Dettagli
		paneBuying.add(cartTable, BorderLayout.WEST);
		// Pane Sale
		panelSales.add(salePane(pers), BorderLayout.NORTH);
		paneBuying.add(panelSales);
		return paneBuying;
	}

	// FUNZIONI PRIVATE
	private static JPanel operationPane() {
		// Form center
		final JPanel pane = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();
		// Registra nuovo cliente
		constraints.gridx = 0;
		constraints.gridy = 0;
		final JPanel pClient = createClient();
		pClient.setVisible(false);
		addClient = Components.createButton("Registra Nuovo Cliente");
		addClient.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				// PANE ADD CLIENT
				pClient.setVisible(true);
				pane.revalidate();
				pane.repaint();
				((JButton) e.getSource()).setEnabled(false);
			});
		});
		pane.add(addClient, constraints);
		// Registra nuovo cliente
		constraints.gridx = 0;
		constraints.gridy++;
		pane.add(pClient, constraints);
		return pane;
	}

	private static JPanel createClient() {

		final JPanel pane = Components.createPaneBorder("Cliente");
		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();

		// Nome
		constraints.gridx = 0;
		constraints.gridy = 0;
		final JLabel nameClient = Components.createLabel("Nome ");
		tNameClient = Components.createTextField(Components.getMaxChar());
		Components.addInCenterPanel(pCenter, constraints, nameClient, tNameClient);

		// Cognome
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel lastName = Components.createLabel("Cognome ");
		tLastNameClient = Components.createTextField(Components.getMaxChar());
		Components.addInCenterPanel(pCenter, constraints, lastName, tLastNameClient);

		// Indirizzo
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel street = Components.createLabel("Indirizzo ");
		tStreetClient = Components.createTextField(Components.getMaxChar());
		Components.addInCenterPanel(pCenter, constraints, street, tStreetClient);

		// Numero civico
		constraints.gridx = 2;
		final JLabel streetNumber = Components.createLabel("N. ");
		pCenter.add(streetNumber, constraints);
		constraints.gridx = 3;
		tStreetNumberClient = Components.createSpinner();
		pCenter.add(tStreetNumberClient, constraints);

		// Città
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel streetCity = Components.createLabel("Città ");
		tStreetCityClient = Components.createTextField(Components.getMaxChar());
		Components.addInCenterPanel(pCenter, constraints, streetCity, tStreetCityClient);

		// Telefono
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel phone = Components.createLabel("Telefono ");
		tPhoneClient = Components.createTextField(Components.getMaxCharPhone());
		Components.addInCenterPanel(pCenter, constraints, phone, tPhoneClient);
		pane.add(pCenter, BorderLayout.CENTER);

		// Button inserisci
		final JPanel paneAdd = Components.createPaneFlow();
		final JButton add = Components.createButton("Inserisci");
		final JButton cancel = Components.createButton("Annulla");
		paneAdd.add(add);
		paneAdd.add(cancel);
		cancel.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				addClient.setEnabled(true);
				resetClient();
				pane.setVisible(false);
			});
		});

		add.addActionListener(e -> {
			boolean success = false;
			try {
				client = new ClientBuilderImpl().setName(tNameClient.getText()).setLastName(tLastNameClient.getText())
						.setStreet(tStreetClient.getText())
						.setStreetNumber(((Number) tStreetNumberClient.getValue()).intValue())
						.setStreetCity(tStreetCityClient.getText()).setPhoneNumber(tPhoneClient.getText()).build();

				success = QueriesEmployee.insertClient(client);
				if (success) {
					Components.infoPane("Inserimento del Cliente è andata a buon fine", pane);
					resetClient();
					Controller.getInstance().addClient(client);
					Utility.log(client + "\n" + Controller.getInstance().getListClients());
					tClient.addItem(client.string());
					tClient.revalidate();
					tClient.revalidate();
					client = null;
				}
			} catch (NullPointerException ex) {
				Components.errorPane(Components.getFieldNotSet(), pane);
			} catch (JustInsertException ex) {
				Components.errorPane(ex.getMessage(), pane);
			}
		});
		pane.add(paneAdd, BorderLayout.SOUTH);
		return pane;
	}

	private static void resetClient() {
		tStreetNumberClient.setValue(Components.getRESET_FIELD_NUMBER());
		Components.resetTextComponents(
				Arrays.asList(tNameClient, tLastNameClient, tStreetClient, tStreetCityClient, tPhoneClient));
	}

	private static JPanel salePane(final PersonCompany pers) {
		final JPanel pane = Components.createPaneBorder("Dati Preliminari");
		// Pane Sale
		final JPanel paneSale = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();

		// Data
		constraints.gridy = 0;
		constraints.gridx = 0;
		final JLabel dateBith = Components.createLabel("Data ");
		tDateBuy = Components.createDateField();
		Components.addInCenterPanel(paneSale, constraints, dateBith, tDateBuy);

		// Cliente
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel idCliente = Components.createLabel("Cliente ");

		tClient = Components.createComboBox();
		Components.addInCenterPanel(paneSale, constraints, idCliente, tClient,
				Controller.getInstance().getListClients().stream().map(Client::string));

		pane.add(paneSale, BorderLayout.CENTER);

		details = detailsSalePane();
		panelSales.add(details, BorderLayout.CENTER);
		details.setVisible(false);

		paneClose.add(priceTot, BorderLayout.WEST);
		panelSales.add(paneClose, BorderLayout.SOUTH);
		paneClose.setVisible(false);

		// Button Inserisci
		final JPanel paneAdd = Components.createPaneFlow();
		addBuy = Components.createButton("Avanti");
		cancelBuy = Components.createButton("Annulla");
		cancelBuy.setEnabled(false);
		
		addBuy.addActionListener(e -> {
			try {
				final String[] clientSelected = String.valueOf(tClient.getSelectedItem())
						.split(Pattern.quote(Utility.getSplit()));
				buy = new BuyingBuilderImpl()
						.setIdClient(Client.find(Controller.getInstance().getListClients(), clientSelected[0],
								clientSelected[1], clientSelected[2]).getID())
						.setIdCompany(pers.getID()).setDateBuying(Utility.dateSql(tDateBuy.getDate())).build();
				Utility.log(buy);

				SwingUtilities.invokeLater(() -> {
					// Pane Details
					details.setVisible(true);
					cancelBuy.setEnabled(true);
					// Prezzo Totale
					paneClose.setVisible(true);
					((JButton) e.getSource()).setEnabled(false);
					tDateBuy.setEnabled(false);
					tClient.setEnabled(false);
				});
				
				try {
					wines = QueriesEmployee.listOfWines();
					wines.stream().map(Product::string).forEach(w -> tVino.addItem(w));
				} catch (NullPointerException ex) {
				}
				
			} catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
				Components.errorPane(Components.getFieldNotSet(), pane);
			}
		});

		cancelBuy.addActionListener(e -> resetBuying());

		paneAdd.add(addBuy);
		paneAdd.add(cancelBuy);
		pane.add(paneAdd, BorderLayout.SOUTH);

		return pane;
	}

	private static JPanel detailsSalePane() {
		final JPanel pane = Components.createPaneBorder("Dettagli");

		final JPanel pDet = Components.createPaneGridBag();
		final GridBagConstraints consDet = Components.createGridBagConstraints();
		// Vino
		consDet.gridy = 0;
		consDet.gridx = 0;
		final JLabel idVino = Components.createLabel("Vino ");
		tVino = Components.createComboBox();
		Components.addInCenterPanel(pDet, consDet, idVino, tVino);

		// Num Bottiglie
		consDet.gridy++;
		consDet.gridx = 0;
		final JLabel numBottiglie = Components.createLabel("N. Bottiglie (0.75 Lt) ");
		tNumBottiglie = Components.createSpinner();
		Components.addInCenterPanel(pDet, consDet, numBottiglie, tNumBottiglie);

		// Litri Damigliana
		consDet.gridy++;
		consDet.gridx = 0;
		final JLabel litriDam = Components.createLabel("Litri (Vino Sfuso) ");
		tLitriDam = Components.createSpinnerDouble();
		Components.addInCenterPanel(pDet, consDet, litriDam, tLitriDam);

		pane.add(pDet, BorderLayout.CENTER);
		// Button Add and close acquisto
		final JPanel addPane = Components.createPaneFlow();
		final JButton add = Components.createButton("Aggiungi");
		addPane.add(add);

		final JButton cancel = Components.createButton("Cancella");
		addPane.add(cancel);
		pane.add(addPane, BorderLayout.SOUTH);

		close = Components.createButton("Chiudi Acquisto");
		close.setVisible(false);
		paneClose.add(close, BorderLayout.EAST);

		add.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				try {
					buy.addInCart(Product.find(wines, tVino.getSelectedItem().toString()),
							((Number) tNumBottiglie.getValue()).intValue(),
							((Number) tLitriDam.getValue()).doubleValue());
					Utility.log("cart = " + buy.getCart());

					SwingUtilities.invokeLater(() -> {
						repaintTableBuy();
						repaintPrice(buy.getPriceTot());
						// Chiusura Acqusito
						if (!close.isVisible()) {
							close.setVisible(!close.isVisible());
						}
					});
					Utility.log("Aggiunto vino nel carello");
				} catch (NullPointerException ex) {
					Components.errorPane(Components.getFieldNotSet(), pane);
				} catch (WineJustInsertInCart ex1) {
					Components.errorPane(ex1.getMessage(), pane);
				} catch (WineNotEnoughException e1) {
					Components.errorPane(e1.getMessage(), pane);
				}
			});
		});

		cancel.addActionListener(e -> {
		
			try {
				buy.removeToCart(Product.find(wines, tVino.getSelectedItem().toString()));
				Utility.log("cart = " + buy.getCart());
				SwingUtilities.invokeLater(() -> {
					repaintPrice(buy.getPriceTot());
					repaintTableBuy();
				});
				Utility.log("Cancellato vino dal carello");
			} catch (WineNotInsertInCartException e1) {
				Components.errorPane(e1.getMessage(), pane);
			} catch (NullPointerException ex) {
				Components.errorPane(Components.getFieldNotSet(), pane);
			}
		});

		close.addActionListener(e -> {
			try {
				QueriesEmployee.insertBuy(buy);
				QueriesEmployee.updateQuantityWine(buy);
				resetBuying();
				Components.infoPane("Inserimento dell'intero Acquisto è andato a buon fine", pane);
			} catch (InsertFailedException e1) {
				Components.errorPane("Acquisto non è stato inserito! ", pane);
			}
		});
		return pane;
	}

	private static void repaintPrice(final double piceTot) {
		PRICE_TOT = "Prezzo Totale : " + piceTot + " €";
		priceTot.setText(PRICE_TOT);
		priceTot.revalidate();
		priceTot.repaint();
	}

	private static void repaintTableBuy() {
		cartTable.getViewport().add(new BuyDetailsTable(buy).createTable());
		cartTable.revalidate();
		cartTable.repaint();
	}

	private static void resetBuying() {	
		SwingUtilities.invokeLater(() -> {
			// Pane Details
			details.setVisible(false);
			paneClose.setVisible(false);
			addBuy.setEnabled(true);
			cancelBuy.setEnabled(false);
			tDateBuy.setEnabled(true);
			tClient.setEnabled(true);
			repaintPrice(0.0);
			cartTable.getViewport().removeAll();
			cartTable.revalidate();
			cartTable.repaint();
			tClient.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
			tVino.removeAllItems();
			tVino.addItem(null);
			tVino.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
			tVino.revalidate();
			tVino.repaint();
			close.setVisible(false);
			Components.resetNumberJSpinner(Arrays.asList(tNumBottiglie, tLitriDam));
		});
		Utility.log("Reset Acquisto");
	}
}
