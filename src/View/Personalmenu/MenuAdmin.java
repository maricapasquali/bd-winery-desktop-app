package View.Personalmenu;

import javax.swing.JScrollPane;

import Model.PersonCompany;
import View.Component.PanelsAdmin;

public class MenuAdmin extends PersonalMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4576103043632915218L;
	
	private final String assumption = "Assunzioni";
	private final String administration = "Amministrazione";
	
	public MenuAdmin(final PersonCompany p) {
		super(p);
		super.getTabbedPane().addTab(assumption, new JScrollPane(PanelsAdmin.createAssumption(p.getID())));
		super.getTabbedPane().addTab(super.phaseProduction, new JScrollPane(PanelsAdmin.createProductions()));
		super.getTabbedPane().addTab(super.sales, new JScrollPane(PanelsAdmin.createSales(p)));
		super.getTabbedPane().addTab(administration, new JScrollPane(PanelsAdmin.createAdministration()));
		super.getTabbedPane().addTab(super.search, new JScrollPane(PanelsAdmin.createSearch(p)));
	}

}
