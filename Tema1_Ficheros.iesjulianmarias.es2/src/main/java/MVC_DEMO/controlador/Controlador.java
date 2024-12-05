package MVC_DEMO.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import MVC_DEMO.modelo.Modelo;
import MVC_DEMO.vista.Vista;

public class Controlador {
	private Modelo modelo;
	private Vista vista;
	
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		vista.btnSumar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tomaValores();
				vista.textResult.setText(String.valueOf(modelo.suma()));	
			}
		});
		
		vista.btnRestar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tomaValores();
				vista.textResult.setText(String.valueOf(modelo.resta()));
			}
		});
	}

	protected void tomaValores() {
		modelo.setOperador1(Integer.parseInt(vista.textOp1.getText()));
		modelo.setOperador2(Integer.parseInt(vista.textOp2.getText()));
	}
}
