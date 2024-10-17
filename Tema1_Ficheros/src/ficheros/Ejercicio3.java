package ficheros;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Ejercicio3 {

	public final static String RUTA = System.getProperty("user.dir") + System.getProperty("file.separator") +
			"src" + System.getProperty("file.separator") + "data" + System.getProperty("file.separator");
	
	private final static String DOCTRABAJO = "4_Ejercicio.csv";
	
	public static void main(String[] args) {
		
		try {
			BufferedReader bfRd = new BufferedReader(new FileReader(RUTA+DOCTRABAJO));
			String linea;
			while ((linea = bfRd.readLine())!=null) {
				System.out.println(linea);
			}
			bfRd.close();
			PrintWriter pW = new PrintWriter(new FileWriter(RUTA+DOCTRABAJO, true));
			pW.println("Aurora;Gómez;DAM;2");
			pW.flush();
			pW.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no existe");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error de lectura");
			e.printStackTrace();
		} 

	}

}
