package model.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AristaTest.class,
    BacktrackingTest.class,
    ConteoDeDominacionTest.class,
    ComparacionSolversTest.class,
    EjecucionCanceladaExceptionTest.class,
    GeneradorGrafosTest.class,
    GolosoTest.class,
    GrafoTest.class,
    RedDeGrafosTest.class,
    ResultadoInstanciaTest.class,
    StressRunnerTest.class
})
public class AllTest {
}