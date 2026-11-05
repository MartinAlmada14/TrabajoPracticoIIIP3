package model.Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.ConteoDeDominacion;
import model.Grafo;

public class ConteoDeDominacionTest {

    private Grafo grafo;
    private ConteoDeDominacion conteo;

    @Before
    public void setUp() {
        grafo = new Grafo(4);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        conteo = new ConteoDeDominacion(grafo);
    }

    @Test
    public void testEstadoInicial() {
        assertFalse(conteo.todosDominados());
        assertFalse(conteo.estaDominado(0));
        assertFalse(conteo.estaDominado(1));
        assertFalse(conteo.estaDominado(2));
        assertFalse(conteo.estaDominado(3));
    }

    @Test
    public void testAgregarDominadorDominaAVecinosYASiMismo() {
        conteo.agregarDominador(0);
        assertTrue(conteo.estaDominado(0));
        assertTrue(conteo.estaDominado(1));
        assertFalse(conteo.estaDominado(2));
        assertFalse(conteo.estaDominado(3));
        assertFalse(conteo.todosDominados());
    }

    @Test
    public void testAgregarDominadoresHastaTodosDominados() {
        conteo.agregarDominador(1);
        conteo.agregarDominador(3);
        assertTrue(conteo.estaDominado(0));
        assertTrue(conteo.estaDominado(1));
        assertTrue(conteo.estaDominado(2));
        assertTrue(conteo.estaDominado(3));
        assertTrue(conteo.todosDominados());
    }

    @Test
    public void testQuitarDominador() {
        conteo.agregarDominador(0);
        conteo.quitarDominador(0);
        assertFalse(conteo.estaDominado(0));
        assertFalse(conteo.estaDominado(1));
        assertFalse(conteo.todosDominados());
    }

    @Test
    public void testSuperposicionDeDominadores() {
        conteo.agregarDominador(0);
        conteo.agregarDominador(1);
        assertTrue(conteo.estaDominado(0));
        assertTrue(conteo.estaDominado(1));
        assertTrue(conteo.estaDominado(2));
        
        conteo.quitarDominador(0);
        assertTrue(conteo.estaDominado(0));
        assertTrue(conteo.estaDominado(1));
        assertTrue(conteo.estaDominado(2));
    }

    @Test
    public void testDobleAgregarYQuitar() {
        conteo.agregarDominador(0);
        conteo.agregarDominador(0);
        assertTrue(conteo.estaDominado(0));
        assertTrue(conteo.estaDominado(1));

        conteo.quitarDominador(0);
        assertTrue(conteo.estaDominado(0));
        assertTrue(conteo.estaDominado(1));

        conteo.quitarDominador(0);
        assertFalse(conteo.estaDominado(0));
        assertFalse(conteo.estaDominado(1));
    }

    @Test
    public void testGrafoUnSoloVertice() {
        Grafo g1 = new Grafo(1);
        ConteoDeDominacion c1 = new ConteoDeDominacion(g1);
        assertFalse(c1.todosDominados());
        c1.agregarDominador(0);
        assertTrue(c1.estaDominado(0));
        assertTrue(c1.todosDominados());
        c1.quitarDominador(0);
        assertFalse(c1.todosDominados());
    }

    @Test
    public void testGrafoVacio() {
        Grafo g0 = new Grafo(0);
        ConteoDeDominacion c0 = new ConteoDeDominacion(g0);
        assertTrue(c0.todosDominados());
    }
}