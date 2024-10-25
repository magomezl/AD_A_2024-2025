package jaxb.clasesEjercicio16;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

public class Respuesta {
	private String validez;
	private String texto;
	
	public Respuesta() {
		
	}
	public Respuesta(String validez, String texto) {
		super();
		this.validez = validez;
		this.texto = texto;
	}
	@XmlAttribute()
	public String getValidez() {
		return validez;
	}
	public void setValidez(String validez) {
		this.validez = validez;
	}
	@XmlValue()
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
}
