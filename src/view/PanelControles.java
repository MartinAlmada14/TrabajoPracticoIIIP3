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
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PanelControles extends JPanel implements IControles {

	private final int ANCHO = 300;

	private int y = 10;

	private JTextField txtNombre;
	private JTextField txtPeso;

	private JComboBox<Integer> comboOrigen;
	private JComboBox<Integer> comboDestino;

	private JButton btnAgregarVertice;
	private JButton btnAgregarArista;
	private JButton btnCalcularGoloso;
	private JButton btnCalcularBackTraking;
	private JButton btnGuardar;
	private JButton btnGuardarComo;
	private JButton btnCargar;
	private JButton btnCargarArchivo;
	private JButton btnEliminarArista;
	private JButton btnTestStress;
	private int separacion = 30;

	public PanelControles() {
		setLayout(null);
		setPreferredSize(new Dimension(ANCHO, 600));

		construirControles();
	}

	private void construirControles() {

		agregarVertice();
		agregarArista();
		eliminarArista();
		calcularGoloso();
		calcularBacktracking();
		agregarBotonesArchivo();
	}

	private void agregarVertice() {
		agregarEtiqueta("Nuevo vertice");
		y += separacion;

		/*agregarEtiqueta("Nombre:");
		txtNombre = agregarCampoTexto(80, ANCHO-80);
		y += separacion;*/

		btnAgregarVertice = new JButton("Agregar Vertice");
		btnAgregarVertice.setBounds(0, y, ANCHO, 25);
		add(btnAgregarVertice);

		y += 45;
	}

	private void agregarArista() {
		agregarEtiqueta("Nueva Arista");
		y+=separacion;

		agregarEtiqueta("Origen:");

		comboOrigen = new JComboBox<>();
		comboOrigen.setBounds(80, y, ANCHO - 80, 25);
		add(comboOrigen);

		y += separacion;

		agregarEtiqueta("Destino:");

		comboDestino = new JComboBox<>();
		comboDestino.setBounds(80, y, ANCHO - 80, 25);
		add(comboDestino);

		y += separacion;

		btnAgregarArista = new JButton("Agregar Arista");
		btnAgregarArista.setBounds(0, y, ANCHO, 25);
		add(btnAgregarArista);

		y += separacion;
	}
	
	private void eliminarArista() {
		btnEliminarArista = new JButton("Eliminar Arista");
		btnEliminarArista.setBounds(0, y, ANCHO, 25);
		add(btnEliminarArista);

		y += 45;
	}
	
	private void calcularGoloso() {
		btnCalcularGoloso = new JButton("Calcular conjunto dominante (Goloso)");
		btnCalcularGoloso.setBounds(0, y, ANCHO, 25);
		add(btnCalcularGoloso);
		y += separacion;
	}
	
	private void calcularBacktracking() {
		btnCalcularBackTraking = new JButton("Calcular conjunto dominante (Backtracking)");
		btnCalcularBackTraking.setBounds(0, y, ANCHO, 25);
		add(btnCalcularBackTraking);
		y += 45;
	}

	private void agregarBotonesArchivo() {
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(0, y, (ANCHO - 10) / 2, 25);
		add(btnGuardar);

		btnCargar = new JButton("Cargar");
		btnCargar.setBounds((ANCHO + 10) / 2, y, (ANCHO - 10) / 2, 25);
		add(btnCargar);

		y += 35;

		btnCargarArchivo = new JButton("Cargar desde archivo...");
		btnCargarArchivo.setBounds(0, y, ANCHO, 25);
		add(btnCargarArchivo);
		y += 35;
		
		btnGuardarComo = new JButton("Guardar en: ");
		btnGuardarComo.setBounds(0, y, ANCHO, 25);
		add(btnGuardarComo);
		y+= 35;
		
		btnTestStress = new JButton("Test de Stress...");
		btnTestStress.setBounds(0, y, ANCHO, 25);
		add(btnTestStress);
		y+= 35;
	}

	private void agregarEtiqueta(String texto) {
		JLabel etiqueta = new JLabel(texto);
		etiqueta.setBounds(0, y, 200, 20);
		add(etiqueta);
	}

	/*private JTextField agregarCampoTexto(int x, int ancho) {
		JTextField campo = new JTextField();
		campo.setBounds(x, y, ancho, 22);
		add(campo);

		return campo;
	}*/

	//	METODOS DE ACCION

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
	
	public void setAccionCalcularBackTraking(ActionListener listener) {
		btnCalcularBackTraking.addActionListener(listener);
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


	// METODOS DE LECTURA

	/*public String getNombre() {
		return txtNombre.getText().trim();
	}

	public String getPeso() {
		return txtPeso.getText().trim();
	}*/

	public int getIndiceOrigen() {
	    return comboOrigen.getSelectedItem() == null
	            ? -1 : (Integer) comboOrigen.getSelectedItem();
	}

	public int getIndiceDestino() {
	    return comboDestino.getSelectedItem() == null
	            ? -1 : (Integer) comboDestino.getSelectedItem();
	}

	@Override
	public Optional<Path> elegirArchivoParaAbrir() {
		JFileChooser selector = new JFileChooser("Data");
		return archivoElegido(selector, selector.showOpenDialog(this));
	}

	@Override
	public Optional<Path> elegirArchivoParaGuardar() {
		JFileChooser selector = new JFileChooser("Data");
		return archivoElegido(selector, selector.showSaveDialog(this));
	}

	private Optional<Path> archivoElegido(JFileChooser selector, int resultado) {
		if (resultado != JFileChooser.APPROVE_OPTION) {
			return Optional.empty();
		}
		return Optional.of(selector.getSelectedFile().toPath());
	}
	
	public void actualizarVertices(int cantidadVertices) {

	    comboOrigen.removeAllItems();
	    comboDestino.removeAllItems();

	    for (int i = 0; i < cantidadVertices; i++) {
	        comboOrigen.addItem(i);
	        comboDestino.addItem(i);
	    }
	}

	@Override
	public void mostrarResultado(String texto) {
	    JOptionPane.showMessageDialog(this, texto, "Operación exitosa",JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void mostrarError(String mensaje) {
	    JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void mostrarProgreso(int tamanoGrafo, long milisegundos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void habilitarDetener(boolean habilitado) {
		// TODO Auto-generated method stub
		
	}
}

