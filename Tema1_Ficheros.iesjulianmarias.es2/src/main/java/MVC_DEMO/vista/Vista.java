package MVC_DEMO.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField textOp1;
	public JTextField textOp2;
	public JTextField textResult;
	private JLabel lblOperador;
	private JLabel lblResultado;
	public JButton btnSumar;
	public JButton btnRestar;

	
	/**
	 * Create the frame.
	 */
	public Vista() {
		setTitle("MVC_Calculadora Simple");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 424, 261);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textOp1 = new JTextField();
		textOp1.setBounds(104, 30, 86, 20);
		panel.add(textOp1);
		textOp1.setColumns(10);
		
		textOp2 = new JTextField();
		textOp2.setBounds(104, 61, 86, 20);
		panel.add(textOp2);
		textOp2.setColumns(10);
		
		textResult = new JTextField();
		textResult.setColumns(10);
		textResult.setBounds(104, 104, 86, 20);
		panel.add(textResult);
		
		JLabel lblNewLabel = new JLabel("Operador 1:");
		lblNewLabel.setBounds(30, 33, 73, 14);
		panel.add(lblNewLabel);
		
		lblOperador = new JLabel("Operador 2:");
		lblOperador.setBounds(30, 64, 73, 14);
		panel.add(lblOperador);
		
		lblResultado = new JLabel("Resultado::");
		lblResultado.setBounds(30, 107, 73, 14);
		panel.add(lblResultado);
		
		btnSumar = new JButton("Sumar");
		
		btnSumar.setBounds(30, 163, 89, 23);
		panel.add(btnSumar);
		
		btnRestar = new JButton("Restar");
		btnRestar.setBounds(129, 163, 89, 23);
		panel.add(btnRestar);
		setVisible(true);
	}
}
