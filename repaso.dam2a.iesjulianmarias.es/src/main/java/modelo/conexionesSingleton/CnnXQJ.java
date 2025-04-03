package modelo.conexionesSingleton;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

import net.xqj.exist.ExistXQDataSource;

public class CnnXQJ {
		private static  CnnXQJ instancia;
		private static XQConnection conn;
		
		private CnnXQJ(String user, String passwd) {
			try {
				XQDataSource xqs = new ExistXQDataSource();

				xqs.setProperty("serverName", "localhost");
				xqs.setProperty("port", "8080");
				xqs.setProperty("user", user);
				xqs.setProperty("password", passwd);
				conn = xqs.getConnection();

			} catch (XQException e) {
				e.printStackTrace();
			}
		}
		
		public static synchronized CnnXQJ getInstancia(String user, String passwd) {
			if (instancia==null ) {
				instancia = new CnnXQJ(user, passwd);
			}
			return instancia;
		}

		public static XQConnection getCon() {
			return conn;
		}
	}


