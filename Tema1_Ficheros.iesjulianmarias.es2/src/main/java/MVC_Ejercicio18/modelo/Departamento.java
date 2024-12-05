package MVC_Ejercicio18.modelo;

public class Departamento {
	private int depNum;
	private String depNombre;
	private String depLocalidad;

	
	public Departamento() {
	}

	public Departamento(int depNum, String depNombre, String depLocalidad) {
		this.depNum = depNum;
		this.depNombre = depNombre;
		this.depLocalidad = depLocalidad;
	}

	public int getDepNum() {
		return depNum;
	}

	public void setDepNum(int depNum) {
		this.depNum = depNum;
	}

	public String getDepNombre() {
		return depNombre;
	}

	public void setDepNombre(String depNombre) {
		this.depNombre = depNombre;
	}

	public String getDepLocalidad() {
		return depLocalidad;
	}

	public void setDepLocalidad(String depLocalidad) {
		this.depLocalidad = depLocalidad;
	}
	
	

}
