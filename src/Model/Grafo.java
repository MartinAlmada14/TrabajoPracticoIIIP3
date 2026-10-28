package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grafo {
	private List<Set<Integer>> vecinos = new ArrayList<Set<Integer>>();

	public Grafo(int cantVertices) {
		if(cantVertices < 0) {
			throw new IllegalArgumentException("La cantidad de vertices no puede ser negativa " + cantVertices);
		}
		for(int i = 0; i < cantVertices; i++) {
			agregarVertice();
		}
	}
	
	public void agregarVertice() {
		vecinos.add(new HashSet<Integer>());
	}
	
	public void agregarArista(int origen,int destino) {
		verificarAmbosVertices(origen, destino);
		vecinos.get(origen).add(destino);
		vecinos.get(destino).add(origen);
	}

	public void eliminarArista(int origen, int destino) {
		verificarAmbosVertices(origen, destino);
		vecinos.get(origen).remove(destino);
		vecinos.get(destino).remove(origen);
	}

	public boolean existeArista(int origen, int destino) {
		verificarAmbosVertices(origen, destino);
		return vecinos.get(origen).contains(destino);
	}

	public int tamano() {
		return vecinos.size();
	}

	public Set<Integer> vecinos(int vertice) {
		verificarVertice(vertice);		
		return Collections.unmodifiableSet(vecinos.get(vertice));
	}
	
	public int grado(int vertice) {
		verificarVertice(vertice);
		return vecinos.get(vertice).size();
	}

	public int cantidadAristas() {
		int sumaGrados = 0;
		for(Set<Integer> vecinos : vecinos) {
			sumaGrados += vecinos.size();
		}
		return sumaGrados / 2;
	}
	
	public List<Arista> aristas(){
		List<Arista> resultado = new ArrayList<Arista>();
		for(int origen = 0; origen < vecinos.size(); origen++) {
			for(int destino : vecinos.get(origen)) {
				if(origen < destino) {
					resultado.add(new Arista(origen, destino));
				}
			}
		}
		
		return resultado;
	}
	
	public Set<Integer> vertices() {
	    Set<Integer> resultado = new HashSet<Integer>();
	    for (int vertice = 0; vertice < vecinos.size(); vertice++) {
	        resultado.add(vertice);
	    }
	    return resultado;
	}
	
	public boolean esDominante(Set<Integer> candidato) {
		for(int v : candidato) {
			verificarVertice(v);
		}
		for(int v = 0; v < vecinos.size(); v++) {
			if(!candidato.contains(v) && !tieneVecinoEn(v, candidato)) {
				return false;
			}
		}
		return true;
	}
	
	
	private boolean tieneVecinoEn(int v, Set<Integer> candidato) {
		for(int vecino : vecinos.get(v)) {
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
		if (vertice >= vecinos.size()) {
			throw new IllegalArgumentException("Los vertices deben estar entre 0 y |V|-1: " + vertice);
		}
	}
}
