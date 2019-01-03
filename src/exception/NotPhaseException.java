package exception;

public class NotPhaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6625112975605667734L;

	private String nameGrape;
	
	public NotPhaseException() {
	}
	
	public NotPhaseException(final String name) {
		nameGrape=name;
	}

	@Override
	public String getMessage() {
		return "Nessuna Fase per " + nameGrape;
	}
}
