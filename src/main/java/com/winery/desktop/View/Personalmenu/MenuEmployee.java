package com.winery.desktop.View.Personalmenu;

import javax.swing.JScrollPane;

import com.winery.desktop.Model.PersonCompany;
import com.winery.desktop.View.Panels.PanelsEmployee;

public class MenuEmployee extends PersonalMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4576103043632915218L;
		
	public MenuEmployee(final PersonCompany p) {
		super(p);					
		super.getTabbedPane().addTab(super.phaseProduction,  new JScrollPane(PanelsEmployee.createProductions()));
		super.getTabbedPane().addTab(super.sales,  new JScrollPane(PanelsEmployee.createSales(p)));
		super.getTabbedPane().addTab(super.search,  new JScrollPane(PanelsEmployee.createSearch(p)));				
	}
}