package xstream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.thoughtworks.xstream.XStream;

import ficheros.ejercicio6.Persona;

import java.io.EOFException;
import java.io.File;

import com.thoughtworks.xstream.io.xml.DomDriver;

import utilidades.Utilidades;
	
public class Ejercicio15 {
	private static final String DOCUMENTO_IN = "FicheroPersonasSerializado.dat";
	private static final String DOCUMENTO_OUT = "Ejercicio15.xml";
	private static ObjectInputStream oIS = null;
	public static void main(String[] args) {
		ListaPersonas lP = null;
		XStream xS = null;
		try {
			oIS = new ObjectInputStream(new FileInputStream(
					new File(Utilidades.RUTA+DOCUMENTO_IN)));
			xS = new XStream(new DomDriver("UTF-8"));
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
				xS.toXML(lP, new FileOutputStream(Utilidades.RUTA+DOCUMENTO_OUT));
				oIS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
