package presenter;

import java.util.Random;

import model.ResultadoInstancia;
import model.StressRunner;
import view.IVistaStress;

public class StressPresenter {
	private final IVistaStress vista;
	private Thread hiloDelStress;
	private volatile ResultadoInstancia ultimoResultado;
	

	public StressPresenter(IVistaStress vista) {
		this.vista = vista;
	}

	public void iniciar(int verticesIniciales, int incremento) {
		if(estaEnEjecucion()) {
			return;
		}
		vista.limpiarResultados();
		vista.mostrarEnEjecucion();
		hiloDelStress = new Thread(() -> correr(verticesIniciales, incremento), "stress-backtracking");
		hiloDelStress.start();
		ultimoResultado = null;
	}

	public void detener() {
		if(estaEnEjecucion()) {
			hiloDelStress.interrupt();
		}
	}

	private void correr(int verticesIniciales, int incremento) {
		try {
			new StressRunner(new Random(), this::alFinalizarInstancia).ejecutar(verticesIniciales, incremento);
			vista.mostrarMensajeFinal(mensajeFinal());
		} catch(IllegalArgumentException e) {
			vista.mostrarError(e.getMessage());
		} finally {
			vista.mostrarDetenido();
		}
	}

	private void alFinalizarInstancia(ResultadoInstancia resultado) {
		ultimoResultado = resultado;
		mostrarResultado(resultado);
	}
	
	private void mostrarResultado(ResultadoInstancia resultado) {
		vista.mostrarResultado(resultado.getTamanoGrafo(),
				resultado.getTiempoMiliSegundos(), resultado.getTamanoConjuntoDominante());
	}
	private String mensajeFinal() {
		if(ultimoResultado == null) {
			return "Test detenido antes de completar la primera instancia.";
		}
		return "Test detenido. Ultimo grafo resuelto: " + ultimoResultado.getTamanoGrafo()
				+ " vertices en " + ultimoResultado.getTiempoMiliSegundos()
				+ " ms (conjunto dominante de tamano " + ultimoResultado.getTamanoConjuntoDominante() + ").";
	}

	private boolean estaEnEjecucion() {
		return hiloDelStress != null && hiloDelStress.isAlive();
	}
}