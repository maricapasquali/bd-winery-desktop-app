package com.winery.Exception;

public class JustInsertException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4596558218944233264L;

	private String name;
	public JustInsertException(final String n) {
		name = n;
	}
	
	@Override
	public String getMessage() {
		return name + " è già stata/o inserita/o.";
	}
}
