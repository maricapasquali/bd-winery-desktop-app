package View.tables.search;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.search.BuyingSearch;
import View.tables.Table;

public class BuyTable extends Table {
	
	private List<BuyingSearch> b;
	
	public BuyTable(final List<BuyingSearch> b) {
		super();
		this.b = b;
	}

	@Override
	protected AbstractTableModel getModelTable() {
		
		return new AbstractTableModel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5340058367015598108L;


			private static final int NUM_COLUM = 4;

			@Override
			public final int getRowCount() {
				return b.size();
			}

			@Override
			public final int getColumnCount() {
				return NUM_COLUM;
			}

			@Override
			public final Object getValueAt(final int rowIndex, final int columnIndex) {
							
				switch (columnIndex) {
				case 0:
					return b.get(rowIndex).getDateBuying().toString();
				case 1:
					return b.get(rowIndex).getWine();
				case 2:
					return b.get(rowIndex).getNum_bottle().orElse(null);
				case 3:
					return b.get(rowIndex).getNum_liter().orElse(null);
							
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
					return "Vino";
				case 2:
					return "Numero Bottiglie";
				case 3:
					return "Vino Sfuso (Lt)";
				
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
					return String.class;
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
