package modelo.clasesHibernate;
// Generated 10 abr 2025 11:52:28 by Hibernate Tools 6.5.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Religiones generated by hbm2java
 */
public class Religiones implements java.io.Serializable {

	private int idReligion;
	private String nombre;
	private Set practicareligioneses = new HashSet(0);

	public Religiones() {
	}

	public Religiones(int idReligion) {
		this.idReligion = idReligion;
	}

	public Religiones(int idReligion, String nombre, Set practicareligioneses) {
		this.idReligion = idReligion;
		this.nombre = nombre;
		this.practicareligioneses = practicareligioneses;
	}

	public int getIdReligion() {
		return this.idReligion;
	}

	public void setIdReligion(int idReligion) {
		this.idReligion = idReligion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getPracticareligioneses() {
		return this.practicareligioneses;
	}

	public void setPracticareligioneses(Set practicareligioneses) {
		this.practicareligioneses = practicareligioneses;
	}

}
