package modelo.clasesJAXB;

import javax.xml.bind.annotation.XmlValue;

public class Idioma {
	private String idioma;

	public Idioma() {
		super();
	}

	public Idioma(String idioma) {
		super();
		this.idioma = idioma;
	}
	@XmlValue()
	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
}
