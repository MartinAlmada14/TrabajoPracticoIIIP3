package Model;

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
	
	public Set<Integer> conjuntoDominanteGoloso(){
		Set<Integer> goloso = new Goloso().resolver(grafo);
		return goloso;
	}
}
