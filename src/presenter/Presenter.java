package presenter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;


import model.RedDeGrafos;
import persistence.IPersistencia;
import persistence.PersistenciaException;
import view.IControles;
import view.IVistaGrafo;

public class Presenter {

	private static final Path RUTA_EJEMPLO = Paths.get("Data", "grafoPrueba.json");

	private final IVistaGrafo vista;
	private final IControles controles;
	private final IPersistencia persistencia;
	private RedDeGrafos red = new RedDeGrafos();
	private Path archivoActual;   // null si el grafo no esta asociado a un archivo

	public Presenter(IVistaGrafo vista, IControles controles, IPersistencia persistencia) {
		this.vista = vista;
		this.controles = controles;
		this.persistencia = persistencia;
	}

	public void agregarVertice() {
		red.agregarVertice();
		actualizarVista();
	}

	public void agregarArista(int origen, int destino) {
		try {
			red.agregarArista(origen, destino);
			actualizarVista();
		} catch (IllegalArgumentException e) {
			controles.mostrarError(e.getMessage());
		}
	}

	public void eliminarArista(int origen, int destino) {
		try {
			red.eliminarArista(origen, destino);
			actualizarVista();
		} catch (IllegalArgumentException e) {
			controles.mostrarError(e.getMessage());
		}
	}

	public void calcularConjuntoDominanteGoloso() {
		informar(red.conjuntoDominanteGoloso());
	}

	public void calcularConjuntoDominanteBacktracking() {
		informar(red.conjuntoDominanteBacktracking());
	}

	public void cargarEjemplo() {
		cargar(RUTA_EJEMPLO);
		archivoActual = null;     // asi "Guardar" no pisa el ejemplo
	}

	public void cargarDesdeArchivo() {
		controles.elegirArchivoParaAbrir().ifPresent(this::cargar);
	}

	public void guardar() {
		if (archivoActual == null) {
			guardarComo();
			return;
		}
		guardarEn(archivoActual);
	}

	public void guardarComo() {
		controles.elegirArchivoParaGuardar().ifPresent(this::guardarEn);
	}

	public void limpiar() {
		red = new RedDeGrafos();
		archivoActual = null;
		actualizarVista();
	}

	private void cargar(Path archivo) {
		try {
			red = persistencia.cargar(archivo);
			archivoActual = archivo;      // solo si la carga salio bien
			actualizarVista();
			controles.mostrarResultado("Grafo cargado correctamente.");
		} catch (PersistenciaException e) {
			controles.mostrarError(e.getMessage());
		}
	}

	private void guardarEn(Path archivo) {
		try {
			persistencia.guardar(red, archivo);
			archivoActual = archivo;
			controles.mostrarResultado("Grafo guardado en " + archivo);
		} catch (PersistenciaException e) {
			controles.mostrarError(e.getMessage());
		}
	}

	private void informar(Set<Integer> dominantes) {
		vista.resaltarDominantes(dominantes);
		controles.mostrarResultado(mensajeDeConjuntoDominante(dominantes));
	}

	private String mensajeDeConjuntoDominante(Set<Integer> dominantes) {
		return "Conjunto dominante (tamano " + dominantes.size() + "): "
				+ new TreeSet<Integer>(dominantes);
	}

	private void actualizarVista() {
		vista.dibujar(red.cantidadVertices(), red.obtenerAristasComoPares());
		controles.actualizarVertices(red.cantidadVertices());
	}


}