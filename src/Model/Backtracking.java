package Model;

import java.util.HashSet;
import java.util.Set;

public class Backtracking implements Solver {

	@Override
	public Set<Integer> resolver(Grafo grafo) {
		if(grafo == null) {
			throw new IllegalArgumentException("El grafo no puede ser nulo");
		}
		return new Busqueda(grafo).ejecutar();
	}

	private static class Busqueda {
		private final Grafo grafo;
		private final int[] dominadoPor;
		private final Set<Integer> actual = new HashSet<Integer>();
		private Set<Integer> mejor;
		private int sinDominar;

		public Busqueda(Grafo grafo) {
			this.grafo = grafo;
			this.dominadoPor = new int[grafo.tamano()];
			this.sinDominar = grafo.tamano();
			this.mejor = todosLosVertices();
		}

		private Set<Integer> ejecutar() {
			explorar(0);
			return mejor;
		}

		private void explorar(int vertice) {
			verificarCancelacion();
			if(actual.size() >= mejor.size()) {
				return;
			}
			if(sinDominar == 0) {
				mejor = new HashSet<Integer>(actual);
				return;
			}
			if(vertice == grafo.tamano() || hayInalcanzable(vertice)) {
				return;
			}
			elegir(vertice);
			explorar(vertice + 1);
			descartar(vertice);
			explorar(vertice + 1);
		}

		private void elegir(int vertice) {
			actual.add(vertice);
			incrementar(vertice);
			for(int vecino : grafo.vecinos(vertice)) {
				incrementar(vecino);
			}
		}

		private void descartar(int vertice) {
			actual.remove(vertice);
			decrementar(vertice);
			for(int vecino : grafo.vecinos(vertice)) {
				decrementar(vecino);
			}
		}

		private void incrementar(int v) {
			if(dominadoPor[v]++ == 0) {
				sinDominar--;
			}
		}

		private void decrementar(int v) {
			if(--dominadoPor[v] == 0) {
				sinDominar++;
			}
		}

		// Poda: un vertice ya decidido, sin dominar y sin vecinos por decidir, no se puede arreglar
		private boolean hayInalcanzable(int vertice) {
			for(int u = 0; u < vertice; u++) {
				if(dominadoPor[u] == 0 && !tieneVecinoDesde(u, vertice)) {
					return true;
				}
			}
			return false;
		}

		private boolean tieneVecinoDesde(int u, int vertice) {
			for(int vecino : grafo.vecinos(u)) {
				if(vecino >= vertice) {
					return true;
				}
			}
			return false;
		}

		private void verificarCancelacion() {
			if(Thread.currentThread().isInterrupted()) {
				throw new EjecucionCanceladaException();
			}
		}

		private Set<Integer> todosLosVertices() {
			Set<Integer> todos = new HashSet<Integer>();
			for(int v = 0; v < grafo.tamano(); v++) {
				todos.add(v);
			}
			return todos;
		}
	}
}