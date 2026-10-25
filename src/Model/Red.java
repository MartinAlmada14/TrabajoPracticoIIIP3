package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Red {
	private final List<Vertice> vertices = new ArrayList<Vertice>();
	private final Map<String, Integer> indices = new HashMap<String, Integer>();
	private final Grafo grafo = new Grafo(0);

	public int agregarVertice(String nombre) {
		Vertice vertice = new Vertice(nombre);
		if(indices.containsKey(vertice.getNombre())) {
			throw new IllegalArgumentException("Ya existe un vertice llamado: " + vertice.getNombre());
		}
		vertices.add(vertice);
		int indice = grafo.agregarVertice();
		indices.put(vertice.getNombre(), indice);
		return indice;
	}

	public void agregarArista(int origen, int destino) {
		grafo.agregarArista(origen, destino);
	}

	public void eliminarArista(int origen, int destino) {
		grafo.eliminarArista(origen, destino);
	}

	public int indiceDe(String nombre) {
		Integer indice = indices.get(nombre == null ? null : nombre.trim());
		if(indice == null) {
			throw new IllegalArgumentException("No existe el vertice: " + nombre);
		}
		return indice;
	}
	
	public List<Vertice> getVertices() {
		return Collections.unmodifiableList(vertices);
	}
	
	public List<Arista> aristas() {
		return grafo.aristas();
	}
	
	public Set<Integer> resolver(Solver solver) {
		return solver.resolver(grafo);
	}
}
