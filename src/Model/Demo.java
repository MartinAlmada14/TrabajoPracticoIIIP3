package Model;

import java.util.Random;
import java.util.Set;


public class Demo {

	public static void main(String[] args) throws InterruptedException {
		Grafo g = new Grafo(11);
		int[][] aristas = { {0,1},{0,2},{1,2},{2,3},{2,4},{3,4},
		                    {5,6},{5,7},{6,7},{6,8},{6,9},{8,9} };
		for(int[] a : aristas) {
			g.agregarArista(a[0], a[1]);
		}

		Set<Integer> goloso = new Goloso().resolver(g);
		Set<Integer> backtracking = new Backtracking().resolver(g);
		System.out.println("Goloso:       " + goloso + "  tamano " + goloso.size());
		System.out.println("Backtracking: " + backtracking + "  tamano " + backtracking.size());

		System.out.println("--- Stress (10 segundos) ---");
		Thread hilo = new Thread(() -> new StressRunner().ejecutar(10, 2, 0.5, new Random(),
				r -> System.out.println("n=" + r.getTamanoGrafo() + "  "
						+ r.getTiempoMiliSegundos() + " ms  dominante de "
						+ r.getTamanoConjuntoDominante())));
		hilo.start();
		Thread.sleep(10000);
		hilo.interrupt();   // esto es lo que hara el boton "Detener"
		hilo.join();
		System.out.println("Stress detenido");
	}
}