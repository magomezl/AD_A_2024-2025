package preparacionExamen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.GregorianCalendar;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utilidades.Utilidades;

public class ManejoFechasMySQL {
	private static Connection con;
	private final static String DOCTRABAJO_IN = "EjemploFechas.xlsx";
	private static Workbook wb;
	
	public static void main(String[] args) {
		
		try {
			establecerConexion_MySQL();
			wb = new XSSFWorkbook(new FileInputStream(new File(Utilidades.RUTA+DOCTRABAJO_IN)));
			//copiaFechasExcelMySQL();
			Set<String> provinciasSD = buscarProvinciasSinRepetir();
			for(String provinciaSD: provinciasSD) {
				System.out.println(provinciaSD);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				con.close();
				wb.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
	}

	public static Set<String> buscarProvinciasSinRepetir() {
		try {
			PreparedStatement sentencia = con.prepareStatement("SELECT provincia FROM provincias");
			ResultSet rS = sentencia.executeQuery();
			Set<String> provincias = new HashSet<String>();
			while(rS.next()) {
				provincias.add(rS.getString(1));
			}
			return provincias;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void copiaFechasExcelMySQL() {
		Sheet hoja =wb.getSheetAt(0);
		int numFila = 1;
		Row fila = hoja.getRow(numFila);

		PreparedStatement sentencia;
		try {
			sentencia = con.prepareStatement("INSERT INTO `fechas` (`fecha`) VALUES (?)");
			while(fila!=null) {
				Cell celdaBusqueda = fila.getCell(0);
				if (celdaBusqueda!=null) {
					
					java.util.Date fechaU = new SimpleDateFormat("yyyy/MM/dd").parse(celdaBusqueda.getStringCellValue());
					Calendar calendario = new GregorianCalendar();
					java.sql.Date fechaSQL = new java.sql.Date(fechaU.getYear(), fechaU.getMonth(), fechaU.getDay());
					
					sentencia.setDate(1, fechaSQL);
					sentencia.executeUpdate();
					
				}
				fila = hoja.getRow(numFila++);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public static Connection establecerConexion_MySQL() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fechas", "aurora", "aurora");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

}
