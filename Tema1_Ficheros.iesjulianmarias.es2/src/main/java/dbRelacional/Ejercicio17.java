package dbRelacional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ejercicio17 {

	private static Connection con;
	
	public Ejercicio17() {
		
	}

	public static void main(String[] args) {
		establecerConexion();
		listarDptos();
		cerrarConexion();
	}

	private static void listarDptos() {
		try {
			PreparedStatement sentencia = con.prepareStatement("SELECT * FROM empresa.departamentos;");
			ResultSet rS = sentencia.executeQuery();
			while(rS.next()) {
				System.out.println(rS.getInt(1) + " " + rS.getString(2) + " (" + rS.getString(3) + ")");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void cerrarConexion() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void establecerConexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa", "aurora", "aurora");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
