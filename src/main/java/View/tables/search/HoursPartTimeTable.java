package View.tables.search;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang3.Pair;

import View.tables.Table;

public class HoursPartTimeTable extends Table {
	
	private Map<Pair<String, Date>, Integer> hours;
	
	public HoursPartTimeTable(final Map<Pair<String, Date>, Integer> h) {
		super();
		hours = h;
	}

	@Override
	protected AbstractTableModel getModelTable() {
		
		return new AbstractTableModel() {

			
			/**
			 * 
			 */
			private static final long serialVersionUID = 7856411751511408025L;
			private static final int NUM_COLUM = 3;

			@Override
			public final int getRowCount() {
				return hours.size();
			}

			@Override
			public final int getColumnCount() {
				return NUM_COLUM;
			}

			@Override
			public final Object getValueAt(final int rowIndex, final int columnIndex) {
							
				switch (columnIndex) {
				case 0:
					return new ArrayList<>(hours.keySet()).get(rowIndex).left;
				case 1:
					return new ArrayList<>(hours.keySet()).get(rowIndex).right.toString();
				case 2:
					return new ArrayList<>(hours.values()).get(rowIndex).intValue();
				default:
				}
				return "";
			}

			@Override
			public final String getColumnName(final int column) {

				switch (column) {
				case 0:
					return "Fase";
				case 1:
					return "Data";
				case 2:
					return "Ore Lavoro";
				
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
							
				default:
				}
				return Object.class;
			}
			
		};
	}

}
