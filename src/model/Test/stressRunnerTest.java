package model.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;

import model.ObservadorDeStress;
import model.ResultadoInstancia;
import model.StressRunner;

public class stressRunnerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRandomNuloLanzaExcepcion() {
        new StressRunner(null, resultado -> {});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorObservadorNuloLanzaExcepcion() {
        new StressRunner(new Random(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncrementoMenorAUnoLanzaExcepcion() {
        StressRunner runner = new StressRunner(new Random(), resultado -> {});
        runner.ejecutar(5, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncrementoNegativoLanzaExcepcion() {
        StressRunner runner = new StressRunner(new Random(), resultado -> {});
        runner.ejecutar(5, -1);
    }

    @Test
    public void testEjecucionYNotificacionObservador() {
        List<ResultadoInstancia> resultados = new ArrayList<>();
        StressRunner runner = new StressRunner(new Random(42), resultado -> {
            resultados.add(resultado);
            if (resultados.size() == 2) {
                Thread.currentThread().interrupt();
            }
        });

        runner.ejecutar(2, 1);

        assertEquals(2, resultados.size());
        assertEquals(2, resultados.get(0).getTamanoGrafo());
        assertEquals(3, resultados.get(1).getTamanoGrafo());
        assertTrue(resultados.get(0).getTiempoMiliSegundos() >= 0);
        assertTrue(resultados.get(0).getTamanoConjuntoDominante() >= 0);
    }

    @Test
    public void testObservadorFunctionalInterface() {
        boolean[] llamado = new boolean[]{false};
        ObservadorDeStress observador = resultado -> llamado[0] = true;
        
        ResultadoInstancia mockResultado = new ResultadoInstancia(5, 100L, 2);
        observador.instanciaFinalizada(mockResultado);
        
        assertTrue(llamado[0]);
    }
}