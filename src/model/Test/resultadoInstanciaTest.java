package model.Test;

import static org.junit.Assert.*;
import org.junit.Test;

import model.ResultadoInstancia;

public class resultadoInstanciaTest {

    @Test
    public void testGetters() {
        ResultadoInstancia resultado = new ResultadoInstancia(10, 150L, 3);
        assertEquals(10, resultado.getTamanoGrafo());
        assertEquals(150L, resultado.getTiempoMiliSegundos());
        assertEquals(3, resultado.getTamanoConjuntoDominante());
    }

    @Test
    public void testInstanciaValoresCero() {
        ResultadoInstancia resultado = new ResultadoInstancia(0, 0L, 0);
        assertEquals(0, resultado.getTamanoGrafo());
        assertEquals(0L, resultado.getTiempoMiliSegundos());
        assertEquals(0, resultado.getTamanoConjuntoDominante());
    }
}