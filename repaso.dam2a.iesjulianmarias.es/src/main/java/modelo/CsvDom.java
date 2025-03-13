package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.opencsv.CSVReader;

public class CsvDom {

	public static void main(String[] args) {
	
		try {
			CSVReader reader = new CSVReader(new FileReader(Utilidades.getRuta()+Utilidades.getFileIn()), 
					Utilidades.getSeparador(), Utilidades.getDelimitadorValoresMultiples());
			
			String[] lineaPais = null;
			boolean primeraLinea = true;
//			DecimalFormat formato = new DecimalFormat("#,###.##");
			
			
			
//			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
//			DocumentBuilder dB = dBF.newDocumentBuilder();
//			Document doc = dB.newDocument();
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element raiz = doc.createElement("paises");
			doc.appendChild(raiz);
			
			while((lineaPais = reader.readNext())!=null) {
				if (!primeraLinea) {
					Element pais = doc.createElement("pais");
					pais.setAttribute("nombre", lineaPais[0]);
					Element habitantes = doc.createElement("habitantes");
					habitantes.appendChild(doc.createTextNode(lineaPais[1]));
					pais.appendChild(habitantes);
										
					Element idiomasOficiales = doc.createElement("idiomas_oficiales");
					String[] idiomas = lineaPais[2].split(",");
					for(String idioma: idiomas) {
						Element idiomaE = doc.createElement("idioma");
						idiomaE.appendChild(doc.createTextNode(idioma));
						idiomasOficiales.appendChild(idiomaE);
					}
					pais.appendChild(idiomasOficiales);

					Element superficie = doc.createElement("superficie");
//					superficie.setAttribute("km_linea_costa", formato.format(Double.parseDouble(lineaPais[4])));
//					superficie.setAttribute("km2_agua", formato.format(Double.parseDouble(lineaPais[5])));
//					superficie.setAttribute("km2_tierra", formato.format(Double.parseDouble(lineaPais[6])));
//					superficie.appendChild(doc.createTextNode(formato.format(Double.parseDouble(lineaPais[3]))));
					
					//SIN FORMATO
					superficie.setAttribute("km_linea_costa", lineaPais[4]);
					superficie.setAttribute("km2_agua", lineaPais[5]);
					superficie.setAttribute("km2_tierra", lineaPais[6]);
					superficie.appendChild(doc.createTextNode(lineaPais[3]));
					
					
					pais.appendChild(superficie);
					
					Element densidadPoblacion = doc.createElement("densidad_poblacion");
//					densidadPoblacion.appendChild(doc.createTextNode(String.valueOf(
//							formato.format( Double.parseDouble(lineaPais[1])/Double.parseDouble(lineaPais[6]) ) )));
					
					//SIN FORMATO
					densidadPoblacion.appendChild(doc.createTextNode(String.valueOf(
							Double.parseDouble(lineaPais[1])/Double.parseDouble(lineaPais[6]) ) ));
					pais.appendChild(densidadPoblacion);
					
					raiz.appendChild(pais);
				}
				primeraLinea=false;
			}
			
//			TransformerFactory tF = TransformerFactory.newInstance();
//			Transformer t = tF.newTransformer();
			
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(doc), new StreamResult(new File(Utilidades.getRuta()+Utilidades.getFileOut())));
			
			
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no existe");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error de lectura");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.out.println("Error al crear el documento DOM");
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en el proceso de transformación de DOM a Fichero");
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en la transformación de DOM a Fichero");
			e.printStackTrace();
		}
	}
}
