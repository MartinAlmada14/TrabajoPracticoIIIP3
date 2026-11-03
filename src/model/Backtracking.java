package model;

import java.util.HashSet;
import java.util.Set;

public class Backtracking implements Solver {

	@Override
	public Set<Integer> resolver(Grafo grafo) {
		if(grafo == null) {
			throw new IllegalArgumentException("El grafo no puede ser nulo");
		}
		Busqueda busqueda = new Busqueda(grafo);
		busqueda.buscar();
		return busqueda.mejorSolucion();
	}

	private static class Busqueda {
		private final Grafo grafo;
		private final ConteoDeDominacion dominacion;
		private final Set<Integer> solucionParcial = new HashSet<Integer>();
		private Set<Integer> mejorSolucion;

		Busqueda(Grafo grafo) {
			this.grafo = grafo;
			this.dominacion = new ConteoDeDominacion(grafo);
			this.mejorSolucion = grafo.vertices();
		}

		void buscar() {
			explorar(0);
		}

		Set<Integer> mejorSolucion() {
			return mejorSolucion;
		}

		private void explorar(int vertice) {
			verificarCancelacion();
			if(noPuedeMejorar()) {
				return;
			}
			if(dominacion.todosDominados()) {
				guardarSolucionParcial();
				return;
			}
			if(esCaminoSinSalida(vertice)) {
				return;
			}
			explorarEligiendo(vertice);
			explorarDescartando(vertice);
		}

		private void explorarEligiendo(int vertice) {
			solucionParcial.add(vertice);
			dominacion.agregarDominador(vertice);
			explorar(vertice + 1);
			dominacion.quitarDominador(vertice);
			solucionParcial.remove(vertice);
		}

		private void explorarDescartando(int vertice) {
			explorar(vertice + 1);
		}

		private boolean noPuedeMejorar() {
			return solucionParcial.size() >= mejorSolucion.size();
		}

		private void guardarSolucionParcial() {
			mejorSolucion = new HashSet<Integer>(solucionParcial);
		}

		private boolean esCaminoSinSalida(int primerVerticeSinDecidir) {
			return primerVerticeSinDecidir == grafo.tamano()
					|| hayVerticeImposibleDeDominar(primerVerticeSinDecidir);
		}

		// Poda: un vertice ya decidido, sin dominar y sin vecinos por decidir, no tiene arreglo
		private boolean hayVerticeImposibleDeDominar(int primerVerticeSinDecidir) {
			for(int decidido = 0; decidido < primerVerticeSinDecidir; decidido++) {
				if(!dominacion.estaDominado(decidido)
						&& !tieneVecinoSinDecidir(decidido, primerVerticeSinDecidir)) {
					return true;
				}
			}
			return false;
		}

		private boolean tieneVecinoSinDecidir(int vertice, int primerVerticeSinDecidir) {
			for(int vecino : grafo.vecinos(vertice)) {
				if(vecino >= primerVerticeSinDecidir) {
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
	}
}