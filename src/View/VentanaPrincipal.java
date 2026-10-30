package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import Presenter.Presenter;

public class VentanaPrincipal {
	private Presenter presenter;
	private JFrame frame;
	private VistaGrafo vistaGrafo;
	private PanelControles panelControles;
	private String nombre = "Conjunto dominante Minimo";
	
	public VentanaPrincipal() {
		inicializar();
	}

	private void inicializar() {
		establecerParametros();
		
		vistaGrafo = new VistaGrafo();
		panelControles = new PanelControles();
		presenter = new Presenter();
		frame.add(vistaGrafo, java.awt.BorderLayout.CENTER);
		frame.add(panelControles, java.awt.BorderLayout.SOUTH);		
		conectarEventos();
	}

	private void establecerParametros() {
		frame = new JFrame(nombre);
		
		frame.setBounds(100, 100, 1000, 650); // dentro del límite 1366x768
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new java.awt.BorderLayout());
	}
	
	private void mostrar() {	
		frame.setVisible(true);
	}

	private void conectarEventos() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
	    EventQueue.invokeLater(() -> {
	        VentanaPrincipal ventana = new VentanaPrincipal();
	        ventana.mostrar();
	    });
	}
}
