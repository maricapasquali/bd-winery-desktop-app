package exception;

public class WineJustInsertInCart extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3754298364073097146L;
	
	@Override
	public String getMessage() {
		return "Vino già inserito nel carrello";

	}

}
