package view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import presenter.StressPresenter;

public class VentanaStress extends JDialog implements IVistaStress {

	private final DefaultTableModel modeloTabla = new DefaultTableModel(
			new String[] { "Vertices", "Tiempo (ms)", "Tamano del dominante" }, 0);
	private final JTable tabla = new JTable(modeloTabla);
	private final JSpinner verticesIniciales = new JSpinner(new SpinnerNumberModel(10, 1, 500, 1));
	private final JSpinner incremento = new JSpinner(new SpinnerNumberModel(2, 1, 100, 1));
	private final JButton botonIniciar = new JButton("Iniciar");
	private final JButton botonDetener = new JButton("Detener");
	private final JLabel labelResumen = new JLabel(" ");
	private final StressPresenter presenter;

	public VentanaStress(Window propietaria) {
		super(propietaria, "Test de stress - Backtracking", Dialog.ModalityType.MODELESS);
		presenter = new StressPresenter(this);
		armarPantalla();
		conectarEventos();
		mostrarDetenido();
	}

	@Override
	public void mostrarResultado(int tamanoGrafo, long milisegundos, int tamanoConjuntoDominante) {
		SwingUtilities.invokeLater(() -> agregarFila(tamanoGrafo, milisegundos, tamanoConjuntoDominante));
	}

	@Override
	public void limpiarResultados() {
		SwingUtilities.invokeLater(() -> {
			modeloTabla.setRowCount(0);
			labelResumen.setText(" ");
			});
	}

	@Override
	public void mostrarEnEjecucion() {
		SwingUtilities.invokeLater(() -> {
			botonIniciar.setEnabled(false);
			botonDetener.setEnabled(true);
		});
	}

	@Override
	public void mostrarDetenido() {
		SwingUtilities.invokeLater(() -> {
			botonIniciar.setEnabled(true);
			botonDetener.setEnabled(false);
		});
	}

	@Override
	public void mostrarError(String mensaje) {
		SwingUtilities.invokeLater(() ->
				JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE));
	}

	@Override
	public void mostrarMensajeFinal(String mensaje) {
		SwingUtilities.invokeLater(() -> labelResumen.setText(mensaje));
	}
	
	private void agregarFila(int tamanoGrafo, long milisegundos, int tamanoConjuntoDominante) {
		modeloTabla.addRow(new Object[] { tamanoGrafo, milisegundos, tamanoConjuntoDominante });
		int ultimaFila = modeloTabla.getRowCount() - 1;
		tabla.scrollRectToVisible(tabla.getCellRect(ultimaFila, 0, true));
	}

	private void armarPantalla() {
		JPanel parametros = new JPanel(new FlowLayout(FlowLayout.LEFT));
		parametros.add(new JLabel("Vertices iniciales:"));
		parametros.add(verticesIniciales);
		parametros.add(new JLabel("Incremento:"));
		parametros.add(incremento);
		parametros.add(botonIniciar);
		parametros.add(botonDetener);
		
		add(parametros, BorderLayout.NORTH);
		add(new JScrollPane(tabla), BorderLayout.CENTER);
		
		labelResumen.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
		add(labelResumen, BorderLayout.SOUTH);
		setSize(620, 420);
		setLocationRelativeTo(getOwner());
	}

	private void conectarEventos() {
		botonIniciar.addActionListener(e ->
				presenter.iniciar((int) verticesIniciales.getValue(), (int) incremento.getValue()));
		botonDetener.addActionListener(e -> presenter.detener());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evento) {
				presenter.detener();
			}
		});
	}
}