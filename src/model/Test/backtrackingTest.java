package model.Test;

import static org.junit.Assert.*;

import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import model.Backtracking;
import model.EjecucionCanceladaException;
import model.Grafo;

public class backtrackingTest {

    private Backtracking solver;

    @Before
    public void setUp() {
        solver = new Backtracking();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGrafoNuloLanzaExcepcion() {
        solver.resolver(null);
    }

    @Test
    public void testGrafoVacio() {
        Grafo grafo = new Grafo(0);
        Set<Integer> solucion = solver.resolver(grafo);
        assertTrue(solucion.isEmpty());
    }

    @Test
    public void testGrafoUnVertice() {
        Grafo grafo = new Grafo(1);
        Set<Integer> solucion = solver.resolver(grafo);
        assertEquals(1, solucion.size());
        assertTrue(solucion.contains(0));
    }

    @Test
    public void testGrafoVerticesAislados() {
        Grafo grafo = new Grafo(4);
        Set<Integer> solucion = solver.resolver(grafo);
        assertEquals(4, solucion.size());
        assertTrue(solucion.contains(0));
        assertTrue(solucion.contains(1));
        assertTrue(solucion.contains(2));
        assertTrue(solucion.contains(3));
    }

    @Test
    public void testGrafoCompleto() {
        Grafo grafo = new Grafo(4);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(0, 2);
        grafo.agregarArista(0, 3);
        grafo.agregarArista(1, 2);
        grafo.agregarArista(1, 3);
        grafo.agregarArista(2, 3);

        Set<Integer> solucion = solver.resolver(grafo);
        assertEquals(1, solucion.size());
    }

    @Test
    public void testGrafoEstrella() {
        Grafo grafo = new Grafo(5);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(0, 2);
        grafo.agregarArista(0, 3);
        grafo.agregarArista(0, 4);

        Set<Integer> solucion = solver.resolver(grafo);
        assertEquals(1, solucion.size());
        assertTrue(solucion.contains(0));
    }

    @Test
    public void testGrafoCamino() {
        Grafo grafo = new Grafo(4);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        grafo.agregarArista(2, 3);

        Set<Integer> solucion = solver.resolver(grafo);
        assertEquals(2, solucion.size());
        assertTrue(esConjuntoDominanteValido(grafo, solucion));
    }

    @Test
    public void testGrafoDesconectadoConComponentes() {
        Grafo grafo = new Grafo(6);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        
        grafo.agregarArista(3, 4);
        grafo.agregarArista(4, 5);

        Set<Integer> solucion = solver.resolver(grafo);
        assertEquals(2, solucion.size());
        assertTrue(esConjuntoDominanteValido(grafo, solucion));
    }

    @Test
    public void testGrafoEnunciadoTP() {
        Grafo grafo = new Grafo(6);
        grafo.agregarArista(3, 5);
        grafo.agregarArista(3, 2);
        grafo.agregarArista(3, 4);
        grafo.agregarArista(4, 1);
        grafo.agregarArista(4, 0);
        grafo.agregarArista(1, 0);
        grafo.agregarArista(1, 2);

        Set<Integer> solucion = solver.resolver(grafo);
        assertEquals(2, solucion.size());
        assertTrue(esConjuntoDominanteValido(grafo, solucion));
    }

    @Test
    public void testOptimalidadEnGrafoCiclo() {
        Grafo ciclo = new Grafo(6);
        ciclo.agregarArista(0, 1);
        ciclo.agregarArista(1, 2);
        ciclo.agregarArista(2, 3);
        ciclo.agregarArista(3, 4);
        ciclo.agregarArista(4, 5);
        ciclo.agregarArista(5, 0);

        Set<Integer> solucion = solver.resolver(ciclo);
        assertEquals(2, solucion.size());
        assertTrue(esConjuntoDominanteValido(ciclo, solucion));
    }

    @Test(expected = EjecucionCanceladaException.class)
    public void testInterrupcionDeHiloLanzaExcepcion() throws InterruptedException {
        Grafo grafo = new Grafo(20);
        for (int i = 0; i < 20; i++) {
            for (int j = i + 1; j < 20; j++) {
                if ((i + j) % 3 == 0) {
                    grafo.agregarArista(i, j);
                }
            }
        }

        Thread.currentThread().interrupt();
        try {
            solver.resolver(grafo);
        } finally {
            Thread.interrupted();
        }
    }

    private boolean esConjuntoDominanteValido(Grafo grafo, Set<Integer> conjunto) {
        Set<Integer> dominados = new java.util.HashSet<Integer>(conjunto);
        for (int v : conjunto) {
            dominados.addAll(grafo.vecinos(v));
        }
        return dominados.size() == grafo.tamano();
    }
}