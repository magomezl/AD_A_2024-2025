package xstream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;

import com.thoughtworks.xstream.XStream;

import ficheros.ejercicio6.Ejercicio6;
import ficheros.ejercicio6.Persona;

import java.io.EOFException;
import java.io.File;

import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import utilidades.Utilidades;
	
public class Ejercicio15 {
	private static final String DOCUMENTO_IN = "FicheroPersonasSerializado.dat";
	private static final String DOCUMENTO_OUT = "Ejercicio15.xml";
	private static final String DOCUMENTO_IN_XML = "Ejercicio15_in.xml";
	private static ObjectInputStream oIS = null;
	private static XStream xS = new XStream(new DomDriver("UTF-8"));
	
	public static void main(String[] args) {
		
//		deserializa_a_XML();
		serializa_desde_XML();
		Ejercicio6.leerObjetos() ;
		
	}
	
	
	private static void deserializa_a_XML() {
		ListaPersonas lP = null;
		try {
			oIS = new ObjectInputStream(new FileInputStream(
					new File(Utilidades.RUTA+DOCUMENTO_IN)));
			lP = new ListaPersonas();
			while (true) {
				Persona person = (Persona) oIS.readObject();
				lP.anadir(person);
			}
		}catch (EOFException e) { 
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				defineEstructura();
				xS.toXML(lP, new FileOutputStream(Utilidades.RUTA+DOCUMENTO_OUT));
				oIS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void defineEstructura() {
		xS.alias("familia", ListaPersonas.class);
		xS.alias("miembro", Persona.class);
		xS.addImplicitCollection(ListaPersonas.class, "lista");
		xS.aliasField("primerApellido", Persona.class, "apellido1");
		xS.aliasField("segundoApellido", Persona.class, "apellido2");
		xS.aliasField("name", Persona.class, "nombre");
		xS.useAttributeFor(Persona.class, "nombre");
	}


	private static void serializa_desde_XML() {
		try {
			ListaPersonas lP = new ListaPersonas();
			xS.addPermission(AnyTypePermission.ANY);
			
			defineEstructura();
			xS.aliasField("nombre", Persona.class, "nombre");
			lP = (ListaPersonas) xS.fromXML(new FileInputStream(Utilidades.RUTA+DOCUMENTO_IN_XML));
			Iterator<Persona> it = lP.getLista().iterator();
			Ejercicio6.inicializar();
			Persona p = new Persona();
			while(it.hasNext()) {
				p = it.next();
				Ejercicio6.escribirObjeto(p);
			}
			try {
				Ejercicio6.getoOS().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	

}
