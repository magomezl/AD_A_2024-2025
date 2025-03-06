package modelo.clasesJAXB;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="geografia")
public class Geografia {
	private ArrayList<Pais> paises = new ArrayList<Pais>();
	private ArrayList<Idioma> idiomas = new ArrayList<Idioma>();

	public Geografia() {
		super();
	}
	
	public Geografia(ArrayList<Pais> paises, ArrayList<Idioma> idiomas) {
		super();
		this.paises = paises;
		this.idiomas = idiomas;
	}
	@XmlElementWrapper(name="paises")
	@XmlElement(name="pais")
	public ArrayList<Pais> getPaises() {
		return paises;
	}
	public void setPaises(ArrayList<Pais> paises) {
		this.paises = paises;
	}
	@XmlElementWrapper(name="idiomas_oficiales")
	@XmlElement(name="idioma")
	public ArrayList<Idioma> getIdiomas() {
		return idiomas;
	}
	public void setIdiomas(ArrayList<Idioma> idiomas) {
		this.idiomas = idiomas;
	}
	
	

}
