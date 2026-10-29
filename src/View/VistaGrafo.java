
package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

public class VistaGrafo extends JPanel implements IVistaGrafo {

    private List<String> nombres;
    private List<int[]> aristas;
    private List<Point> posiciones;
    private Set<Integer> dominantes;

    private final int diametro = 40;
    private final int separacionX = 100;
    private final int separacionY = 100;
    private final int margenSuperior = 40;

    public VistaGrafo() {
        nombres = new ArrayList<String>();
        aristas = new ArrayList<int[]>();
        posiciones = new ArrayList<Point>();
        dominantes = new HashSet<Integer>();

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(700, 500));
    }

    @Override
    public void dibujar(List<String> nombres, List<int[]> aristas) {
        this.nombres = new ArrayList<String>(nombres);

        this.aristas = new ArrayList<int[]>();
        for (int[] arista : aristas) {
            this.aristas.add(arista.clone());
        }

        calcularPosiciones();
        repaint();
    }

    @Override
    public void resaltarDominantes(Set<Integer> dominantes) {
        this.dominantes = new HashSet<Integer>(dominantes);
        repaint();
    }

    @Override
    public void limpiar() {
        nombres.clear();
        aristas.clear();
        posiciones.clear();
        dominantes.clear();

        repaint();
    }

    private void calcularPosiciones() {
        posiciones.clear();

        int ancho = getWidth();

        if (ancho <= 0) {
            ancho = getPreferredSize().width;
        }

        int centroX = ancho / 2;
        int fila = 0;
        int columna = 0;

        for (int v = 0; v < nombres.size(); v++) {

            // Filas alternadas de 3 y 2 vertices
            int verticesEnFila = (fila % 2 == 0) ? 3 : 2;

            if (columna >= verticesEnFila) {
                fila++;
                columna = 0;
                verticesEnFila = (fila % 2 == 0) ? 3 : 2;
            }

            int anchoFila = (verticesEnFila - 1) * separacionX;
            int inicioX = centroX - anchoFila / 2;

            int x = inicioX + columna * separacionX;
            int y = margenSuperior + fila * separacionY;

            posiciones.add(new Point(x, y));
            columna++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        calcularPosiciones();

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Dibujar las aristas primero
        g2.setColor(Color.BLACK);

        for (int[] arista : aristas) {
            int origen = arista[0];
            int destino = arista[1];

            if (origen < 0 || origen >= posiciones.size()
                    || destino < 0 || destino >= posiciones.size()) {
                continue;
            }

            Point p1 = posiciones.get(origen);
            Point p2 = posiciones.get(destino);

            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        // Dibujar los vertices por encima de las aristas
        for (int v = 0; v < nombres.size(); v++) {
            Point p = posiciones.get(v);

            int x = p.x - diametro / 2;
            int y = p.y - diametro / 2;

            if (dominantes.contains(v)) {
                g2.setColor(Color.GREEN);
            } else {
                g2.setColor(Color.WHITE);
            }

            g2.fillOval(x, y, diametro, diametro);

            g2.setColor(Color.BLACK);
            g2.drawOval(x, y, diametro, diametro);

            String texto = nombres.get(v);
            FontMetrics fm = g2.getFontMetrics();

            int textoX = p.x - fm.stringWidth(texto) / 2;
            int textoY = p.y
                    + fm.getAscent() / 2
                    - fm.getDescent() / 2;

            g2.drawString(texto, textoX, textoY);
        }

        g2.dispose();
    }
}
