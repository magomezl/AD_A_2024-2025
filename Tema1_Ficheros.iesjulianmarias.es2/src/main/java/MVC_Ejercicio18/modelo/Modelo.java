package MVC_Ejercicio18.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import utilidades.Utilidades;

public class Modelo {

	
	private final static String DB = "dbEmpresaSQLite.dat";
	
	private static void anadirEmpleado(String nombre, String apellido1, String apellido2, String dpto) {
		Scanner sc = new Scanner(System.in);
		Scanner scI = new Scanner(System.in);
		try {
			PreparedStatement sentencia1 = Conexion.getInstance().getCon().prepareStatement("SELECT dept_no FROM departamentos where dnombre = ?");
			sentencia1.setString(1, dpto);
			ResultSet rS1 = sentencia1.executeQuery();
			if (!rS1.next()) {
				System.out.println("No existe el departamento. A continuación se muestran los departamentos existentes:");
				listarDptos();
				System.out.print("\n1. Añadir el departamento y el empleado "
						+ "\n2. Añadir el empleado a un departamento existente "
						+ "\n3. Salir sin hacer nada "
						+ "\nSeleccione una opción: ");

				switch(sc.nextInt()) {
				case 1:
					Conexion.getInstance().getCon().setAutoCommit(false);
					System.out.println("Localidad del departamento: ");
					String localidad = scI.nextLine();
					PreparedStatement sentencia2 = Conexion.getInstance().getCon().prepareStatement("INSERT INTO departamentos (dnombre, loc) VALUES (?, ?)", 
							PreparedStatement.RETURN_GENERATED_KEYS);
					sentencia2.setString(1, dpto );
					sentencia2.setString(2, localidad);
					sentencia2.executeUpdate();
					ResultSet idGenerado = sentencia2.getGeneratedKeys();
					idGenerado.next();
					int idDpto = idGenerado.getInt(1);
					PreparedStatement sentencia3 = Conexion.getInstance().getCon().prepareStatement("INSERT INTO empleados (nombre, apellido1, "
							+ "apellido2, departamento) VALUES (?, ?, ?, ?)");
					sentencia3.setString(1, nombre);
					sentencia3.setString(2, apellido1);
					sentencia3.setString(3, apellido2);
					sentencia3.setInt(4, idDpto);
					sentencia3.executeUpdate();
					Conexion.getInstance().getCon().commit();
					Conexion.getInstance().getCon().setAutoCommit(true);
					break;
				case 2:
					System.out.println("Indique el nombre del departamento al que desea añadir al empleado: ");
					String departamento = scI.nextLine();
					anadirEmpleado(nombre, apellido1, apellido2, departamento);
					break;
				case 3:
					break;
				}

			}else {
				PreparedStatement sentencia3 = Conexion.getInstance().getCon().prepareStatement("INSERT INTO empleados (nombre, apellido1, "
						+ "apellido2, departamento) VALUES (?, ?, ?, ?)");
				sentencia3.setString(1, nombre);
				sentencia3.setString(2, apellido1);
				sentencia3.setString(3, apellido2);
				sentencia3.setInt(4, rS1.getInt(1));
				sentencia3.executeUpdate();
			}
		} catch (SQLException e) {
			try {
				Conexion.getInstance().getCon().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public static int anadirDpto(String dptoNom, String dptoLoc) {
		try {
			PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement("INSERT INTO departamentos (dnombre, loc) VALUES (?, ?)");
			sentencia.setString(1, dptoNom);
			sentencia.setString(2, dptoLoc);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	public static int borrarDpto(Integer dptoNum) {
		try {
			PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement("DELETE FROM departamentos WHERE (dept_no = ?)");
			sentencia.setInt(1, dptoNum);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int modificarDpto(Integer dptoNum, String dptoNom, String dptoLoc) {
		try {
			PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement("UPDATE departamentos SET dnombre = ?, loc = ? WHERE dept_no = ?");
			sentencia.setString(1, dptoNom);
			sentencia.setString(2, dptoLoc);
			sentencia.setInt(3, dptoNum);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	public static ArrayList<Departamento> listarDptos() {
		try {
			PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement("SELECT * FROM departamentos");
			ResultSet rS = sentencia.executeQuery();
			ArrayList<Departamento> arDpto = new ArrayList<Departamento>();
			while(rS.next()) {
				arDpto.add(new Departamento(rS.getInt(1), rS.getString(2), rS.getString(3)));
			}
			sentencia.close();
			rS.close();
			return arDpto;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	private static void establecerConexion_SQLite() {
//		//TODO faltaria ejecuta creación de tabla de empleados
//		try {
//			Class.forName("org.sqlite.JDBC");
//			con = DriverManager.getConnection("jdbc:sqlite:"+Utilidades.RUTA+DB);
//			PreparedStatement sentencia = con.prepareStatement(
//					"CREATE TABLE IF NOT EXISTS departamentos (dept_no INTEGER PRIMARY KEY AUTOINCREMENT , dnombre VARCHAR(15), loc VARCHAR(15));" );
//			sentencia.executeUpdate();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

}
