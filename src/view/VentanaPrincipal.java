package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import persistence.GSONPersistencia;
import presenter.Presenter;
import view.backtracking.VentanaStress;

public class VentanaPrincipal {
	private final String nombre = "Conjunto dominante Minimo";
	private Presenter presenter;
	private JFrame frame;
	private VistaGrafo vistaGrafo;
	private PanelControles panelControles;

	public VentanaPrincipal() {
		inicializar();
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

	private void inicializar() {
		establecerParametros();
		inicializarClases();
		armarLayout();
		conectarEventos();
		presenter.limpiar();
	}

	private void establecerParametros() {
		frame = new JFrame(nombre);
		frame.setBounds(100, 100, 1000, 650); // dentro del límite 1366x768
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
	}

	private void inicializarClases() {
		vistaGrafo = new VistaGrafo();
		panelControles = new PanelControles();
		presenter = new Presenter(vistaGrafo, panelControles, new GSONPersistencia());
	}

	private void armarLayout() {
		frame.add(vistaGrafo, BorderLayout.CENTER);
		frame.add(crearContenedorControles(), BorderLayout.EAST);
	}

	private JPanel crearContenedorControles() {
		JPanel contenedor = new JPanel(new BorderLayout());
		contenedor.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		contenedor.add(panelControles, BorderLayout.CENTER);
		return contenedor;
	}

	private void conectarEventos() {
		panelControles.setAccionAgregarVertice(e -> presenter.agregarVertice());
		panelControles.setAccionAgregarArista(e -> presenter.agregarArista(
				panelControles.getIndiceOrigen(), panelControles.getIndiceDestino()));
		panelControles.setAccionEliminarArista(e -> presenter.eliminarArista(
				panelControles.getIndiceOrigen(), panelControles.getIndiceDestino()));

		panelControles.setAccionCalcularGoloso(e -> presenter.calcularConjuntoDominanteGoloso());
		panelControles.setAccionCalcularBacktraking(e -> presenter.calcularConjuntoDominanteBacktracking());

		panelControles.setAccionCargarArchivo(e -> presenter.cargarDesdeArchivo());
		panelControles.setAccionGuardar(e -> presenter.guardar());
		panelControles.setAccionGuardarComo(e -> presenter.guardarComo());
		panelControles.setAccionCargar(e -> presenter.cargarEjemplo());

		panelControles.setAccionTestStress(e -> abrirVentanaStress());
	}

	private void abrirVentanaStress() {
		new VentanaStress(frame).setVisible(true);
	}
}