package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

public class VistaGrafo extends JPanel implements IVistaGrafo {

    private mxGraph grafo;
    private Object parent;
    private mxGraphComponent componente;

    private List<Object> vertices;

    public VistaGrafo() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(700, 500));

        grafo = new mxGraph();
        parent = grafo.getDefaultParent();

        vertices = new ArrayList<Object>();

        // Configuración visual e interacción.
        grafo.setCellsEditable(false);
        grafo.setCellsMovable(false);
        grafo.setCellsResizable(false);

        componente = new mxGraphComponent(grafo);
        componente.setConnectable(false);

        add(componente, BorderLayout.CENTER);
    }

    @Override
    public void dibujar(List<String> nombres, List<int[]> aristas) {

        grafo.getModel().beginUpdate();

        try {
            // Eliminamos el dibujo anterior.
            grafo.removeCells(
                grafo.getChildCells(parent, true, true)
            );

            vertices.clear();

            // Creamos los vertices.
            for (int i = 0; i < nombres.size(); i++) {

                int[] posicion = calcularPosicion(i);

                Object vertice = grafo.insertVertex(
                    parent,
                    null,
                    nombres.get(i),
                    posicion[0],
                    posicion[1],
                    70,
                    50,
                    "shape=ellipse;fillColor=#FFFFFF;strokeColor=#000000;fontColor=#000000;"
                );

                vertices.add(vertice);
            }

            // Creamos las aristas.
            for (int[] arista : aristas) {

                int origen = arista[0];
                int destino = arista[1];

                if (origen >= 0 && origen < vertices.size()
                    && destino >= 0 && destino < vertices.size()) {

                    grafo.insertEdge(
                        parent,
                        null,
                        "",
                        vertices.get(origen),
                        vertices.get(destino)
                    );
                }
            }

        } finally {
            grafo.getModel().endUpdate();
        }

        componente.refresh();
    }

    @Override
    public void resaltarDominantes(Set<Integer> dominantes) {

        grafo.getModel().beginUpdate();

        try {

            // Primero dejamos todos los vertices en blanco.
            for (Object vertice : vertices) {
                grafo.setCellStyles(
                    mxConstants.STYLE_FILLCOLOR,
                    "#FFFFFF",
                    new Object[] { vertice }
                );
            }

            // Luego resaltamos los vertices dominantes.
            for (int indice : dominantes) {

                if (indice >= 0 && indice < vertices.size()) {

                    grafo.setCellStyles(
                        mxConstants.STYLE_FILLCOLOR,
                        "#90EE90",
                        new Object[] { vertices.get(indice) }
                    );
                }
            }

        } finally {
            grafo.getModel().endUpdate();
        }

        componente.refresh();
    }

    @Override
    public void limpiar() {

        grafo.getModel().beginUpdate();

        try {
            grafo.removeCells(
                grafo.getChildCells(parent, true, true)
            );

            vertices.clear();

        } finally {
            grafo.getModel().endUpdate();
        }

        componente.refresh();
    }

    // Distribuye los vertices en filas alternadas de 3 y 2.
    private int[] calcularPosicion(int indice) {

        int fila = 0;
        int restantes = indice;

        while (true) {

            int cantidadFila = (fila % 2 == 0) ? 3 : 2;

            if (restantes < cantidadFila) {
                break;
            }

            restantes -= cantidadFila;
            fila++;
        }

        int cantidadFila = (fila % 2 == 0) ? 3 : 2;

        int separacion = 150;
        int inicioX = (cantidadFila == 3) ? 100 : 175;

        int x = inicioX + restantes * separacion;
        int y = 50 + fila * 110;

        return new int[] { x, y };
    }
}