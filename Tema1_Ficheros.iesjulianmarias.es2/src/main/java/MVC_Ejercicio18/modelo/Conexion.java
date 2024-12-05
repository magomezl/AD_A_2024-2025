package MVC_Ejercicio18.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static Conexion instance;
	private static Connection con;
	
	private Conexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa", "root", "toor");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Conexion getInstance() {
		if (instance==null) {
			instance = new Conexion();
		}
		return instance;
	}

	public static Connection getCon() {
		return con;
	}
	
	public static void cerrarCon() {
		try {
			instance.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
