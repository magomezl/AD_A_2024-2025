package modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ConexionFicheroExcel {
	private static ConexionFicheroExcel instancia;
	private static Workbook wb;
	private static Sheet hoja;
	
	
	private ConexionFicheroExcel() {
		
			Workbook wb = null;
			try {
				wb = new XSSFWorkbook(new FileInputStream(Utilidades.getRutadatos()+ Utilidades.getDoctrabajoIn()));
				hoja = wb.getSheetAt(0);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static ConexionFicheroExcel getInstancia() {
		if (instancia==null) {
			instancia = new ConexionFicheroExcel();
		}
		return instancia;
	}
	
	
	public static Sheet getHoja() {
		return hoja;
	}

	public static void cerrarConexion() {
		try {
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
