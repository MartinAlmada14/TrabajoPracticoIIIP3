package View;

import java.util.List;
import java.util.Set;

public interface IVistaGrafo {
	void dibujar(List<String> nombres, List<int[]> aristas);
	void resaltarDominantes(Set<Integer> dominantes);
	void limpiar();
}
