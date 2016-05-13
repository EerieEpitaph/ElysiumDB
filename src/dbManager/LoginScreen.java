package dbManager;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.alee.laf.WebLookAndFeel;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JButton;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.Color;

@SuppressWarnings("serial") // ROMPE SOLTANTO..
public class LoginScreen extends JFrame {

	private final JLabel lblNewLabel = new JLabel("");
	private final JLabel lblUsername = new JLabel(" Username:");
	private final JTextField textUsername = new JTextField();
	private final JLabel lblPassword = new JLabel(" Password:");
	private final JPasswordField pswPassword = new JPasswordField();
	private final JSeparator separator = new JSeparator();
	private final JButton btnAccedi = new JButton("Accedi");
	private final JLabel lblCopyrightLischio = new JLabel(" @Lischio Ottavio; Based on MySQL Architecture");
	private final JButton impostazioni = new JButton("");
	
	public static String password;
	public static String username;
	public static int tentativi;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					WebLookAndFeel.install();
					LoginScreen frame = new LoginScreen();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginScreen() {
		////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////// ACCESSO
		btnAccedi.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) 
			{
				tentativi = LoginOpzioni.tentativi;
				for(int i = 0; i < tentativi; i++)
				{
					try
					{
						textUsername.setBackground(Color.WHITE);       // A OGNI TENTATIVO SETTA DI BIANCO LO SFONDO DELLE CASELLE
						pswPassword.setBackground(Color.WHITE);
						username = textUsername.getText();
						password = pswPassword.getText();
						String nomeDriver = LoginOpzioni.nomeDriver;   // NOME DEL DRIVER JDBC
						String serverUrl = LoginOpzioni.URL;  // URL A CUI COLLEGARSI; LOCALHOST E' QUESTO STESSO PC (E SI'! PUO' USARE ANCHE DATABASES SU INTERNET!)
						Connection conn = null;							
					
						Class.forName(nomeDriver);
						conn = DriverManager.getConnection(serverUrl,username,password); // TENTA DI STABILIRE UNA CONNESSIONE
						conn.close();                                                    // TENTA DI CHIUDERE LA CONNESSIONE
					
						i = tentativi;
						new GUI(); // CREA UNA NUOVA ISTANZA DELLA TABELLA
						dispose();
					}
					catch(CommunicationsException e)// ERRORE DI COMUNICAZIONE, SERVER SPENTO O INESISTENTE                -GIALLO-
					{
						textUsername.setBackground(new Color(255, 255, 153));
						pswPassword.setBackground(new Color(255, 255, 153));
						GUI.frame.dispose();
						e.printStackTrace();
					} 
					catch(ClassNotFoundException e) // DRIVER NON TROVATO, NOME SBAGLIATO?                                -ARANCIONE-
					{
						textUsername.setBackground(new Color(255, 204, 153));
						pswPassword.setBackground(new Color(255, 204, 153));
						GUI.frame.dispose();
						e.printStackTrace();
					} 
					catch(SQLException e)          // ERRORE NELL'USERNAME O NELLA PASSWORD                                -ROSSO-
					{
						textUsername.setBackground(new Color(255, 153, 153));
						pswPassword.setBackground(new Color(255, 153, 153));
						GUI.frame.dispose();
						e.printStackTrace();
					}
				}
			}
		});
		
		pswPassword.addActionListener(new ActionListener() {                 // ENTER NEL CAMPO USERNAME
			public void actionPerformed(ActionEvent arg0) 
			{
				btnAccedi.doClick(); // IL DOCLICK SIMULA UN CLICK SU UN COMPONENTE
			}
		});
		
		textUsername.addActionListener(new ActionListener() {				 // ENTER NEL CAMPO PASSWORD
			public void actionPerformed(ActionEvent arg0)
			{
				btnAccedi.doClick(); // IL DOCLICK SIMULA UN CLICK SU UN COMPONENTE
			}
		});
		///////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////// OPZIONI
		impostazioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				new LoginOpzioni();
			}
		});
		///////////////////////////////////////////////////////////////////////////
		
		textUsername.setText("root");
		pswPassword.setText("55161911getbent97");
		
		setResizable(false);
		setTitle("Login");
		textUsername.setBackground(Color.WHITE);
		textUsername.setBounds(10, 92, 192, 26);
		textUsername.setColumns(10);
		
		getContentPane().setBackground(SystemColor.window);
		setBounds(100, 100, 452, 181);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		lblNewLabel.setIcon(new ImageIcon(LoginScreen.class.getResource("/dbManager/LoginIcona.PNG")));
		lblNewLabel.setBounds(10, 11, 434, 47);
		
		getContentPane().add(lblNewLabel);
		lblUsername.setBounds(9, 77, 90, 14);
		
		getContentPane().add(lblUsername);
		
		getContentPane().add(textUsername);
		lblPassword.setBounds(213, 76, 70, 16);

		impostazioni.setIcon(new ImageIcon(LoginScreen.class.getResource("/dbManager/impostazioni-.png")));
		
		getContentPane().add(lblPassword);
		pswPassword.setEchoChar('*');
		pswPassword.setBackground(Color.WHITE);
		pswPassword.setBounds(214, 92, 223, 26);
		
		getContentPane().add(pswPassword);
		separator.setBounds(12, 64, 424, 2);
		
		getContentPane().add(separator);
		btnAccedi.setBounds(311, 118, 98, 26);
		
		getContentPane().add(btnAccedi);
		lblCopyrightLischio.setForeground(SystemColor.activeCaptionBorder);
		lblCopyrightLischio.setBounds(10, 121, 265, 20);
		
		getContentPane().add(lblCopyrightLischio);
		impostazioni.setBounds(411, 118, 26, 26);
		
		getContentPane().add(impostazioni);

	}
}
