package com.winery.Controller;

import java.util.List;
import java.util.Observable;

import com.winery.DataBaseConnections.QueriesEmployee;
import com.winery.DataBaseConnections.QueriesPartTime;
import com.winery.Model.Cask;
import com.winery.Model.Client;
import com.winery.Model.Grape;
import com.winery.Model.Harvester;
import com.winery.Model.PersonCompany;
import com.winery.Utility.Utility;

public class Controller extends Observable  {

	private List<Client> listClients;
	private List<Grape> listGrapes;
	private List<Cask> listCacks;
	private List<PersonCompany> listWorkers;
	private List<Harvester> listHarves;
	
	private Controller() {
	}
	
	private static class LazyController {
		private static final Controller CONTROLLER = new Controller();
	}

	// FUNZIONI PUBBLICHE
	public static Controller getInstance() {
		return LazyController.CONTROLLER;
	}
		
	public void execQuery(final PersonCompany p) {
		Utility.log("Caricamento elementi preliminari dal DB");
		if(!p.isPartTime()) {
			listClients = QueriesEmployee.listOfClients();	
		}
		listGrapes = QueriesPartTime.listOfGrapes();	
		listCacks = QueriesPartTime.selectAvailableCasks();	
		listWorkers = QueriesPartTime.selectAllWorker();	
		listHarves = QueriesPartTime.selectAllHarvesters();
	}

	public List<Client> getListClients() {
		return listClients;
	}
		
	public void addClient(final Client c) {
		c.setID(this.idNextClient());
		listClients.add(c);				
		this.setChanged();
		this.notifyObservers(listClients.stream().filter(cl -> cl.equals(c)).findFirst().get());
	}
	
	public List<Grape> getListGrapes(){
		return listGrapes;
	}

	public void addGrape(final Grape g) {
		listGrapes.add(g);				
		this.setChanged();
		this.notifyObservers(listGrapes.stream().filter(gr -> gr.equals(g)).findFirst().get());		
	}

	public List<Cask> getListCasks(){
		return listCacks;
	}
	
	public void addCask(final Cask cask) {
		listCacks.add(cask);				
		this.setChanged();	
		this.notifyObservers(listCacks.stream().filter(c -> c.equals(cask)).findFirst().get());		
	}
	
	public List<PersonCompany> getListWorkers(){
		return listWorkers;
	}
	
	public void addWorker(final PersonCompany w) {
		w.setID(this.idNextWorker());
		listWorkers.add(w);				
		this.setChanged();	
		this.notifyObservers(listWorkers.stream().filter(wo -> wo.equals(w)).findFirst().get());		
	}
	
	public List<Harvester> getListHarves() {
		return listHarves;
	}

	public void addHarve(final Harvester h) {
		h.setIDVend(this.idNextHarve());
		listHarves.add(h);
		this.setChanged();	
		this.notifyObservers(listHarves.stream().filter(ha -> ha.equals(h)).findFirst().get());	
	}
		
	// FUNZIONI PRIVATE
	private Long idNextClient() {
		return listClients.stream().map(Client::getID).max(Long::compare).get()+1;
	}
	
	private Long idNextWorker() {
		return listWorkers.stream().map(PersonCompany::getID).max(Long::compare).get()+1;
	}
	
	private Long idNextHarve() {
		return listHarves.stream().map(Harvester::getIDVend).max(Long::compare).get()+1;
	}
}
