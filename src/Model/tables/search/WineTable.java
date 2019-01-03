package Model.tables.search;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.Product;
import Model.tables.Table;

public class WineTable extends Table {

	private List<Product> wine; 
	public WineTable(final List<Product> p) {
		super();
		wine = p;
	}

	public WineTable() {
	}

	@Override
	protected AbstractTableModel getModelTable() {
		return new AbstractTableModel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 9118036819914158151L;
			private static final int NUM_COLUM = 4;

			@Override
			public final int getRowCount() {
				return wine.size();
			}

			@Override
			public final int getColumnCount() {
				return NUM_COLUM;
			}

			@Override
			public final Object getValueAt(final int rowIndex, final int columnIndex) {
							
				switch (columnIndex) {
				case 0:
					return wine.get(rowIndex).getPhaseProduction().getDate().toString();
				case 1:
					return wine.get(rowIndex).getQuantity();
				case 2:
					return wine.get(rowIndex).getQuantityActual();
				case 3:
					return wine.get(rowIndex).getCask().orElse(null);
				
				default:
				}
				return "";
			}

			@Override
			public final String getColumnName(final int column) {

				switch (column) {
				case 0:
					return "Data";
				case 1:
					return "Quantita";
				case 2:
					return "Quantita Attuale";
				case 3:
					return "Botte";
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
					return Double.class;
				case 2:
					return Double.class;
				case 3:
					return Integer.class;
			
				default:
				}
				return Object.class;
			}
			
		};
	}

}