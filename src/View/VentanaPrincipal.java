package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
		presenter = new Presenter(vistaGrafo,panelControles);
		
		frame.add(vistaGrafo,BorderLayout.CENTER);

		JPanel contenedorControles = new JPanel(new BorderLayout());
		contenedorControles.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		contenedorControles.add(panelControles, BorderLayout.CENTER);

		frame.add(contenedorControles, BorderLayout.EAST);	
		
		conectarEventos();
		presenter.limpiar();
	}
	
	private void establecerParametros() {
		frame = new JFrame(nombre);
		
		frame.setBounds(100, 100, 1000, 650); // dentro del límite 1366x768
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
	}

	private void conectarEventos() {
	    panelControles.setAccionAgregarVertice(e -> presenter.agregarVertice());

	    panelControles.setAccionAgregarArista(e -> presenter.agregarArista(panelControles.getIndiceOrigen(),panelControles.getIndiceDestino()));

	    panelControles.setAccionEliminarArista(e -> presenter.eliminarArista(panelControles.getIndiceOrigen(),panelControles.getIndiceDestino()));

	    panelControles.setAccionCalcularGoloso(e -> presenter.calcularConjuntoDominanteGoloso());
	    
	    panelControles.setAccionCalcularBackTraking(e -> presenter.calcularConjuntoDominanteBackTraking());
	    
	    panelControles.setAccionCargarArchivo(e -> presenter.cargarDesdeArchivo());
	    
	    panelControles.setAccionGuardar(e -> presenter.guardar());

	    panelControles.setAccionCargar(e -> presenter.cargar());
	}
	
	public static void main(String[] args) {
	    EventQueue.invokeLater(() -> {
	        VentanaPrincipal ventana = new VentanaPrincipal();
	        ventana.mostrar();
	    });
	}
	
	private void mostrar() {	
		frame.setVisible(true);
	}
}
