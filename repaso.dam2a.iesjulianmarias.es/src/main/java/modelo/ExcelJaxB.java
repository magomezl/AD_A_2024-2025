package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import modelo.clasesJAXB.Idioma_v1;
import modelo.clasesJAXB.Pais_v2;
import modelo.clasesJAXB.Paises_v2;
import modelo.clasesJAXB.Superficie_v2;


public class ExcelJaxB {

	public static void main(String[] args) {
		try {
			JAXBContext jC = JAXBContext.newInstance(Paises_v2.class);
			
			Unmarshaller jUM = jC.createUnmarshaller();
			Paises_v2 paises = (Paises_v2) jUM.unmarshal(new File(Utilidades.getRuta()+Utilidades.getFileOut()));
		
			Workbook wb = new HSSFWorkbook(new FileInputStream(new File(Utilidades.getRuta() + Utilidades.getFileInExcel())));;
			Sheet hoja =wb.getSheetAt(0);
			int numFila = 1;
			Row fila = hoja.getRow(numFila);
		
			while(fila!=null) {
				Pais_v2 pais = new Pais_v2();
				pais.setNombre(fila.getCell(0).getStringCellValue());
				pais.setHabitantes(fila.getCell(1).getNumericCellValue());
				Superficie_v2 superficie = new Superficie_v2(
						fila.getCell(6).getNumericCellValue(),
						fila.getCell(7).getNumericCellValue(),
						fila.getCell(8).getNumericCellValue(),
						fila.getCell(9).getNumericCellValue());
				pais.setSuperficie(superficie);
				pais.setDensidad_poblacion(fila.getCell(1).getNumericCellValue()/fila.getCell(9).getNumericCellValue());
				 
				for(int i=2; i<=5; i++) {
					if (fila.getCell(i)!=null){
						if (!fila.getCell(i).getStringCellValue().isBlank()) {
							Idioma_v1 idioma = new Idioma_v1(fila.getCell(i).getStringCellValue());
							pais.getIdiomas().add(idioma);
						}
					}
				}
				
				fila = hoja.getRow(numFila++);
				paises.getPaises().add(pais);
			}
			
			
			Marshaller jM = jC.createMarshaller();
			jM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jM.marshal(paises, new File(Utilidades.getRuta() + Utilidades.getFileOutJaxbexcel()));
	
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
