package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.algoritmo.Backtracking;
import model.algoritmo.Goloso;

public class RedDeGrafos {

	private final Grafo grafo = new Grafo(0);

	public void agregarVertice() {
		grafo.agregarVertice();
	}

	public void agregarArista(int origen, int destino) {
		grafo.agregarArista(origen, destino);
	}

	public void eliminarArista(int origen, int destino) {
		grafo.eliminarArista(origen, destino);
	}

	public Set<Integer> conjuntoDominanteGoloso() {
		return new Goloso().resolver(grafo);
	}

	public Set<Integer> conjuntoDominanteBacktracking() {
		return new Backtracking().resolver(grafo);
	}

	public int cantidadVertices() {
		return grafo.tamano();
	}

	public List<int[]> obtenerAristasComoPares() {
		List<int[]> pares = new ArrayList<int[]>();
		for(Arista arista : grafo.obtenerAristasSinRepetidos()) {
			pares.add(new int[] { arista.getOrigen(), arista.getDestino() });
		}
		return pares;
	}
}