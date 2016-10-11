package lps.com.br.find_it;

import java.sql.DriverManager;
import com.mysql.jdbc.Connection;

/**
 * Classe de Conexão ao Banco de Dados.
 * 
 * @author Wellington Santos Corrêa.
 * @version 1.2 - 21/02/16.
 *
 */
public class ConnectionDB {
	
	private String url= "jdbc:mysql://db4free.net:3306/";
	private String dbName = "findit";
	private String driver = "com.mysql.jdbc.Driver";
	private String userName = "lpspuc";
	private String password = "laboratorio";
	private Connection conn;
	private static ConnectionDB instance;
	
	private ConnectionDB(){
		try {
            Class.forName(driver).newInstance();
            this.setConnection((Connection)DriverManager.getConnection(url+dbName,userName,password));
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
	}
	
	public static ConnectionDB getInstance(){
		
		if(instance == null) {

	          instance = new ConnectionDB();

	      }

	      return instance;
	}

	public Connection getConnection() {
		return conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
}
