package view.backtracking;

public interface IVistaStress {
	void mostrarResultado(int tamanoGrafo, long milisegundos, int tamanoConjuntoDominante);
	void limpiarResultados();
	void mostrarEnEjecucion();
	void mostrarDetenido();
	void mostrarError(String mensaje);
	void mostrarMensajeFinal(String mensaje);
}
