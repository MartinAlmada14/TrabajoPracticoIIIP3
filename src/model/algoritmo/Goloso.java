package model.algoritmo;

import java.util.HashSet;
import java.util.Set;

import model.Grafo;

public class Goloso implements Solver {
	private Set<Integer> dominantes;
	private Set<Integer> sinCubrir;

	@Override
	public Set<Integer> resolver(Grafo grafo) {
		dominantes = new HashSet<Integer>();
		sinCubrir = grafo.vertices();

		while(!sinCubrir.isEmpty()) {
			agregarDominante(elegirMejorVertice(grafo), grafo);
		}
		return dominantes;
	}

	private void agregarDominante(int vertice, Grafo grafo) {
		dominantes.add(vertice);
		sinCubrir.remove(vertice);
		sinCubrir.removeAll(grafo.vecinos(vertice));
	}

	private int elegirMejorVertice(Grafo grafo) {
		int mejorVertice = -1;
		int mejorCobertura = -1;

		for(int vertice = 0; vertice < grafo.tamano(); vertice++) {
			int cobertura = cobertura(vertice, grafo);
			if(cobertura > mejorCobertura) {
				mejorCobertura = cobertura;
				mejorVertice = vertice;
			}
		}
		return mejorVertice;
	}

	private int cobertura(int vertice, Grafo grafo) {
		int cobertura = unoSiSinCubrir(vertice);
		for(int vecino : grafo.vecinos(vertice)) {
			cobertura += unoSiSinCubrir(vecino);
		}
		return cobertura;
	}

	private int unoSiSinCubrir(int vertice) {
		return sinCubrir.contains(vertice) ? 1 : 0;
	}
}