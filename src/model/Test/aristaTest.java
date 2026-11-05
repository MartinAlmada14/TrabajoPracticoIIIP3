package model.Test;

import static org.junit.Assert.*;
import org.junit.Test;

import model.Arista;

public class aristaTest {

    @Test
    public void testGetters() {
        Arista arista = new Arista(1, 2);
        assertEquals(1, arista.getOrigen());
        assertEquals(2, arista.getDestino());
    }

    @Test
    public void testEqualsMismosVerticesMismoOrden() {
        Arista a1 = new Arista(1, 2);
        Arista a2 = new Arista(1, 2);
        assertTrue(a1.equals(a2));
    }

    @Test
    public void testEqualsMismosVerticesOrdenInvertido() {
        Arista a1 = new Arista(1, 2);
        Arista a2 = new Arista(2, 1);
        assertTrue(a1.equals(a2));
    }

    @Test
    public void testEqualsMismaInstancia() {
        Arista a = new Arista(3, 4);
        assertTrue(a.equals(a));
    }

    @Test
    public void testEqualsDistintosVertices() {
        Arista a1 = new Arista(1, 2);
        Arista a2 = new Arista(1, 3);
        assertFalse(a1.equals(a2));
    }

    @Test
    public void testEqualsConObjetoNulo() {
        Arista a = new Arista(1, 2);
        assertFalse(a.equals(null));
    }

    @Test
    public void testEqualsConTipoDiferente() {
        Arista a = new Arista(1, 2);
        assertFalse(a.equals("arista"));
    }

    @Test
    public void testHashCodeSimetria() {
        Arista a1 = new Arista(1, 2);
        Arista a2 = new Arista(2, 1);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testHashCodeIguales() {
        Arista a1 = new Arista(5, 10);
        Arista a2 = new Arista(5, 10);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testToString() {
        Arista a = new Arista(1, 2);
        assertEquals("(1,2)", a.toString());
    }

    @Test
    public void testAutoBucle() {
        Arista a1 = new Arista(3, 3);
        Arista a2 = new Arista(3, 3);
        assertEquals(3, a1.getOrigen());
        assertEquals(3, a1.getDestino());
        assertTrue(a1.equals(a2));
        assertEquals(a1.hashCode(), a2.hashCode());
        assertEquals("(3,3)", a1.toString());
    }

    @Test
    public void testVerticesNegativos() {
        Arista a1 = new Arista(-1, -2);
        Arista a2 = new Arista(-2, -1);
        assertTrue(a1.equals(a2));
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testVerticeCero() {
        Arista a1 = new Arista(0, 5);
        Arista a2 = new Arista(5, 0);
        assertTrue(a1.equals(a2));
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testValoresExtremosInteger() {
        Arista a1 = new Arista(Integer.MIN_VALUE, Integer.MAX_VALUE);
        Arista a2 = new Arista(Integer.MAX_VALUE, Integer.MIN_VALUE);
        assertTrue(a1.equals(a2));
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testTransitividadEquals() {
        Arista a = new Arista(1, 2);
        Arista b = new Arista(2, 1);
        Arista c = new Arista(1, 2);
        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));
    }
}