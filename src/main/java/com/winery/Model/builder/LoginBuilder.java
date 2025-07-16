package com.winery.Model.builder;

import java.sql.Date;

import com.winery.Model.Login;

public interface LoginBuilder {

    Login build();

	LoginBuilder setIdAziendale(long id);

	LoginBuilder setPassword(String password);

	LoginBuilder setLastlogin(Date lastlogin);

}
