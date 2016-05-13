package dbManager;

import javax.swing.JFrame;

import com.alee.laf.WebLookAndFeel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class DatabaseCreator{
	
	private final JFrame frmNuovoDatabase = new JFrame();
	private final JLabel lblNomeDelDatabase = new JLabel("Nome:");
	private final JTextField textField = new JTextField();
	private final JButton btnAggiungi = new JButton("Aggiungi");
	private final JLabel lblSetDiCaratteri = new JLabel("Set di Caratteri:");
	private final JComboBox<String> comboBox = new JComboBox<String>();

	public DatabaseCreator() {
		//////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////// AGGIUNGI
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					String nome = textField.getText();
					nome = nome.trim();
					nome = nome.replaceAll(" ", "_");
					
					DBManager.query = "CREATE DATABASE IF NOT EXISTS " + nome + 
									 " CHARACTER SET "+ comboBox.getSelectedItem().toString().replace("-", "") + 
									 " COLLATE " + comboBox.getSelectedItem().toString().replace("-", "") + "_general_ci" + ";";
					
					GUI.eseguiQuery(false);
				
					DBManager.query = "SHOW DATABASES;";
					GUI.eseguiQuery(false);
					GUI.table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("DATABASES");
				
					try
					{
						Integer.parseInt(nome);
					}
					catch(Exception e){}
				}
				catch(Exception e)
				{}
				
			frmNuovoDatabase.dispose();
			}
		});
		///////////////////////////////////////////////////////////////////////////

		WebLookAndFeel.install();
		textField.setBounds(54, 8, 380, 24);
		textField.setColumns(10);
		frmNuovoDatabase.setResizable(false);
		frmNuovoDatabase.setTitle("Nuovo Database");
		frmNuovoDatabase.setVisible(true);
		frmNuovoDatabase.setBounds(100, 100, 450, 95);
		frmNuovoDatabase.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNuovoDatabase.getContentPane().setLayout(null);
		lblNomeDelDatabase.setBounds(12, 13, 36, 14);
		
		frmNuovoDatabase.getContentPane().add(lblNomeDelDatabase);
		
		frmNuovoDatabase.getContentPane().add(textField);
		btnAggiungi.setBounds(345, 33, 89, 24);
		
		frmNuovoDatabase.getContentPane().add(btnAggiungi);
		lblSetDiCaratteri.setBounds(12, 37, 89, 14);
		
		frmNuovoDatabase.getContentPane().add(lblSetDiCaratteri);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"UTF-8", "UTF-16"}));
		comboBox.setBounds(109, 33, 101, 24);
		
		frmNuovoDatabase.getContentPane().add(comboBox);

	}
}
