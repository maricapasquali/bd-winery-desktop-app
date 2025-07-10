package View.tables;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public abstract class Table {

	private JTable table;
	
	public Table() {
	}

	public JTable createTable() {
		table = new JTable(getModelTable());
		final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		table.setAutoCreateRowSorter(true);
		return table;
	}
	
	protected abstract AbstractTableModel getModelTable();
}
