package View.Personalmenu;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.JCheckBox;
//import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import DataBaseConnections.QueriesSystem;
import Model.PersonCompany;
import Utility.Components;
import Utility.Utility;

import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class PersonalMenu extends AbstractFrameDefault {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3719919684999929927L;

	private static final int WIDTH = 1200;
	private static final int HEIGHT = 800;

	private final String personalArea = "Area Personale";
	private final String displayDetails = "Visualizza Dettagli Personali";
	private final String info = "Informazioni";
	private final String id = "IDENTIFICATORE: ";
	private final String name = "NOME: ";
	private final String lastName = "COGNOME: ";
	private final String type = "GERARCHIA: ";
	private final String dateAssumption = "DATA DI ASSUNZIONE: ";
	private final String hoursWork = "ORE TOTALI DI LAVORO: ";
	private final String salary = "STIPENDIO MENSILE: ";
	private final String lastAccess = "ULTIMO ACCESSO: ";
	private final String details = "Dettagli Personali";
	private final String dateBirth = "DATA DI NASCITA: ";
	private final String street = "INDIRIZZO: ";
	private final String streetNumber = " N. ";
	private final String streetCity = "CITTÁ: ";
	private final String phoneNumber = "TELEFONO: ";
	
	protected final String phaseProduction = "Fasi Produzione";
	protected final String sales = "Vendite";
	protected final String search = "Ricerca";
	
	private JPanel contentPane;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private PersonCompany person;

	/**
	 * Create the frame.
	 */
	public PersonalMenu(final PersonCompany p) {
		super(WIDTH, HEIGHT);
		this.person = p;

		this.setTitle(p.getName());
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		super.getContentPane().add(contentPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		contentPane.add(tabbedPane);
		tabbedPane.addTab(personalArea, new JScrollPane(createPersonalArea()));
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public PersonCompany getPerson() {
		return person;
	}

	@Override
	public void closeAction() {
		if (JOptionPane.showConfirmDialog(this, EXIT_MESSAGE) == JOptionPane.YES_OPTION) {
			QueriesSystem.updateDateLogin(Utility.dateNow(), this.getPerson().getID());
			Utility.log("Aggiornamento data dell'ultimo login\nExit");
			System.exit(0);
		}
	}

	public JPanel createPersonalArea() {
		final JPanel pane = Components.createPaneBorder();
		// title
		pane.add(Components.title(personalArea), BorderLayout.NORTH);
		// Form center
		final JPanel pCenter = Components.createPaneGridBag();
		final GridBagConstraints constraints = Components.createGridBagConstraints();
		// Dettagli in ambito lavorativo
		constraints.gridy = 0;
		pCenter.add(createCompanyDetails(), constraints);

		// Dettagli Personali
		constraints.gridy++;
		final JCheckBox details = Components.createCheckBox(displayDetails);
		pCenter.add(details, constraints);

		constraints.gridy++;
		final JPanel pPersonalDetails = createPersonalDetails();
		pCenter.add(pPersonalDetails, constraints);

		details.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				if (pPersonalDetails.isVisible()) {
					pPersonalDetails.setVisible(false);
				} else {
					pPersonalDetails.setVisible(true);
				}
				pCenter.revalidate();
				pCenter.repaint();
			});

		});
		pane.add(pCenter, BorderLayout.CENTER);
		return pane;
	}

	protected JPanel createCompanyDetails() {

		final JPanel pCenter = Components.createPaneGridBag(info);
		final GridBagConstraints constraints = Components.createGridBagConstraints();

		// Identificatore
		constraints.gridy = 0;
		final JLabel id = Components.createLabel(this.id + this.getPerson().getID());
		pCenter.add(id, constraints);

		// Nome
		constraints.gridy++;
		pCenter.add(Components.createLabel(name + this.getPerson().getName()), constraints);

		// Cognome
		constraints.gridy++;
		pCenter.add(Components.createLabel(lastName + this.getPerson().getLastName()), constraints);

		// Tipo
		constraints.gridy++;
		pCenter.add(Components.createLabel(type + this.getPerson().getType()), constraints);

		if (!this.getPerson().isAdmin()) {
			// Data di Assunzione
			constraints.gridy++;
			pCenter.add(Components.createLabel(dateAssumption + this.getPerson().getDateAssumption()), constraints);
			
			// Varianti del TIPO
			constraints.gridy++;	
			if(this.getPerson().isEmployee()) {
				pCenter.add(Components.createLabel(salary + this.getPerson().getMonthlySalary().get() + " €"),constraints);
			}else {
				final Integer hours = QueriesSystem.selectAllHoursWork(this.getPerson().getID());
				pCenter.add(Components.createLabel(hoursWork + (hours==null?"":hours)), constraints);
			}
		}

		// Ultimo Accesso
		constraints.gridy++;
		pCenter.add(Components.createLabel(lastAccess + this.getPerson().getLastAccess()), constraints);

		return pCenter;
	}

	protected JPanel createPersonalDetails() {
		final JPanel pCenter = Components.createPaneGridBag(details);
		pCenter.setVisible(false);
		final GridBagConstraints constraints = Components.createGridBagConstraints();

		// Data Nascita
		constraints.gridy++;
		pCenter.add(Components.createLabel(dateBirth + this.getPerson().getDataBirth()), constraints);

		// Indirizzo
		constraints.gridy++;
		pCenter.add(
				Components.createLabel(
						street + this.getPerson().getStreet() + streetNumber + this.getPerson().getStreetNumber()),
				constraints);

		// Città
		constraints.gridy++;
		pCenter.add(Components.createLabel(streetCity + this.getPerson().getStreetCity()), constraints);

		// Telefono
		constraints.gridy++;
		pCenter.add(Components.createLabel(phoneNumber + this.getPerson().getPhoneNumber()), constraints);

		return pCenter;
	}

}
