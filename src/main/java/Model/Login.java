package Model;

import java.sql.Date;
import java.util.Optional;

public class Login {

	private long idAziendale;
	private String passwordEncrypted;
	private Optional<Date> lastLogin = Optional.empty();

	public Login() {
	}

	public long getIdAziendale() {
		return idAziendale;
	}
	
	public void setIdAziendale(final long id) {
		this.idAziendale = id;
	}

	public String getPassword() {
		return passwordEncrypted;
	}

	public void setPassword(final String password) {
		this.passwordEncrypted = password;
	}

	public Optional<Date> getLastlogin() {
		return Optional.ofNullable(lastLogin).get();
	}

	public void setLastlogin(final Date lastlogin) {
		this.lastLogin = Optional.ofNullable(lastlogin);

	}

	@Override
	public String toString() {
		return "Login [idAziendale=" + idAziendale + ", password=" + passwordEncrypted + ", lastLogin="
				+ getLastlogin() + "]";
	}

}
