package com.winery.desktop.Exception;

public class NotInSystemException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2046206196119694531L;

	public NotInSystemException() {
	}

	@Override
	public String getMessage() {
		return "ACCESSO NEGATO: Non sei nel sistema";
	}

}
