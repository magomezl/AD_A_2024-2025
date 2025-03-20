package modelo.clasesJAXB;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"habitantes", "idiomas", "superficie", "densidad_poblacion"})
public class Pais_v2 {
	private String nombre;
	private double habitantes;
	private ArrayList<Idioma_v1> idiomas = new ArrayList<Idioma_v1>();
	private Superficie_v2 superficie;
	private double densidad_poblacion;
	
	public Pais_v2() {
		super();
	}
	public Pais_v2(String nombre, double habitantes, ArrayList<Idioma_v1> idiomas, Superficie_v2 superficie,
			double densidad) {
		super();
		this.nombre = nombre;
		this.habitantes = habitantes;
		this.idiomas = idiomas;
		this.superficie = superficie;
		this.densidad_poblacion = densidad;
	}
	@XmlAttribute()
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@XmlElement()
	public double getHabitantes() {
		return habitantes;
	}
	public void setHabitantes(double habitantes) {
		this.habitantes = habitantes;
	}
	@XmlElementWrapper(name="idiomas_oficiales")
	@XmlElement(name="idioma")
	public ArrayList<Idioma_v1> getIdiomas() {
		return idiomas;
	}
	public void setIdiomas(ArrayList<Idioma_v1> idiomas) {
		this.idiomas = idiomas;
	}
	
	@XmlElement()
	public Superficie_v2 getSuperficie() {
		return superficie;
	}
	public void setSuperficie(Superficie_v2 superficie) {
		this.superficie = superficie;
	}
	@XmlElement(name="densidad_poblacion")
	public double getDensidad_poblacion() {
		return densidad_poblacion;
	}
	public void setDensidad_poblacion(double densidad) {
		this.densidad_poblacion = densidad;
	}
	@Override
	public String toString() {
		return "Pais_v2 [nombre=" + nombre + ", habitantes=" + habitantes + ", idiomas=" + idiomas + ", superficie="
				+ superficie + ", densidad=" + densidad_poblacion + "]";
	}
	
	

}
