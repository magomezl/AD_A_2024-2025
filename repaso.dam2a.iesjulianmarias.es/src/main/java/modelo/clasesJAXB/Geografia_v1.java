package modelo.clasesJAXB;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement(name="geografia")
@XmlType(propOrder= {"paises", "idiomas"})
public class Geografia_v1 {
	private ArrayList<Pais_v1> paises = new ArrayList<Pais_v1>();
	private ArrayList<Idioma_v1> idiomas = new ArrayList<Idioma_v1>();

	public Geografia_v1() {
		super();
	}
	
	public Geografia_v1(ArrayList<Pais_v1> paises, ArrayList<Idioma_v1> idiomas) {
		super();
		this.paises = paises;
		this.idiomas = idiomas;
	}
	@XmlElementWrapper(name="paises")
	@XmlElement(name="pais")
	public ArrayList<Pais_v1> getPaises() {
		return paises;
	}
	public void setPaises(ArrayList<Pais_v1> paises) {
		this.paises = paises;
	}
	@XmlElementWrapper(name="idiomas_oficiales")
	@XmlElement(name="idioma")
	public ArrayList<Idioma_v1> getIdiomas() {
		return idiomas;
	}
	public void setIdiomas(ArrayList<Idioma_v1> idiomas) {
		this.idiomas = idiomas;
	}
	
	

}
