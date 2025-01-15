package modelo;

public class Utilidades {
	private final static String RUTADATOS = System.getProperty("user.dir") + 
			System.getProperty("file.separator") + "src" + 
			System.getProperty("file.separator") + "main" + 
			System.getProperty("file.separator") + "resources" +
			System.getProperty("file.separator");
	private final static String DOCTRABAJO_IN = "IngresosIctus.xlsx";
	private final static String DOCUMENTO_XML = "ictus.xml";
	
	public static String getRutadatos() {
		return RUTADATOS;
	}
	public static String getDoctrabajoIn() {
		return DOCTRABAJO_IN;
	}
	public static String getDocumentoXml() {
		return DOCUMENTO_XML;
	}
	
	

}
