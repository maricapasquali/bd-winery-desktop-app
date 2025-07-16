package com.winery.desktop.Exception;

public class UsedHarvesterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3911322667130126626L;

	@Override 
	public String getMessage() {
		return "Non puoi inserire altri Operai";
	}
}
