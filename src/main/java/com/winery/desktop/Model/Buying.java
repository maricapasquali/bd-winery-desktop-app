package com.winery.desktop.Model;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.winery.desktop.Exception.WineJustInsertInCart;
import com.winery.desktop.Exception.WineNotEnoughException;
import com.winery.desktop.Exception.WineNotInsertInCartException;
import com.winery.desktop.Utility.Utility;
import org.apache.commons.lang3.Pair;


public class Buying {

	private final double capacityBottle = 0.75;
	private Date dateBuying;
	private Long idClient;
	private Long idCompany;
	private Map<Product, Pair<Optional<Pair<Integer, Double>>, Optional<Pair<Double, Double>>>> cart;
	private Double priceTot = Double.valueOf(0);

	public Buying() {
	}

	public Date getDateBuying() {
		return dateBuying;
	}

	public void setDateBuying(Date dateBuying) {
		Utility.check(dateBuying == null);
		this.dateBuying = dateBuying;
	}

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		Utility.check(idClient == null);
		this.idClient = idClient;
	}

	public Long getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}

	public Map<Product, Pair<Optional<Pair<Integer, Double>>, Optional<Pair<Double, Double>>>> getCart() {
		return cart;
	}

	public void addInCart(final Product w, final int numBottle, final double liter)
			throws WineJustInsertInCart, WineNotEnoughException {
		if (cart == null) {
			cart = new HashMap<>();
		}
		Utility.check(w == null || (numBottle == 0 && liter == 0));
		if (this.cart.containsKey(w)) {
			throw new WineJustInsertInCart();
		}

		if (w.getQuantityActual() < (numBottle * capacityBottle + liter)) {
			throw new WineNotEnoughException(w.getQuantityActual());
		}

		Pair<Integer, Double> num_price_bottle = null;
		Pair<Double, Double> liter_price_liter = null;

		if (numBottle != 0) {
			double priceTotBottle = w.getPhaseProduction().getGrape().getPriceBottle() * numBottle;
			priceTot += priceTotBottle;
			num_price_bottle = new Pair<Integer, Double>(numBottle, priceTotBottle);
		}
		if (liter != 0) {
			double priceTotLiter = w.getPhaseProduction().getGrape().getPriceLiter() * liter;
			priceTot += priceTotLiter;
			liter_price_liter = new Pair<Double, Double>(liter, priceTotLiter);
		}
		this.cart.put(w, new Pair<>(Optional.ofNullable(num_price_bottle), Optional.ofNullable(liter_price_liter)));

		w.setQuantityActual(w.getQuantityActual() - literInCart(w));

		logWineQuantity(w);
	}

	public void removeToCart(final Product w) throws WineNotInsertInCartException {
		if (!this.cart.containsKey(w)) {
			throw new WineNotInsertInCartException();
		}
		if (cart.get(w).left.isPresent()) {
			priceTot -= cart.get(w).left.get().right.doubleValue();
		}
		if (cart.get(w).right.isPresent()) {
			priceTot -= cart.get(w).right.get().right.doubleValue();
		}
		w.setQuantityActual(w.getQuantityActual() + literInCart(w));

		this.cart.remove(w);

		logWineQuantity(w);
	}

	public Double getPriceTot() {
		return Utility.round2(priceTot);
	}

	public void setPriceTot(final Double priceTot) {
		this.priceTot = priceTot;
	}
	
	@Override
	public String toString() {
		return "Buying [dateBuying=" + dateBuying + ", idClient=" + idClient + ", idCompany=" + idCompany + ", cart="
				+ cart + "]";
	}

	private void logWineQuantity(final Product w) {
		Utility.log("Vino= " + w.getPhaseProduction().getGrape().getName() + " -> Quantità attuale = "
				+ w.getQuantityActual());
	}

	private double literInCart(final Product w) {

		return this.cart.get(w).left.orElse(Pair.of(0, 0.0)).left.intValue() * capacityBottle
				+ this.cart.get(w).right.orElse(Pair.of(0.0, 0.0)).left.doubleValue();
	}
}
