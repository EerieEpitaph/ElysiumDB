package dbManager;

import java.sql.*;
import java.util.Vector;


public class DBManager {
	
	String nomeDriver = "com.mysql.jdbc.Driver";
	String serverUrl = "jdbc:mysql://localhost/";
	String username = LoginScreen.username;
	String password = LoginScreen.password;
	static String query = "";

	static Connection conn = null;
	static Statement st = null;
	static ResultSet ris = null;
	static ResultSetMetaData rsmd = null; 
	
	static int righe;
	static int colonne;
	static int tolleranza;
	
	static Vector<Object> titoli = new Vector<Object>(1,1);
	static Vector<Vector<Object>> dati = new Vector<Vector<Object>>(1,1); 
	
	public DBManager() throws SQLException
	{
		////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////CONNESSIONE PRINCIPALE
		try
		{
			Class.forName(nomeDriver);
			conn = DriverManager.getConnection(serverUrl,username,password); //CONNESSIONE AL DATABASE
			st = conn.createStatement();
		}
		catch(Exception e){}
	}
}


