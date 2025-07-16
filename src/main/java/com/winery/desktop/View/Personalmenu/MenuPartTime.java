package com.winery.desktop.View.Personalmenu;

import javax.swing.JScrollPane;

import com.winery.desktop.Model.PersonCompany;
import com.winery.desktop.View.Panels.PanelsPartTime;

public class MenuPartTime extends PersonalMenu{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4576103043632915218L;
	
	public MenuPartTime(final PersonCompany p) {
		super(p);
		super.getTabbedPane().addTab(super.phaseProduction,  new JScrollPane(PanelsPartTime.createProductions()));
		super.getTabbedPane().addTab(super.search,  new JScrollPane(PanelsPartTime.createSearch(p)));	
	}

}