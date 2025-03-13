package modelo;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import modelo.clasesJAXB.Geografia_v1;

public class SaxJaxB {

	public static void main(String[] args) {

		try {
			SAXParser sP = SAXParserFactory.newInstance().newSAXParser();
			Manejador gestor = new Manejador();
			InputSource fileXML = new InputSource(Utilidades.getRuta()+Utilidades.getFileOut());
			sP.parse(fileXML, gestor);
			
			JAXBContext jC = JAXBContext.newInstance(Geografia_v1.class);
			Marshaller jM = jC.createMarshaller();
			jM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			jM.marshal(Manejador.getGeografia(), new File(Utilidades.getRuta() + Utilidades.getFileOutJaxb()));
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}
