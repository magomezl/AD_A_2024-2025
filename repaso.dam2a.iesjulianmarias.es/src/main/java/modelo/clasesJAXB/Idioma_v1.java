package modelo.clasesJAXB;

import javax.xml.bind.annotation.XmlValue;

public class Idioma_v1 {
	private String idioma;

	public Idioma_v1() {
		super();
	}

	public Idioma_v1(String idioma) {
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

	@Override
	public String toString() {
		return "Idioma_v1 [idioma=" + idioma + "]";
	}
}
