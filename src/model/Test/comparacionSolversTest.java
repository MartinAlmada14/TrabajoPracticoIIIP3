package model.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import model.GeneradorGrafos;
import model.Grafo;
import model.algoritmo.Backtracking;
import model.algoritmo.Goloso;
import model.algoritmo.StressRunner;

public class comparacionSolversTest {
	
	@Test
	public void testGolosoMayorOIgualABacktrackingYAmbosSonDominantes() {
		Random random = new Random(12345); // Semilla fija para pruebas repetibles
		Goloso goloso = new Goloso();
		Backtracking backtracking = new Backtracking();

		for (int n = 4; n <= 14; n++) {
			for (int repeticion = 0; repeticion < 5; repeticion++) {
				Grafo g = GeneradorGrafos.generarAleatorio(n, 0.5, random);

				Set<Integer> solGoloso = goloso.resolver(g);
				Set<Integer> solBT = backtracking.resolver(g);

				assertTrue("La solución Golosa debe ser un conjunto dominante válido (n=" + n + ")",
						g.esDominante(solGoloso));

				assertTrue("La solución de Backtracking debe ser un conjunto dominante válido (n=" + n + ")",
						g.esDominante(solBT));

				assertTrue("El tamaño de Goloso (" + solGoloso.size()
						+ ") debe ser >= al óptimo de Backtracking (" + solBT.size() + ")",
						solGoloso.size() >= solBT.size());
			}
		}
	}
	
	@Test
	public void golosoNoEsElMasOptimo() {
		Grafo g = new Grafo(5);
		g.agregarArista(0, 1);
		g.agregarArista(0, 2);
		g.agregarArista(1, 4);
		g.agregarArista(2, 3);
		assertEquals(3, new Goloso().resolver(g).size());
		assertEquals(2, new Backtracking().resolver(g).size());
	}
	
	@Test(timeout = 5000)
	public void elStressSeDetieneAlInterrumpirElHilo() throws InterruptedException {
		AtomicInteger instancias = new AtomicInteger();
		StressRunner runner = new StressRunner(new Random(1), resultado -> instancias.incrementAndGet());
		Thread hilo = new Thread(() -> runner.ejecutar(10, 2));

		hilo.start();
		Thread.sleep(300);
		hilo.interrupt();
		hilo.join();

		assertFalse(hilo.isAlive());
		assertTrue(instancias.get() >= 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void incrementoMenorAUnoEsInvalido() {
		new StressRunner(new Random(), resultado -> { }).ejecutar(10, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void elObservadorEsObligatorio() {
		new StressRunner(new Random(), null);
	}
	
	
}