package exception;

public class NeverLoggedIn extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3700919133557346692L;
	
	public NeverLoggedIn() {
	}

	@Override
	public String getMessage() {
		return "Registrazione";
	}

}
