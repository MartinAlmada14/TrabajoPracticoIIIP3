package model.Test;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

import model.GeneradorGrafos;
import model.Grafo;

public class generadorGrafosTest {

    @Test
    public void testGenerarGrafoSinAristasProbabilidadCero() {
        Random random = new Random(42);
        Grafo grafo = GeneradorGrafos.generarAleatorio(5, 0.0, random);
        assertEquals(5, grafo.tamano());
        assertEquals(0, grafo.cantidadAristas());
    }

    @Test
    public void testGenerarGrafoCompletoProbabilidadUno() {
        Random random = new Random(42);
        Grafo grafo = GeneradorGrafos.generarAleatorio(4, 1.0, random);
        assertEquals(4, grafo.tamano());
        assertEquals(6, grafo.cantidadAristas());
    }

    @Test
    public void testGenerarGrafoVacio() {
        Random random = new Random(42);
        Grafo grafo = GeneradorGrafos.generarAleatorio(0, 0.5, random);
        assertEquals(0, grafo.tamano());
        assertEquals(0, grafo.cantidadAristas());
    }

    @Test
    public void testEsDeterministicoConSemilla() {
        Random r1 = new Random(12345);
        Random r2 = new Random(12345);

        Grafo g1 = GeneradorGrafos.generarAleatorio(10, 0.5, r1);
        Grafo g2 = GeneradorGrafos.generarAleatorio(10, 0.5, r2);

        assertEquals(g1.cantidadAristas(), g2.cantidadAristas());
        for (int i = 0; i < 10; i++) {
            assertEquals(g1.grado(i), g2.grado(i));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerticesNegativosLanzaExcepcion() {
        GeneradorGrafos.generarAleatorio(-1, 0.5, new Random());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProbabilidadMenorACeroLanzaExcepcion() {
        GeneradorGrafos.generarAleatorio(5, -0.1, new Random());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProbabilidadMayorAUnoLanzaExcepcion() {
        GeneradorGrafos.generarAleatorio(5, 1.1, new Random());
    }
}