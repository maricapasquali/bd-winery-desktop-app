package Model.builder;

import java.sql.Date;

import Model.Login;

public interface LoginBuilder {

    Login build();

	LoginBuilder setIdAziendale(long id);

	LoginBuilder setPassword(String password);

	LoginBuilder setLastlogin(Date lastlogin);

}
