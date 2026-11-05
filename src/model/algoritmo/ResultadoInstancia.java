package model.algoritmo;

public class ResultadoInstancia {
	private final int tamanoGrafo;
	private final long tiempoMiliSegundos;
	private final int tamanoConjuntoDominante;
	public ResultadoInstancia(int tamanoGrafo, long tiempoMiliSegundos, int tamanoConjuntoDominante) {
		this.tamanoGrafo = tamanoGrafo;
		this.tiempoMiliSegundos = tiempoMiliSegundos;
		this.tamanoConjuntoDominante = tamanoConjuntoDominante;
	}
	public int getTamanoGrafo() {
		return tamanoGrafo;
	}
	public long getTiempoMiliSegundos() {
		return tiempoMiliSegundos;
	}
	public int getTamanoConjuntoDominante() {
		return tamanoConjuntoDominante;
	}
	
	
	
}
