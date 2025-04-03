package modelo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import modelo.clasesJAXB.Idioma_v1;

public class DOMExcel {

	public static void main(String[] args) {
		
		try {
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBF.newDocumentBuilder();
			
			Document doc = dB.parse(new FileInputStream(new File(Utilidades.getRuta()+Utilidades.getFileOut())));
			doc.toString();
			//TODO coger el elemento raiz
			Element raiz = doc.getDocumentElement();
			// Abrir el excel leer y añadir a doc
			Workbook wb = new HSSFWorkbook(new FileInputStream(new File(Utilidades.getRuta() + Utilidades.getFileInDomexcel())));;
			Sheet hoja =wb.getSheetAt(0);
			int numFila = 1;
			Row fila = hoja.getRow(numFila);
		
			while(fila!=null) {
				Element pais = doc.createElement("pais");
				pais.setAttribute("nombre", fila.getCell(0).getStringCellValue());
				Element habitantes = doc.createElement("habitantes");
				habitantes.appendChild(doc.createTextNode(String.valueOf(fila.getCell(1).getNumericCellValue())));
				pais.appendChild(habitantes);
								
				Element idiomasOficiales = doc.createElement("idiomas_oficiales");
				for(int i=2; i<=5; i++) {
					if (fila.getCell(i)!=null){
						if (!fila.getCell(i).getStringCellValue().isBlank()) {
							Element idiomaE = doc.createElement("idioma");
							idiomaE.appendChild(doc.createTextNode(fila.getCell(i).getStringCellValue()));
							idiomasOficiales.appendChild(idiomaE);
						}
					}
				}
				pais.appendChild(idiomasOficiales);
				
				Element superficie = doc.createElement("superficie");
				superficie.setAttribute("kmlineacosta", String.valueOf(fila.getCell(7).getNumericCellValue()));
				superficie.setAttribute("kmagua", String.valueOf(fila.getCell(8).getNumericCellValue()));
				superficie.setAttribute("kmtierra", String.valueOf(fila.getCell(9).getNumericCellValue()));
				superficie.appendChild(doc.createTextNode(String.valueOf(fila.getCell(6).getNumericCellValue())));
				pais.appendChild(superficie);
				
				Element densidadPoblacion = doc.createElement("densidad_poblacion");
				densidadPoblacion.appendChild(doc.createTextNode(String.valueOf(
						fila.getCell(1).getNumericCellValue()/fila.getCell(9).getNumericCellValue() ) ));
				pais.appendChild(densidadPoblacion);
				
				raiz.appendChild(pais);
				
				fila = hoja.getRow(++numFila);
			}
			
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(doc), new StreamResult(new File(Utilidades.getRuta()+Utilidades.getFileOutDomexcel())));
			
//			doc = dB.parse(new FileInputStream(new File(Utilidades.getRuta()+Utilidades.getFileOutDomexcel())));
//			t.setOutputProperty(OutputKeys.INDENT, "yes");
//			t.transform(new DOMSource(doc), new StreamResult(new File(Utilidades.getRuta()+Utilidades.getFileOutDomexcel())));
//			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
