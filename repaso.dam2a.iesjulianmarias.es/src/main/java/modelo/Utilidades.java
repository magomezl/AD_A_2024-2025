package modelo;

public class Utilidades {
	private static final char SEPARADOR = ',';
	private static final char DELIMITADOR_VALORES_MULTIPLES = '"';
	private static final String RUTA = System.getProperty("user.dir") + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "main" 
			+ System.getProperty("file.separator") + "resources" + System.getProperty("file.separator");
	private static final String FILE_IN = "paises.csv";
	private static final String FILE_IN_EXCEL = "paises.xls";
	private static final String FILE_IN_DOMEXCEL = "paises2.xls";
	
	private static final String FILE_OUT = "paises.xml";
	private static final String FILE_OUT_JAXB = "paises_jaxb.xml";
	private static final String FILE_OUT_JAXBEXCEL = "paises2.xml";
	private static final String FILE_OUT_DOMEXCEL = "paisesDOMExcel.xml";
	
	public static String getFileOutDomexcel() {
		return FILE_OUT_DOMEXCEL;
	}
	public static String getFileInDomexcel() {
		return FILE_IN_DOMEXCEL;
	}
	public static String getFileInExcel() {
		return FILE_IN_EXCEL;
	}
	public static String getFileOutJaxb() {
		return FILE_OUT_JAXB;
	}
	public static char getSeparador() {
		return SEPARADOR;
	}
	public static char getDelimitadorValoresMultiples() {
		return DELIMITADOR_VALORES_MULTIPLES;
	}
	public static String getFileOutJaxbexcel() {
		return FILE_OUT_JAXBEXCEL;
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
