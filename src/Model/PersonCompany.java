package Model;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import Model.enumeration.TypeAccess;
import Utility.Utility;

public class PersonCompany extends Person {

	private Date dataBirth;
	private TypeAccess type;
	private Optional<Double> monthlySalary = Optional.empty();	
	private Optional<Date> lastAccess = Optional.empty();
	private Date dateAssumption = null;

	public PersonCompany() {
		super();
	}

	public Date getDataBirth() {
		return dataBirth;
	}

	public void setDataBirth(Date dataBirth) {
		this.dataBirth = dataBirth;
	}

	public TypeAccess getType() {
		return type;
	}

	public void setType(final String type) {
		Utility.checkLength(type);
		if(type.equals(TypeAccess.ADMIN.toString())) {
			this.type = TypeAccess.ADMIN;
		}else if(type.equals(TypeAccess.DIPENDENTE.toString())) {
			this.type = TypeAccess.DIPENDENTE;
		}else {
			this.type = TypeAccess.PART_TIME;
		}
	}
	
	public Optional<Double> getMonthlySalary() {
		return monthlySalary;
	}

	public void setMonthlySalary(final double monthlySalary) {
		this.monthlySalary = Optional.ofNullable(monthlySalary);
	}
	
	public Object getLastAccess() {
		return lastAccess.isPresent()? lastAccess.get() : "";
	}

	public void setLastAccess(final Optional<Date> lastAccess) {
		this.lastAccess = lastAccess;
	}
	
	public Date getDateAssumption() {
		return dateAssumption;
	}

	public void setDateAssumption(final Date dateAssumption) {
		this.dateAssumption = dateAssumption;
	}
	
	@Override
	public String toString() {
		return "Person Company [ID = " + getID() + ", Name = " + getName() + ", LastName = " + getLastName() + ", Street = "
				+ getStreet() + ", StreetNumber = " + getStreetNumber() + ", StreetCity = " + getStreetCity()
				+ ", PhoneNumber = " + getPhoneNumber() + ", Data Birth = " + getDataBirth() + ", Type = "
				+ getType() + ", Monthly Salary = " + getMonthlySalary() + ", Date Assumption = " + getDateAssumption() +"]";
	}
	
	public static PersonCompany find(final List<PersonCompany> workers, final String w) {
		if(w==null) {
			throw new NullPointerException();
		}
		final String[] workerSelected = w.split(Pattern.quote(Utility.getSplit()));
		return workers.stream().filter(worker -> worker.getID()==Long.parseLong(workerSelected[0])).collect(Collectors.toList()).get(0);
	}
	
	public static Long findPartTime(final List<PersonCompany> workers, final String w) {
		if(w==null) {
			throw new NullPointerException();
		}
		final String[] workerSelected = w.split(Pattern.quote(Utility.getSplit()));
		return workers.stream().filter(worker -> worker.getName().equals(workerSelected[0]) && worker.getLastName().equals(workerSelected[1])).collect(Collectors.toList()).get(0).getID();
	}
		
	public boolean isAdmin() {
		return this.getType().equals(TypeAccess.ADMIN);
	}

	public boolean isEmployee() {
		return this.getType().equals(TypeAccess.DIPENDENTE);
	}
	
	public boolean isPartTime() {
		return this.getType().equals(TypeAccess.PART_TIME);
	}

}
