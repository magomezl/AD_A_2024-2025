package jaxb.clasesEjercicio16;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="examen")
public class Examen {
	private ArrayList<Autor> listaAutor;
	private ArrayList<Pregunta> listaPregunta;
	
	public Examen() {
	}
	
	public Examen(ArrayList<Autor> listaAutor, ArrayList<Pregunta> listaPregunta) {
		super();
		this.listaAutor = listaAutor;
		this.listaPregunta = listaPregunta;
	}
	@XmlElementWrapper(name="autores")
	@XmlElement(name="autor")
	public ArrayList<Autor> getListaAutor() {
		return listaAutor;
	}
	public void setListaAutor(ArrayList<Autor> listaAutor) {
		this.listaAutor = listaAutor;
	}
	@XmlElementWrapper(name="preguntas")
	@XmlElement(name="pregunta")
	public ArrayList<Pregunta> getListaPregunta() {
		return listaPregunta;
	}
	public void setListaPregunta(ArrayList<Pregunta> listaPregunta) {
		this.listaPregunta = listaPregunta;
	}
}
