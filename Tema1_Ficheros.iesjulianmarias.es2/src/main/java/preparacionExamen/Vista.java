package preparacionExamen;

import java.awt.EventQueue;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
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
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 414, 240);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(79, 58, 194, 22);
		ManejoFechasMySQL.establecerConexion_MySQL();
		Set<String> provinciasSD = ManejoFechasMySQL.buscarProvinciasSinRepetir();
		for(String provinciaSD: provinciasSD) {
			comboBox.addItem(provinciaSD);
		}
		panel.add(comboBox);
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(79, 91, 194, 22);
		for(Colores color: Colores.values()) {
			comboBox_1.addItem(color);
		}
		
		panel.add(comboBox_1);
		
		
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(comboBox.getSelectedItem());
				System.out.println(comboBox_1.getSelectedItem().toString());
			}
		});
		btnNewButton.setBounds(79, 130, 89, 23);
		panel.add(btnNewButton);
		
		
	}
}
