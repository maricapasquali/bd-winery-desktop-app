package View.Component;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import com.toedter.calendar.JDateChooser;

import DataBaseConnections.QueriesPartTime;
import Model.Cask;
import Model.Grape;
import Model.Group;
import Model.Harvester;
import Model.PersonCompany;
import Model.PhaseProduction;
import Model.Product;
import Model.builder.GrapeBuilderImpl;
import Model.builder.PhaseProductionBuilderImpl;
import Model.builder.ProductBuilderImpl;
import Model.enumeration.PhaseProductionWine;
import Model.enumeration.TypeGrape;
import Model.enumeration.TypeProduct;
import Model.tables.GroupsTable;
import Utility.Utility;
import exception.JustInsertException;
import exception.NotPhaseException;
import exception.UsedHarvesterException;

public class PanelsPartTime {

	private final static JScrollPane paneSelect = new JScrollPane();
	private final static JPanel paneSelectProductins = Components.createPaneBorder();
	// Panel Phase
	private static JLabel namePhase;
	private static JComboBox<Object> tNamePhase;
	private static JLabel datePhase;
	private static JDateChooser tDatePhase;
	private static JLabel grapesLabel;
	private static JComboBox<Object> tGrapesPhase;
	private static JLabel quantityGrape;
	private static JSpinner tQuantityGrape;
	private static JButton addPhase;
	private static PhaseProduction pp;
	// Panel Product
	private static JComboBox<Object> tPhase;
	private static JComboBox<Object> tGrapesProduct;
	private static JComboBox<Object> tProduct;
	private static JLabel quantity;
	private static String q;
	private static JSpinner tQuantity;
	private static JLabel cask;
	private static JComboBox<Object> tCask;

	private static JButton addProduct;
	private static JPanel productselect;
	private static JLabel dateChooseToInsert;
	private static JComboBox<Object> tDateChooseToInsert;
	private static JButton ahead;
	private static Product p;
	private static List<Cask> casks;
	private static Map<String, Cask> caskForFeccia;
	private static List<PhaseProduction> listPhase;

	// Panel Group
	private static JScrollPane tableGroup = new JScrollPane();
	private static JPanel paneAddGroup;
	private static JComboBox<Object> tDateChooseToInsertGroup;
	private static List<PhaseProduction> listPhaseGroup;
	private static PhaseProduction phase;
	private static JButton aheadGroup;
	private static JLabel phaseGroup;
	private static JComboBox<Object> tPhaseGroup;
	private static JLabel workerLabel;
	private static JComboBox<Object> tWorker;
	private static JLabel hours;
	private static JSpinner tHours;
	private static JLabel harvester;
	private static JComboBox<Object> tHarvester;
	private static JButton addWorker;
	private static JButton reset;
	private static JButton close;
	private static Group group = new Group();
	private static List<PersonCompany> workers;
	private static List<Harvester> harve;

	// Model for all
	private static List<Grape> grapes;
	private static boolean setOneWorker = false;

	// FUNZIONI PUBBLICHE
	public static JSplitPane createProductions() {
		final JSplitPane slipPane = new JSplitPane();
		setSlipPane();
		slipPane.setLeftComponent(paneSelect);
		slipPane.setRightComponent(paneSelectProductins);
		return slipPane;
	}

	public static JPanel createSearch() {
		final JPanel contentPane = Components.createPaneBorder();
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		contentPane.add(tabbedPane);
		tabbedPane.addTab("Informazioni sul Vino", new SearchWineInfo());
		tabbedPane.addTab("Acquisti cliente", new SearchClientBuying());
		tabbedPane.addTab("Part-Time Ore", new SearchWorkerHours());
		tabbedPane.addTab("Prodotti per Fase", new SearchProduct());
		return contentPane;
	}
	
	// FUNZIONI PRIVATE
	private static void setSlipPane() {

		final JPanel paneGrid = Components.createPaneGridBag();
		final GridBagConstraints constraint = Components.createGridBagConstraints();
		final JButton phase = Components.createButton("Fase");
		final JPanel panePhase = createPhase();
		final JButton prodot = Components.createButton("Prodotto");
		final JPanel paneProdot = createProduct();
		final JButton group = Components.createButton("Gruppo");
		final JPanel paneGroup = createGroup();

		paneSelectProductins.add(panePhase);
		phase.addActionListener(e -> select(panePhase, Arrays.asList(paneProdot, paneGroup)));
		prodot.addActionListener(e -> select(paneProdot, Arrays.asList(panePhase, paneGroup)));
		group.addActionListener(e -> select(paneGroup, Arrays.asList(panePhase, paneProdot)));

		constraint.gridy = 0;
		paneGrid.add(phase, constraint);
		constraint.gridy++;
		paneGrid.add(prodot, constraint);
		constraint.gridy++;
		paneGrid.add(group, constraint);

		paneSelect.getViewport().add(paneGrid);
	}

	private static void select(final JPanel paneSelected, final List<JPanel> panesToRemove) {
		SwingUtilities.invokeLater(() -> {
			panesToRemove.stream().forEach(p -> paneSelectProductins.remove(p));
			paneSelectProductins.add(paneSelected);
			paneSelectProductins.revalidate();
			paneSelectProductins.repaint();
		});
	}

	private static JPanel createPhase() {
		final JPanel pane = Components.createPaneBorder();
		pane.add(Components.title("Fase"), BorderLayout.NORTH);
		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();

		// NomeFASE
		constraints.gridx = 0;
		constraints.gridy = 0;
		namePhase = Components.createLabel("Nome ");
		tNamePhase = Components.createComboBox();
		Components.addInCenterPanel(pCenter, constraints, namePhase, tNamePhase,
				Stream.of(PhaseProductionWine.values()));

		// Data Produzione
		constraints.gridy++;
		constraints.gridx = 0;
		datePhase = Components.createLabel("Data ");
		tDatePhase = Components.createDateField();
		Components.addInCenterPanel(pCenter, constraints, datePhase, tDatePhase);

		// Uva
		constraints.gridy++;
		constraints.gridx = 0;
		grapesLabel = Components.createLabel("Uva ");
		tGrapesPhase = Components.createComboBox();

		try {
			grapes = QueriesPartTime.listOfGrapes();
			grapes.forEach(System.out::println);
		} catch (NullPointerException ex) {
		}
		Components.addInCenterPanel(pCenter, constraints, grapesLabel, tGrapesPhase,
				grapes.stream().map(Grape::getName));

		// Quantità Uva Raccolta
		constraints.gridy++;
		constraints.gridx = 0;
		quantityGrape = Components.createLabel("Quantità (Kg) ");
		tQuantityGrape = Components.createSpinnerDouble();
		Components.addInCenterPanel(pCenter, constraints, quantityGrape, tQuantityGrape);
		Components.setVisibleComponents(Arrays.asList(quantityGrape, tQuantityGrape), false);

		pane.add(pCenter, BorderLayout.CENTER);
		// Button Aggiungi
		final JPanel paneAdd = Components.createPaneFlow();
		addPhase = Components.createButton("Aggiungi");
		pane.add(paneAdd, BorderLayout.SOUTH);
		paneAdd.add(addPhase);

		setActionPhase(pane);
		return pane;
	}

	@SuppressWarnings("unchecked")
	private static void setActionPhase(final JPanel pane) {
		final List<JComponent> listQuantity = Arrays.asList(quantityGrape, tQuantityGrape);

		tNamePhase.addActionListener(e -> {
			try {
				switch ((PhaseProductionWine) ((JComboBox<Object>) e.getSource()).getSelectedItem()) {
				case RACCOLTA:
					Components.setVisibleComponents(listQuantity, true);
					break;
				default:
					Components.setVisibleComponents(listQuantity, false);
					break;
				}
			} catch (NullPointerException ex) {
				Components.setVisibleComponents(listQuantity, false);
			}
			Components.resetNumberJSpinner(
					listQuantity.stream().filter(i -> i instanceof JSpinner).collect(Collectors.toList()));
		});

		addPhase.addActionListener(e -> {
			boolean success = false;
			try {
				pp = new PhaseProductionBuilderImpl().setPpw((PhaseProductionWine) tNamePhase.getSelectedItem())
						.setDate(Utility.dateSql(tDatePhase.getDate()))
						.setGrape(Grape.find(grapes, tGrapesPhase.getSelectedItem().toString())).build();

				if (pp.isCollection()) {
					pp.setQuantity(((Number) tQuantityGrape.getValue()).doubleValue());
				}
				Utility.log(pp);
				success = QueriesPartTime.insertPhaseProduction(pp);
				if (success) {
					Components.infoPane("Inserimento " + pp.getPpw() + " è andata a buon fine", pane);
					tNamePhase.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
					tDatePhase.setDate(null);
					tGrapesPhase.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
					Components.setVisibleComponents(listQuantity, false);
					Components.resetNumberJSpinner(
							listQuantity.stream().filter(i -> i instanceof JSpinner).collect(Collectors.toList()));
				}
			} catch (NullPointerException ex) {
				Components.errorPane(Components.getFieldNotSet(), pane);
			}
		});
	}

	private static JPanel createProduct() {
		final JPanel pane = Components.createPaneBorder();
		pane.add(Components.title("Prodotto"), BorderLayout.NORTH);
		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();

		// Fase e Uva
		constraints.gridx = 0;
		constraints.gridy = 0;
		pCenter.add(researchPhase(), constraints);

		constraints.gridy++;
		// Prodotto
		productselect = addQuantityOfProduct();
		productselect.setVisible(false);
		pCenter.add(productselect, constraints);

		pane.add(pCenter);
		return pane;
	}

	@SuppressWarnings("unchecked")
	private static JPanel researchPhase() {
		final JPanel paneResearchPhase = Components.createPaneBorder();

		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();
		// Fase
		constraints.gridx = 0;
		constraints.gridy = 0;
		final JLabel phase = Components.createLabel("Fase ");
		tPhase = Components.createComboBox();
		Components.addInCenterPanel(pCenter, constraints, phase, tPhase, PhaseProductionWine.withProducts().stream());

		tPhase.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				tProduct.removeAllItems();
				tProduct.addItem(null);
				try {
					TypeProduct.productOf((PhaseProductionWine) ((JComboBox<Object>) e.getSource()).getSelectedItem())
							.forEach(p -> tProduct.addItem(p));
				} catch (NullPointerException ex) {
				}
				tProduct.revalidate();
				tProduct.repaint();
			});
		});
		// Uva
		constraints.gridy++;
		constraints.gridx = 0;
		grapesLabel = Components.createLabel("Uva ");
		tGrapesProduct = Components.createComboBox();
		Components.addInCenterPanel(pCenter, constraints, grapesLabel, tGrapesProduct,
				grapes.stream().map(Grape::getName));
		paneResearchPhase.add(pCenter, BorderLayout.CENTER);

		// Prodotto
		constraints.gridy++;
		constraints.gridx = 0;
		final JLabel product = Components.createLabel("Prodotto ");
		tProduct = Components.createComboBox();
		Components.addInCenterPanel(pCenter, constraints, product, tProduct);

		// Button Aggiungi
		final JPanel paneAdd = Components.createPaneFlow();
		ahead = Components.createButton("Avanti");
		ahead.addActionListener(e -> {
			try {
				listPhase = QueriesPartTime
						.selectPhase(
								new PhaseProductionBuilderImpl().setPpw((PhaseProductionWine) tPhase.getSelectedItem())
										.setGrape(new GrapeBuilderImpl()
												.setName(tGrapesProduct.getSelectedItem().toString()).build())
										.build(),
								tProduct.getSelectedItem().toString());
				Utility.log(listPhase);
				
				if (listPhase != null) {

					if (!listPhase.isEmpty()) {
						SwingUtilities.invokeLater(() -> {
							productselect.setVisible(true);
							tPhase.setEnabled(false);
							tGrapesProduct.setEnabled(false);

							ahead.setVisible(false);
							listPhase.stream().map(PhaseProduction::getDate)
									.forEach(d -> tDateChooseToInsert.addItem(d.toString()));

							final List<JComponent> caskComponents = Arrays.asList(cask, tCask);

							Components.setVisibleComponents(Arrays.asList(quantity, tQuantity), true);
							final TypeProduct selectProduct = (TypeProduct) tProduct.getSelectedItem();
							switch (selectProduct) {
							case RASPI:
								Components.setVisibleComponents(caskComponents, false);
								break;
							case VINACCIA:
								Components.setVisibleComponents(caskComponents, false);
								break;
							default:
								Components.setVisibleComponents(caskComponents, true);
								break;
							}
							updateCaskGui(selectProduct);
							switch (selectProduct) {
							case VNF:
								q = " (Lt) ";
								break;
							case VINO:
								q = " (Lt) ";
								break;
							default:
								q = " (Kg) ";
								break;
							}
							quantity.setText("Quantità " + q);
							tProduct.setEditable(false);
							tProduct.revalidate();
							tProduct.repaint();
						});
					}
				}
			} catch (NullPointerException e1) {
				Components.errorPane(Components.getFieldNotSet(), paneResearchPhase);
			} catch (NotPhaseException e1) {
				Components.errorPane(e1.getMessage(), paneResearchPhase);
			} catch (JustInsertException e1) {
				Components.errorPane(e1.getMessage(), paneResearchPhase);
			}

		});

		paneAdd.add(ahead);
		paneResearchPhase.add(paneAdd, BorderLayout.SOUTH);
		return paneResearchPhase;
	}

	private static JPanel addQuantityOfProduct() {
		final JPanel pane = Components.createPaneBorder();

		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();

		// Date
		constraints.gridy = 0;
		constraints.gridx = 0;
		dateChooseToInsert = Components.createLabel("Data ");

		tDateChooseToInsert = Components.createComboBox();
		Components.addInCenterPanel(pCenter, constraints, dateChooseToInsert, tDateChooseToInsert);

		// Quantità
		constraints.gridy++;
		constraints.gridx = 0;
		quantity = Components.createLabel();
		tQuantity = Components.createSpinnerDouble();
		Components.addInCenterPanel(pCenter, constraints, quantity, tQuantity);

		// Botte (escluso RASPI e VINACCIA)
		constraints.gridy++;
		constraints.gridx = 0;
		cask = Components.createLabel("Botte");
		tCask = Components.createComboBox();
		try {
			casks = QueriesPartTime.selectAvailableCasks();
			casks.forEach(System.out::println);

			caskForFeccia = QueriesPartTime.selectCasksForFeccia();
			caskForFeccia.entrySet().forEach(System.out::println);
		} catch (NullPointerException ex) {
		}

		Components.addInCenterPanel(pCenter, constraints, cask, tCask, casks.stream().map(Cask::string));

		pane.add(pCenter, BorderLayout.CENTER);

		// Button Aggiungi
		final JPanel paneAdd = Components.createPaneFlow();
		addProduct = Components.createButton("Aggiungi");

		addProduct.addActionListener(e -> {
			boolean success = false;
			try {

				final TypeProduct tp = (TypeProduct) tProduct.getSelectedItem();
				p = new ProductBuilderImpl().setName(tp).setQuantity(((Number) tQuantity.getValue()).doubleValue())
						.setPhaseProd(listPhase.stream().filter(
								pw -> pw.getGrape().getName().equals(tGrapesProduct.getSelectedItem().toString()) && pw
										.getDate().toString().equals(tDateChooseToInsert.getSelectedItem().toString()))
								.findFirst().get())
						.build();

				if (TypeProduct.withCask().contains(tp)) {
					Cask c = null;
					if (TypeProduct.FECCIA.equals(tp)) {
						c = Cask.find(caskForFeccia.values(), tCask.getSelectedItem().toString());
					} else {
						c = Cask.find(casks, tCask.getSelectedItem().toString());
						casks.remove(c);
					}
					p.setCask(c.getIDBotte());
				}

				if (tp.equals(TypeProduct.VINO))
					p.setQuantityActual(((Number) tQuantity.getValue()).doubleValue());

				Utility.log(p);
				success = QueriesPartTime.insertProduct(p);
				if (success) {
					Components.infoPane("Inserimento " + p.getName() + " è andata a buon fine",
							pCenter);
					resetProduct();
				}
			} catch (NumberFormatException ex) {
				Components.errorPane("Inserire Anno", pCenter);
			} catch (NullPointerException ex) {
				Components.errorPane(Components.getFieldNotSet(), pCenter);
			}
		});
		paneAdd.add(addProduct);

		final JButton cancel = Components.createButton("Annulla");
		cancel.addActionListener(e -> {
			resetProduct();
		});
		paneAdd.add(cancel);
		pane.add(paneAdd, BorderLayout.SOUTH);

		return pane;
	}

	private static void resetProduct() {
		SwingUtilities.invokeLater(() -> {
			tPhase.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
			tGrapesProduct.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
			tPhase.setEnabled(true);
			tGrapesProduct.setEnabled(true);
			tDateChooseToInsert.removeAllItems();
			tDateChooseToInsert.addItem(null);
			tQuantity.setValue(Components.getRESET_FIELD_NUMBER());
			resetCask(casks.stream().map(Cask::string));
			productselect.setVisible(false);
			ahead.setVisible(true);
		});
		p = null;
		listPhase = null;
	}

	private static void updateCaskGui(final TypeProduct p) {
		if (caskForFeccia != null) {
			switch (p) {
			case FECCIA:
				if (tGrapesProduct.getSelectedItem() != null) {
					final TypeGrape typeGrapeSelect = Grape.find(grapes, tGrapesProduct.getSelectedItem().toString())
							.getType();
					grapes.forEach(System.out::println);
					Utility.log(typeGrapeSelect);
					if (typeGrapeSelect != null) {
						resetCask(Arrays.asList(caskForFeccia.get(typeGrapeSelect.toString())).stream()
								.map(Cask::string));
						Arrays.asList(caskForFeccia.get(typeGrapeSelect.toString())).forEach(System.out::println);
					}
				}
				break;
			default:
				resetCask(casks.stream().map(Cask::string));
				break;
			}
		}
	}

	private static void resetCask(final Stream<String> listCask) {
		tCask.removeAllItems();
		tCask.addItem(null);
		listCask.forEach(c -> tCask.addItem(c));
		tCask.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
	}
	
	@SuppressWarnings("unchecked")
	private static JPanel researchPhaseForGroup() {
		final JPanel pane = Components.createPaneBorder();
		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();
		// Fase
		constraints.gridx = 0;
		constraints.gridy = 0;
		phaseGroup = Components.createLabel("Fase ");
		tPhaseGroup = Components.createComboBox();
		Components.addInCenterPanel(pCenter, constraints, phaseGroup, tPhaseGroup,
				Stream.of(PhaseProductionWine.values()));

		tPhaseGroup.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				try {
					final List<JComponent> harverComponents = Arrays.asList(harvester, tHarvester);
					switch ((PhaseProductionWine) ((JComboBox<Object>) e.getSource()).getSelectedItem()) {
					case RACCOLTA:
						Components.setVisibleComponents(harverComponents, true);
						break;
					default:
						Components.setVisibleComponents(harverComponents, false);
						break;
					}
					pCenter.revalidate();
					pCenter.repaint();
				} catch (NullPointerException ec) {
				}
			});
		});
		pane.add(pCenter, BorderLayout.CENTER);
		
		// Button Aggiungi
		final JPanel paneAhead = Components.createPaneFlow();
		aheadGroup = Components.createButton("Avanti");
		paneAhead.add(aheadGroup);
		
		aheadGroup.addActionListener(e->{		
			try {
				listPhaseGroup = QueriesPartTime.selectPhaseGroup(tPhaseGroup.getSelectedItem().toString());
				SwingUtilities.invokeLater(() -> {
					paneAddGroup.setVisible(true);
					((JButton)(e.getSource())).setVisible(false);
					tPhaseGroup.setEnabled(false);
					listPhaseGroup.stream().map(PhaseProduction::getDate).forEach(d -> tDateChooseToInsertGroup.addItem(d.toString()));	
				});
				Utility.log(listPhaseGroup);
			} catch (NullPointerException e3) {
				Components.errorPane(Components.getFieldNotSet(), pane);
			} catch (JustInsertException e1) {
				Components.errorPane(e1.getMessage(), pane);
			}
		});
		pane.add(paneAhead, BorderLayout.SOUTH);
		return pane;
	}

	@SuppressWarnings("unchecked")
	private static JPanel addGroups() {
		final JPanel pane = Components.createPaneBorder();
		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();
		// Date
		constraints.gridx = 0;
		constraints.gridy = 0;
		final JLabel dateChooseToInsertGroup = Components.createLabel("Data ");
		tDateChooseToInsertGroup = Components.createComboBox();
		Components.addInCenterPanel(pCenter, constraints, dateChooseToInsertGroup, tDateChooseToInsertGroup);
	
		// Operaio
		constraints.gridx = 0;
		constraints.gridy++;
		workerLabel = Components.createLabel("Operaio ");
		tWorker = Components.createComboBox();
		try {
			workers = QueriesPartTime.selectAllWorker();
			workers.forEach(w -> w.string());
		} catch (NullPointerException ec) {
		}
		Components.addInCenterPanel(pCenter, constraints, workerLabel, tWorker,
				workers.stream().map(PersonCompany::string));

		tWorker.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				try {
					final List<JComponent> hoursComponents = Arrays.asList(hours, tHours);
					switch (PersonCompany
							.find(workers, ((JComboBox<Object>) e.getSource()).getSelectedItem().toString())
							.getType()) {
					case PART_TIME:
						Components.setVisibleComponents(hoursComponents, true);
						break;
					default:
						Components.setVisibleComponents(hoursComponents, false);
						break;
					}
					pCenter.revalidate();
					pCenter.repaint();
				} catch (NullPointerException ec) {
				}
			});
		});
		// Se Operaio selezionato è tipo PART - TIME -> Ore Lavoro
		constraints.gridy++;
		constraints.gridx = 0;
		hours = Components.createLabel("Ore Lavoro ");
		tHours = Components.createSpinner();
		Components.addInCenterPanel(pCenter, constraints, hours, tHours);
		Components.setVisibleComponents(Arrays.asList(hours, tHours), false);

		// vendemmiatrice
		constraints.gridy++;
		constraints.gridx = 0;
		harvester = Components.createLabel("Vendemmiatrice ");
		tHarvester = Components.createComboBox();
		try {
			harve = QueriesPartTime.selectAllHarvesters();
			harve.forEach(System.out::println);
		} catch (NullPointerException ec) {
		}
		Components.addInCenterPanel(pCenter, constraints, harvester, tHarvester, harve.stream().map(Harvester::string));
		Components.setVisibleComponents(Arrays.asList(harvester, tHarvester), false);

		pane.add(pCenter, BorderLayout.CENTER);

		// Button Aggiungi
		final JPanel paneAdd = Components.createPaneFlow();

		addWorker = Components.createButton("Aggiungi");
		
		addWorker.addActionListener(e -> {
			
			try {
				if(setOneWorker) {
					throw new UsedHarvesterException();
				}
				if (!group.isSetIdPhase()) {
					phase = listPhaseGroup.stream().filter(p -> p.getPpw().toString().equals(tPhaseGroup.getSelectedItem().toString()) && 
							tDateChooseToInsertGroup.getSelectedItem().toString().equals(p.getDate().toString())).findFirst().get();
					
					group.setPhaseProduction(phase);	
					group.setIdHarvester(Harvester.find(harve, tHarvester.getSelectedItem()));
					if(!group.getIdHarvester().isPresent()) {
						Components.setVisibleComponents(Arrays.asList(harvester, tHarvester), false);
					}else {
						setOneWorker  = true;
					}
				}
				
				try {
					group.addWorkerAndHoursWork(PersonCompany.find(workers, tWorker.getSelectedItem().toString()),
							((Number) tHours.getValue()).intValue());
					Group.String();
					SwingUtilities.invokeLater(() -> {
						tWorker.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
						tHours.setValue(Components.getRESET_FIELD_NUMBER());					
						Components.setVisibleComponents(Arrays.asList(hours, tHours), false);
						tHarvester.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
						tableGroup.getViewport().add(new GroupsTable(group).createTable());
						pane.revalidate();
						pane.repaint();
					});
				} catch (JustInsertException e1) {
					Components.errorPane(e1.getMessage(), pane);
				} 
			} catch (NullPointerException e3) {
				Components.errorPane(Components.getFieldNotSet(), pane);
			} catch (UsedHarvesterException e1) {
				Components.errorPane(e1.getMessage(), pane);
			}
		});
		paneAdd.add(addWorker);
		reset = Components.createButton("Reset");
		reset.addActionListener(e -> resetGroup(pCenter));
		paneAdd.add(reset);
		
		close = Components.createButton("Chiudi");
		close.addActionListener(e -> {
			try {
				QueriesPartTime.insertGroup(group);
				resetGroup(pCenter);
			} catch (NullPointerException ex) {
				Components.errorPane("Non è stato inserito nessun Operaio", pane);
			}
		});
		paneAdd.add(close);
		pane.add(paneAdd, BorderLayout.SOUTH);
		
		return pane;
	}

	private static JPanel createGroup() {

		final JPanel pane = Components.createPaneBorder();
		pane.add(Components.title("Gruppo"), BorderLayout.NORTH);

		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();

		// Fase
		constraints.gridx = 0;
		constraints.gridy = 0;
		final JPanel panePhase = researchPhaseForGroup();
		pCenter.add(panePhase, constraints);
		constraints.gridy++;
		paneAddGroup = addGroups();
		paneAddGroup.setVisible(false);
		pCenter.add(paneAddGroup, constraints);	
		pane.add(pCenter, BorderLayout.CENTER);
	
		pane.add(tableGroup, BorderLayout.EAST);

		return pane;
	}	

	private static void resetGroup(final JPanel pCenter) {
		group.reset();
		SwingUtilities.invokeLater(() -> {
			aheadGroup.setVisible(true);
			tPhaseGroup.setEnabled(true);
			tPhaseGroup.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
			paneAddGroup.setVisible(false);
			tDateChooseToInsertGroup.removeAllItems();
			tDateChooseToInsertGroup.addItem(null);
			tWorker.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
			tHours.setValue(Components.getRESET_FIELD_NUMBER());
			tHarvester.setSelectedIndex(Components.getRESET_FIELD_NUMBER());
			tableGroup.getViewport().removeAll();
			tableGroup.revalidate();
			tableGroup.repaint();
			pCenter.revalidate();
			pCenter.repaint();
		});
	}

}
