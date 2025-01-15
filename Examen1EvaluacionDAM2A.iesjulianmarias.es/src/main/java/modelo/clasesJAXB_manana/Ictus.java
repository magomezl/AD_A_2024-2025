package modelo.clasesJAXB_manana;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement()
public class Ictus {
	private int total_casos;
	private ArrayList<Periodo> periodos;
	
	
	public Ictus() {
		super();
	}

	public Ictus(int total_casos, ArrayList<Periodo> periodos) {
		super();
		this.total_casos = total_casos;
		this.periodos = periodos;
	}
	@XmlAttribute()
	public int getTotal_casos() {
		return total_casos;
	}

	public void setTotal_casos(int total_casos) {
		this.total_casos = total_casos;
	}

	@XmlElement(name="periodo")
	public ArrayList<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(ArrayList<Periodo> periodos) {
		this.periodos = periodos;
	}
}
