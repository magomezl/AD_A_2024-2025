package App;

import java.util.HashSet;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;

import modelo.ModeloXQJ;
import modelo.clasesXML.Modulo;

public class AppXQJ {

	public static void main(String[] args) {
		ModeloXQJ modeloxqj = new ModeloXQJ("aurora", "aurora");
		XQConnection con = modeloxqj.getCon();
		modeloxqj.metaInformacion(con);
		
		modeloxqj.muestraModulosCiclo(con, "DAM");
		
		HashSet<String> ciclos = new HashSet<String>();
		ciclos.add("DAM");
		
		Modulo modulo = new  Modulo("0003", "Acceso a Datos", 180, 2, ciclos);
		
		modeloxqj.anadirModulo(con, modulo);
				
		
		
		try {
			con.close();
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
