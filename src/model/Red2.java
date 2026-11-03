package model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Red2 {

    private Grafo grafo = new Grafo(0);

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

    public List<Arista> obtenerAristasSinRepetidos() {
        return grafo.obtenerAristasSinRepetidos();
    }

	public void cargarDesdeArchivo(Path path) {
		
	}
}