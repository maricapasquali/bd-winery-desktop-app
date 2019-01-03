package Model.builder;

import Model.Client;;

public class ClientBuilderImpl implements ClientBuilder {

	private Client client;
	
	public ClientBuilderImpl() {
		client = new Client();
	}
	
	@Override
	public Client build() {
		return client;
	}
	
	@Override
	public ClientBuilder setID(long id) {
		client.setID(id);
		return this;
	}

	@Override
	public ClientBuilder setName(final String name) {
		client.setName(name);
		return this;
	}

	@Override
	public ClientBuilder setLastName(final String lastName) {
		client.setLastName(lastName);
		return this;
	}

	@Override
	public ClientBuilder setStreet(final String street) {
		client.setStreet(street);
		return this;
	}

	@Override
	public ClientBuilder setStreetNumber(final int streetNumber) {
		client.setStreetNumber(streetNumber);
		return this;
	}

	@Override
	public ClientBuilder setStreetCity(final String streetCity) {
		client.setStreetCity(streetCity);
		return this;
	}

	@Override
	public ClientBuilder setPhoneNumber(final String phoneNumber) {
		client.setPhoneNumber(phoneNumber);
		return this;
	}

	
}
