package model.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    aristaTest.class,
    backtrackingTest.class,
    ConteoDeDominacionTest.class,
    ejecucionCanceladaExceptionTest.class,
    generadorGrafosTest.class,
    golosoTest.class,
    grafoTest.class,
    red2Test.class,
    resultadoInstanciaTest.class,
    stressRunnerTest.class
})
public class allTest {
}