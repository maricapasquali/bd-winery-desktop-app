package com.winery.View.tables;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.swing.table.AbstractTableModel;
import org.apache.commons.lang3.Pair;

import com.winery. Model.Buying;
import com.winery.Model.Product;
import com.winery.Utility.Utility;

public class BuyDetailsTable extends Table {

	private static Buying b;

	public BuyDetailsTable(final Buying buy) {
		b = buy;
	}

	@Override
	protected AbstractTableModel getModelTable() {
		return new AbstractTableModel(){
			private static final long serialVersionUID = -2638359166260334698L;

			private static final int NUM_COLUM = 4;

			@Override
			public final int getRowCount() {
				return b.getCart().size();
			}

			@Override
			public final int getColumnCount() {
				return NUM_COLUM;
			}

			@Override
			public final Object getValueAt(final int rowIndex, final int columnIndex) {
				final Product w = new ArrayList<>(b.getCart().keySet()).get(rowIndex);
				final List<Pair<Optional<Pair<Integer, Double>>, Optional<Pair<Double, Double>>>> quantity = new ArrayList<>(b.getCart().values());
				
				final String grape = w.getPhaseProduction().getGrape().getName();
				final Integer yearOfProduction = Utility.dataPart(w.getPhaseProduction().getDate(), Calendar.YEAR);
				final Integer bottle = quantity.get(rowIndex).left.isPresent()? quantity.get(rowIndex).left.get().left: null;
				final Double liter = quantity.get(rowIndex).right.isPresent()? quantity.get(rowIndex).right.get().left: null;
					
				switch (columnIndex) {
				case 0:
					return grape;
				case 1:
					return yearOfProduction;
				case 2:
					return bottle;
				case 3:
					return liter;

				default:
				}
				return "";
			}

			@Override
			public final String getColumnName(final int column) {

				switch (column) {
				case 0:
					return "Vino";
				case 1:
					return "Anno Produzione";
				case 2:
					return "Num. Bottilie";
				case 3:
					return "Litri";
				default:
				}
				return "";
			}

			@Override
			public final Class<?> getColumnClass(final int column) {

				switch (column) {
				case 0:
					return String.class;
				case 1:
					return Integer.class;
				case 2:
					return Integer.class;
				case 3:
					return Double.class;
				default:
				}
				return Object.class;
			}
		};
	}
}
