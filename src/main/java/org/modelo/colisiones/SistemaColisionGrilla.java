package org.modelo.colisiones;

import org.modelo.entidades.Ente;
import org.modelo.utilidades.Coordenada;

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
            celdas[c.getCeldaX()][c.getCeldaY()].add(e);
        }
    }

    public void removerEnte(Ente e) {
        for (Coordenada c : celdasParaEnte(e)) {
            celdas[c.getCeldaX()][c.getCeldaY()].remove(e);
        }
    }

    // ------------------ CELDAS OCUPADAS ------------------
    private List<Coordenada> celdasParaEnte(Ente e) {
        List<Coordenada> lista = new ArrayList<>();
        int x1 = e.getPosicion().getCeldaX();
        int y1 = e.getPosicion().getCeldaY();

        double bordeDerecho = e.getPosicion().getPixelX() + e.getDimensiones().getAncho() - 1;
        double bordeInferior = e.getPosicion().getPixelY() + e.getDimensiones().getAlto() - 1;
        Coordenada esquinaInferiorDerecha = new Coordenada(bordeDerecho, bordeInferior);

        int x2 = esquinaInferiorDerecha.getCeldaX();
        int y2 = esquinaInferiorDerecha.getCeldaY();

        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                if (i >= 0 && i < cols && j >= 0 && j < filas) {
                    lista.add(new Coordenada(i, j));
                }
            }
        }
        return lista;
    }


    // ------------------ CHEQUEO DE COLISION ------------------
    public void chequearColisiones(Ente mover) {
        for (Coordenada c : celdasParaEnte(mover)) {
            for (Ente otro : new ArrayList<>(celdas[c.getCeldaX()][c.getCeldaY()])) {
                if (otro != mover && mover.intersecta(otro)) {
                    colisionHandler.manejarColision(mover, otro);
                }
            }
        }
    }

    // ------------------ ACTUALIZACION DE POSICION ------------------
    public void actualizarPosicion(Ente e, Coordenada anterior) {
        int x1 = anterior.getCeldaX();
        int y1 = anterior.getCeldaY();
        int x2 = (anterior.getCeldaX() + e.getDimensiones().getAncho() - 1);
        int y2 = (anterior.getCeldaY() + e.getDimensiones().getAlto() - 1);

        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++)
                if (i >= 0 && i < cols && j >= 0 && j < filas)
                    celdas[i][j].remove(e);

        agregarEnte(e);
    }
}
