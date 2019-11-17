package ps2.conexao;

import java.sql.*;
public class ConexaoJavaDb {
	private String usuario;
	private String senha;
	private String host;
	private int port;
	private String dbName;
	private Connection connection;
	public ConexaoJavaDb(String u, String s, String h, int p, String d) {
		usuario = u;
		senha = s;
		host = h;
		port = p;
		dbName = d;
		connection = null;
	}
	public Connection getConnection() throws ConexaoException {
		if (connection == null) {
			try {
				String url;
				url = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";create=true";
				Class.forName("org.apache.derby.jdbc.ClientDriver");
				connection = DriverManager.getConnection(url, usuario, senha);
			} catch (ClassNotFoundException ex) {
				throw new ConexaoException("Falha ao carregar driver!");
			} catch (SQLException ex) {
				throw new ConexaoException("Falha ao abrir conexão!");
			}
 		}
		return connection;
	}
	public void close() throws ConexaoException {
		if (connection != null) {
			try { 
				connection.close(); 
			} catch (SQLException ex) { 
				throw new ConexaoException("Problema ao fechar conexão!");
			}
		}
	}
}