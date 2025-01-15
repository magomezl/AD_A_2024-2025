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

public class Vista_manana extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JButton btnGeneraXml;
	public JTextField textFieldCasos;
	public JComboBox<Integer> comboBoxAnio;
	public JLabel lblEligeSexo;
	public JComboBox<String> combooxSexo;
	public JButton btnConsultaCasos;
	private JLabel lblEligeRangoDe;
	public JTextField textFieldEdadMinima;
	private JLabel lblA;
	public JTextField textFieldEdadMaxima;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista_manana frame = new Vista_manana();
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
	public Vista_manana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnGeneraXml = new JButton("Genera XML");
		btnGeneraXml.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnGeneraXml.setBounds(322, 217, 246, 32);
		contentPane.add(btnGeneraXml);
		
		comboBoxAnio = new JComboBox();
		comboBoxAnio.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxAnio.setBounds(171, 39, 184, 32);
		contentPane.add(comboBoxAnio);
		
		JLabel lblNewLabel = new JLabel("Año");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(48, 39, 150, 32);
		contentPane.add(lblNewLabel);
		
		lblEligeSexo = new JLabel("Sexo");
		lblEligeSexo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEligeSexo.setBounds(48, 150, 150, 32);
		contentPane.add(lblEligeSexo);
		
		combooxSexo = new JComboBox();
		combooxSexo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		combooxSexo.setBounds(171, 150, 184, 32);
		contentPane.add(combooxSexo);
		
		btnConsultaCasos = new JButton("Consulta Casos");
		btnConsultaCasos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnConsultaCasos.setBounds(52, 217, 246, 32);
		contentPane.add(btnConsultaCasos);
		
		textFieldCasos = new JTextField();
		textFieldCasos.setBounds(389, 36, 177, 42);
		contentPane.add(textFieldCasos);
		textFieldCasos.setColumns(10);
		
		lblEligeRangoDe = new JLabel("Rango de edad de ");
		lblEligeRangoDe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEligeRangoDe.setBounds(43, 105, 140, 32);
		contentPane.add(lblEligeRangoDe);
		
		textFieldEdadMinima = new JTextField();
		textFieldEdadMinima.setColumns(10);
		textFieldEdadMinima.setBounds(185, 107, 87, 32);
		contentPane.add(textFieldEdadMinima);
		
		lblA = new JLabel("  a ");
		lblA.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblA.setBounds(293, 105, 34, 32);
		contentPane.add(lblA);
		
		textFieldEdadMaxima = new JTextField();
		textFieldEdadMaxima.setColumns(10);
		textFieldEdadMaxima.setBounds(333, 107, 87, 32);
		contentPane.add(textFieldEdadMaxima);
		setVisible(true);
		setTitle("Casos Ictus");
	}
}
