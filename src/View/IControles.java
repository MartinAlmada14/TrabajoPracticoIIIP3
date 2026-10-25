package View;

public interface IControles {
	void mostrarResultado(String texto);
	void mostrarError(String mensaje);
	void mostrarProgreso(int tamanoGrafo, long milisegundos);
	void habilitarDetener(boolean habilitado);
	
	
	
}
