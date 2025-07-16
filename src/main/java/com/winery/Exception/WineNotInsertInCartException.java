package com.winery.Exception;

public class WineNotInsertInCartException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7822351893147329926L;

	@Override
	public String getMessage() {
		return "Vino non ancora inserito nel carrello";

	}
}
