package model.Test;

import static org.junit.Assert.*;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import model.Arista;
import model.Red2;

public class red2Test {

    private Red2 red;

    @Before
    public void setUp() {
        red = new Red2();
    }

    @Test
    public void testEstadoInicial() {
        assertEquals(0, red.cantidadVertices());
        assertTrue(red.obtenerAristasSinRepetidos().isEmpty());
    }

    @Test
    public void testAgregarVerticeYCantidadVertices() {
        red.agregarVertice();
        red.agregarVertice();
        assertEquals(2, red.cantidadVertices());
    }

    @Test
    public void testAgregarYEliminarArista() {
        red.agregarVertice();
        red.agregarVertice();
        red.agregarVertice();
        red.agregarArista(0, 1);
        red.agregarArista(1, 2);
        assertEquals(2, red.obtenerAristasSinRepetidos().size());

        red.eliminarArista(0, 1);
        List<Arista> aristas = red.obtenerAristasSinRepetidos();
        assertEquals(1, aristas.size());
        assertEquals(new Arista(1, 2), aristas.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarAristaVerticeInexistenteLanzaExcepcion() {
        red.agregarVertice();
        red.agregarArista(0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarAristaLoopLanzaExcepcion() {
        red.agregarVertice();
        red.agregarArista(0, 0);
    }

    @Test
    public void testConjuntoDominanteGoloso() {
        red.agregarVertice();
        red.agregarVertice();
        red.agregarVertice();
        red.agregarArista(0, 1);
        red.agregarArista(1, 2);

        Set<Integer> conjunto = red.conjuntoDominanteGoloso();
        assertNotNull(conjunto);
        assertFalse(conjunto.isEmpty());
    }

    @Test
    public void testConjuntoDominanteBacktracking() {
        red.agregarVertice();
        red.agregarVertice();
        red.agregarVertice();
        red.agregarArista(0, 1);
        red.agregarArista(1, 2);

        Set<Integer> conjunto = red.conjuntoDominanteBacktracking();
        assertNotNull(conjunto);
        assertEquals(1, conjunto.size());
        assertTrue(conjunto.contains(1));
    }

    @Test
    public void testCargarDesdeArchivoMetodoVacio() {
        red.cargarDesdeArchivo(null);
    }
}