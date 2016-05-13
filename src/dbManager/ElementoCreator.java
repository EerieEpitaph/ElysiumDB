package dbManager;

import java.awt.Dimension;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.alee.laf.WebLookAndFeel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.ScrollPaneConstants;

public class ElementoCreator
{
	private final JFrame frame = new JFrame();
	private final JTable table = new JTable();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JPanel panel = new JPanel();
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JButton btnInserisci = new JButton("Inserisci");
	
	Vector<JLabel> labelVector = new Vector<JLabel>(1,1);
	Vector<JTextField> txtFieldVector = new Vector<JTextField>(1,1);
	Vector<JLabel> labelVector1 = new Vector<JLabel>(1,1);
	Vector<JLabel> labelVector2 = new Vector<JLabel>(1,1);
	Vector<JCheckBox> chkVector = new Vector<JCheckBox>(1,1);
	Vector<JCheckBox> chkVector1 = new Vector<JCheckBox>(1,1);
	
	private final JLabel lblConsole = new JLabel("Console:");
	private final JTextArea console = new JTextArea();
	
	public ElementoCreator()
	{
		console.setEditable(false);
		console.setBorder(new LineBorder(Color.LIGHT_GRAY));
		console.setBounds(58, 225, 494, 25);
		console.setColumns(10);
	/////////////////////////////////////////////////////////
	/////////////////////////////////////////// INSERISCI
		btnInserisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					DBManager.query = "INSERT INTO " + GUI.tabelliere + "(";  //INIZIA A COSTRUIRE LA QUERY
					for(int i = 0; i < GUI.table.getColumnCount(); i++)
					{
						if(i+1 == GUI.table.getColumnCount())                             //ACQUISISCE LE COLONNE
							DBManager.query += GUI.table.getColumnName(i) + ") VALUES(";  //DELLA TABELLA
						else
							DBManager.query += GUI.table.getColumnName(i) + ",";
					}
					
					for(int i = 0; i < txtFieldVector.size(); i++)
					{
						if(i+1 == txtFieldVector.size())// SE E' L'ULTIMO
						{								// SE E' UN CHAR O BLOB AGGIUNGE LE """"
							if((DBManager.rsmd.isNullable(i+1) == 1 || DBManager.rsmd.isAutoIncrement(i+1)) && txtFieldVector.elementAt(i).getText().trim().equals(""))
							{//SE HAI LASCIATO VUOTO ED E' NULL C:
								DBManager.query += "null" + ");";
							}
							else
							{
								if(labelVector1.elementAt(i).getText().contains("CHAR") || labelVector1.elementAt(i).getText().contains("BLOB"))
									DBManager.query += "'" + txtFieldVector.elementAt(i).getText() + "'" + ");";
								
								else
									DBManager.query += txtFieldVector.elementAt(i).getText() + ");";	
							}
						}
						else //SE NON E' L'ULTIMO, POI STESSA SCELTA DI PRIMA
						{
							if((DBManager.rsmd.isNullable(i+1) == 1 || DBManager.rsmd.isAutoIncrement(i+1)) && txtFieldVector.elementAt(i).getText().trim().equals(""))
							{//SE HAI LASCIATO VUOTO ED E' NULL C:
								DBManager.query += "null" + ",";
							}
							else
							{
								if(labelVector1.elementAt(i).getText().contains("CHAR") == true || labelVector1.elementAt(i).getText().contains("BLOB") == true)
									DBManager.query += "'" + txtFieldVector.elementAt(i).getText() + "'" + ",";
								
								else
									DBManager.query += txtFieldVector.elementAt(i).getText() + ",";
							}
						}
					}
					System.out.println(DBManager.query);
					GUI.eseguiQuery(true);
					GUI.aggiornaJTable();
				}
				catch(Exception e)
				{}
			}
		});
	/////////////////////////////////////////////////////////
		WebLookAndFeel.install();
		scrollPane.setBounds(10, 11, 634, 239);
		scrollPane.setViewportView(table);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle("Inserisci Entrata");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 670, 289);
		frame.getContentPane().setLayout(null);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(0, 0, 654, 214);
		
		frame.getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(panel);
		panel.setLayout(null);
		btnInserisci.setBounds(563, 225, 85, 25);
		frame.getContentPane().add(btnInserisci);
		lblConsole.setBounds(10, 230, 46, 15);
		
		frame.getContentPane().add(lblConsole);
		
		frame.getContentPane().add(console);
		
		int sup1 = 11;
		int sup2 = 6;
		
		if(GUI.table.getColumnCount() > 8)
			panel.setPreferredSize(new Dimension(654, 233 + (GUI.table.getColumnCount() - 9) * 25));
			
		for(int i = 1; i < GUI.table.getColumnCount()+1; i++) // FINCHE' CI SONO COLONNE NELLA TABELLA
		{
			try
			{
				JLabel tmpLbl = new JLabel(GUI.table.getColumnName(i-1));
				JTextField tmpTxt = new JTextField();
				JLabel tmpLbl1 = new JLabel(DBManager.rsmd.getColumnTypeName(i));
				JCheckBox tmpChk = new JCheckBox("null");
				JCheckBox tmpChk1 = new JCheckBox("auto-increment");
				
				if(DBManager.rsmd.getColumnTypeName(i).contains("FLOAT") || DBManager.rsmd.getColumnTypeName(i).contains("DOUBLE") || DBManager.rsmd.getColumnTypeName(i).contains("DECIMAL"))
				{
					JLabel tmpLbl2 = new JLabel("(" + Integer.toString(DBManager.rsmd.getPrecision(i)) + "," + DBManager.rsmd.getScale(i) + ")");
					tmpLbl2.setBounds(340, sup1, 50, 15);
					panel.add(tmpLbl2);
					labelVector2.addElement(tmpLbl2);
				}
				else
				{
					JLabel tmpLbl2 = new JLabel("(" + Integer.toString(DBManager.rsmd.getPrecision(i)) + ")");
					tmpLbl2.setBounds(340, sup1, 50, 15);
					panel.add(tmpLbl2);
					labelVector2.addElement(tmpLbl2);
				}

				tmpLbl.setBounds(10, sup1, 136, 15);
				tmpTxt.setBounds(156, sup2, 110, 25);
				tmpLbl1.setBounds(265, sup1, 64, 15);
				tmpChk.setBounds(400, sup1, 45, 16);
				tmpChk1.setBounds(460, sup1, 104, 16);
				
				tmpChk.setEnabled(false);
				tmpChk1.setEnabled(false);
				
				if(DBManager.rsmd.isNullable(i) == ResultSetMetaData.columnNullable)
					tmpChk.setSelected(true);
				else
					tmpChk.setSelected(false);
				
				if(DBManager.rsmd.isAutoIncrement(i))
					tmpChk1.setSelected(true);
				else
					tmpChk1.setSelected(false);
				
				panel.add(tmpLbl);
				panel.add(tmpTxt);
				panel.add(tmpLbl1);
				panel.add(tmpChk);
				panel.add(tmpChk1);
				
				labelVector.add(tmpLbl);
				txtFieldVector.add(tmpTxt);
				labelVector1.add(tmpLbl1);
				chkVector.add(tmpChk);
				chkVector.add(tmpChk1);
				
				sup1 += 25;
				sup2 += 25;
				panel.updateUI();
			}
			catch(Exception e)
			{e.printStackTrace();}
		}
	}
}
