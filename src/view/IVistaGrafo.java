package view;

import java.util.List;
import java.util.Set;

public interface IVistaGrafo {

    void dibujar(int cantidadVertices, List<int[]> aristas);

    void resaltarDominantes(Set<Integer> dominantes);

    void limpiar();

}