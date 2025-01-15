package modelo.clasesJAXB_manana;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class Edad {
	private int minima;
	private int maxima;
	private int total_casos;
	private int hombres;
	private int mujeres;
	
	
	
	public Edad() {
		super();
	}
	
	
	
	public Edad(int minima, int maxima, int total_casos, int hombres, int mujeres) {
		super();
		this.minima = minima;
		this.maxima = maxima;
		this.total_casos = total_casos;
		this.hombres = hombres;
		this.mujeres = mujeres;
	}



	@XmlAttribute()
	public int getMinima() {
		return minima;
	}
	public void setMinima(int minima) {
		this.minima = minima;
	}
	@XmlAttribute()
	public int getMaxima() {
		return maxima;
	}
	public void setMaxima(int maxima) {
		this.maxima = maxima;
	}
	
	@XmlAttribute()
	public int getTotal_casos() {
		return total_casos;
	}

	public void setTotal_casos(int total_casos) {
		this.total_casos = total_casos;
	}

	@XmlElement()
	public int getHombres() {
		return hombres;
	}
	public void setHombres(int hombres) {
		this.hombres = hombres;
	}
	@XmlElement()
	public int getMujeres() {
		return mujeres;
	}
	public void setMujeres(int mujeres) {
		this.mujeres = mujeres;
	}

}
