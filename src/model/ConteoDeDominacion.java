package model;

class ConteoDeDominacion {
	private final Grafo grafo;
	private final int[] dominadores;
	private int cantidadSinDominar;

	ConteoDeDominacion(Grafo grafo) {
		this.grafo = grafo;
		this.dominadores = new int[grafo.tamano()];
		this.cantidadSinDominar = grafo.tamano();
	}

	void agregarDominador(int vertice) {
		sumarDominador(vertice);
		for(int vecino : grafo.vecinos(vertice)) {
			sumarDominador(vecino);
		}
	}

	void quitarDominador(int vertice) {
		restarDominador(vertice);
		for(int vecino : grafo.vecinos(vertice)) {
			restarDominador(vecino);
		}
	}

	boolean todosDominados() {
		return cantidadSinDominar == 0;
	}

	boolean estaDominado(int vertice) {
		return dominadores[vertice] > 0;
	}

	private void sumarDominador(int vertice) {
		boolean estabaSinDominar = dominadores[vertice] == 0;
		dominadores[vertice]++;
		if(estabaSinDominar) {
			cantidadSinDominar--;
		}
	}

	private void restarDominador(int vertice) {
		dominadores[vertice]--;
		if(dominadores[vertice] == 0) {
			cantidadSinDominar++;
		}
	}
}