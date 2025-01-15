package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class Vista_manana_paraAlumnos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldCasos;
	private JTextField textFieldEdadMinima;
	private JTextField textFieldEdadMaxima;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista_manana_paraAlumnos frame = new Vista_manana_paraAlumnos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Vista_manana_paraAlumnos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnGeneraXml = new JButton("Genera XML");
		btnGeneraXml.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnGeneraXml.setBounds(322, 217, 246, 32);
		contentPane.add(btnGeneraXml);
		
		JComboBox comboBoxAnio = new JComboBox();
		comboBoxAnio.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxAnio.setBounds(190, 39, 165, 32);
		contentPane.add(comboBoxAnio);
		
		JLabel lblNewLabel = new JLabel("Año");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(48, 39, 105, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblEligeRangoDe = new JLabel("Rango de edad de ");
		lblEligeRangoDe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEligeRangoDe.setBounds(48, 97, 140, 32);
		contentPane.add(lblEligeRangoDe);
		
		JLabel lblEligeSexo = new JLabel("Sexo");
		lblEligeSexo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEligeSexo.setBounds(48, 150, 105, 32);
		contentPane.add(lblEligeSexo);
		
		JComboBox comboBoxSexo = new JComboBox();
		comboBoxSexo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxSexo.setBounds(190, 150, 165, 32);
		contentPane.add(comboBoxSexo);
		
		JButton btnConsultaCasos = new JButton("Consulta Casos");
		btnConsultaCasos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnConsultaCasos.setBounds(52, 217, 246, 32);
		contentPane.add(btnConsultaCasos);
		
		textFieldCasos = new JTextField();
		textFieldCasos.setBounds(389, 36, 177, 32);
		contentPane.add(textFieldCasos);
		textFieldCasos.setColumns(10);
		
		textFieldEdadMinima = new JTextField();
		textFieldEdadMinima.setColumns(10);
		textFieldEdadMinima.setBounds(190, 99, 87, 32);
		contentPane.add(textFieldEdadMinima);
		
		textFieldEdadMaxima = new JTextField();
		textFieldEdadMaxima.setColumns(10);
		textFieldEdadMaxima.setBounds(338, 99, 87, 32);
		contentPane.add(textFieldEdadMaxima);
		
		JLabel lblA = new JLabel("  a ");
		lblA.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblA.setBounds(298, 97, 140, 32);
		contentPane.add(lblA);
		
	}
}
