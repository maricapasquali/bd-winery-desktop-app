package com.winery.Exception;

public class InsertFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6532574779366347542L;

	public InsertFailedException() {
	}

	@Override
	public String getMessage() {
		return "Inserimento fallito";
	}

}
