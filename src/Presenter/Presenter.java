package Presenter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import Model.Arista;
import Model.Red2;
import Persistence.GSONPersistencia;
import Persistence.IPersistencia;
import View.IControles;
import View.IVistaGrafo;

public class Presenter {

	private Red2 red;
	private IVistaGrafo vista;
	private IControles controles;
	private IPersistencia persistencia;

	public Presenter(IVistaGrafo vista, IControles controles) {
	    this.red = new Red2();
	    this.vista = vista;
	    this.controles = controles;
	    this.persistencia = new GSONPersistencia();
	}

	private void actualizarVista() {
	    int cantidadVertices = red.cantidadVertices();
	    List<Arista> aristasModelo = red.obtenerAristasSinRepetidos();
	    List<int[]> aristasVista = new ArrayList<>();

	    for (Arista arista : aristasModelo) {
	        aristasVista.add(new int[] {
	            arista.getOrigen(),
	            arista.getDestino()
	        });
	    }

	    vista.dibujar(cantidadVertices, aristasVista);
	    controles.actualizarVertices(cantidadVertices);
	}

    public void agregarVertice() {
        red.agregarVertice();
        actualizarVista();
    }

    public void agregarArista(int origen, int destino) {
        red.agregarArista(origen, destino);
        actualizarVista();
    }

    public void eliminarArista(int origen, int destino) {
        red.eliminarArista(origen, destino);
        actualizarVista();
    }

    public void calcularConjuntoDominanteGoloso() {
        Set<Integer> dominantes = red.conjuntoDominanteGoloso();
        vista.resaltarDominantes(dominantes);
    }
    
    public void calcularConjuntoDominanteBackTraking() {
  
    }
    
    public void cargarDesdeArchivo() {
        Optional<Path> archivo = controles.elegirArchivo();

        if (!archivo.isPresent()) {
            return;
        }
        try {
            red.cargarDesdeArchivo(archivo.get());
            actualizarVista();
        } catch (Exception e) {
            controles.mostrarError("No se pudo cargar el grafo.");
        }
    }
    
    public void guardar() {
        try {
            persistencia.guardar(red);
            controles.mostrarResultado("Grafo guardado correctamente.");

        } catch (Exception e) {
            controles.mostrarError("No se pudo guardar el grafo: " + e.getMessage());
        }
    }
    
    public void cargar() {
        try {
            red = persistencia.cargar();
            actualizarVista();
            controles.mostrarResultado("Grafo cargado correctamente.");

        } catch (Exception e) {

            controles.mostrarError("No se pudo cargar el grafo: " + e.getMessage());
        }
    }

    public void limpiar() {
        red = new Red2();
        actualizarVista();
    }
}