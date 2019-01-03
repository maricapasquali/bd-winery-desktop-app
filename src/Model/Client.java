package Model;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import Utility.Utility;


public class Client extends Person {

	public Client()  {	
		super();
	}

	@Override
	public String toString() {
		return "Client [ID = " + getID() + ", Name = " + getName() + ", LastName = " + getLastName() + "]";
	}
	
	public static Client find(final List<Client> clients, final String id, final String name, final String lastName) {
		return clients.stream().filter(
				c -> (c.getID() == Long.parseLong(id) && c.getName().equals(name) && c.getLastName().equals(lastName)))
				.collect(Collectors.toList()).get(0);
	}

	public static Long find(final List<Client> clients, final String string) {
		if(string==null){
			throw new NullPointerException();
		}
		final String[] clientSelected = string.split(Pattern.quote(Utility.getSplit()));
		return clients.stream().filter(	c -> (c.getName().equals(clientSelected[0]) 
				&& c.getLastName().equals(clientSelected[1]))).collect(Collectors.toList()).get(0).getID();
	}
	
}
