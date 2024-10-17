package ficheros;

import java.util.Properties;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

public class Ejercicio7 {
	private final static String DOCTRABAJO_IN = "Configuracion.props";
	private static Properties conf = new Properties();
	
	
	public static void main(String[] args) {
		crearConfiguracion();
		System.out.println("CONFIGURACIÓN INICIAL");
		leeConfiguracion();
		modificaConfiguracion();
		System.out.println("CONFIGURACIÓN MODIFICADA");
		leeConfiguracion();

	}


	private static void modificaConfiguracion() {
		//TODO capturar excepciones NumberFormatException de manera que se repita la petición del valor
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("MODIFICANDO CONFIGURACIÓN");
			conf.load(new FileInputStream(Ejercicio3.RUTA+DOCTRABAJO_IN));
			System.out.println("user: ");
			conf.setProperty("user", sc.nextLine());
			System.out.println("password: ");
			conf.setProperty("password", sc.nextLine());
			System.out.println("server: ");
			conf.setProperty("server", sc.nextLine());
			
			System.out.println("Valor a sumar al puerto: ");
			int puerto = Integer.valueOf(sc.nextLine());
			puerto = Integer.valueOf(conf.getProperty("port")) + puerto;
			conf.setProperty("port", String.valueOf(puerto));
						
			System.out.println("Nuevo mes para la fecha: ");
			int mes = Integer.valueOf(sc.nextLine());
			conf.setProperty("date", 
					String.valueOf(LocalDate.parse(conf.getProperty("date")).withMonth(mes)));
			conf.setProperty("power", String.valueOf(!(Boolean.valueOf(conf.getProperty("power")))));
			conf.store(new FileOutputStream(Ejercicio3.RUTA+DOCTRABAJO_IN), "Fichero modificado");
			sc.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


	private static void leeConfiguracion() {
		try {
			conf.load(new FileInputStream(Ejercicio3.RUTA+DOCTRABAJO_IN));
			conf.list(System.out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static void crearConfiguracion() {
		conf.setProperty("user", "usuario");
		conf.setProperty("password", "micontrasenia");
		conf.setProperty("server", "localhost");
		conf.setProperty("port", "3306");
		conf.setProperty("date", "2022-11-12");
		conf.setProperty("power", "true");
		
		try {
			conf.store(new FileOutputStream(Ejercicio3.RUTA+DOCTRABAJO_IN), "Fichero de configuración");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
