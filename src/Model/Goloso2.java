package Model;

import java.util.HashSet;
import java.util.Set;

public class Goloso2 implements Solver{
	private Set<Integer> dominantes;
	private Set<Integer> sinCubrir;
	private int mejorVertice;
	private int mejorCobertura;

	@Override
	public Set<Integer> resolver(Grafo grafo) {
		dominantes = new HashSet<Integer>();
		sinCubrir = grafo.vertices();

		while(!sinCubrir.isEmpty()) {
			
			mejorCobertura(grafo);
			dominantes.add(mejorVertice);
			
			sinCubrir.remove(mejorVertice);
			sinCubrir.removeAll(grafo.vecinos(mejorVertice));
		}

		return dominantes;
	}
	
	private void mejorCobertura(Grafo grafo){
		mejorVertice = -1;
		mejorCobertura = -1;
		for(int vertice = 0; vertice < grafo.tamano(); vertice++) {
			int cobertura = cobertura(vertice, grafo);
			if (cobertura > mejorCobertura) {
				mejorCobertura = cobertura;
				mejorVertice = vertice;
			}
		}
	}	
	
	private int cobertura(int v, Grafo grafo) {
		int cobertura = 0;
		if(sinCubrir.contains(v)) {
			cobertura++;
		}
		Set<Integer> vecinos = grafo.vecinos(v);
		for(int vecino : vecinos) {
			if(sinCubrir.contains(vecino)) {
				cobertura++;
			}
		}		
		return cobertura;
	}
}
