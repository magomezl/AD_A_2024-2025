package dbRelacional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import utilidades.Utilidades;

public class Ejercicio17 {

	private static Connection con;
	private final static String DB = "dbEmpresaSQLite.dat";
	
	
	public static void main(String[] args) {
		establecerConexion_MySQL();
//		System.out.println(anadirDpto("Ventas", "Sevilla")!=0?"El departamento se añadió con éxito": "El departamento NO se añadió");
//		System.out.println(anadirDpto("Fabricación", "Sevilla")!=0?"El departamento se añadió con éxito": "El departamento NO se añadió");
//		
//		listarDptos();
//		System.out.println(borrarDpto(10)!=0?"El departamento se eliminó con éxito": "El departamento NO se ELIMINO");
//		System.out.println(borrarDpto(1)!=0?"El departamento se eliminó con éxito": "El departamento NO se ELIMINO");
//		listarDptos();
//		System.out.println(modificarDpto(2, "RRHH", "Burgos")!=0?"El departamento se modificó con éxito": "El departamento NO se MODIFICO");
//		listarDptos();
	//	anadirEmpleado("Miguel", "Alcantara", "Gómez", "Ventas");
		System.out.println("Añado dpto");
		anadirEmpleado("Julia", "Cantara", "Pérez", "Propaganda");
		System.out.println("Asigno a dpto existente");
		anadirEmpleado("Anselmo", "Muñoz", "Pérez", "Sanciones");
		System.out.println("No hago nada");
		anadirEmpleado("Andrea", "Carrasco", "Pérez", "Sanciones2");
		
		
//		listarDptos();
//		System.out.println(borrarDpto(10)!=0?"El departamento se eliminó con éxito": "El departamento NO se ELIMINO");
//		System.out.println(borrarDpto(100)!=0?"El departamento se eliminó con éxito": "El departamento NO se ELIMINO");
//		listarDptos();
//		System.out.println(modificarDpto(20, "RRHH", "Burgos")!=0?"El departamento se modificó con éxito": "El departamento NO se MODIFICO");
//		System.out.println(modificarDpto(200, "RRHH", "Burgos")!=0?"El departamento se modificó con éxito": "El departamento NO se MODIFICO");
//		listarDptos();
		anadirEmpleado("Manuel", "García", "López", "RRHH");
		cerrarConexion();
	}

	private static void anadirEmpleado(String nombre, String apellido1, String apellido2, String dpto) {
		Scanner sc = new Scanner(System.in);
		Scanner scI = new Scanner(System.in);
		try {
			PreparedStatement sentencia1 = con.prepareStatement("SELECT dept_no FROM departamentos where dnombre = ?");
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
					con.setAutoCommit(false);
					System.out.println("Localidad del departamento: ");
					String localidad = scI.nextLine();
					PreparedStatement sentencia2 = con.prepareStatement("INSERT INTO departamentos (dnombre, loc) VALUES (?, ?)", 
							PreparedStatement.RETURN_GENERATED_KEYS);
					sentencia2.setString(1, dpto );
					sentencia2.setString(2, localidad);
					sentencia2.executeUpdate();
					ResultSet idGenerado = sentencia2.getGeneratedKeys();
					idGenerado.next();
					int idDpto = idGenerado.getInt(1);
					PreparedStatement sentencia3 = con.prepareStatement("INSERT INTO empleados (nombre, apellido1, "
							+ "apellido2, departamento) VALUES (?, ?, ?, ?)");
					sentencia3.setString(1, nombre);
					sentencia3.setString(2, apellido1);
					sentencia3.setString(3, apellido2);
					sentencia3.setInt(4, idDpto);
					sentencia3.executeUpdate();
					con.commit();
					con.setAutoCommit(true);
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
				PreparedStatement sentencia3 = con.prepareStatement("INSERT INTO empleados (nombre, apellido1, "
						+ "apellido2, departamento) VALUES (?, ?, ?, ?)");
				sentencia3.setString(1, nombre);
				sentencia3.setString(2, apellido1);
				sentencia3.setString(3, apellido2);
				sentencia3.setInt(4, rS1.getInt(1));
				sentencia3.executeUpdate();
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
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
		//TODO faltaria ejecuta creación de tabla de empleados
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
