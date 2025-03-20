package modelo.clasesJAXB;

import java.text.DecimalFormat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;



@XmlType(propOrder= {"kmagua", "kmtierra", "kmlineacosta"})
public class Superficie_v2 {
	private double superficie;
	private double kmlineacosta;
	private double kmagua;
	private double kmtierra;
	
	public Superficie_v2() {
		super();
	}
	public Superficie_v2(double superficie, double kmlineacosta, double kmagua, double kmtierra) {
		super();
		this.superficie = superficie;
		this.kmlineacosta = kmlineacosta;
		this.kmagua = kmagua;
		this.kmtierra = kmtierra;
	}
	@XmlValue()
	public double getSuperficie() {
		return superficie;
	}
	public void setSuperficie(double superficie) {
		this.superficie = superficie;
	}
	@XmlAttribute()
	public double getKmlineacosta() {
		return kmlineacosta;
	}
	public void setKmlineacosta(double kmlineacosta) {
		this.kmlineacosta = kmlineacosta;
	}
	@XmlAttribute()
	public double getKmagua() {
		return kmagua;
	}
	public void setKmagua(double km2agua) {
		this.kmagua = km2agua;
	}
	@XmlAttribute()
	public double getKmtierra() {
		return kmtierra;
	}
	public void setKmtierra(double km2tierra) {
		this.kmtierra = km2tierra;
	}
	
	

}
