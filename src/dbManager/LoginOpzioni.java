package dbManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginOpzioni{
	
	JFrame frmOpzioniAggiuntibr = new JFrame();
	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("Nome del Driver:");
	private final JTextField txtDriver = new JTextField();
	private final JTextField txtURL = new JTextField();
	private final JLabel lblUrl = new JLabel("URL:");
	private final JLabel lblTentativi = new JLabel("Tentativi:");
	private final JSpinner spinner = new JSpinner();
	private final JButton btnOk = new JButton("Ok!");
	
	public static String nomeDriver = "com.mysql.jdbc.Driver";
	public static String URL = "jdbc:mysql://localhost/";
	public static int tentativi = 1;
	
	public LoginOpzioni() {
		/////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////// CAMBIO IMPOSTAZIONI
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				nomeDriver = txtDriver.getText();
				URL = txtURL.getText();
				tentativi = (int) spinner.getValue();
				frmOpzioniAggiuntibr.dispose();
			}
		});
		//////////////////////////////////////////////////////////////////////////////
		txtURL.setText(URL);
		txtURL.setBounds(55, 34, 377, 25);
		txtURL.setColumns(10);
		txtDriver.setText(nomeDriver);
		txtDriver.setBounds(121, 7, 311, 25);
		txtDriver.setColumns(10);
		frmOpzioniAggiuntibr.setVisible(true);
		frmOpzioniAggiuntibr.setResizable(false);
		frmOpzioniAggiuntibr.setTitle("Opzioni Aggiuntive");
		frmOpzioniAggiuntibr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmOpzioniAggiuntibr.setBounds(100, 100, 450, 125);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmOpzioniAggiuntibr.setContentPane(contentPane);
		contentPane.setLayout(null);
		lblNewLabel.setBounds(12, 12, 91, 15);
		
		contentPane.add(lblNewLabel);
		
		contentPane.add(txtDriver);
		
		contentPane.add(txtURL);
		lblUrl.setBounds(12, 39, 25, 15);
		
		contentPane.add(lblUrl);
		lblTentativi.setBounds(12, 66, 53, 15);
		
		contentPane.add(lblTentativi);
		spinner.setModel(new SpinnerNumberModel(tentativi, 1, 100, 1));
		spinner.setBounds(83, 60, 53, 28);
		
		contentPane.add(spinner);
		btnOk.setBounds(347, 61, 85, 25);
		
		contentPane.add(btnOk);
	}
}
