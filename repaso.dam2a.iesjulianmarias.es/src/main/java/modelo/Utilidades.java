package modelo;

public class Utilidades {
	private static final char SEPARADOR = ',';
	private static final char DELIMITADOR_VALORES_MULTIPLES = '"';
	private static final String RUTA = System.getProperty("user.dir") + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "main" 
			+ System.getProperty("file.separator") + "resources" + System.getProperty("file.separator");
	private static final String FILE_IN = "paises.csv";
	private static final String FILE_OUT = "paises.xml";
	
	public static char getSeparador() {
		return SEPARADOR;
	}
	public static char getDelimitadorValoresMultiples() {
		return DELIMITADOR_VALORES_MULTIPLES;
	}
	public static String getRuta() {
		return RUTA;
	}
	public static String getFileIn() {
		return FILE_IN;
	}
	public static String getFileOut() {
		return FILE_OUT;
	}
}
