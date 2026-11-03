package model;

import java.util.Random;
import java.util.Set;

public class StressRunner {
	private static final double DENSIDAD_DE_ARISTAS = 0.5;

	private final Random random;
	private final ObservadorDeStress observador;

	public StressRunner(Random random, ObservadorDeStress observador) {
		if(random == null || observador == null) {
			throw new IllegalArgumentException("El generador aleatorio y el observador son obligatorios");
		}
		this.random = random;
		this.observador = observador;
	}

	public void ejecutar(int verticesIniciales, int incremento) {
		validarIncremento(incremento);
		try {
			correrHastaSerInterrumpido(verticesIniciales, incremento);
		} catch(EjecucionCanceladaException e) {
			// La cancelacion pedida por el usuario es el final normal del stress
		}
	}

	private void validarIncremento(int incremento) {
		if(incremento < 1) {
			throw new IllegalArgumentException("El incremento debe ser al menos 1");
		}
	}

	private void correrHastaSerInterrumpido(int verticesIniciales, int incremento) {
		int vertices = verticesIniciales;
		while(!Thread.currentThread().isInterrupted()) {
			observador.instanciaFinalizada(correrInstancia(vertices));
			vertices += incremento;
		}
	}

	private ResultadoInstancia correrInstancia(int vertices) {
		Grafo grafo = GeneradorGrafos.generarAleatorio(vertices, DENSIDAD_DE_ARISTAS, random);
		long inicio = System.nanoTime();
		Set<Integer> solucion = new Backtracking().resolver(grafo);
		return new ResultadoInstancia(vertices, milisegundosDesde(inicio), solucion.size());
	}

	private long milisegundosDesde(long inicioEnNanosegundos) {
		return (System.nanoTime() - inicioEnNanosegundos) / 1_000_000;
	}
}