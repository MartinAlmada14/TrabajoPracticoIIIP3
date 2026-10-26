package Model;

import java.util.Random;
import java.util.Set;

public class StressRunner {
	
	@FunctionalInterface
	public interface CallbackStress {
		void onInstanciaFinalizada(ResultadoInstancia instancia);
	}
	
	public static class ResultadoInstancia {
		private final int tamanoGrafo;
		private final long tiempoMiliSegundos;
		private final int tamanoConjuntoDominante;
		public ResultadoInstancia(int tamanoGrafo, long tiempoMiliSegundos, int tamanoConjuntoDominante) {
			this.tamanoGrafo = tamanoGrafo;
			this.tiempoMiliSegundos = tiempoMiliSegundos;
			this.tamanoConjuntoDominante = tamanoConjuntoDominante;
		}
		public int getTamanoGrafo() {
			return tamanoGrafo;
		}
		public long getTiempoMiliSegundos() {
			return tiempoMiliSegundos;
		}
		public int getTamanoConjuntoDominante() {
			return tamanoConjuntoDominante;
		}
	}
	
	public void ejecutar(int verticesIniciales, int incremento, double densidad,
			Random random, CallbackStress callback) {
		if(incremento < 1) {
			throw new IllegalArgumentException("El incremento debe ser al menos 1");
		}
		int vertices = verticesIniciales;
		try {
			while(!Thread.currentThread().isInterrupted()) {
				notificar(callback, correrInstancia(vertices, densidad, random));
				vertices += incremento;
			}
		} catch(EjecucionCanceladaException e) {
			// cancelacion pedida por el usuario: es el final normal del stress
		}
	}

	private ResultadoInstancia correrInstancia(int vertices, double densidad, Random random) {
		Grafo grafo = GeneradorGrafos.generarAleatorio(vertices, densidad, random);
		long inicio = System.nanoTime();
		Set<Integer> solucion = new Backtracking().resolver(grafo);
		long milisegundos = (System.nanoTime() - inicio) / 1_000_000;
		return new ResultadoInstancia(vertices, milisegundos, solucion.size());
	}

	private void notificar(CallbackStress callback, ResultadoInstancia resultado) {
		if(callback != null) {
			callback.onInstanciaFinalizada(resultado);
		}	
	}
	
}
