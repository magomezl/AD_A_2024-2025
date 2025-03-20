package modelo.clasesJAXB;

import java.text.DecimalFormat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlType(propOrder= {"km2_agua", "km2_tierra", "km_linea_costa"})
public class Superficie_v2 {
	private double superficie;
	private double km_linea_costa;
	private double km2_agua;
	private double km2_tierra;
	
	public Superficie_v2() {
		super();
	}
	public Superficie_v2(double superficie, double km_linea_costa, double km2_agua, double km2_tierra) {
		this.superficie = superficie;
		this.km_linea_costa = km_linea_costa;
		this.km2_agua = km2_agua;
		this.km2_tierra = km2_tierra;
	}
	@XmlValue()
	public double getSuperficie() {
		return superficie;
	}
	public void setSuperficie(double superficie) {
		this.superficie = superficie;
	}
	@XmlAttribute()
	public double getKm_linea_costa() {
		return km_linea_costa;
	}
	public void setKm_linea_costa(double km_linea_costa) {
		this.km_linea_costa = km_linea_costa;
	}
	@XmlAttribute()
	public double getKm2_agua() {
		return km2_agua;
	}
	public void setKm2_agua(double km2_agua) {
		this.km2_agua = km2_agua;
	}
	@XmlAttribute()
	public double getKm2_tierra() {
		return km2_tierra;
	}
	@XmlAttribute()
	public void setKm2_tierra(double km2_tierra) {
		this.km2_tierra = km2_tierra;
	}
	@Override
	public String toString() {
		return "Superficie_v2 [superficieV=" + superficie + ", km_linea_costa=" + km_linea_costa + ", km2_agua="
				+ km2_agua + ", km2_tierra=" + km2_tierra + "]";
	}
	
	


}
