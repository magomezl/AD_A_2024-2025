package MVC_DEMO;

import MVC_DEMO.controlador.Controlador;
import MVC_DEMO.modelo.Modelo;
import MVC_DEMO.vista.Vista;

public class App {

	public static void main(String[] args) {
		Controlador controlador = new Controlador(new Modelo(), new Vista());
	}
}
