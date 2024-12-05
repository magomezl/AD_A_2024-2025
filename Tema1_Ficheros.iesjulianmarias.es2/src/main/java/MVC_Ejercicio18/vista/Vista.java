package MVC_Ejercicio18.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JTextField textFieldUsuario;
	public JTextField textFieldContrasenia;
	public JTextField textFieldNombre;
	public JTextField textFieldLocalidad;
	public JTable tableResultados;
	public DefaultTableModel modeloTbl = new DefaultTableModel();
	public JButton btnIniciarSesion;
	public JButton btnModificar;
	public JButton btnBorrar;
	public JButton btnGuardar;
	public JButton btnListar;
	public JButton btnNuevo;

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
		setBounds(100, 100, 520, 391);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnConectar = new JMenu("Conectar");
		menuBar.add(mnConectar);
		
		JMenu mnConectarDB = new JMenu("Conectar ddbb");
		mnConectar.add(mnConectarDB);
		
		JMenuItem mntmOracle = new JMenuItem("Oracle");
		mnConectarDB.add(mntmOracle);
		
		JMenuItem mntmMySQL = new JMenuItem("MySQL");
		mnConectarDB.add(mntmMySQL);
		
		JMenuItem mntmSQLite = new JMenuItem("SQLite");
		mnConectarDB.add(mntmSQLite);
		
		JMenuItem mntmIniciarSesion = new JMenuItem("Iniciar sesion");
		mnConectar.add(mntmIniciarSesion);
		
		JMenu mnSalir = new JMenu("Salir");
		menuBar.add(mnSalir);
		
		JMenuItem mntmDesconectar = new JMenuItem("Desconectar");
		mnSalir.add(mntmDesconectar);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnSalir.add(mntmSalir);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelLogin = new JPanel();
		panelLogin.setBounds(0, 26, 177, 144);
		contentPane.add(panelLogin);
		panelLogin.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(10, 29, 61, 14);
		panelLogin.add(lblNewLabel);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(71, 26, 86, 20);
		panelLogin.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblContrasenia = new JLabel("Contrasenia");
		lblContrasenia.setBounds(10, 57, 61, 14);
		panelLogin.add(lblContrasenia);
		
		textFieldContrasenia = new JTextField();
		textFieldContrasenia.setColumns(10);
		textFieldContrasenia.setBounds(71, 54, 86, 20);
		panelLogin.add(textFieldContrasenia);
		
		btnIniciarSesion = new JButton("Iniciar Sesión");
		btnIniciarSesion.setBounds(41, 88, 116, 23);
		panelLogin.add(btnIniciarSesion);
		
		JPanel panelCRUD = new JPanel();
		panelCRUD.setBounds(187, 29, 307, 273);
		contentPane.add(panelCRUD);
		panelCRUD.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(24, 29, 61, 14);
		panelCRUD.add(lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(85, 26, 86, 20);
		panelCRUD.add(textFieldNombre);
		
		JLabel lblLocalidad = new JLabel("Localidad");
		lblLocalidad.setBounds(24, 57, 61, 14);
		panelCRUD.add(lblLocalidad);
		
		textFieldLocalidad = new JTextField();
		textFieldLocalidad.setColumns(10);
		textFieldLocalidad.setBounds(85, 54, 86, 20);
		panelCRUD.add(textFieldLocalidad);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(199, 25, 79, 23);
		panelCRUD.add(btnNuevo);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(104, 85, 79, 23);
		panelCRUD.add(btnBorrar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGuardar.setBounds(199, 84, 79, 23);
		panelCRUD.add(btnGuardar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(10, 85, 79, 23);
		panelCRUD.add(btnModificar);
		
		btnListar = new JButton("Listar");
		btnListar.setBounds(199, 53, 79, 23);
		panelCRUD.add(btnListar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 120, 287, 142);
		
		tableResultados = new JTable();
		tableResultados.setModel(modeloTbl);
		Object[] identificadores = {"Id", "Nombre", "Localidad"};
		modeloTbl.setColumnIdentifiers(identificadores);
		scrollPane.setViewportView(tableResultados);
		tableResultados.setFillsViewportHeight(true);
		
		tableResultados.setBounds(10, 120, 287, 142);
		panelCRUD.add(scrollPane);
		setVisible(true);
	}
}
