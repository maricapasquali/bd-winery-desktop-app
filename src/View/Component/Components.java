package View.Component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;

import com.toedter.calendar.JDateChooser;

import Utility.Utility;

public class Components {

	private static final String FIELD_NOT_SET = "Alcuni campi non sono stati inseriti";
	private static String RESET_FIELD_TEXT = "";
	private static int RESET_FIELD_NUMBER = 0;
	private static final int MAX_CHAR = 20;
	private static final int MAX_CHAR_PHONE = 10;
	private final static int SIZE_FONT = 20;
	private final static int GAPTOP = 20;
	private final static int GAPLEFT = 20;
	private final static int GAPBETWEENBUTTON = 20;
	private final static int GAPRIGHT = 20;

	public static Image createImage(final String path) {
		return new ImageIcon(Components.class.getResource(path)).getImage();
	}

	public static JLabel createLabel() {
		final JLabel label = new JLabel();
		label.setFont(createFontButton());
		return label;
	}

	public static JLabel createLabel(final String text) {
		final JLabel label = createLabel();
		label.setText(text);
		return label;
	}

	public static JButton createButton(final String text) {
		final JButton button = new JButton(text);
		button.setFont(createFontButton());

		return button;
	}

	public static JDateChooser createDateField() {
		final JDateChooser dateField = new JDateChooser();
		dateField.setFont(createFontTextField());
		return dateField;
	}

	public static JSpinner createSpinner() {
		final SpinnerModel value = new SpinnerNumberModel(0, // initial value
				0, // minimum value
				10000, // maximum value
				1); // step
		final JSpinner tSpinner = new JSpinner(value);
		tSpinner.setFont(createFontTextField());
		return tSpinner;
	}

	public static JSpinner createSpinnerDouble() {
		final SpinnerModel value = new SpinnerNumberModel(0.0, // initial value
				0.0, // minimum value
				100000.0, // maximum value
				0.1); // step
		final JSpinner tSpinner = new JSpinner(value);
		tSpinner.setFont(createFontTextField());
		return tSpinner;
	}

	public static JCheckBox createCheckBox(final String text) {
		final JCheckBox checkBox = new JCheckBox(text);
		checkBox.setFont(createFontTextField());
		checkBox.setBorder(null);
		return checkBox;
	}

	public static GridBagConstraints createGridBagConstraints() {
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(GAPTOP, GAPLEFT, GAPBETWEENBUTTON, GAPRIGHT);
		constraints.fill = GridBagConstraints.BOTH;
		return constraints;
	}

	public static JPanel createPaneFlow() {
		final JPanel pane = new JPanel(new FlowLayout());
		pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		return pane;
	}

	public static JPanel createPaneGridBag() {
		final JPanel pane = new JPanel(new GridBagLayout());
		return pane;
	}

	public static JPanel createPaneBorder() {
		final JPanel pane = new JPanel(new BorderLayout());
		return pane;
	}

	public static JPanel createPaneFlow(final String tTitle) {
		final JPanel pane = new JPanel(new FlowLayout());
		pane.setBorder(createTitleBorder(tTitle));
		pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		return pane;
	}

	public static JPanel createPaneGridBag(final String tTitle) {
		final JPanel pane = new JPanel(new GridBagLayout());
		pane.setBorder(createTitleBorder(tTitle));
		return pane;
	}

	public static JPanel createPaneBorder(final String tTitle) {
		final JPanel pane = new JPanel(new BorderLayout());
		pane.setBorder(createTitleBorder(tTitle));
		return pane;
	}

	public static JTextField createTextField(final int minChar) {
		final JTextField textField = new JTextField(10);
		textField.setFont(createFontTextField());
		textField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				String s = ((JTextField) ke.getSource()).getText();
				if (s.length() > minChar) {
					((JTextField) ke.getSource()).setText(s.substring(0, minChar - 1));
				}
			}
		});
		return textField;
	}

	public static JComboBox<Object> createComboBox(final Object[] list) {
		final JComboBox<Object> combo = new JComboBox<Object>(list);
		combo.setFont(createFontTextField());
		return combo;

	}

	public static JComboBox<Object> createComboBox() {
		final JComboBox<Object> combo = new JComboBox<Object>();
		combo.setFont(createFontTextField());
		combo.addItem(null);
		combo.setSelectedIndex(0);
		return combo;

	}

	public static JPasswordField createPasswField() {
		final JPasswordField passField = new JPasswordField();
		passField.setFont(createFontTextField());
		return passField;
	}

	public static JPanel title(final String title) {
		final JPanel pNorth = Components.createPaneFlow();
		final JLabel op = Components.createLabel(title);
		op.setFont(createFontTitle());
		pNorth.add(op);
		return pNorth;
	}

	public static void errorPane(final String error, final Component comp) {
		Utility.logError(error);
		JOptionPane.showMessageDialog(comp, error, "Errore", JOptionPane.ERROR_MESSAGE);
	}

	public static void infoPane(final String info, final Component comp) {
		Utility.log(info);
		JOptionPane.showMessageDialog(comp, info, "Info", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void setVisibleComponents(final List<JComponent> listComp, final boolean cond) {
		listComp.stream().forEach(c -> c.setVisible(cond));
	}

	public static void resetTextComponents(final List<JTextField> listComp) {
		listComp.stream().forEach(c -> c.setText(RESET_FIELD_TEXT));
	}

	public static void resetNumberJSpinner(final List<?> listComp) {
		listComp.stream().forEach(c -> ((JSpinner) c).setValue(RESET_FIELD_NUMBER));
	}

	public static void addInCenterPanel(final JPanel pCenter, final GridBagConstraints constraints, final JLabel label,
			final JComboBox<Object> combo, final Stream<?> stream) {
		final JLabel name = label;
		pCenter.add(name, constraints);
		constraints.gridx = 1;
		final JComboBox<Object> tname = combo;
		stream.forEach(s -> tname.addItem(s));
		pCenter.add(tname, constraints);
	}

	public static void addInCenterPanel(final JPanel pCenter, final GridBagConstraints constraints, final JLabel label,
			final JComponent component) {
		final JLabel name = label;
		pCenter.add(name, constraints);
		constraints.gridx = 1;
		final JComponent tname = component;
		pCenter.add(tname, constraints);
	}

	public static String getFieldNotSet() {
		return FIELD_NOT_SET;
	}

	public static String getRESET_FIELD_TEXT() {
		return RESET_FIELD_TEXT;
	}

	public static int getRESET_FIELD_NUMBER() {
		return RESET_FIELD_NUMBER;
	}

	public static int getMaxChar() {
		return MAX_CHAR;
	}

	public static int getMaxCharPhone() {
		return MAX_CHAR_PHONE;
	}

	// PRIVATE
	private static TitledBorder createTitleBorder(final String title) {
		final TitledBorder tBorder = new TitledBorder(title);
		return tBorder;
	}

	private static Font createFontButton() {
		return new Font(Font.SANS_SERIF, Font.BOLD, SIZE_FONT);
	}

	private static Font createFontTitle() {
		return new Font(Font.SANS_SERIF, Font.ITALIC, 100);
	}

	private static Font createFontTextField() {
		return new Font(Font.SANS_SERIF, Font.ROMAN_BASELINE, SIZE_FONT);
	}

	public static JTextField createTextField(final String string, final int minChar) {
		final JTextField tField = createTextField(minChar);
		tField.setText(string);
		return tField;
	}

}
