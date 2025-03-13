package modelo;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import modelo.clasesJAXB.Pais_v2;
import modelo.clasesJAXB.Paises_v2;

public class ExcelJaxB {

	public static void main(String[] args) {
		try {
			JAXBContext jC = JAXBContext.newInstance(Paises_v2.class);
			Unmarshaller jUM = jC.createUnmarshaller();
			Paises_v2 paises = (Paises_v2) jUM.unmarshal(new File(Utilidades.getRuta()+Utilidades.getFileOut()));
			for(Pais_v2 pais: paises.getPaises()) {
				System.out.println(pais);
			}
			
//			String cadena = "8.350";
//			System.out.println(Double.valueOf(cadena));
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
