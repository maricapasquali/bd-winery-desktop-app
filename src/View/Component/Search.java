package View.Component;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.KeyListener;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Utility.Components;
import Utility.KeyboardAdapter;

public abstract class Search extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7624715153373588058L;

	private JComboBox<Object> tosearchCombo;
	private final JScrollPane pTable = new JScrollPane();

	private final JPanel pane = Components.createPaneBorder();
	private final JPanel paneCenter = Components.createPaneGridBag();
	private final GridBagConstraints cons = Components.createGridBagConstraints();
	private final KeyListener keyL = new KeyboardAdapter(() -> searchActions());

	private JPanel paneSouth = Components.createPaneFlow();

	public Search(final String toSearch, final Stream<?> listToSearch) {
		this.setLayout(new BorderLayout());

		// Campi e ricerca
		cons.gridx = 0;
		cons.gridy = 0;
		final JLabel tosearchLabel = Components.createLabel(toSearch);
		tosearchCombo = Components.createComboBox();
		Components.addInCenterPanel(paneCenter, cons, tosearchLabel, tosearchCombo, listToSearch);
		pane.add(paneCenter, BorderLayout.CENTER);

		final JPanel searchPane = Components.createPaneFlow();
		final JButton search = Components.createButton("Ricerca");
		searchPane.add(search);
		pane.add(searchPane, BorderLayout.SOUTH);

		this.add(pane, BorderLayout.NORTH);
		this.add(pTable, BorderLayout.CENTER);

		search.addActionListener(e -> searchActions());

		tosearchCombo.addKeyListener(keyL);
	}

	private void searchActions() {
		try {
			searchButton();
		} catch (NullPointerException x) {
			Components.errorPane(Components.getFieldNotSet(), super.getParent());
		} catch (IndexOutOfBoundsException x) {
			Components.errorPane("Non esistono prodotti per quell'anno", super.getParent());
		}
	}

	protected void setJViewPointTable(final JTable table) {
		pTable.getViewport().add(table);
		pTable.revalidate();
		pTable.repaint();
	}

	public JComboBox<Object> getComboBox() {
		return tosearchCombo;
	}
	
	public void addItemComboBox(final Object item) {
		tosearchCombo.addItem(item);
		tosearchCombo.revalidate();
		tosearchCombo.repaint();
	}

	protected String getSelectedCombo() {
		return tosearchCombo.getSelectedItem().toString();
	}

	protected JPanel getPaneSearch() {
		return paneCenter;
	}

	protected GridBagConstraints getConstraints() {
		return cons;
	}

	protected KeyListener getKeyL() {
		return keyL;
	}

	protected void addInPaneSouth(final JButton compute, final JLabel label) {
		paneSouth.setVisible(false);
		this.add(paneSouth, BorderLayout.SOUTH);
		paneSouth.add(compute);
		paneSouth.add(label);
		label.setVisible(false);
	}

	protected void reset(final JButton compute, final JLabel label) {
		label.setVisible(false);
		compute.setEnabled(true);
		paneSouth.setVisible(true);
	}

	protected abstract void searchButton();
}
