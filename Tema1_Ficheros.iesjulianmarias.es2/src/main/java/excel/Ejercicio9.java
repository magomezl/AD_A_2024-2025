package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utilidades.Utilidades;


/**
 * Modificar el ejercicio creando al menos dos métodos:
 * 		 buscarCiudad le pasaremos el nombre de una ciudad y nos mostrará las ubicaciones de los puntos de recarga. MODIFICACIÓN: crear una hoja en el documento excel con el nombre que se le pase como parámetro y almacenar en ella la información obtenida 
 * 		 buscarMarca le pasaremos la marca y nos mostrará las ubicaciones de los puntos de recarga
 * 		 Añadir una nueva hoja al documento excel y 	
 * 
 */
public class Ejercicio9 {
	private final static String DOCTRABAJO_IN = "vehiculosElectricos.xlsx";
	private static Workbook wb;
	
	
	private static void buscarMarca(String marca) {
		Sheet hoja =wb.getSheetAt(0);
		int numFila = 1;
		Row fila = hoja.getRow(numFila);
		
		// Muestro las localizaciones de los puntos de carga de la marca dada
		System.out.println("PUNTOS DE RECARGA DE " + marca + " EN CASTILLA Y LEÓN");
		while(fila!=null) {
			Cell celdaBusqueda = fila.getCell(2);
			if (celdaBusqueda!=null) {
				if (celdaBusqueda.getStringCellValue().contains(marca)) {
					System.out.println("----> " + fila.getCell(0).getStringCellValue() + "\t - " + 
							fila.getCell(1).getStringCellValue());
				}
			}
			fila = hoja.getRow(numFila++);
		}
	}
	
	private static void buscarCiudad(String ciudad) {
		Sheet hoja =wb.getSheetAt(0);
		Sheet newHoja = wb.createSheet(ciudad);
		int numFila = 1, newNumFila=0;
		
		Row fila = hoja.getRow(numFila);
		
		// Muestro las localizaciones de los puntos de carga en la ciudad
		System.out.println("\n\n\nPUNTOS DE RECARGA EN " + ciudad);
		while(fila!=null) {
			Cell celdaBusqueda = fila.getCell(1);
			if (celdaBusqueda!=null) {
				if (celdaBusqueda.getStringCellValue().contains("("+ciudad+")")) {
					System.out.println("----> " + fila.getCell(0).getStringCellValue() + "\t - " + 
							fila.getCell(1).getStringCellValue());
					Row newFila = newHoja.createRow(newNumFila++);
					newFila.createCell(0).setCellValue((String)fila.getCell(0).getStringCellValue());
					newFila.createCell(1).setCellValue((String)fila.getCell(1).getStringCellValue());
				}
			}
			fila = hoja.getRow(numFila++);
		}
		
		try {
			wb.write(new FileOutputStream(new File(Utilidades.RUTA+DOCTRABAJO_IN)));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
		
		try {
			// Abro un documento excel con extensión xslx 	
			wb = new XSSFWorkbook(new FileInputStream(new File(Utilidades.RUTA+DOCTRABAJO_IN)));
			// tomo la primera página
			buscarMarca("IBERDROLA");
			buscarCiudad("ZAMORA");
			wb.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
