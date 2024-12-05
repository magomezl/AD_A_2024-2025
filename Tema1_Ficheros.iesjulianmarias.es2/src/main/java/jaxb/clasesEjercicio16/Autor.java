package jaxb.clasesEjercicio16;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
@XmlType(propOrder= {"nombre", "apellido1", "apellido2", "puesto", "entidadTrabajo"})
public class Autor {
	private String id;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String entidadTrabajo;
	private String puesto;
	
	public Autor() {
	}

	public Autor(String id, String nombre, String apellido1, String apellido2, String entidadTrabajo, String puesto) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.entidadTrabajo = entidadTrabajo;
		this.puesto = puesto;
	}
	@XmlAttribute()
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getEntidadTrabajo() {
		return entidadTrabajo;
	}

	public void setEntidadTrabajo(String entidadTrabajo) {
		this.entidadTrabajo = entidadTrabajo;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	@Override
	public String toString() {
		return "Autor: " + apellido1 + " " + apellido2 + ", " + nombre + "(" + id + ") - " + 
					entidadTrabajo + " - " + puesto;
	}
	
	
}
