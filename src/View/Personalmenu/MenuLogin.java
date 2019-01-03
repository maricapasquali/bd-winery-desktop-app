package View.Personalmenu;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



import DataBaseConnections.QueriesSystem;
import Model.Login;
import Model.PersonCompany;
import Model.builder.LoginBuilderImpl;
import Utility.EnDeCrypt;
import Utility.KeyboardAdapter;
import Utility.Utility;
import View.Component.Components;
import exception.NeverLoggedIn;
import exception.NotInSystemException;

public class MenuLogin extends AbstractFrameDefault {

	/**
	 * 
	 */
	private static final long serialVersionUID = -356192359115364377L;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 350;

	private static final JLabel USERNAME = Components.createLabel("User Name ");
	private static final JTextField USER = Components.createTextField("nome.cognome", 41);
	private static final JButton FORWARD = Components.createButton("Avanti");

	private static final JLabel FIELDPASSW = Components.createLabel("Password ");
	private static final JPasswordField PASSW = Components.createPasswField();
	private static final JLabel FIELDCONFPASSW = Components.createLabel("Conferma Password ");
	private static final JPasswordField CONFPASSW = Components.createPasswField();
	private static final JButton SUBMIT = Components.createButton("Accedi");


	private static final JPanel centerPanel = Components.createPaneGridBag();
	private static GridBagConstraints constraints = Components.createGridBagConstraints();
	private static final JPanel southPanel = Components.createPaneFlow();

	private static File dataBase = null;

	private PersonCompany p;
	private Login login;
	private PersonalMenu menu;

	public MenuLogin() {
		super(WIDTH, HEIGHT);
		this.setTitle("Login");
		this.addButtonsInCenterPanel();
		southPanel.add(FORWARD);
		this.add(southPanel, BorderLayout.SOUTH);
		this.setActions();
	}

	private void addButtonsInCenterPanel() {
		// user name
		constraints.gridy = 0;
		centerPanel.add(USERNAME, constraints);
		constraints.gridx = 1;
		centerPanel.add(USER, constraints);

		// password
		constraints.gridx = 0;
		constraints.gridy++;
		centerPanel.add(FIELDPASSW, constraints);
		constraints.gridx = 1;
		centerPanel.add(PASSW, constraints);

		// conferma password
		constraints.gridx = 0;
		constraints.gridy++;
		centerPanel.add(FIELDCONFPASSW, constraints);
		constraints.gridx = 1;
		centerPanel.add(CONFPASSW, constraints);

		this.setVisiblePassw(false);

		this.add(centerPanel, BorderLayout.CENTER);
	}

	private void setActions() {

		USER.addKeyListener(new KeyboardAdapter(() -> verificationUserName()));

		FORWARD.addActionListener(e -> verificationUserName());

		PASSW.addKeyListener(new KeyboardAdapter(() -> verificationPassword()));

		SUBMIT.addActionListener(e -> verificationPassword());

		CONFPASSW.addKeyListener(new KeyboardAdapter(() -> verificationPassword()));
	}

	private void verificationUserName() {
		Utility.log("Verifico USERNAME \n");
		SwingUtilities.invokeLater(() -> {
			try {
				final String[] nameLastName = USER.getText().split(Pattern.quote(Utility.getSplitUserName()));
				// Ricerca nel DB della persona
				this.p = QueriesSystem.IsInSystem(nameLastName[0], nameLastName[1]);

				Utility.log("Esiste nel sistema : " + p);
				// Ricerca nel DB se ha mai effettuato un accesso (login)
				login = QueriesSystem.isLoddedIn(p.getID());

				Utility.log(login);
				p.setLastAccess(login.getLastlogin());
				accessWithYourAccount();

			} catch (ArrayIndexOutOfBoundsException ex) {
				Components.errorPane("Formato del User Name è nome.cognome", this);
			} catch (NotInSystemException ex1) {
				Components.errorPane(ex1.getMessage(), this);
			} catch (NeverLoggedIn ex2) {
				registrationAccount(ex2.getMessage());
			}
		});
	}

	private void verificationPassword() {
		Utility.log("Verifico PASSWORD \n");
		if (login != null) {
			// Se ha effettuato un login, immetto password e verifico che sia corretta
			final String passw = EnDeCrypt.dencrypt(login.getPassword());
			if (isPasswordCorrert(String.valueOf(PASSW.getPassword()), passw)) {
				loginPersonalMenu();
			} else {
				Components.errorPane("ACCESSO NEGATO: Password Errata", this);
			}
		} else {
			// Se non ha mai effettuato login, registro il login (password, data accesso,..)

			if (isPasswordCorrert(String.valueOf(CONFPASSW.getPassword()), String.valueOf(PASSW.getPassword()))) {

				login = new LoginBuilderImpl().setIdAziendale(p.getID())
						.setPassword(EnDeCrypt.encrypt(String.valueOf(PASSW.getPassword()))).build();
				Utility.log(login);
				// Inserimento nel DB il login
				QueriesSystem.insertLogin(login);

				loginPersonalMenu();
			} else {
				if(String.valueOf(CONFPASSW.getPassword()).equals("")) {
					Components.infoPane("Confermare Password ", this);
				} else {
					Components.errorPane("ACCESSO NEGATO: Password Errata", this);
				}
			}
		}
	}

	private boolean isPasswordCorrert(final String passwordPassed, final String passwordExpected) {
		return passwordPassed.equals(passwordExpected);
	}

	private void loginPersonalMenu() {
		switch (p.getType()) {
		case ADMIN:
			menu = new MenuAdmin(p);
			break;
		case DIPENDENTE:
			menu = new MenuEmployee(p);
			break;
		case PART_TIME:
			menu = new MenuPartTime(p);
			break;
		default:
			break;
		}
		SwingUtilities.invokeLater(() -> {
			reset();
			menu.display();
			this.dispose();
		});
	}

	private void registrationAccount(final String title) {
		USER.setEnabled(false);
		southPanel.remove(FORWARD);
		this.setTitle(title);
		this.setVisiblePassw(true);
		southPanel.add(SUBMIT, BorderLayout.SOUTH);
		super.rebuildGui();
	}

	private void accessWithYourAccount() {
		southPanel.remove(FORWARD);
		FIELDPASSW.setVisible(true);
		PASSW.setVisible(true);
		PASSW.requestFocus();
		southPanel.add(SUBMIT, constraints);
		super.rebuildGui();
	}

	private void reset() {
		southPanel.remove(SUBMIT);
		USER.setEnabled(true);
		this.setTitle("Login");
		setVisiblePassw(false);
		Components.resetTextComponents(Arrays.asList(USER, PASSW, CONFPASSW));
		southPanel.add(FORWARD, constraints);
		super.rebuildGui();
	}

	private void setVisiblePassw(final boolean isVisible) {
		FIELDPASSW.setVisible(isVisible);
		FIELDCONFPASSW.setVisible(isVisible);
		PASSW.setVisible(isVisible);
		CONFPASSW.setVisible(isVisible);
	}

	@Override
	protected void closeAction() {
		if (JOptionPane.showConfirmDialog(this, EXIT_MESSAGE) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public static File getDataDase() {
		return dataBase;
	}

	public static void main(final String[] arg) {
		try {	
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			final AbstractFrameDefault f = new MenuLogin();
			try {
				dataBase = Utility.dbChoosen(f);
				while (!dataBase.getName().equals("Azienda.accdb")) {
					Components.errorPane("Selezionare database 'Azienda.accdb' ", f);
					dataBase = Utility.dbChoosen(f);
				}
				f.display();
			} catch (NullPointerException e) {
				System.exit(0);
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}	
	}
}
