

package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import modelo.clasesJAXB_manana.Edad;
import modelo.clasesJAXB_manana.Ictus;
import modelo.clasesJAXB_manana.Periodo;
import modelo.clasesParaDatos.Rango;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class Modelo {
	private static ArrayList<Rango> rangos = creaRangosEdad(); 
	
	public static void main(String[] args) {
		//TODO mejorar para clases no deprecated
		llenaTablaIctus();
		
//		obtenerAños();
//		creaDocumentoXML_Ictus();
		//creaDocumentoXML_Ictus();
	}
	
	
	public static Set<Integer> obtenerAños() {
		PreparedStatement sAnios;
		try {
			sAnios = ConexionMySQL.getInstancia().getCon().prepareStatement("SELECT distinct fecha_ingreso FROM ictus order by fecha_ingreso desc");
			ResultSet rSAnios = sAnios.executeQuery();
			Set<Integer> anios = new HashSet<Integer>();
			Calendar calendario = new GregorianCalendar();
			while(rSAnios.next()) {
				calendario.setTime(rSAnios.getDate(1));
				anios.add(calendario.get(Calendar.YEAR));
			}
			return anios;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public static ArrayList<Rango> creaRangosEdad() {
		ArrayList<Rango> rangos = new ArrayList<Rango>();
		rangos.add(new Rango(0, 9));	
		rangos.add(new Rango(10, 19));
		rangos.add(new Rango(20, 29));
		rangos.add(new Rango(30, 39));
		rangos.add(new Rango(40, 49));
		rangos.add(new Rango(50, 59));
		rangos.add(new Rango(60, 69));
		rangos.add(new Rango(70, 79));
		rangos.add(new Rango(80, 89));
		rangos.add(new Rango(90, 99));
		rangos.add(new Rango(100, 150));
		return rangos;
	}

	public static int calculaCasos(int anio, int edadMinima, int edadMaxima, String sexo) {
		try {
			System.out.println("año: " + anio + "edad minima: " + edadMinima +" edad máxima: " + edadMaxima + " sexo" + sexo);
			PreparedStatement sentencia = ConexionMySQL.getInstancia().getCon().prepareStatement(
					"SELECT count(*) FROM ictus where year(fecha_ingreso)= ? and edad >= ? and edad <= ? and sexo like ?");
			sentencia.setInt(1, anio);
			sentencia.setInt(2, edadMinima);
			sentencia.setInt(3, edadMaxima);
			sentencia.setString(4, sexo);
			ResultSet rS = sentencia.executeQuery();
			rS.next();
			return rS.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void creaDocumentoXML_Ictus() {
		Ictus icT;
		try {
			icT = new Ictus();
			PreparedStatement sTotalTodos = ConexionMySQL.getInstancia().getCon().prepareStatement("SELECT count(*) FROM ictus");
			ResultSet rSTotalTodos = sTotalTodos.executeQuery();
			rSTotalTodos.next();
			icT.setTotal_casos(rSTotalTodos.getInt(1));
			icT.setPeriodos(new ArrayList<Periodo>());
			
			Set<Integer> anios = new HashSet<Integer>();
			anios = obtenerAños();
			
			// Ya tenemos los años en anios
			// Tb tenemos los rangos
			// Necesitamos las consultas de hombres y mujeres en esos rangos para esos cada año.
			
			PreparedStatement sTotalAnio = ConexionMySQL.getInstancia().getCon().prepareStatement("SELECT count(*) FROM ictus WHERE year(fecha_ingreso)=?");
			PreparedStatement sTotalAnioRango = ConexionMySQL.getInstancia().getCon().prepareStatement("SELECT count(*) FROM ictus where year(fecha_ingreso)= ? and edad >= ? and edad <= ?");
			PreparedStatement sTotalAnioRangoSexo = ConexionMySQL.getInstancia().getCon().prepareStatement("SELECT count(*) FROM ictus where year(fecha_ingreso)= ? and edad >= ? and edad <= ? and sexo like ?");
			for(int anio: anios) {
				sTotalAnio.setInt(1, anio);
				ResultSet rSTotalAnio = sTotalAnio.executeQuery();
				rSTotalAnio.next();
								
				Periodo periodoAct = new Periodo();
				periodoAct.setTotal_casos(rSTotalAnio.getInt(1));
				periodoAct.setAnio(anio);
				periodoAct.setEdades(new ArrayList<Edad>());
				
				for (Rango rg: rangos) {
					Edad edadAct = new Edad();
					sTotalAnioRango.setInt(1, anio);
					sTotalAnioRango.setInt(2, rg.getMinimo());
					sTotalAnioRango.setInt(3, rg.getMaximo());
					ResultSet rSTotalAnioRango = sTotalAnioRango.executeQuery();
					rSTotalAnioRango.next();
					edadAct.setTotal_casos(rSTotalAnioRango.getInt(1));
					edadAct.setMinima(rg.getMinimo());
					edadAct.setMaxima(rg.getMaximo());
										
					for(Sexo sexo: Sexo.values()) {
						sTotalAnioRangoSexo.setInt(1, anio);
						sTotalAnioRangoSexo.setInt(2, rg.getMinimo());
						sTotalAnioRangoSexo.setInt(3, rg.getMaximo());
						sTotalAnioRangoSexo.setString(4, sexo.toString());
						ResultSet rSTotalAnioRangoSexo = sTotalAnioRangoSexo.executeQuery();
						rSTotalAnioRangoSexo.next();
						if (sexo.toString()=="HOMBRE") {
							edadAct.setHombres(rSTotalAnioRangoSexo.getInt(1));
						}else {
							edadAct.setMujeres(rSTotalAnioRangoSexo.getInt(1));
						}
						
					}
					periodoAct.getEdades().add(edadAct);
				}
				icT.getPeriodos().add(periodoAct);
			}

			JAXBContext jC = JAXBContext.newInstance(Ictus.class);
			Marshaller Ms = jC.createMarshaller();
			Ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			Ms.marshal(icT, new File(Utilidades.getRutadatos() + Utilidades.getDocumentoXml()));
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	
	
	
	

	public static void llenaTablaIctus() {
		System.out.println("En llenaTablaIctus");
		int numFila = 1;
		Calendar calendario = new GregorianCalendar();
		Row fila = ConexionFicheroExcel.getInstancia().getHoja().getRow(numFila);
		// Tomamos cada fila y la colocamos en la tabla
		PreparedStatement sentencia;
		try {
			sentencia = ConexionMySQL.getInstancia().getCon().prepareStatement("INSERT INTO `ictus`"
					+ " (`fecha_ingreso`, `hospital`, `edad`, `sexo`) VALUES (?, ?, ?, ?)");
			
			ResultSet rSH; 
			PreparedStatement sentenciaH = ConexionMySQL.getInstancia().getCon().prepareStatement("SELECT id_hospital FROM hospitales WHERE nombre like ?");

			while(fila!=null) {
				//Fecha ingreso
				java.util.Date fechaI = new SimpleDateFormat("yyyy-MM-dd").parse(fila.getCell(0).getStringCellValue());
		
				sentencia.setDate(1, new java.sql.Date(fechaI.getYear(), fechaI.getMonth(), fechaI.getDay()));
				
				sentenciaH.setString(1, fila.getCell(1).getStringCellValue());
				rSH = sentenciaH.executeQuery();
				rSH.next();
				// Buscar hospital
				sentencia.setInt(2, rSH.getInt(1));
				sentencia.setInt(3, (int) fila.getCell(3).getNumericCellValue());
				sentencia.setString(4, fila.getCell(4).getStringCellValue());
				System.out.println("Insertado:" + sentencia.executeUpdate());
				
				fila = ConexionFicheroExcel.getInstancia().getHoja().getRow(numFila++);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
