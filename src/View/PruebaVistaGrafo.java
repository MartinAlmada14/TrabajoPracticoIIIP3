package View;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.swing.JFrame;

public class PruebaVistaGrafo {

    public static void main(String[] args) {

        JFrame ventana = new JFrame("Prueba VistaGrafo");

        VistaGrafo vista = new VistaGrafo();

        List<String> nombres = Arrays.asList(
            "0", "1", "2", "3", "4",
            "5", "6", "7", "8", "9"
        );

        List<int[]> aristas = Arrays.asList(
            new int[] {0, 1},
            new int[] {0, 3},
            new int[] {1, 2},
            new int[] {1, 4},
            new int[] {2, 5},
            new int[] {3, 4},
            new int[] {4, 5},
            new int[] {5, 6},
            new int[] {6, 7},
            new int[] {7, 8},
            new int[] {8, 9}
        );

        vista.dibujar(nombres, aristas);

        Set<Integer> dominantes = new HashSet<Integer>();
        dominantes.add(0);
        dominantes.add(4);
        dominantes.add(8);

        vista.resaltarDominantes(dominantes);

        ventana.add(vista);
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}