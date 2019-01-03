package Model.tables.search;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.Buying;
import Model.tables.Table;

public class BuyTable extends Table {
	
	private List<Buying> b;
	
	public BuyTable(final List<Buying> b) {
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


			private static final int NUM_COLUM = 3;

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
					return b.get(rowIndex).getPriceTot();
				case 2:
					return b.get(rowIndex).getIdCompany();
							
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
					return "Prezzo totale";
				case 2:
					return "Id Aziendale";
				
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
					return Long.class;
						
				default:
				}
				return Object.class;
			}
			
			
		};
	}

}
