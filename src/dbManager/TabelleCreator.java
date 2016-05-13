package dbManager;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.alee.laf.WebLookAndFeel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class TabelleCreator{

	JFrame frmNuovaTabella = new JFrame();
	private final JLabel lblNewLabel = new JLabel("Numero di colonne:");
	private final JTextField txtNumeroColonne = new JTextField();
	private final JLabel lblTipoDiEngine = new JLabel("Tipo di engine:");
	private final JComboBox<String> comboBox = new JComboBox<String>();
	private final JLabel lblNome = new JLabel("Nome:");
	private final JTextField txtNomeTabella = new JTextField();
	private final JPanel panel = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane(panel);
	private final JButton btnCrea = new JButton("Fatto!");
	private final JButton btnOk = new JButton("");
	
	int i = 0;
	int numeroColonne = 0;
	int colonnatore = -1;
	
	String nomeTabella;
	
	Vector<JLabel> labelVector = new Vector<JLabel>(1,1);
	Vector<JTextField> txtFieldVector = new Vector<JTextField>(1,1);
	@SuppressWarnings("rawtypes")
	Vector<JComboBox> cmbVector = new Vector<JComboBox>(1,1);
	Vector<JTextField> txtValVector = new Vector<JTextField>(1,1);
	Vector<JCheckBox> chkVector = new Vector<JCheckBox>(1,1);
	Vector<JCheckBox> chkVector2 = new Vector<JCheckBox>(1,1);
	Vector<JRadioButton> rbtnVector = new Vector<JRadioButton>(1,1);
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public TabelleCreator() {
	//////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////// LISTENER NUMERO DI COLONNE
		btnOk.addActionListener(new ActionListener(){
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent arg0) 
			{
				numeroColonne = Integer.parseInt(txtNumeroColonne.getText().trim());
				if(numeroColonne != colonnatore)   // CONTROLLA SE STAI CERCANDO DI CREARE DUE PREVIEW UGUALI
				{
					try
					{
						colonnatore = numeroColonne;
						txtNumeroColonne.setBackground(new Color(255, 255, 255)); // SETTA DI BIANCO LO SFONDO
						
//						if(numeroColonne > DBManager.tolleranza) // SE VAI OLTRE LE COLONNE MAX SCATTA L'ERRORE
//							throw new Exception();
						
						int supTxtCmb = 7;  // SUPPORTO PER IL VECTOR DI FIELD 
						int supLabel = 12;  // SUPPORTO PER IL VECTOR DI LABELS
						int supChk = 12;    // SUPPORTO PER I CHECKBOX

						for(JLabel j : labelVector)        	// LOOPS DI RIMOZIONE
							panel.remove(j);				// OVVERO: PULISCONO IL PANNELLO
						for(JTextField j : txtFieldVector)
							panel.remove(j);
						for(JComboBox j : cmbVector)
							panel.remove(j);
						for(JTextField j : txtValVector)
							panel.remove(j);
						for(JCheckBox j : chkVector)
							panel.remove(j);
						for(JCheckBox j : chkVector2)
							panel.remove(j);
						for(JRadioButton j : rbtnVector)
							panel.remove(j);
						
						labelVector.clear();			    // PULIZIA VECTORS
						txtFieldVector.clear();
						cmbVector.clear();
						txtValVector.clear();
						chkVector.clear();
						chkVector2.clear();
						rbtnVector.clear();

						if(numeroColonne > 9) // QUANDO GLI ELEMENTI SUPERANO IL LIMITE IN VERTICALE, CREA ALTRO SPAZIO
						{
							panel.setPreferredSize(new Dimension(694, 233 + (numeroColonne - 9) * 25));
							panel.revalidate();
							panel.repaint();
						}
						else
						{
							panel.setPreferredSize(new Dimension(694,233));
							panel.revalidate();
							panel.repaint();
						}
						
						for(i = 1; i < numeroColonne +1; i++) // FINCHE NON RAGGIUNGE IL NUMERO DI COLONNE POPOLA IL PANEL CON ELEMENTI
						{
							final JLabel tempLabel = new JLabel("Nome della " + i + "\u00B0 colonna:");
							final JTextField tempField = new JTextField();
							final JComboBox tempCombo = new JComboBox();
							final JTextField tempField2 = new JTextField();
							final JCheckBox tempChk = new JCheckBox("null-abile");
							final JCheckBox tempChk2 = new JCheckBox("A-Incr");
							final JRadioButton tempRbtn = new JRadioButton("PK");
							tempCombo.setModel(new DefaultComboBoxModel<String>(new String[] 
									{"INT" , "TINYINT" , "SMALLINT" , "MEDIUMINT",
									 "BIGINT" , "FLOAT" , "DOUBLE" , "DECIMAL" , 
									 "DATE" , "DATETIME" , "TIMESTAMP" , "TIME" ,
									 "YEAR" , "CHAR" , "VARCHAR" , "BLOB" , 
									 "TINYBLOB" , "MEDIUMBLOB" , "LONGBLOB" , "ENUM"}));
							
							tempLabel.setBounds(12, supLabel, 150, 15);
							tempField.setBounds(157, supTxtCmb, 187, 25);
							tempCombo.setBounds(356, supTxtCmb, 101, 25);
							tempField2.setBounds(453, supTxtCmb, 37, 25);
							tempChk.setBounds(495, supChk, 66, 16);
							tempChk2.setBounds(565, supChk, 53, 16);
							tempRbtn.setBounds(625, supChk, 34, 16);
							buttonGroup.add(tempRbtn);
							
							tempChk.addActionListener(new ActionListener() {  	// QUESTI DUE
								public void actionPerformed(ActionEvent e)  	// ESLCUDONO 
								{												// IL LORO
									if(tempChk.isSelected())					// ANTAGONISTA
										tempChk2.setEnabled(false);				// LOGICO
									else
										tempChk2.setEnabled(true);			// (O sono NULL oppure INCR)
								}
							});
							tempChk2.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) 
								{
									if(tempChk2.isSelected())
										tempChk.setEnabled(false);
									else
										tempChk.setEnabled(true);
								}
							});
							
							panel.add(tempLabel);
							panel.add(tempField);
							panel.add(tempCombo);
							panel.add(tempField2);
							panel.add(tempChk);
							panel.add(tempChk2);
							panel.add(tempRbtn);
							
							labelVector.add(tempLabel);
							txtFieldVector.add(tempField);
							cmbVector.add(tempCombo);
							txtValVector.add(tempField2);
							chkVector.add(tempChk);
							chkVector2.add(tempChk2);
							rbtnVector.add(tempRbtn);
							
							supLabel += 25;
							supTxtCmb += 25;
							supChk += 25;
							panel.updateUI();
						}
					}
					catch(Exception e) // NEL CASO TI SENTI RIBELLE..
					{
						txtNumeroColonne.setBackground(new Color(255, 153, 153)); // COLORE ROSSO COME SFONDO :)
						
						for(JLabel j : labelVector)
							panel.remove(j);
						for(JTextField j : txtFieldVector)
							panel.remove(j);
						for(JComboBox j : cmbVector)
							panel.remove(j);
						
						txtFieldVector.clear();
						labelVector.clear();
						cmbVector.clear();
						
						panel.updateUI();     // AGGIORNA LA GRAFICA DEL PANNELLO
					}
				}
			}
		});
	//////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////// ENTER SU FIELD
		txtNumeroColonne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				btnOk.doClick();
			}
		});
	//////////////////////////////////////////////////////////////////////////////	
	//////////////////////////////////////////////////////////// CREA TABELLA
		btnCrea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				DBManager.query = "CREATE TABLE IF NOT EXISTS " + txtNomeTabella.getText() + "(";
				
				for(int i = 0; i < numeroColonne; i++)
				{
					DBManager.query += txtFieldVector.get(i).getText() + " ";
					DBManager.query += cmbVector.get(i).getSelectedItem().toString() + "(" + txtValVector.get(i).getText() + ") ";
					
					if(rbtnVector.get(i).isSelected())
						DBManager.query += "PRIMARY KEY ";
					
					if(!chkVector.get(i).isSelected())
						DBManager.query += "NOT NULL ";
					
					if(chkVector2.get(i).isSelected())
						DBManager.query += "AUTO_INCREMENT ";
					
					if(!(i + 1 == numeroColonne))
						DBManager.query += ",";
				}
				DBManager.query += ")engine=" + comboBox.getSelectedItem().toString();
				System.out.println(DBManager.query);
				GUI.eseguiQuery(false);
				GUI.aggiornaJTable();
			}
		});
	//////////////////////////////////////////////////////////////////////////////
		txtNomeTabella.setBounds(51, 7, 139, 25);
		txtNomeTabella.setColumns(10);

		txtNumeroColonne.setBounds(526, 7, 60, 25);
		txtNumeroColonne.setColumns(10);
		WebLookAndFeel.install();
		frmNuovaTabella.setTitle("Nuova Tabella");
		frmNuovaTabella.setBounds(100, 100, 700, 300);
		frmNuovaTabella.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNuovaTabella.setResizable(false);
		frmNuovaTabella.setVisible(true);
		frmNuovaTabella.getContentPane().setLayout(null);
		lblNewLabel.setBounds(417, 12, 107, 15);
		
		frmNuovaTabella.getContentPane().add(lblNewLabel);
		
		frmNuovaTabella.getContentPane().add(txtNumeroColonne);
		lblTipoDiEngine.setBounds(207, 12, 86, 15);
		
		frmNuovaTabella.getContentPane().add(lblTipoDiEngine);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"InnoDB" , "MRG_MYISAM" , "BLACKHOLE" , "MyISAM"}));
		comboBox.setBounds(292, 7, 107, 25);
		
		frmNuovaTabella.getContentPane().add(comboBox);
		lblNome.setBounds(12, 12, 36, 15);
		
		frmNuovaTabella.getContentPane().add(lblNome);
		
		frmNuovaTabella.getContentPane().add(txtNomeTabella);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 39, 694, 233);
		
		frmNuovaTabella.getContentPane().add(scrollPane);
		panel.setLayout(null);
		btnCrea.setBounds(623, 7, 60, 25);
		frmNuovaTabella.getContentPane().add(btnCrea);
		btnOk.setIcon(new ImageIcon(TabelleCreator.class.getResource("/dbManager/tick2.png")));
		btnOk.setBounds(583, 7, 25, 25);
		
		frmNuovaTabella.getContentPane().add(btnOk);

	}
}
