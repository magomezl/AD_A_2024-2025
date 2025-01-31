package modelo.clasesXML;

import java.util.HashSet;

public class Modulo {
	private String codigo;
	private String nombre;
	private int horas;
	private int curso;
	private HashSet<String> ciclos;
	
	public Modulo(String codigo, String nombre, int horas, int curso, HashSet<String> ciclos) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.horas = horas;
		this.curso = curso;
		this.ciclos = ciclos;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public int getHoras() {
		return horas;
	}

	public int getCurso() {
		return curso;
	}

	public HashSet<String> getCiclos() {
		return ciclos;
	}
	
	
}


