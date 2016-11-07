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
class ConnectionDB {

	private Connection conn;
	private static ConnectionDB instance;
	
	private ConnectionDB(){
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver).newInstance();
			String url = "jdbc:mysql://db4free.net:3306/";
			String dbName = "findit";
			String userName = "lpspuc";
			String password = "laboratorio";
			this.setConnection((Connection)DriverManager.getConnection(url + dbName, userName, password));
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
	}
	
	static ConnectionDB getInstance(){
		
		if(instance == null) {

	          instance = new ConnectionDB();

	      }

	      return instance;
	}

	Connection getConnection() {
		return conn;
	}

	private void setConnection(Connection conn) {
		this.conn = conn;
	}
	
}
