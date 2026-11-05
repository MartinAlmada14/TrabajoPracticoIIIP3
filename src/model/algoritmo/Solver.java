package model.algoritmo;

import java.util.Set;
import model.Grafo;

public interface Solver {
	Set<Integer> resolver(Grafo grafo);
}
