package View.Panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import com.toedter.calendar.JDateChooser;

import Controller.Controller;
import DataBaseConnections.QueriesAdmin;
import Model.Cask;
import Model.Grape;
import Model.Harvester;
import Model.PersonCompany;
import Model.builder.CaskBuilderImpl;
import Model.builder.GrapeBuilderImpl;
import Model.builder.PersonCompanyBuilderImpl;
import Model.enumeration.TypeAccess;
import Model.enumeration.TypeGrape;
import Utility.Components;
import Utility.Utility;
import exception.InsertFailedException;
import exception.JustInsertException;

public class PanelsAdmin extends PanelsEmployee {

	private static JLabel rate;
	private static JSpinner tRate;
	private static JLabel price;
	private static JSpinner tPrice;

	private static Grape grape;
	private static Cask cask;
	private static Harvester harve;
	private static PersonCompany personCompany;

	// FUNZIONI PUBBLICHE
	@SuppressWarnings("unchecked")
	public static JPanel createAssumption(final long idOwner) {
		final JPanel pane = Components.createPaneBorder();

		// title
		pane.add(Components.title("Operaio"), BorderLayout.NORTH);

		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();

		// Nome
		constraints.gridx = 0;
		constraints.gridy = 0;
		final JLabel name = Components.createLabel("Nome ");
		final JTextField tName = Components.createTextField(Components.getMaxChar());
		Components.addInCenterPanel(pCenter, constraints, name, tName);

		// Cognome
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel lastName = Components.createLabel("Cognome ");
		final JTextField tLastName = Components.createTextField(Components.getMaxChar());
		Components.addInCenterPanel(pCenter, constraints, lastName, tLastName);

		// Data Nascita
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel dateBith = Components.createLabel("Data di Nascita ");
		final JDateChooser tDateBirth = Components.createDateField();
		Components.addInCenterPanel(pCenter, constraints, dateBith, tDateBirth);

		// Indirizzo
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel street = Components.createLabel("Indirizzo ");
		final JTextField tStreet = Components.createTextField(Components.getMaxChar());
		Components.addInCenterPanel(pCenter, constraints, street, tStreet);

		// Numero civico
		constraints.gridx = 2;
		final JLabel streetNumber = Components.createLabel("N. ");
		pCenter.add(streetNumber, constraints);
		constraints.gridx = 3;
		final JSpinner tStreetNumber = Components.createSpinner();
		pCenter.add(tStreetNumber, constraints);

		// Città
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel streetCity = Components.createLabel("Città ");
		final JTextField tStreetCity = Components.createTextField(Components.getMaxChar());
		Components.addInCenterPanel(pCenter, constraints, streetCity, tStreetCity);

		// Telefono
		constraints.gridy++;
		constraints.gridx = 0;

		final JLabel phone = Components.createLabel("Telefono ");
		final JTextField tPhone = Components.createTextField(Components.getMaxCharPhone());
		Components.addInCenterPanel(pCenter, constraints, phone, tPhone);

		// Data Assunzione
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel dateAssumption = Components.createLabel("Data di Assunzione ");
		final JDateChooser tDateAssumption = Components.createDateField();
		Components.addInCenterPanel(pCenter, constraints, dateAssumption, tDateAssumption);

		// Tipo
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel type = Components.createLabel("Tipo ");
		final JComboBox<Object> tType = Components.createComboBox();
		Components.addInCenterPanel(pCenter, constraints, type, tType, TypeAccess.workers().stream());

		// Stipendio
		constraints.gridx = 0;
		constraints.gridy++;
		final JLabel salary = Components.createLabel("Stipendio (€) ");
		final JSpinner tSalary = Components.createSpinnerDouble();
		Components.addInCenterPanel(pCenter, constraints, salary, tSalary);
		Components.setVisibleComponents(Arrays.asList(salary, tSalary), false);

		// se dipendente : stipendio
		tType.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				try {
					switch ((TypeAccess) ((JComboBox<Object>) e.getSource()).getSelectedItem()) {
					case DIPENDENTE:
						Components.setVisibleComponents(Arrays.asList(salary, tSalary), true);
						break;
					default:
						Components.setVisibleComponents(Arrays.asList(salary, tSalary), false);
						break;
					}
				
					pCenter.revalidate();
					pCenter.repaint();
				} catch (NullPointerException ex) {
					Components.setVisibleComponents(Arrays.asList(salary, tSalary), false);
				}
				tSalary.setValue(Components.getRESET_FIELD_NUMBER());
			});

		});
		pane.add(pCenter, BorderLayout.CENTER);

		// Button Assumi
		final JPanel paneAdd = Components.createPaneFlow();
		final JButton button = Components.createButton("Assumi");
		paneAdd.add(button);

		button.addActionListener(e -> {
			boolean success = false;
			try {
				personCompany = new PersonCompanyBuilderImpl().setName(tName.getText()).setLastName(tLastName.getText())
						.setStreet(tStreet.getText()).setStreetNumber(((Number) tStreetNumber.getValue()).intValue())
						.setStreetCity(tStreetCity.getText()).setPhoneNumber(tPhone.getText())
						.setDataBirth(Utility.dateSql(tDateBirth.getDate())).setType(tType.getSelectedItem().toString())
						.setMonthlySalary(((Number) tSalary.getValue()).doubleValue()).build();

				personCompany.setDateAssumption(Utility.dateSql(tDateAssumption.getDate()));

				success = QueriesAdmin.assumptionWorker(personCompany, idOwner);
				if (success) {
					Components.infoPane("Assunzione è andata a buon fine", pane);
					Controller.getInstance().addWorker(personCompany);
					Utility.log(personCompany + "\n" + Controller.getInstance().getListWorkers());
					SwingUtilities.invokeLater(() -> {
						Components.resetTextComponents(Arrays.asList(tName, tLastName, tStreet, tStreetCity, tPhone));
						Components.resetNumberJSpinner(Arrays.asList(tStreetNumber, tSalary));
						tDateBirth.setDate(null);
						tType.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
						Components.setVisibleComponents(Arrays.asList(salary, tSalary), false);
						tDateAssumption.setDate(null);
					});
				}
			} catch (NullPointerException ex) {
				Components.errorPane(Components.getFieldNotSet(), pane);
			} catch (InsertFailedException e1) {
				Components.errorPane(e1.getMessage(), pane);
			} catch (JustInsertException e1) {
				Components.errorPane(e1.getMessage(), pane);
			}
		});
		pane.add(paneAdd, BorderLayout.SOUTH);
		return pane;
	}

	public static JPanel createAdministration() {
		final JPanel pane = Components.createPaneBorder();

		// title
		pane.add(Components.title("Amministrazione"), BorderLayout.NORTH);
		// Form center
		final JPanel pCenter = Components.createPaneGridBag();

		final GridBagConstraints constraints = Components.createGridBagConstraints();
		// Uva
		constraints.gridy = 0;
		final JButton addGrape = Components.createButton("Aggiungi Uva");
		pCenter.add(addGrape, constraints);
		addGrape.addActionListener(e -> {
			((JButton) e.getSource()).setEnabled(false);
			SwingUtilities.invokeLater(() -> {
				constraints.gridy = 0;
				pCenter.add(grapesPane(), constraints);
				pCenter.remove(addGrape);
				pCenter.revalidate();
				pCenter.repaint();
			});
		});

		// Botte
		constraints.gridy++;
		final JButton addCask = Components.createButton("Aggiungi Botte");
		pCenter.add(addCask, constraints);
		addCask.addActionListener(e -> {
			((JButton) e.getSource()).setEnabled(false);
			SwingUtilities.invokeLater(() -> {
				constraints.gridy = 1;
				pCenter.add(caskPane(), constraints);
				pCenter.remove(addCask);
				pCenter.revalidate();
				pCenter.repaint();
			});
		});

		// Vendemmiatrice
		constraints.gridy++;
		final JButton addHarvester = Components.createButton("Aggiungi Vendemmiatrice");
		pCenter.add(addHarvester, constraints);
		addHarvester.addActionListener(e -> {
			((JButton) e.getSource()).setEnabled(false);
			SwingUtilities.invokeLater(() -> {
				constraints.gridy = 2;
				pCenter.add(harvesterPane(), constraints);
				pCenter.remove(addHarvester);
				pCenter.revalidate();
				pCenter.repaint();
			});
		});

		pane.add(pCenter, BorderLayout.CENTER);
		return pane;
	}

	// FUNZIONI PRIVATE
	private static JPanel grapesPane() {
		final JPanel pane = Components.createPaneBorder("Uva");

		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();

		// Nome
		constraints.gridx = 0;
		constraints.gridy = 0;

		final JLabel name = Components.createLabel("Nome ");
		final JTextField tName = Components.createTextField(Components.getMaxChar());
		Components.addInCenterPanel(pCenter, constraints, name, tName);

		// Tipologia
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel type = Components.createLabel("Tipologia ");
		final JComboBox<Object> tType = Components.createComboBox();
		Components.addInCenterPanel(pCenter, constraints, type, tType, Stream.of(TypeGrape.values()));

		// Prezzo Damigiana al Litro
		constraints.gridx = 0;
		constraints.gridy++;
		final JLabel pricesDam = Components.createLabel("Prezzo al Litro ");
		final JSpinner tPricesDam = Components.createSpinnerDouble();
		Components.addInCenterPanel(pCenter, constraints, pricesDam, tPricesDam);

		// Prezzo Bottiglie
		constraints.gridx = 0;
		constraints.gridy++;
		final JLabel pricesBott = Components.createLabel("Prezzo a Bottiglie ");
		final JSpinner tPricesBott = Components.createSpinnerDouble();
		Components.addInCenterPanel(pCenter, constraints, pricesBott, tPricesBott);

		pane.add(pCenter, BorderLayout.CENTER);

		// Button aggiungi
		final JPanel paneAdd = Components.createPaneFlow();
		final JButton button = Components.createButton("Inserisci");
		paneAdd.add(button);
		button.addActionListener(e -> {
			boolean success = false;
			try {
				grape = new GrapeBuilderImpl().setName(tName.getText()).setType(tType.getSelectedItem().toString())
						.setPriceLiter(((Number) tPricesDam.getValue()).doubleValue())
						.setPriceBottle(((Number) tPricesBott.getValue()).doubleValue()).build();

				success = QueriesAdmin.addGrapes(grape);
				if (success) {
					Components.infoPane("Inserimento dell' Uva ha avuto successo", pane);
					Controller.getInstance().addGrape(grape);
					Utility.log(grape + "\n" + Controller.getInstance().getListGrapes());
					SwingUtilities.invokeLater(() -> {
						tName.setText(Components.getRESET_FIELD_TEXT());
						tType.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
						Components.resetNumberJSpinner(Arrays.asList(tPricesDam, tPricesBott));
					});
				}
			} catch (NullPointerException ex) {
				Components.errorPane(Components.getFieldNotSet(), pane);
			} catch (JustInsertException e1) {
				Components.errorPane(e1.getMessage(), pane);
			}
		});
		pane.add(paneAdd, BorderLayout.SOUTH);
		return pane;
	}

	private static JPanel caskPane() {
		final JPanel pane = Components.createPaneBorder("Botte");

		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();

		// Nome
		constraints.gridx = 0;
		constraints.gridy = 0;
		final JLabel id = Components.createLabel("Numero ");
		final JSpinner tId = Components.createSpinner();
		Components.addInCenterPanel(pCenter, constraints, id, tId);

		// Capacità
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel capacity = Components.createLabel("Capacità (Lt) ");
		final JSpinner tCapacity = Components.createSpinnerDouble();
		Components.addInCenterPanel(pCenter, constraints, capacity, tCapacity);

		// Cantina
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel cellar = Components.createLabel("Cantina ");
		final JSpinner tCellar = Components.createSpinner();
		Components.addInCenterPanel(pCenter, constraints, cellar, tCellar);

		pane.add(pCenter, BorderLayout.CENTER);

		// Button aggiungi
		final JPanel paneAdd = Components.createPaneFlow();
		final JButton button = Components.createButton("Inserisci");
		paneAdd.add(button);
		button.addActionListener(e -> {
			boolean success = false;
			try {
				cask = new CaskBuilderImpl().setIDBotte(((Number) tId.getValue()).intValue())
						.setCapacity(((Number) tCapacity.getValue()).doubleValue())
						.setWinery(((Number) tCellar.getValue()).intValue()).build();
			
				success = QueriesAdmin.addCask(cask);
				if (success) {
					Components.infoPane("Inserimento della Botte ha avuto successo", pane);
					Controller.getInstance().addCask(cask);
					Utility.log(cask + "\n" + Controller.getInstance().getListCasks());
					Components.resetNumberJSpinner(Arrays.asList(tId, tCapacity, tCellar));
				}
			} catch (NullPointerException ex) {
				Components.errorPane(Components.getFieldNotSet(), pane);
			} catch (JustInsertException e1) {
				Components.errorPane(e1.getMessage(), pane);
			}
		});
		pane.add(paneAdd, BorderLayout.SOUTH);
		return pane;
	}

	private static JPanel harvesterPane() {

		final JPanel pane = Components.createPaneBorder("Vendemmiatrice");

		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();

		// Marca
		constraints.gridx = 0;
		constraints.gridy = 0;

		final JLabel brand = Components.createLabel("Marca ");
		final JTextField tbrand = Components.createTextField(Components.getMaxChar());
		Components.addInCenterPanel(pCenter, constraints, brand, tbrand);

		// Modello
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel template = Components.createLabel("Modello ");
		final JTextField tTemplate = Components.createTextField(Components.getMaxChar());
		Components.addInCenterPanel(pCenter, constraints, template, tTemplate);

		// in prestito si o no
		constraints.gridy++;
		constraints.gridx = 0;
		rate = Components.createLabel("Tarifa Oraria ");
		tRate = Components.createSpinnerDouble();
		price = Components.createLabel("Costo ");
		tPrice = Components.createSpinnerDouble();
		setVisibleRateOrPrice(false);

		final JCheckBox borrow = Components.createCheckBox("In prestito");
		borrow.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				setVisibleRateOrPrice(((JCheckBox) e.getSource()).isSelected() ? true : false);
				pCenter.revalidate();
				pCenter.repaint();
			});
		});
		pCenter.add(borrow, constraints);

		// Tariffa oraria [0-1]
		constraints.gridy++;
		constraints.gridx = 0;
		Components.addInCenterPanel(pCenter, constraints, rate, tRate);

		// Costo [0-1]
		constraints.gridy++;
		constraints.gridx = 0;
		Components.addInCenterPanel(pCenter, constraints, price, tPrice);

		pane.add(pCenter, BorderLayout.CENTER);

		// Button aggiungi
		final JPanel paneAdd = Components.createPaneFlow();
		final JButton button = Components.createButton("Inserisci");

		button.addActionListener(e -> {
			boolean success = false;
			try {
				harve = new Harvester(tbrand.getText(), tTemplate.getText(), ((Number) tPrice.getValue()).doubleValue(),
						((Number) tRate.getValue()).doubleValue());

				success = QueriesAdmin.addHarvester(harve);
				if (success) {
					Components.infoPane("Inserimento della Vendemmiatrice ha avuto successo", pane);
					Controller.getInstance().addHarve(harve);
					Utility.log(harve + "\n" + Controller.getInstance().getListHarves());
					Components.resetTextComponents(Arrays.asList(tbrand, tTemplate));
					Components.resetNumberJSpinner(Arrays.asList(tPrice, tRate));
				}
			} catch (NullPointerException ex) {
				Components.errorPane(Components.getFieldNotSet(), pane);
			} catch (JustInsertException e1) {
				Components.errorPane(e1.getMessage(), pane);
			}
		});
		pane.add(paneAdd, BorderLayout.SOUTH);
		paneAdd.add(button);
		return pane;
	}

	private static void setVisibleRateOrPrice(final boolean cond) {
		rate.setVisible(cond);
		tRate.setVisible(cond);
		price.setVisible(!cond);
		tPrice.setVisible(!cond);
	}

}
