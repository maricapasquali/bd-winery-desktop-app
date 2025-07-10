package Model.builder;

import java.sql.Date;

import Model.PersonCompany;

public interface PersonCompanyBuilder {
	
	PersonCompany build();
	
	PersonCompanyBuilder setID(Long id);
	
	PersonCompanyBuilder setName(String name);
	
	PersonCompanyBuilder setLastName(String lastName);
	
	PersonCompanyBuilder setStreet(String street);
	
	PersonCompanyBuilder setStreetNumber(int streetNumber);
	
	PersonCompanyBuilder setStreetCity(String streetCity);
	
	PersonCompanyBuilder setPhoneNumber(String phoneNumber);
	
	PersonCompanyBuilder setDataBirth(Date dataBirth);
	
	PersonCompanyBuilder setType(String type);
	
	PersonCompanyBuilder setMonthlySalary(double monthlySalary);

}
