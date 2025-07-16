package com.winery.desktop.Utility;

import java.util.Observable;
import java.util.Observer;
import java.util.function.BiConsumer;

import javax.swing.JComboBox;

public class ComboBoxObserver extends JComboBox<Object> implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4411552319092459984L;
	private BiConsumer<Observable, Object>  consumer;
	
	public ComboBoxObserver() {
	}
	
	@Override
	public void update(Observable o, Object arg) {
		try {
			consumer.accept(o, arg);	
		}catch(ClassCastException ex) {
			
		}
	}
	
	public void setEventChange(final BiConsumer<Observable, Object> consEvent) {
		consumer = consEvent;
	}
}
