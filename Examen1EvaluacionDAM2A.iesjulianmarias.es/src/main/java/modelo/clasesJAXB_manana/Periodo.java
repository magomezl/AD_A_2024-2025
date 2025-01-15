package modelo.clasesJAXB_manana;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class Periodo {
	private int anio;
	private int total_casos;
	private ArrayList<Edad> edades;
	
	public Periodo() {
		super();
	}

	
	public Periodo(int anio, int total_casos, ArrayList<Edad> edades) {
		super();
		this.anio = anio;
		this.total_casos = total_casos;
		this.edades = edades;
	}


	@XmlAttribute()
	public int getAnio() {
		return anio;
	}
	
	public void setAnio(int anio) {
		this.anio = anio;
	}
	@XmlAttribute()
	public int getTotal_casos() {
		return total_casos;
	}


	public void setTotal_casos(int total_casos) {
		this.total_casos = total_casos;
	}


	
	@XmlElement(name="edad")
	public ArrayList<Edad> getEdades() {
		return edades;
	}

	public void setEdades(ArrayList<Edad> edades) {
		this.edades = edades;
	}
}
