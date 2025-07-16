package com.winery.Model.builder;

import java.sql.Date;

import com.winery.Model.Login;

public class LoginBuilderImpl implements LoginBuilder {

	private Login login;

	public LoginBuilderImpl() {
		login = new Login();
	}

	@Override
	public Login build() {
		return login;
	}

	@Override
	public LoginBuilder setIdAziendale(final long id) {
		login.setIdAziendale(id);
		return this;
	}

	@Override
	public LoginBuilder setPassword(final String password) {
		login.setPassword(password);
		return this;
	}

	@Override
	public LoginBuilder setLastlogin(final Date lastlogin) {
		login.setLastlogin(lastlogin);
		return this;
	}

}
