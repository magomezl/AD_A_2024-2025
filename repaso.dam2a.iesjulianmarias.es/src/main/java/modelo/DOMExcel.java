package modelo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
import org.xml.sax.SAXException;

public class DOMExcel {

	public static void main(String[] args) {
		
		try {
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB;
			dB = dBF.newDocumentBuilder();
			
			Document doc = dB.parse(new FileInputStream(new File(Utilidades.getRuta()+Utilidades.getFileOut())));
			
			// Abrir el excell leer y añadir a doc
			Workbook wb = new HSSFWorkbook(new FileInputStream(new File(Utilidades.getRuta() + Utilidades.getFileInDomexcel())));;
			Sheet hoja =wb.getSheetAt(0);
			int numFila = 1;
			Row fila = hoja.getRow(numFila);
		
			while(fila!=null) {
				
				fila = hoja.getRow(numFila++);
			}
			
			
			
			
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(doc), new StreamResult(new File(Utilidades.getRuta()+Utilidades.getFileOutDomexcel())));
			
			
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
