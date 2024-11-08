package dbRelacional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilidades.Utilidades;

public class Ejercicio17 {

	private static Connection con;
	private final static String DB = "dbEmpresaSQLite.dat";
	
	public Ejercicio17() {
		
	}

	public static void main(String[] args) {
		establecerConexion_SQLite();
		System.out.println(anadirDpto("Ventas", "Sevilla")!=0?"El departamento se añadió con éxito": "El departamento NO se añadió");
		System.out.println(anadirDpto("Fabricación", "Sevilla")!=0?"El departamento se añadió con éxito": "El departamento NO se añadió");
		
		listarDptos();
		System.out.println(borrarDpto(10)!=0?"El departamento se eliminó con éxito": "El departamento NO se ELIMINO");
		System.out.println(borrarDpto(1)!=0?"El departamento se eliminó con éxito": "El departamento NO se ELIMINO");
		listarDptos();
		System.out.println(modificarDpto(2, "RRHH", "Burgos")!=0?"El departamento se modificó con éxito": "El departamento NO se MODIFICO");
		listarDptos();
		
//		listarDptos();
//		System.out.println(borrarDpto(10)!=0?"El departamento se eliminó con éxito": "El departamento NO se ELIMINO");
//		System.out.println(borrarDpto(100)!=0?"El departamento se eliminó con éxito": "El departamento NO se ELIMINO");
//		listarDptos();
//		System.out.println(modificarDpto(20, "RRHH", "Burgos")!=0?"El departamento se modificó con éxito": "El departamento NO se MODIFICO");
//		System.out.println(modificarDpto(200, "RRHH", "Burgos")!=0?"El departamento se modificó con éxito": "El departamento NO se MODIFICO");
//		listarDptos();
		cerrarConexion();
	}

	private static int anadirDpto(String dptoNom, String dptoLoc) {
		try {
			PreparedStatement sentencia = con.prepareStatement("INSERT INTO departamentos (dnombre, loc) VALUES (?, ?)");
			sentencia.setString(1, dptoNom);
			sentencia.setString(2, dptoLoc);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	private static int borrarDpto(Integer dptoNum) {
		try {
			PreparedStatement sentencia = con.prepareStatement("DELETE FROM departamentos WHERE (dept_no = ?)");
			sentencia.setInt(1, dptoNum);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private static int modificarDpto(Integer dptoNum, String dptoNom, String dptoLoc) {
		try {
			PreparedStatement sentencia = con.prepareStatement("UPDATE departamentos SET dnombre = ?, loc = ? WHERE dept_no = ?");
			sentencia.setString(1, dptoNom);
			sentencia.setString(2, dptoLoc);
			sentencia.setInt(3, dptoNum);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	private static void listarDptos() {
		try {
			PreparedStatement sentencia = con.prepareStatement("SELECT * FROM departamentos");
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

	private static void establecerConexion_MySQL() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa", "aurora", "aurora");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void establecerConexion_SQLite() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:"+Utilidades.RUTA+DB);
			PreparedStatement sentencia = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS departamentos (dept_no INTEGER PRIMARY KEY AUTOINCREMENT , dnombre VARCHAR(15), loc VARCHAR(15));" );
			sentencia.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
