package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grafo {
	private List<Set<Integer>> adyacencia = new ArrayList<Set<Integer>>();

	public Grafo(int vertices) {
		if(vertices < 0) {
			throw new IllegalArgumentException("La cantidad de vertices no puede ser negativa " + vertices);
		}
		for(int i = 0; i < vertices; i++) {
			agregarVertice();
		}
	}
	
	public int agregarVertice() {
		adyacencia.add(new HashSet<Integer>());
		return adyacencia.size() - 1;
	}
	
	public void agregarArista(int origen,int destino) {
		verificarAmbosVertices(origen, destino);
		adyacencia.get(origen).add(destino);
		adyacencia.get(destino).add(origen);
	}

	public void eliminarArista(int origen, int destino) {
		verificarAmbosVertices(origen, destino);
		adyacencia.get(origen).remove(destino);
		adyacencia.get(destino).remove(origen);
	}

	public boolean existeArista(int origen, int destino) {
		verificarAmbosVertices(origen, destino);
		return adyacencia.get(origen).contains(destino);
	}

	public int tamano() {
	//	return matriz.length;
		return adyacencia.size();
	}

	public Set<Integer> vecinos(int vertice) {
		verificarVertice(vertice);		
		return Collections.unmodifiableSet(adyacencia.get(vertice));
	}
	
	public int grado(int vertice) {
		verificarVertice(vertice);
		return adyacencia.get(vertice).size();
	}

	public int cantidadAristas() {
		int sumaGrados = 0;
		for(Set<Integer> vecinos : adyacencia) {
			sumaGrados += vecinos.size();
		}
		return sumaGrados / 2;
	}
	//	OBTENGO LAS ARISTAS SIN REPETIDOS
	public List<Arista> aristas(){
		List<Arista> resultado = new ArrayList<Arista>();
		for(int origen = 0; origen < adyacencia.size(); origen++) {
			for(int destino : adyacencia.get(origen)) {
				if(origen < destino) {
					resultado.add(new Arista(origen, destino));
				}
			}
		}
		
		return resultado;
	}
	
	public boolean esDominante(Set<Integer> candidato) {
		for(int v : candidato) {
			verificarVertice(v);
		}
		for(int v = 0; v < adyacencia.size(); v++) {
			if(!candidato.contains(v) && !tieneVecinoEn(v, candidato)) {
				return false;
			}
		}
		return true;
	}
	
	
	private boolean tieneVecinoEn(int v, Set<Integer> candidato) {
		for(int vecino : adyacencia.get(v)) {
			if(candidato.contains(vecino)) {
				return true;
			}
		}
		return false;
	}

	private void verificarAmbosVertices(int origen, int destino) {
		verificarVertice(destino);
		verificarVertice(origen);
		verificarDistintos(origen, destino);
	}

	private void verificarDistintos(int origen, int destino) {		
		if( origen == destino) {
			throw new IllegalArgumentException("No se permiten loops: (" + origen + ", " + destino + ")");
		}
	}

	private void verificarVertice(int vertice) {
		if(vertice < 0) {
			throw new IllegalArgumentException("El vertice no puede ser negativo: " + vertice);
		}
		if (vertice >= adyacencia.size()) {
			throw new IllegalArgumentException("Los vertices deben estar entre 0 y |V|-1: " + vertice);
		}
	}
}
