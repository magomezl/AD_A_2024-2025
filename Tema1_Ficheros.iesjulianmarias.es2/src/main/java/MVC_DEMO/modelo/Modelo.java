package MVC_DEMO.modelo;

public class Modelo {
	private int operador1;
	private int operador2;
	private int resultado;
	
	
	public Modelo() {

	}
	
	public void setOperador1(int operador1) {
		this.operador1 = operador1;
	}

	public void setOperador2(int operador2) {
		this.operador2 = operador2;
	}


	public int suma() {
		resultado = operador1 + operador2; 
		return resultado;
	}

	public int resta() {
		resultado = operador1 - operador2; 
		return resultado;
	}
}
