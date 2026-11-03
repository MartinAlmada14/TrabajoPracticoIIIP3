package view;

import java.nio.file.Path;
import java.util.Optional;

public interface IControles {
	void mostrarResultado(String texto);
	void mostrarError(String mensaje);
	void mostrarProgreso(int tamanoGrafo, long milisegundos);
	void habilitarDetener(boolean habilitado);
	void actualizarVertices(int cantidadVertices);
	Optional<Path> elegirArchivoParaAbrir();
	Optional<Path> elegirArchivoParaGuardar();
}
