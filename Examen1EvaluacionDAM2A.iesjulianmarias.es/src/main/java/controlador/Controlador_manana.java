package controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Modelo;
import modelo.Sexo;

import vista.Vista_manana;


public class Controlador_manana {
	private Modelo modelo;
	private Vista_manana vista;
	
	public Controlador_manana(Modelo modelo, Vista_manana vista) {
		
		this.modelo = modelo;
		this.vista = vista;
		
		cargaDatosVista();
		
		vista.btnConsultaCasos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 
				
				int valor = modelo.calculaCasos(Integer.valueOf(vista.comboBoxAnio.getSelectedItem().toString()), Integer.valueOf(vista.textFieldEdadMinima.getText()),
						Integer.valueOf(vista.textFieldEdadMaxima.getText()), vista.combooxSexo.getSelectedItem().toString());
				vista.textFieldCasos.setText(String.valueOf(valor));   
			}
			
		});
		vista.btnGeneraXml.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO controlar valor devuelto por el método y deshabilitar en ese caso
				modelo.creaDocumentoXML_Ictus();
				vista.btnGeneraXml.setEnabled(false);
			}
			
		});
	}

	private void cargaDatosVista() {
		
		for(Integer anio: modelo.obtenerAños()) {
			vista.comboBoxAnio.addItem(anio);
		}
		for(Sexo sexo: Sexo.values()) {
			vista.combooxSexo.addItem(sexo.toString());
		}
	}
	
	

}
