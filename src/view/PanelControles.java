package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PanelControles extends JPanel implements IControles {

	private static final int ANCHO = 300;
	private static final int ALTO = 600;
	private static final int ALTO_CONTROL = 25;
	private static final int ALTO_ETIQUETA = 20;
	private static final int ANCHO_ETIQUETA = 200;
	private static final int ANCHO_COLUMNA_ETIQUETAS = 80;
	private static final int SEPARACION_ENTRE_SECCIONES = 45;
	private static final int SEPARACION_ENTRE_BOTONES = 35;
	private static final int SIN_SELECCION = -1;
	private static final String CARPETA_DE_DATOS = "Data";

	private final int separacion = 30;
	private int y = 10;

	private JComboBox<Integer> comboOrigen;
	private JComboBox<Integer> comboDestino;

	private JButton btnAgregarVertice;
	private JButton btnAgregarArista;
	private JButton btnCalcularGoloso;
	private JButton btnCalcularBacktraking;
	private JButton btnGuardar;
	private JButton btnGuardarComo;
	private JButton btnCargar;
	private JButton btnCargarArchivo;
	private JButton btnEliminarArista;
	private JButton btnTestStress;
	private static final String EXTENSION_JSON = "json";
	private static final FileNameExtensionFilter FILTRO_JSON =
			new FileNameExtensionFilter("Archivos JSON (*.json)", EXTENSION_JSON);
	public PanelControles() {
		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, ALTO));

		construirControles();
	}

	private void construirControles() {
		construirSeccionVertice();
		construirSeccionArista();
		construirSeccionEliminarArista();
		construirSeccionGoloso();
		construirSeccionBacktracking();
		construirSeccionArchivo();
	}

	private void construirSeccionVertice() {
		agregarEtiqueta("Nuevo vertice");
		y += separacion;

		btnAgregarVertice = crearBotonDeAnchoCompleto("Agregar Vertice");
		add(btnAgregarVertice);
		y += SEPARACION_ENTRE_SECCIONES;
	}

	private void construirSeccionArista() {
		agregarEtiqueta("Nueva Arista");
		y += separacion;

		agregarEtiqueta("Origen:");
		comboOrigen = crearComboDeVertices();
		add(comboOrigen);
		y += separacion;

		agregarEtiqueta("Destino:");
		comboDestino = crearComboDeVertices();
		add(comboDestino);
		y += separacion;

		btnAgregarArista = crearBotonDeAnchoCompleto("Agregar Arista");
		add(btnAgregarArista);
		y += separacion;
	}

	private void construirSeccionEliminarArista() {
		btnEliminarArista = crearBotonDeAnchoCompleto("Eliminar Arista");
		add(btnEliminarArista);
		y += SEPARACION_ENTRE_SECCIONES;
	}

	private void construirSeccionGoloso() {
		btnCalcularGoloso = crearBotonDeAnchoCompleto("Calcular conjunto dominante (Goloso)");
		add(btnCalcularGoloso);
		y += separacion;
	}

	private void construirSeccionBacktracking() {
		btnCalcularBacktraking = crearBotonDeAnchoCompleto("Calcular conjunto dominante (Backtracking)");
		add(btnCalcularBacktraking);
		y += SEPARACION_ENTRE_SECCIONES;
	}

	private void construirSeccionArchivo() {
		btnGuardar = crearBotonDeMedioAncho("Guardar", 0);
		add(btnGuardar);

		btnCargar = crearBotonDeMedioAncho("Cargar", (ANCHO + 10) / 2);
		add(btnCargar);
		y += SEPARACION_ENTRE_BOTONES;

		btnCargarArchivo = crearBotonDeAnchoCompleto("Cargar desde archivo...");
		add(btnCargarArchivo);
		y += SEPARACION_ENTRE_BOTONES;

		btnGuardarComo = crearBotonDeAnchoCompleto("Guardar en: ");
		add(btnGuardarComo);
		y += SEPARACION_ENTRE_BOTONES;

		btnTestStress = crearBotonDeAnchoCompleto("Test de Stress...");
		add(btnTestStress);
		y += SEPARACION_ENTRE_BOTONES;
	}

	private void agregarEtiqueta(String texto) {
		JLabel etiqueta = new JLabel(texto);
		etiqueta.setBounds(0, y, ANCHO_ETIQUETA, ALTO_ETIQUETA);
		add(etiqueta);
	}

	private JButton crearBotonDeAnchoCompleto(String texto) {
		JButton boton = new JButton(texto);
		boton.setBounds(0, y, ANCHO, ALTO_CONTROL);
		return boton;
	}

	private JButton crearBotonDeMedioAncho(String texto, int x) {
		JButton boton = new JButton(texto);
		boton.setBounds(x, y, (ANCHO - 10) / 2, ALTO_CONTROL);
		return boton;
	}

	private JComboBox<Integer> crearComboDeVertices() {
		JComboBox<Integer> combo = new JComboBox<>();
		combo.setBounds(ANCHO_COLUMNA_ETIQUETAS, y, ANCHO - ANCHO_COLUMNA_ETIQUETAS, ALTO_CONTROL);
		return combo;
	}

	public void setAccionAgregarVertice(ActionListener listener) {
		btnAgregarVertice.addActionListener(listener);
	}

	public void setAccionAgregarArista(ActionListener listener) {
		btnAgregarArista.addActionListener(listener);
	}

	public void setAccionEliminarArista(ActionListener listener) {
		btnEliminarArista.addActionListener(listener);
	}

	public void setAccionCalcularGoloso(ActionListener listener) {
		btnCalcularGoloso.addActionListener(listener);
	}

	public void setAccionCalcularBacktraking(ActionListener listener) {
		btnCalcularBacktraking.addActionListener(listener);
	}

	public void setAccionGuardar(ActionListener listener) {
		btnGuardar.addActionListener(listener);
	}

	public void setAccionGuardarComo(ActionListener listener) {
		btnGuardarComo.addActionListener(listener);
	}

	public void setAccionCargar(ActionListener listener) {
		btnCargar.addActionListener(listener);
	}

	public void setAccionCargarArchivo(ActionListener listener) {
		btnCargarArchivo.addActionListener(listener);
	}

	public void setAccionTestStress(ActionListener listener) {
		btnTestStress.addActionListener(listener);
	}

	public int getIndiceOrigen() {
		return indiceSeleccionado(comboOrigen);
	}

	public int getIndiceDestino() {
		return indiceSeleccionado(comboDestino);
	}

	private int indiceSeleccionado(JComboBox<Integer> combo) {
		Integer seleccionado = (Integer) combo.getSelectedItem();
		return seleccionado == null ? SIN_SELECCION : seleccionado;
	}

	@Override
	public void actualizarVertices(int cantidadVertices) {
		cargarVertices(comboOrigen, cantidadVertices);
		cargarVertices(comboDestino, cantidadVertices);
	}

	private void cargarVertices(JComboBox<Integer> combo, int cantidadVertices) {
		combo.removeAllItems();
		for(int i = 0; i < cantidadVertices; i++) {
			combo.addItem(i);
		}
	}

	@Override
	public Optional<Path> elegirArchivoParaAbrir() {
		JFileChooser selector = crearSelectorDeArchivos();
		return archivoElegido(selector, selector.showOpenDialog(this));
	}

	@Override
	public Optional<Path> elegirArchivoParaGuardar() {
		JFileChooser selector = crearSelectorDeArchivos();
		return archivoElegido(selector, selector.showSaveDialog(this))
				.map(this::conExtensionJson);
	}

	private JFileChooser crearSelectorDeArchivos() {
		JFileChooser selector = new JFileChooser(CARPETA_DE_DATOS);
		selector.setFileFilter(FILTRO_JSON);
		return selector;
	}

	private Path conExtensionJson(Path archivo) {
		String nombre = archivo.getFileName().toString();
		if(nombre.toLowerCase().endsWith("." + EXTENSION_JSON)) {
			return archivo;
		}
		return archivo.resolveSibling(nombre + "." + EXTENSION_JSON);
	}

	private Optional<Path> archivoElegido(JFileChooser selector, int resultado) {
		if(resultado != JFileChooser.APPROVE_OPTION) {
			return Optional.empty();
		}
		return Optional.of(selector.getSelectedFile().toPath());
	}

	@Override
	public void mostrarResultado(String texto) {
		JOptionPane.showMessageDialog(this, texto, "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
}