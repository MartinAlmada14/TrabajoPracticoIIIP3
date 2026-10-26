package Model;

import java.util.Random;

public class GeneradorGrafos {

	public static Grafo generarAleatorio(int numVertices, double probabilidadArista, Random random) {
			if(numVertices < 0) {
				throw new IllegalArgumentException("La cantidad de vertices no puede ser negativa" + numVertices);
			}
			
			if(probabilidadArista < 0.0 || probabilidadArista > 1.0){
				throw new IllegalArgumentException("La probabilidad tiene que estar entre 0.0 y 1.0");
			}
			Grafo grafo = new Grafo(numVertices);
			for(int i = 0; i < numVertices; i++) {
				for(int j = i + 1; j < numVertices; j++) {
					if(random.nextDouble() < probabilidadArista) {
						grafo.agregarArista(i, j);
					}
				}
			}
			
			return grafo;
	}
	
}
