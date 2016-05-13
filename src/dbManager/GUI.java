package dbManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.WebLookAndFeel;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.ComponentOrientation;

public class GUI{

    static JFrame frame;
	private JPanel contentPane;
	static final  JTable table = new JTable();
	private final static JScrollPane scrollPane = new JScrollPane();
	private final static  JTextArea textCodice = new JTextArea();
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JButton btnNO = new JButton("");
	private final static JTextArea textConsole = new JTextArea();
	private final JScrollPane scrollPane_2 = new JScrollPane();
	private final JLabel lblImmettiCodice = new JLabel(" Immetti codice:");
	private final JLabel lblConsole = new JLabel(" Console:");
	private final JButton btnOK = new JButton("");
	private final static JComboBox<String> comboBox = new JComboBox<String>();
	private final static JTextField txtCerca = new JTextField();
	private final static JButton btnCerca = new JButton("Cerca");
	private final static JButton btnMostraDatabase = new JButton("");
	final static JButton btnMostraTabelle = new JButton("");
	private final static JButton btnImpostazioniGUI = new JButton("");
	private final JButton btnInserisci = new JButton("Inserisci");
	private final JButton btnElimina = new JButton("Elimina");
	private final JButton btnModifica = new JButton("Modifica");
	static private final JButton btnAggiorna = new JButton("");
	private final static JButton btnOrdina = new JButton("");
	
	static String database;
	public static String tabelliere;
	private static String UltimoElemento;
	
	static Vector<String> daEliminare = new Vector<String>(1,1);
	
	private static int indiceCombo;
	
	public static void aggiornaCombo()					 // AGGIUNGE ELEMENTI AL COMBOBOX 
	{
		for(Object obj : DBManager.titoli)
		{
			comboBox.addItem(obj.toString());
		}
	}
	public static void aggiustaLarghezza(JTable table)	 // ADATTA LA TABELLA IN BASE AI VALORI PIU LUNGHI
	{
		int max = 0;
		Vector<Integer> larghezze = new Vector<Integer>(1,1);
		Font font = table.getFont() ; // PRENDO IL FONT DELLA TABELLA
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); // CREO UN'IMMAGINE VIRTUALE
		FontMetrics fm = img.getGraphics().getFontMetrics(font); // CREO UN OGGETTO FONTMETRICS CHE HA LE PROPRIETA' DI UN' IMMAGINE E DI UN FONT
		
		int numeroColonne = table.getColumnModel().getColumnCount(); // NUMERO DI COLONNE
		
		if(numeroColonne == 1) // SE C'E' UNA SOLA COLONNA
		{
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // OCCUPA TUTTO LO SPAZIO
			table.getColumnModel().getColumn(0).setPreferredWidth(scrollPane.getWidth());
		}
		
		else // TUTTE LE ALTRE
		{		
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // CI CONCEDE SPAZIO ARBITRARIO
			for(int i = 0; i < numeroColonne; i++) 
			{			
				int min = fm.stringWidth(table.getColumnModel().getColumn(i).getHeaderValue().toString()); // METTE COME MINIMA LARGHEZZA LA LARGHEZZA DEL TITOLO
		
				for(int k = 0; k <table.getRowCount(); k++) // PER OGNI COLONNA NELLA TABELLA 
				{											// ACQUISISCE LA LUNGHEZZA DI OGNI STRINGA
					try
					{
						larghezze.add(fm.stringWidth(table.getValueAt(k, i).toString()));     // PUO CAPITARE CHE IL VALORE SIA NULLO..
					}																		  // ECCO PERCHE' ACQUISISCE UN PUNTO
					catch(Exception e)
					{
						larghezze.add(fm.stringWidth("."));
					}
				}
				
				max = 0;
				for(int j = 0; j < larghezze.size(); j++)
				{
					if(larghezze.get(j) > max)
						max = larghezze.get(j);
				}

				larghezze.removeAllElements();
				
				if(max < min + 35)
					table.getColumnModel().getColumn(i).setPreferredWidth(min+35);
				
				else
					table.getColumnModel().getColumn(i).setPreferredWidth(max+20);
			
//				System.out.println(table.getColumnModel().getColumn(i).getHeaderValue().toString() + ": " + table.getColumnModel().getColumn(i).getPreferredWidth());
			}
		}
		
	}
	@SuppressWarnings("serial")
	public static void eseguiQuery(boolean usaCombobox)  // CUORE DEL PROGRAMMA, AGGIORNA LA TABELLA CON I DATI DI mySQL
	{
		try 
		{
//			textConsole.append(DBManager.query + "\n");
			DBManager.st.execute(DBManager.query);          // MANDA LA QUERY AL MOTORE mySQL
			DBManager.ris = DBManager.st.getResultSet();    // PRENDE IL RESPONSO
			DBManager.rsmd = DBManager.ris.getMetaData();	// E LA SUA METADATA
			DBManager.colonne = DBManager.rsmd.getColumnCount();
			DBManager.titoli.clear();
			DBManager.dati.clear();
				
			for(int i = 1; i <= DBManager.colonne; i++) // COSTRUISCE I TITOLI DELLA TABELLA
			{
				DBManager.titoli.addElement(DBManager.rsmd.getColumnName(i));
			}
				
			while(DBManager.ris.next()) // RIEMPIE I DATI DELLA TABELLA
			{
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= DBManager.colonne; columnIndex++) 
			    {
					vector.add(DBManager.ris.getObject(columnIndex));
			    }
				DBManager.dati.addElement(vector);
			}
				
			DefaultTableModel modello = new DefaultTableModel(DBManager.dati,DBManager.titoli) 
			{
				@Override
				public boolean isCellEditable(int row, int column) 
				{
			       return false;
			    }
			};

			table.setModel(modello);
			aggiustaLarghezza(table);
				
			comboBox.removeAllItems();
			if (usaCombobox == true)
			{
				aggiornaCombo();
				comboBox.setSelectedItem(indiceCombo);
			}
		} 
		catch(Exception e) 
		{e.printStackTrace();}
		
	}
	public static void aggiornaJTable()                  // FA IL REFRESH
	{
		if(table.getColumnModel().getColumn(0).getHeaderValue().toString().startsWith("DATABASES"))
		{
			txtCerca.setEnabled(false);
			btnCerca.setEnabled(false);
			comboBox.setEnabled(false);
			btnOrdina.setEnabled(false);
			
			DBManager.query = "SHOW DATABASES;";
			eseguiQuery(false);
			table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("DATABASES");
		}
		
		else if(table.getColumnModel().getColumn(0).getHeaderValue().toString().startsWith("TABELLE"))
		{
			txtCerca.setEnabled(false);
			btnCerca.setEnabled(false);
			comboBox.setEnabled(false);
			btnOrdina.setEnabled(false);
			
			DBManager.query = "SHOW TABLES;";
			eseguiQuery(false);
			table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("TABELLE IN '" + database + "'");
			frame.setTitle("Data Manager Pro - " + database);
		}
		
		else
		{
			txtCerca.setEnabled(true);
			btnCerca.setEnabled(true);
			comboBox.setEnabled(true);
			btnOrdina.setEnabled(true);
			
			DBManager.query = "SELECT * FROM " + tabelliere +";";
			eseguiQuery(true);
		}
	}
	public static void aggiustaBottone(JButton button)   // RENDE I BOTTONI ADATTI ALLE IMMAGINI
	{
		button.setBorderPainted(false); 
		button.setContentAreaFilled(false); 
		button.setFocusPainted(false); 
		button.setOpaque(false);
	}
	
	public String ultimaParola(JTextArea area)   		 // ULTIMA PAROLA DEL CODICE, SALTA ULTIMO CARATTERE
	{
		String stringa = area.getText().substring(DBManager.query.lastIndexOf(" ")+1, DBManager.query.length()-1);
		return stringa;
	}

	public GUI() throws SQLException 
	{
	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////// ESEGUI CODICE
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				DBManager.query = textCodice.getText();
				if(DBManager.query.startsWith("USE"))
				{
					try 
					{
						btnCerca.setEnabled(false);
						btnOrdina.setEnabled(false);
						Vector<String> databases = new Vector<String>(1,1);          // CREO UN VETTORE DI STRINGHE
						ResultSet cic = DBManager.conn.getMetaData().getCatalogs();  // RESULT SET DAL DRIVER
						
						while(cic.next())
						{
							databases.addElement(cic.getString(1));			         // AGGIUNGE AL VET TUTTI I DATABASE
						}               
							
						tabelliere = "";											 // AZZERA TABELLIERE
						database = ultimaParola(textCodice);
					
						if(databases.contains(database))                             // SE IL DATABASE CHE HAI SCELTO NON ESISTE, NON FARE NULLA
						{
							eseguiQuery(false);
							DBManager.query = "SHOW TABLES";
							eseguiQuery(false);											 
							table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("TABELLE IN '" + database + "'");
							btnMostraTabelle.setEnabled(true);
						}
						else
						{
							textConsole.setText("Il database '" + database + "' non esiste");
						}
					} 
					catch (SQLException e) 
					{
						e.printStackTrace();
					}
				}
				else if(DBManager.query.startsWith("SHOW"))
				{
					txtCerca.setEnabled(false);
					btnCerca.setEnabled(false);
					comboBox.setEnabled(false);
					btnOrdina.setEnabled(false);
					eseguiQuery(false);
					
					if(DBManager.query.startsWith("SHOW DATA"))
						table.getColumnModel().getColumn(0).setHeaderValue("DATABASES");
					else
						table.getColumnModel().getColumn(0).setHeaderValue("TABELLE IN '" + database + "'");
						
				}
					
				else if(DBManager.query.startsWith("SELECT *"))               // FUNZIONE SELECT,
				{															  // COME SOPRA MA CON TABELLE
					try
					{
						btnCerca.setEnabled(true);
						btnOrdina.setEnabled(true);
						tabelliere = ultimaParola(textCodice);
						eseguiQuery(true);
					}
					catch(Exception r)
					{
						r.printStackTrace();
					}
				}
				
				else
				{
					eseguiQuery(true);
				}
			}
	});
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////// PULISCI CODICE
		btnNO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				textCodice.setText("");
			}
		});
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////// CERCA - RESPONSO COMBO
		btnCerca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				String cerca = txtCerca.getText().trim();
				UltimoElemento = comboBox.getSelectedItem().toString();
				
				if(comboBox.getSelectedItem().toString() == "...")
				{	
					DBManager.query = "SELECT * FROM " + tabelliere + ";";
					
					txtCerca.setText("");
					eseguiQuery(true);
				}
				
				else if(cerca.startsWith("<") || cerca.startsWith(">") || cerca.startsWith("<=") || cerca.startsWith(">=") || cerca.startsWith("="))
				{
					DBManager.query = "SELECT * FROM "+tabelliere+" WHERE "
							+ (String) comboBox.getSelectedItem() + " " + cerca;
					eseguiQuery(true);
				}
				
				else if(txtCerca.getText().startsWith("@"))
				{
					try
					{
						DBManager.query = "SELECT * FROM "+tabelliere+
										  " WHERE "+(String) comboBox.getSelectedItem() +
										  " LIKE '"+ cerca.substring(1, cerca.length()) +"';";
						eseguiQuery(true);	
					}catch(Exception e){}	
				}
				
				else
				{
					try
					{
						DBManager.query = "SELECT * FROM "+tabelliere+
										  " WHERE "+(String) comboBox.getSelectedItem()+
										  " LIKE '%"+txtCerca.getText()+"%';";
						eseguiQuery(true);	
					}catch(Exception e){}	
				}
				
				comboBox.setSelectedItem(UltimoElemento);
			}
		});
		
		
		// AUTO-AGGIORNATORE DEL COMBOBOX
		/*comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(comboBox.getSelectedItem().toString() == "...")
				{
					btnCerca.setText("Aggiorna");
				}
				else if(DBManager.titoli.contains(comboBox.getSelectedItem()))
				{
					btnCerca.setText("Cerca");
				}
			}
		});*/
		
	////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////// MOSTRA DATABASE	
		btnMostraDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				txtCerca.setEnabled(false);
				txtCerca.setText("");
				btnCerca.setEnabled(false);
				comboBox.setEnabled(false);
				btnOrdina.setEnabled(false);
				
				DBManager.query = "SHOW DATABASES;";
				eseguiQuery(false);
				table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("DATABASES");
			}
		});

	////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////// MOSTRA TABELLE	
		btnMostraTabelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					txtCerca.setEnabled(false);
					txtCerca.setText("");
					btnCerca.setEnabled(false);
					comboBox.setEnabled(false);
					btnOrdina.setEnabled(false);
					
					DBManager.query = "SHOW TABLES;";
					eseguiQuery(false);
					table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("TABELLE IN '" + database + "'");
					frame.setTitle("Data Manager Pro - " + database);
				}
				catch(Exception g)
				{
					table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("DATABASES");
				}
			}
		});
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////// INSERISCI
		btnInserisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(table.getColumnModel().getColumn(0).getHeaderValue() == "DATABASES")
				{
					new DatabaseCreator();
//					frame.setEnabled(false);
				}
				
				else if(table.getColumnModel().getColumn(0).getHeaderValue().toString().startsWith("TABELLE"))
				{
					new TabelleCreator();
//					frame.setEnabled(false);
				}
				
				else
				{
					new ElementoCreator();
//					frame.setEnabled(false);
				}
			}
		});
	/////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////// ELIMINA VOCE
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				GUI.daEliminare.removeAllElements();
				
				if(table.getColumnModel().getColumn(0).getHeaderValue().toString().startsWith("DATABASES"))
				{
					if(table.getSelectedRows().length > 1)
					{
						for(int i : table.getSelectedRows())
						{
							daEliminare.add((String) table.getValueAt(i, 0));
						}
						new ConfermaPericolosa("database+");
					}
					
					else
					{
						daEliminare.add((String) table.getValueAt(table.getSelectedRow(), 0));
						new ConfermaPericolosa("database");
					}
				}
				
				if(table.getColumnModel().getColumn(0).getHeaderValue().toString().startsWith("TABELLE"))
				{
					if(table.getSelectedRows().length > 1)
					{
						for(int i : table.getSelectedRows())
						{
							daEliminare.add((String) table.getValueAt(i, 0));
						}
						new ConfermaPericolosa("tabelle+");
					}
					
					else
					{
						daEliminare.add((String) table.getValueAt(table.getSelectedRow(), 0));
						new ConfermaPericolosa("tabelle");
					}
				}
				
				if(!table.getColumnModel().getColumn(0).getHeaderValue().toString().startsWith("TABELLE") && !table.getColumnModel().getColumn(0).getHeaderValue().toString().startsWith("DATABASES"))
				{
					if(table.getSelectedRows().length > 1)
					{
						new ConfermaPericolosa("riga+");
					}
					
					else
					{
						new ConfermaPericolosa("riga");
					}
				}
			}
		});
	/////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////// MODIFICA
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(table.getColumnModel().getColumn(0).getHeaderValue().toString().startsWith("DATABASES"))
				{
					for(int i : table.getSelectedRows())
					{
						new ModificaDatabase(table.getValueAt(i, 0).toString());
					}
				}
			}
		});
	////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////// DOPPIO CLICK
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		        JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        int clicks = me.getClickCount();
		        
		        if (clicks == 2) 
		        {
		        	if(table.getColumnModel().getColumn(0).getHeaderValue().toString().startsWith("TABELLE"))
		        	{
			        	tabelliere = table.getModel().getValueAt(row, 0).toString();
		        		DBManager.query = "SELECT * FROM " + tabelliere + ";";
			            eseguiQuery(true);
			            
			            txtCerca.setEnabled(true);
			            btnCerca.setEnabled(true);
			            comboBox.setEnabled(true);
			            btnOrdina.setEnabled(true);
		        	}
		        	
		        	if(table.getColumnModel().getColumn(0).getHeaderValue().toString().startsWith("DATABASES"))
		        	{
			        	database = table.getModel().getValueAt(row, 0).toString();
		        		DBManager.query = "USE " + database + ";";
			            eseguiQuery(false);
			            
			            DBManager.query = "SHOW TABLES;";
			            eseguiQuery(false);
			            
			            btnMostraTabelle.setEnabled(true);
			            txtCerca.setEnabled(false);
			            btnCerca.setEnabled(false);
			            comboBox.setEnabled(false);
			            btnOrdina.setEnabled(false);
			            
			            table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("TABELLE IN '" + database + "'");
			            frame.setTitle("Data Manager Pro - " + database);
		        	}
		        	
		        } 
		    }
		});
	//////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////// AGGIORNA
		btnAggiorna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				aggiornaJTable();
			}
		});
	//////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////// ORDINA
		btnOrdina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				new OrdinaTabella();
			}
		});
	//////////////////////////////////////////////////////////////////////////////////
		WebLookAndFeel.install();
	    frame = new JFrame();
	    frame.setResizable(false);
		frame.setVisible(true);
		btnImpostazioniGUI.setToolTipText("Impostazioni");
		
		btnImpostazioniGUI.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnImpostazioniGUI.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnImpostazioniGUI.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImpostazioniGUI.setDefaultCapable(false);
		btnImpostazioniGUI.setIcon(new ImageIcon(GUI.class.getResource("/dbManager/impostazioni-.png")));
		btnImpostazioniGUI.setBounds(957, 530, 25, 25);
		btnImpostazioniGUI.setContentAreaFilled(false);
		
		btnMostraTabelle.setHorizontalTextPosition(SwingConstants.CENTER);
		btnMostraTabelle.setEnabled(false);
		btnMostraTabelle.setToolTipText("Mostra tabelle nel database corrente");
		btnMostraTabelle.setIcon(new ImageIcon(GUI.class.getResource("/dbManager/table.png")));
		
		btnMostraDatabase.setToolTipText("Mostra tutti i databases");
		btnMostraDatabase.setIcon(new ImageIcon(GUI.class.getResource("/dbManager/db.png")));
		
		txtCerca.setEnabled(false);
		btnCerca.setEnabled(false);
		comboBox.setEnabled(false);
		btnOrdina.setEnabled(false);
		
		txtCerca.setBounds(607, 469, 374, 25);
		txtCerca.setColumns(10);
		frame.setTitle("Data Manager Pro");
		
		new DBManager();

		/// TABELLA	
		table.getTableHeader().setReorderingAllowed(false);       
		table.getTableHeader().setResizingAllowed(false);
		
		DBManager.query = "SHOW DATABASES;";				// APRE IL PROGRAMMA CON LA LISTA DI DATABASE
		eseguiQuery(false);
		table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("DATABASES");
		// FINE TABELLA
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1000, 600);
		frame.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollPane.setDoubleBuffered(true);
		scrollPane.setBounds(0, 0, 994, 430);
		
		contentPane.add(scrollPane);
		table.setDragEnabled(true);
		table.setFillsViewportHeight(true);
		table.setDoubleBuffered(true);
		table.setSelectionBackground(SystemColor.activeCaption);

		scrollPane.setViewportView(table);
		scrollPane_1.setBounds(12, 461, 260, 98);
				contentPane.add(scrollPane_1);
		scrollPane_1.setViewportView(textCodice);
		
		btnNO.setIcon(new ImageIcon(GUI.class.getResource("/dbManager/red-x.png.gif")));
		btnOK.setIcon(new ImageIcon(GUI.class.getResource("/dbManager/tick2.png")));
		btnNO.setBounds(272, 514, 25, 45);

		contentPane.add(btnNO);
		scrollPane_2.setBounds(321, 461, 275, 98);
		
		contentPane.add(scrollPane_2);
		scrollPane_2.setViewportView(textConsole);
		lblImmettiCodice.setBounds(12, 442, 93, 15);
		
		contentPane.add(lblImmettiCodice);
		lblConsole.setBounds(321, 442, 93, 15);
		
		contentPane.add(lblConsole);
		btnOK.setBounds(272, 461, 25, 45);
		
		contentPane.add(btnOK);
		comboBox.setBounds(694, 443, 260, 25);
		
		contentPane.add(comboBox);
		
		contentPane.add(txtCerca);
		btnCerca.setBounds(607, 443, 87, 25);
		
		contentPane.add(btnCerca);
		btnMostraDatabase.setBounds(929, 530, 25, 25);
		
		contentPane.add(btnMostraDatabase);
		btnMostraTabelle.setBounds(901, 530, 25, 25);
		
		contentPane.add(btnMostraTabelle);
		btnInserisci.setBounds(799, 505, 183, 25);
		
		contentPane.add(btnInserisci);
		btnElimina.setBounds(607, 505, 183, 25);
		
		contentPane.add(btnElimina);
		btnModifica.setBounds(607, 530, 183, 25);
		
		contentPane.add(btnModifica);
		
		contentPane.add(btnImpostazioniGUI);
		btnAggiorna.setIcon(new ImageIcon(GUI.class.getResource("/dbManager/refresh.png")));
		btnAggiorna.setToolTipText("Mostra tabelle nel database corrente");
		btnAggiorna.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAggiorna.setBounds(799, 530, 25, 25);
		
		contentPane.add(btnAggiorna);
		btnOrdina.setIcon(new ImageIcon(GUI.class.getResource("/dbManager/24967-200.png")));
		btnOrdina.setBounds(956, 443, 25, 25);
		
		contentPane.add(btnOrdina);
	}
}
