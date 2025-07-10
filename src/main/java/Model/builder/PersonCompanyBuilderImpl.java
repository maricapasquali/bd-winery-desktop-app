package Model.builder;

import java.sql.Date;

import Model.PersonCompany;

public class PersonCompanyBuilderImpl implements PersonCompanyBuilder{

	private PersonCompany pCompany;
	
	public PersonCompanyBuilderImpl() {
		pCompany = new PersonCompany();
	}
	
	@Override
	public PersonCompany build() {
		return pCompany;
	}
	
	@Override
	public PersonCompanyBuilder setID(Long id) {
		pCompany.setID(id);
		return this;
	}

	@Override
	public PersonCompanyBuilder setName(final String name) {
		pCompany.setName(name);
		return this;
	}

	@Override
	public PersonCompanyBuilder setLastName(final String lastName) {
		pCompany.setLastName(lastName);
		return this;
	}

	@Override
	public PersonCompanyBuilder setStreet(final String street) {
		pCompany.setStreet(street);
		return this;
	}

	@Override
	public PersonCompanyBuilder setStreetNumber(final int streetNumber) {
		pCompany.setStreetNumber(streetNumber);
		return this;
	}

	@Override
	public PersonCompanyBuilder setStreetCity(final String streetCity) {
		pCompany.setStreetCity(streetCity);
		return this;
	}

	@Override
	public PersonCompanyBuilder setPhoneNumber(final String phoneNumber) {
		pCompany.setPhoneNumber(phoneNumber);
		return this;
	}

	@Override
	public PersonCompanyBuilder setDataBirth(final Date dataBirth) {
		pCompany.setDataBirth(dataBirth);
		return this;
	}

	@Override
	public PersonCompanyBuilder setType(final String type) {
		pCompany.setType(type);
		return this;
	}

	@Override
	public PersonCompanyBuilder setMonthlySalary(final double monthlySalary) {
		pCompany.setMonthlySalary(monthlySalary);
		return this;
	}
}
