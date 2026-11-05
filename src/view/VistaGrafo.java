package view;

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

	private static final String COLOR_VERTICE_NORMAL = "#FFFFFF";
	private static final String COLOR_VERTICE_DOMINANTE = "#90EE90";
	private static final String ESTILO_VERTICE =
			"shape=ellipse;fillColor=" + COLOR_VERTICE_NORMAL + ";strokeColor=#000000;fontColor=#000000;";

	private static final int ANCHO_VERTICE = 70;
	private static final int ALTO_VERTICE = 50;
	private static final int CANTIDAD_FILA_LARGA = 3;
	private static final int CANTIDAD_FILA_CORTA = 2;
	private static final int INICIO_FILA_LARGA = 100;
	private static final int INICIO_FILA_CORTA = 175;
	private static final int MARGEN_SUPERIOR = 50;
	private static final int SEPARACION_HORIZONTAL = 150;
	private static final int SEPARACION_VERTICAL = 110;

	private final mxGraph grafo = new mxGraph();
	private final Object parent = grafo.getDefaultParent();
	private final List<Object> vertices = new ArrayList<Object>();
	private final mxGraphComponent componente;

	public VistaGrafo() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(700, 500));

		grafo.setCellsEditable(false);
		grafo.setCellsMovable(false);
		grafo.setCellsResizable(false);

		componente = new mxGraphComponent(grafo);
		componente.setConnectable(false);

		add(componente, BorderLayout.CENTER);
	}

	@Override
	public void dibujar(int cantidadVertices, List<int[]> aristas) {
		enTransaccion(() -> {
			borrarCeldas();
			insertarVertices(cantidadVertices);
			insertarAristas(aristas);
		});
	}

	@Override
	public void resaltarDominantes(Set<Integer> dominantes) {
		enTransaccion(() -> {
			pintarTodosLosVertices(COLOR_VERTICE_NORMAL);
			pintarVertices(dominantes, COLOR_VERTICE_DOMINANTE);
		});
	}

	@Override
	public void limpiar() {
		enTransaccion(this::borrarCeldas);
	}

	private void enTransaccion(Runnable cambios) {
		grafo.getModel().beginUpdate();
		try {
			cambios.run();
		}
		finally {
			grafo.getModel().endUpdate();
		}
		componente.refresh();
	}

	private void borrarCeldas() {
		grafo.removeCells(grafo.getChildCells(parent, true, true));
		vertices.clear();
	}

	private void insertarVertices(int cantidadVertices) {
		for(int indice = 0; indice < cantidadVertices; indice++) {
			insertarVertice(indice);
		}
	}

	private void insertarVertice(int indice) {
		int[] posicion = calcularPosicion(indice);
		vertices.add(grafo.insertVertex(parent, null, String.valueOf(indice),
				posicion[0], posicion[1], ANCHO_VERTICE, ALTO_VERTICE, ESTILO_VERTICE));
	}

	private void insertarAristas(List<int[]> aristas) {
		for(int[] arista : aristas) {
			if(esIndiceValido(arista[0]) && esIndiceValido(arista[1])) {
				grafo.insertEdge(parent, null, "", vertices.get(arista[0]), vertices.get(arista[1]));
			}
		}
	}

	private void pintarTodosLosVertices(String color) {
		for(Object vertice : vertices) {
			pintar(vertice, color);
		}
	}

	private void pintarVertices(Set<Integer> indices, String color) {
		for(int indice : indices) {
			if(esIndiceValido(indice)) {
				pintar(vertices.get(indice), color);
			}
		}
	}

	private void pintar(Object vertice, String color) {
		grafo.setCellStyles(mxConstants.STYLE_FILLCOLOR, color, new Object[] { vertice });
	}

	private boolean esIndiceValido(int indice) {
		return indice >= 0 && indice < vertices.size();
	}

	private int[] calcularPosicion(int indice) {
		int fila = 0;
		int restantes = indice;

		while(restantes >= cantidadEnFila(fila)) {
			restantes -= cantidadEnFila(fila);
			fila++;
		}

		int x = inicioHorizontal(fila) + restantes * SEPARACION_HORIZONTAL;
		int y = MARGEN_SUPERIOR + fila * SEPARACION_VERTICAL;
		return new int[] { x, y };
	}

	private int cantidadEnFila(int fila) {
		return (fila % 2 == 0) ? CANTIDAD_FILA_LARGA : CANTIDAD_FILA_CORTA;
	}

	private int inicioHorizontal(int fila) {
		return (cantidadEnFila(fila) == CANTIDAD_FILA_LARGA) ? INICIO_FILA_LARGA : INICIO_FILA_CORTA;
	}
}