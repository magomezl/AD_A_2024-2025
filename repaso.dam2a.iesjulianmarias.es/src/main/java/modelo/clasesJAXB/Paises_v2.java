package modelo.clasesJAXB;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="paises")
public class Paises_v2 {
	private ArrayList<Pais_v2> paises = new ArrayList<Pais_v2>();
	
	public Paises_v2() {
		super();
	}

	public Paises_v2(ArrayList<Pais_v2> paises) {
		super();
		this.paises = paises;
	}

	@XmlElement(name="pais")
	public ArrayList<Pais_v2> getPaises() {
		return paises;
	}

	public void setPaises(ArrayList<Pais_v2> paises) {
		this.paises = paises;
	}
	
}
