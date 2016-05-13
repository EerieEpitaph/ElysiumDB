package dbManager;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.WebLookAndFeel;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class ModificaDatabase
{
	private JFrame frmModificaDatabase = new JFrame();
	private JPanel contentPane;
	private final JLabel lblModificaIlDatabase = new JLabel("Modifica il database: ");
	private final JLabel lblNome_1 = new JLabel("");
	private final JLabel lblNome = new JLabel("Nome:");
	private final JSeparator separator = new JSeparator();
	private final JLabel lblCharset = new JLabel("Set di caratteri:");
	private final JTextField textNome = new JTextField();
	private final JComboBox<String> comboBox = new JComboBox<String>();
	private final JButton btnOk = new JButton("Ok!");

	Vector<Vector<Object>> dati = new Vector<Vector<Object>>(1,1);
	
	public ModificaDatabase(String str)
	{
		//////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////// MODIFICA EFFETTUATA
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				String nome = textNome.getText();
				nome = nome.trim();
				nome = nome.replaceAll(" ", "_");
				
				DBManager.query = "USE " + str + ";";
				GUI.eseguiQuery(false);
				
				DBManager.query = "SHOW TABLES;";
				GUI.eseguiQuery(false);
				
				dati = DBManager.dati;
				
//				DBManager.query = "DROP DATABASE " + str + ";";
//				GUI.aggiorna(false);
				
				DBManager.query = "CREATE DATABASE IF NOT EXISTS " + nome + 
								  " CHARACTER SET "+ comboBox.getSelectedItem().toString().replace("-", "") + 
								  " COLLATE " + comboBox.getSelectedItem().toString().replace("-", "") + "_general_ci" + ";";
				GUI.eseguiQuery(true);

				for(Vector<Object> h : dati)
				{			
					for(Object i : h)
					{
						DBManager.query = "RENAME TABLE " + str + "." + i.toString() + " TO " + nome + "." + i.toString() + ";";
						GUI.eseguiQuery(false);
					}	
				}
				
				DBManager.query = "DROP DATABASE " + str + ";";
				GUI.eseguiQuery(false);
				
				DBManager.query = "SHOW DATABASES;";
				GUI.eseguiQuery(false);
				GUI.table.getColumnModel().getColumn(0).setHeaderValue("DATABASES");
				
				DBManager.query = "";
				frmModificaDatabase.dispose();
			}
		});
		/////////////////////////////////////////////////////////////////////////

		WebLookAndFeel.install();
		textNome.setBounds(105, 44, 329, 25);
		textNome.setColumns(10);
		frmModificaDatabase.setTitle("Modifica Database");
		frmModificaDatabase.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmModificaDatabase.setBounds(100, 100, 450, 130);
		frmModificaDatabase.setVisible(true);
		frmModificaDatabase.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmModificaDatabase.setContentPane(contentPane);
		contentPane.setLayout(null);
		lblModificaIlDatabase.setBounds(10, 11, 112, 14);
		
		contentPane.add(lblModificaIlDatabase);
		lblNome_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNome_1.setBounds(134, 11, 300, 14);
		
		contentPane.add(lblNome_1);
		lblNome.setBounds(10, 49, 36, 14);
		
		contentPane.add(lblNome);
		separator.setBounds(10, 36, 424, 2);
		
		contentPane.add(separator);
		lblCharset.setBounds(10, 74, 85, 15);
		
		contentPane.add(lblCharset);
		
		contentPane.add(textNome);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"UTF-8", "UTF-16"}));
		comboBox.setBounds(105, 69, 132, 25);
		
		contentPane.add(comboBox);
		btnOk.setBounds(349, 69, 85, 25);
		
		contentPane.add(btnOk);
		lblNome_1.setText(str);
		textNome.setText(str);
		
	}
}
