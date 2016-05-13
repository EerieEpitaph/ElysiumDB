package dbManager;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.WebLookAndFeel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OrdinaTabella
{
	private JFrame frmOrdinaTabella = new JFrame();
	private JPanel contentPane;
	private final JLabel lblOrdinaLaColonna = new JLabel("Ordina la colonna:");
	private final JComboBox<String> cmbColonne = new JComboBox<String>();
	private final JRadioButton rdbtnAscendenti = new JRadioButton("ascendente");
	private final JRadioButton rdbtnDiscendenti = new JRadioButton("discendente");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JButton btnNewButton = new JButton("Ok!");

	public OrdinaTabella()
	{
	/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////// ORDINA
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				DBManager.query = "SELECT * FROM " + GUI.tabelliere + " ORDER BY " + cmbColonne.getSelectedItem();
				if(rdbtnDiscendenti.isSelected())
					DBManager.query += " DESC";
				
				DBManager.query += ";";
				GUI.eseguiQuery(true);
				frmOrdinaTabella.dispose();
			}
		});
	/////////////////////////////////////////////////////////////////
		WebLookAndFeel.install();
		frmOrdinaTabella.setTitle("Ordina Tabella");
		frmOrdinaTabella.setResizable(false);
		frmOrdinaTabella.setVisible(true);
		frmOrdinaTabella.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmOrdinaTabella.setBounds(100, 100, 535, 66);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmOrdinaTabella.setContentPane(contentPane);
		contentPane.setLayout(null);
		lblOrdinaLaColonna.setBounds(10, 11, 99, 14);
		
		contentPane.add(lblOrdinaLaColonna);
		cmbColonne.setBounds(111, 9, 128, 20);
		
		contentPane.add(cmbColonne);
		buttonGroup.add(rdbtnAscendenti);
		rdbtnAscendenti.setSelected(true);
		rdbtnAscendenti.setBounds(242, 7, 87, 23);
		
		contentPane.add(rdbtnAscendenti);
		buttonGroup.add(rdbtnDiscendenti);
		rdbtnDiscendenti.setBounds(332, 7, 91, 23);
		
		for(Object i : DBManager.titoli)
		{
			cmbColonne.addItem(i.toString());
		}
		
		contentPane.add(rdbtnDiscendenti);
		btnNewButton.setBounds(434, 6, 85, 25);
		
		contentPane.add(btnNewButton);
	}
}
