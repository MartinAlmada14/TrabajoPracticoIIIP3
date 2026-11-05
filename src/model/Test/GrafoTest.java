package model.Test;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import model.Arista;
import model.Grafo;

public class GrafoTest {

    private Grafo grafo;

    @Before
    public void setUp() {
        grafo = new Grafo(4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorVerticesNegativosLanzaExcepcion() {
        new Grafo(-1);
    }

    @Test
    public void testTamanoInicial() {
        assertEquals(4, grafo.tamano());
    }

    @Test
    public void testAgregarVertice() {
        grafo.agregarVertice();
        assertEquals(5, grafo.tamano());
    }

    @Test
    public void testAgregarYExisteArista() {
        grafo.agregarArista(0, 1);
        assertTrue(grafo.existeArista(0, 1));
        assertTrue(grafo.existeArista(1, 0));
        assertEquals(1, grafo.cantidadAristas());
    }

    @Test
    public void testEliminarArista() {
        grafo.agregarArista(0, 1);
        grafo.eliminarArista(0, 1);
        assertFalse(grafo.existeArista(0, 1));
        assertFalse(grafo.existeArista(1, 0));
        assertEquals(0, grafo.cantidadAristas());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarAristaVerticeNegativoLanzaExcepcion() {
        grafo.agregarArista(-1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarAristaVerticeFueraDeRangoLanzaExcepcion() {
        grafo.agregarArista(0, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarAristaLoopLanzaExcepcion() {
        grafo.agregarArista(2, 2);
    }

    @Test
    public void testVecinos() {
        grafo.agregarArista(0, 1);
        grafo.agregarArista(0, 2);
        Set<Integer> vecinos = grafo.vecinos(0);
        assertEquals(2, vecinos.size());
        assertTrue(vecinos.contains(1));
        assertTrue(vecinos.contains(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testVecinosEsInmodificable() {
        grafo.agregarArista(0, 1);
        Set<Integer> vecinos = grafo.vecinos(0);
        vecinos.add(3);
    }

    @Test
    public void testGrado() {
        assertEquals(0, grafo.grado(0));
        grafo.agregarArista(0, 1);
        grafo.agregarArista(0, 2);
        assertEquals(2, grafo.grado(0));
    }

    @Test
    public void testObtenerAristasSinRepetidos() {
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        List<Arista> aristas = grafo.obtenerAristasSinRepetidos();
        assertEquals(2, aristas.size());
        assertTrue(aristas.contains(new Arista(0, 1)));
        assertTrue(aristas.contains(new Arista(1, 2)));
    }

    @Test
    public void testVertices() {
        Set<Integer> v = grafo.vertices();
        assertEquals(4, v.size());
        for (int i = 0; i < 4; i++) {
            assertTrue(v.contains(i));
        }
    }

    @Test
    public void testModificarSetVerticesNoAlteraGrafo() {
        Set<Integer> v = grafo.vertices();
        v.remove(0);
        assertEquals(4, grafo.tamano());
        assertTrue(grafo.vertices().contains(0));
    }

    @Test
    public void testEsDominanteValido() {
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        grafo.agregarArista(2, 3);
        Set<Integer> candidato = new HashSet<Integer>();
        candidato.add(1);
        candidato.add(3);
        assertTrue(grafo.esDominante(candidato));
    }

    @Test
    public void testEsDominanteInvalido() {
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        Set<Integer> candidato = new HashSet<Integer>();
        candidato.add(0);
        assertFalse(grafo.esDominante(candidato));
    }

    @Test
    public void testEsDominanteCandidatoVacio() {
        assertFalse(grafo.esDominante(new HashSet<Integer>()));

        Grafo grafoVacio = new Grafo(0);
        assertTrue(grafoVacio.esDominante(new HashSet<Integer>()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEsDominanteVerticeInvalidoEnCandidato() {
        Set<Integer> candidato = new HashSet<Integer>();
        candidato.add(10);
        grafo.esDominante(candidato);
    }

    @Test
    public void testAgregarAristaDuplicadaNoIncrementaCantidad() {
        grafo.agregarArista(0, 1);
        grafo.agregarArista(0, 1);
        assertEquals(1, grafo.cantidadAristas());
        assertEquals(1, grafo.grado(0));
    }

    @Test
    public void testEliminarAristaInexistente() {
        grafo.eliminarArista(0, 1);
        assertEquals(0, grafo.cantidadAristas());
    }
}