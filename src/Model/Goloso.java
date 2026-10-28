package Model;

import java.util.HashSet;
import java.util.Set;

public class Goloso implements Solver{

	@Override
	public Set<Integer> resolver(Grafo grafo) {
		Set<Integer> dominantes = new HashSet<Integer>();
		Set<Integer> sinCubrir = todosLosVertices(grafo);
		
		while (!sinCubrir.isEmpty()) {
			int mejor = verticeConMayorCobertura(grafo, sinCubrir);
			dominantes.add(mejor);
			sinCubrir.remove(mejor);
			sinCubrir.removeAll(grafo.vecinos(mejor));
		}
		return dominantes;
	}

	private int verticeConMayorCobertura(Grafo grafo, Set<Integer> sinCubrir) {
		int mejor = -1;
		int mejorCobertura = -1;
		for(int v = 0; v < grafo.tamano(); v++) {
			int cobertura = cobertura(grafo, v, sinCubrir);
			if(cobertura > mejorCobertura) {
				mejor = v;
				mejorCobertura = cobertura;
			}
		}
		return mejor;
	}

	
	
	private int cobertura(Grafo grafo, int v, Set<Integer> sinCubrir) {
		int cobertura = sinCubrir.contains(v) ? 1 : 0;
		for(int vecino : grafo.vecinos(v)) {
			if(sinCubrir.contains(vecino)) {
				cobertura++;
			}

		}
		return cobertura;
	}

	private Set<Integer> todosLosVertices(Grafo grafo) {
		Set<Integer> todos = new HashSet<Integer>();
		for(int v = 0; v < grafo.tamano(); v++) {
			todos.add(v);
		}
		return todos;
	}
	
	

}
