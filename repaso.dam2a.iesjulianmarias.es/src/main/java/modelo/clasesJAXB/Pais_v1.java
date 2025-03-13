package modelo.clasesJAXB;

import javax.xml.bind.annotation.XmlAttribute;

public class Pais_v1 {
	private String nombre;
	private String habitantes;
	
	public Pais_v1() {
		super();
	}

	public Pais_v1(String nombre, String habitantes) {
		super();
		this.nombre = nombre;
		this.habitantes = habitantes;
	}
	@XmlAttribute()
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@XmlAttribute()
	public String getHabitantes() {
		return habitantes;
	}

	public void setHabitantes(String habitantes) {
		this.habitantes = habitantes;
	}
}
