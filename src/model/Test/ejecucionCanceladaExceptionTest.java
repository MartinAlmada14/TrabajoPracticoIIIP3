package model.Test;

import static org.junit.Assert.*;
import org.junit.Test;

import model.algoritmo.EjecucionCanceladaException;

public class ejecucionCanceladaExceptionTest {

    @Test
    public void testConstructorMensajePorDefecto() {
        EjecucionCanceladaException exception = new EjecucionCanceladaException();
        assertEquals("La ejecucion fue cancelada por el usuario.", exception.getMessage());
    }

    @Test
    public void testEsInstanciaDeRuntimeException() {
        EjecucionCanceladaException exception = new EjecucionCanceladaException();
        assertTrue(exception instanceof RuntimeException);
    }
}