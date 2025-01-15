package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
	private static ConexionMySQL instancia;
	private static Connection con;
	
	private ConexionMySQL() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ictussacyl", "root", "toor");
			if (con!=null) {
				System.out.println("Conexion establecida con éxito");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ConexionMySQL getInstancia() {
		if (instancia==null) {
			instancia = new ConexionMySQL();
		}
		return instancia;
	}
	
	
	public static Connection getCon() {
		return con;
	}

	public static void cerrarConexion() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
