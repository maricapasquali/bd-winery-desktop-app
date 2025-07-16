package com.winery.View.tables;

import java.util.ArrayList;
import java.util.Optional;

import javax.swing.table.AbstractTableModel;
import com.winery.Model.Group;

public class GroupsTable extends Table{

	private static Group g;
	private static int num_colum ;
	public GroupsTable(final Group group) {
		g = group;
	}

	public GroupsTable() {
	}
	
	public void setGroup(final Group group) {
		g = group;
	}

	@Override
	protected AbstractTableModel getModelTable() {
		switch(g.getPhaseProduction().getPpw()) {
		case RACCOLTA: num_colum = 5; break;
		default:  num_colum = 4; break;
		}
		return new AbstractTableModel(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -2638359166260334698L;

		

			@Override
			public final int getRowCount() {
				return g.getWorkerAndHoursWork().size();
			}

			@Override
			public final int getColumnCount() {
				return  num_colum ;
			}

			@Override
			public final Object getValueAt(final int rowIndex, final int columnIndex) {
				final long idPhane = g.getPhaseProduction().getId();
				final String nameWorder = new ArrayList<>(g.getWorkerAndHoursWork().keySet()).get(rowIndex).getName();
				final String lastNameWorder = new ArrayList<>(g.getWorkerAndHoursWork().keySet()).get(rowIndex).getLastName();
				
				final Optional<Integer> hoursArray = new ArrayList<>(g.getWorkerAndHoursWork().values()).get(rowIndex);
				final Integer hours = hoursArray.isPresent()? hoursArray.get() : null;
				final Long idVend = g.getIdHarvester().isPresent()?g.getIdHarvester().get() : null;
				
				switch (columnIndex) {
				case 0:
					return idPhane;
				case 1:
					return nameWorder;
				case 2:
					return lastNameWorder;
				case 3:
					return hours;
				case 4:
					return idVend;

				default:
				}
				return "";
			}

			@Override
			public final String getColumnName(final int column) {

				switch (column) {
				case 0:
					return "Id Fase";
				case 1:
					return "Nome Operaio";
				case 2:
					return "Cognome Operaio";
				case 3:
					return "Ore Lavoro";
				case 4:
					return "ID Vendemmiatrice";
				default:
				}
				return "";
			}

			@Override
			public final Class<?> getColumnClass(final int column) {

				switch (column) {
				case 0:
					return Long.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return Integer.class;
				case 4:
					return Long.class;

				default:
				}
				return Object.class;
			}
		};
	}
}
