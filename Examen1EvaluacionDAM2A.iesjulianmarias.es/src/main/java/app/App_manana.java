package app;

import controlador.Controlador_manana;
import modelo.Modelo;
import vista.Vista_manana;


public class App_manana {

	public static void main(String[] args) {
		Controlador_manana controlador = new Controlador_manana(new Modelo(), new Vista_manana());
	}
}
