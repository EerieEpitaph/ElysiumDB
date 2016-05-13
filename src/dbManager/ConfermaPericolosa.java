package dbManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.WebLookAndFeel;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;

import java.awt.Font;
import java.awt.ComponentOrientation;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JScrollPane;

public class ConfermaPericolosa{
	
	public JFrame frmEliminaDatabase = new JFrame();
	private JPanel contentPane;
	private final JLabel lblSicuroDiVoler = new JLabel();
	private final JButton btnNewButton = new JButton("No");
	private final JButton btnNewButton_1 = new JButton("S\u00EC");
	private final JSeparator separator = new JSeparator();
	private final JScrollPane scrollPane = new JScrollPane();

	public ConfermaPericolosa(String tipo) {
		////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////// SI!
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(tipo == "database")
				{
					DBManager.query = "DROP DATABASE IF EXISTS " + GUI.daEliminare.firstElement() + ";";
					GUI.eseguiQuery(false);
				
					GUI.aggiornaJTable();
				
					if(GUI.daEliminare.contains(GUI.database))
					{
						GUI.btnMostraTabelle.setEnabled(false);
					}
					frmEliminaDatabase.dispose();
				}
				
				if(tipo == "database+")
				{
					for(String i : GUI.daEliminare)
					{
						DBManager.query = "DROP DATABASE IF EXISTS " + i + ";";
						GUI.eseguiQuery(false);
					}
					
					GUI.aggiornaJTable();
					
					if(GUI.daEliminare.contains(GUI.database))
					{
						GUI.btnMostraTabelle.setEnabled(false);
					}
					frmEliminaDatabase.dispose();
				}
				
				if(tipo == "tabelle")
				{
					DBManager.query = "DROP TABLE IF EXISTS " + GUI.daEliminare.firstElement() + ";";
					GUI.eseguiQuery(false);
				
					GUI.aggiornaJTable();
					frmEliminaDatabase.dispose();
				}
				
				if(tipo == "tabelle+")
				{
					for(String i : GUI.daEliminare)
					{
						DBManager.query = "DROP TABLE IF EXISTS " + i + ";";
						GUI.eseguiQuery(false);
					}
					
					GUI.aggiornaJTable();
					frmEliminaDatabase.dispose();
				}
				
				if(tipo == "riga" || tipo == "riga+")
				{
					try
					{
						int[] righe = GUI.table.getSelectedRows();
						for(int k : righe)
						{	
							DBManager.query = "DELETE FROM " + GUI.tabelliere +  " WHERE ";
							for(int i = 0; i < GUI.table.getColumnCount(); i++)
							{
								DBManager.query += GUI.table.getColumnName(i) + "=";
							
								if(DBManager.rsmd.getColumnTypeName(i+1).toString().contains("CHAR") == true || DBManager.rsmd.getColumnTypeName(i+1).toString().contains("BLOB") == true)
								{
									DBManager.query += " '" + GUI.table.getValueAt(k, i) + "' ";
								}
								else
								{
									DBManager.query += " " + GUI.table.getValueAt(k, i) + " ";
								}
								
								if(!(i+1 == GUI.table.getColumnCount()))
								{
									DBManager.query += "AND ";
								}
							}
							
							DBManager.query += ";";
							GUI.eseguiQuery(true);
//							System.out.println(DBManager.query);
						}
						GUI.eseguiQuery(true);
						DBManager.query = "ALTER TABLE " + GUI.tabelliere + " AUTO_INCREMENT=1;";
						GUI.eseguiQuery(true);
						GUI.aggiornaJTable();
						frmEliminaDatabase.dispose();
					}
					catch (SQLException e){}
				}
				 
			}
		});
		////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////// NO!
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(tipo == "database" || tipo == "database+" || tipo == "tabelle" || tipo == "tabelle+" || tipo == "riga" || tipo == "riga+")
				{	
					frmEliminaDatabase.dispose();
				}
			}
		});
		////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////// ATTENSCION
		if(tipo == "database")
		{
			frmEliminaDatabase.setTitle("Elimina Database");
			lblSicuroDiVoler.setText("Sicuro di voler eliminare il database '" + GUI.daEliminare.firstElement() + "'?");
		}
		
		if(tipo == "database+")
		{
			frmEliminaDatabase.setTitle("Elimina Databases");
			String popolaLabel = "Sicuro di voler eliminare i databases:";
			
			for(String i : GUI.daEliminare)
			{
				popolaLabel += " '" + i + "'; ";
			}
			
			popolaLabel += "?";
			lblSicuroDiVoler.setText(popolaLabel);
		}
		
		if(tipo == "tabelle")
		{
			frmEliminaDatabase.setTitle("Elimina Tabella");
			lblSicuroDiVoler.setText("Sicuro di voler eliminare la tabella '" + GUI.daEliminare.firstElement() + "'?");
		}
		
		if(tipo == "tabelle+")
		{
			frmEliminaDatabase.setTitle("Elimina Tabelle");
			String popolaLabel = "Sicuro di voler eliminare le tabelle:";
			
			for(String i : GUI.daEliminare)
			{
				popolaLabel += " '" + i + "'; ";
			}
			
			popolaLabel += "?";
			lblSicuroDiVoler.setText(popolaLabel);
		}
		
		if(tipo == "riga" || tipo == "riga+")
		{
			frmEliminaDatabase.setTitle("Elimina Riga");
			String popolaLabel = "Eliminare gli elementi selezionati?";
			lblSicuroDiVoler.setText(popolaLabel);
		}
		
//		if(tipo == "riga+")
//		{
//			frmEliminaDatabase.setTitle("Elimina Righe");
//			String popolaLabel = "Sicuro di voler eliminare queste righe?";
//			lblSicuroDiVoler.setText(popolaLabel);
//		}
		/////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////// IN CHIUSURA
		frmEliminaDatabase.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) 
			{
				GUI.daEliminare.removeAllElements();
			}
		});
		////////////////////////////////////////////////////////////////////
		
		WebLookAndFeel.install();
		frmEliminaDatabase.setVisible(true);
		frmEliminaDatabase.setResizable(false);
		frmEliminaDatabase.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmEliminaDatabase.setBounds(100, 100, 450, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmEliminaDatabase.setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollPane.setBounds(12, 12, 420, 52);
		
		contentPane.add(scrollPane);
		scrollPane.setViewportView(lblSicuroDiVoler);
		lblSicuroDiVoler.setHorizontalAlignment(SwingConstants.CENTER);
		lblSicuroDiVoler.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblSicuroDiVoler.setFont(new Font("Verdana", Font.BOLD, 13));
		btnNewButton.setBounds(12, 86, 200, 25);
		
		contentPane.add(btnNewButton);
		btnNewButton_1.setBounds(232, 86, 200, 25);
		
		contentPane.add(btnNewButton_1);
		separator.setBounds(22, 73, 420, 2);
		
		contentPane.add(separator);
		

	}
}
