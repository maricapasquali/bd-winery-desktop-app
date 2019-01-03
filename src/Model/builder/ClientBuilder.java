package Model.builder;

import Model.Client;

public interface ClientBuilder {

	Client build();
	
	ClientBuilder setID(long id);
	
	ClientBuilder setName(String name);
	
	ClientBuilder setLastName(String lastName);
	
	ClientBuilder setStreet(String street);
	
	ClientBuilder setStreetNumber(int streetNumber);
	
	ClientBuilder setStreetCity(String streetCity);
	
	ClientBuilder setPhoneNumber(String phoneNumber);
}
