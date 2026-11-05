package model.Test;

import static org.junit.Assert.*;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import model.Grafo;
import model.algoritmo.Goloso;

public class golosoTest {

    private Goloso solver;

    @Before
    public void setUp() {
        solver = new Goloso();
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
        Grafo grafo = new Grafo(3);
        Set<Integer> solucion = solver.resolver(grafo);
        assertEquals(3, solucion.size());
        assertTrue(grafo.esDominante(solucion));
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
        assertTrue(grafo.esDominante(solucion));
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
        assertTrue(grafo.esDominante(solucion));
    }

    @Test
    public void testGrafoCamino() {
        Grafo grafo = new Grafo(4);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        grafo.agregarArista(2, 3);

        Set<Integer> solucion = solver.resolver(grafo);
        assertTrue(grafo.esDominante(solucion));
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
        assertTrue(grafo.esDominante(solucion));
    }

    @Test
    public void testEmpateDeCobertura() {
        Grafo grafo = new Grafo(4);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(2, 3);

        Set<Integer> solucion = solver.resolver(grafo);
        assertEquals(2, solucion.size());
        assertTrue(grafo.esDominante(solucion));
    }
}