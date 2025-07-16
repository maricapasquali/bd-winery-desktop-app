package com.winery.Exception;

public class WineNotEnoughException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8531719790804941905L;
	private double quantityMax ;
		
	public WineNotEnoughException(final double qMax) {
		quantityMax = qMax;
	}
	
	@Override
	public String getMessage() {
		return "Quantita di Vino inserita è troppa. Quantità massima è " + quantityMax + " Lt";

	}
}
