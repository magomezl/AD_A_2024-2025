package ficheros;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio8 {
	
	private final static String DOCTRABAJO_IN = "Ejercicio08.csv";
	private final static String SEPARADOR = ",";
	private final static String COMILLA = "\"";
	public static void main(String[] args) {
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(Ejercicio3.RUTA+DOCTRABAJO_IN));
			String line;
			while((line = br.readLine())!=null) {
				String[] fields = line.split(SEPARADOR);
				mostrar(fields);
				fields = eliminaComillas(fields);
				mostrar(fields);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		

	}
	private static String[] eliminaComillas(String[] fields) {
		for(int i=0; i<fields.length; i++) {
			fields[i] = fields[i].trim().replaceAll("^"+COMILLA, "").replaceAll(COMILLA+"$", "").replaceAll(COMILLA+COMILLA, COMILLA);
		}
		return fields;
	}
	private static void mostrar(String[] fields) {
		for(int i=0; i<fields.length; i++) {
			System.out.print("\t ---" + fields[i]);
		}
		System.out.println();
	}

}
