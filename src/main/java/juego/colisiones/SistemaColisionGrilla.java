package juego.colisiones;

import juego.entidades.Ente;
import juego.utilidades.Coordenada;

import java.util.ArrayList;
import java.util.List;

public class SistemaColisionGrilla {

    private static final int ANCHO_MAPA = 800;
    private static final int ALTO_MAPA = 600;
    private static final int ANCHO_CELDA = 20;
    private static final int ALTO_CELDA = 20;

    private final int cols = ANCHO_MAPA / ANCHO_CELDA;
    private final int filas = ALTO_MAPA / ALTO_CELDA;

    private final List<Ente>[][] celdas;
    private final ColisionHandler colisionHandler = new ColisionHandler();

    @SuppressWarnings("unchecked")
    public SistemaColisionGrilla() {
        celdas = new List[cols][filas];
        for (int i = 0; i < cols; i++)
            for (int j = 0; j < filas; j++)
                celdas[i][j] = new ArrayList<>();
    }

    // ------------------ AGREGAR / REMOVER ENTES ------------------
    public void agregarEnte(Ente e) {
        for (Coordenada c : celdasParaEnte(e)) {
            celdas[c.getX()][c.getY()].add(e);
        }
    }

    public void removerEnte(Ente e) {
        for (Coordenada c : celdasParaEnte(e)) {
            celdas[c.getX()][c.getY()].remove(e);
        }
    }

    // ------------------ CELDAS OCUPADAS ------------------
    private List<Coordenada> celdasParaEnte(Ente e) {
        List<Coordenada> lista = new ArrayList<>();

        int x1 = e.getPosicion().getX() / ANCHO_CELDA;
        int y1 = e.getPosicion().getY() / ALTO_CELDA;
        int x2 = (e.getPosicion().getX() + e.getDimensiones().getAncho() - 1) / ANCHO_CELDA;
        int y2 = (e.getPosicion().getY() + e.getDimensiones().getAlto() - 1) / ALTO_CELDA;

        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++)
                if (i >= 0 && i < cols && j >= 0 && j < filas)
                    lista.add(new Coordenada(i, j));

        return lista;
    }

    // ------------------ CHEQUEO DE COLISION ------------------
    public void chequearColisiones(Ente mover) {
        for (Coordenada c : celdasParaEnte(mover)) {
            for (Ente otro : new ArrayList<>(celdas[c.getX()][c.getY()])) {
                if (otro != mover && mover.intersecta(otro)) {
                    colisionHandler.manejarColision(mover, otro);
                }
            }
        }
    }

    // ------------------ ACTUALIZACION DE POSICION ------------------
    public void actualizarPosicion(Ente e, int anteriorX, int anteriorY) {
        int x1 = anteriorX / ANCHO_CELDA;
        int y1 = anteriorY / ALTO_CELDA;
        int x2 = (anteriorX + e.getDimensiones().getAncho() - 1) / ANCHO_CELDA;
        int y2 = (anteriorY + e.getDimensiones().getAlto() - 1) / ALTO_CELDA;

        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++)
                if (i >= 0 && i < cols && j >= 0 && j < filas)
                    celdas[i][j].remove(e);

        agregarEnte(e);
    }
}
