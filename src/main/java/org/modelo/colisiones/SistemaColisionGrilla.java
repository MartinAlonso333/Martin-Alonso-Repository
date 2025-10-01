package org.modelo.colisiones;

import org.modelo.entidades.Ente;

import org.modelo.utilidades.Coordenada;

import java.util.HashSet;
import java.util.Set;
public class SistemaColisionGrilla {

    private static final int FILAS = 30;
    private static final int COLS = 40;
    private final Set<Ente>[][] celdas;
    private final ColisionHandler colisionHandler = new ColisionHandler();

    public SistemaColisionGrilla() {
        celdas = new HashSet[COLS][FILAS];
        for (int i = 0; i < COLS; i++)
            for (int j = 0; j < FILAS; j++)
                celdas[i][j] = new HashSet<>();
    }

    public void agregarEnte(Ente e) {
        for (int x = e.getPosicion().getCeldaX(); x <= getCeldaXFinal(e); x++)
            for (int y = e.getPosicion().getCeldaY(); y <= getCeldaYFinal(e); y++)
                if (dentroGrilla(x, y)) celdas[x][y].add(e);
    }

    public void removerEnte(Ente e) {
        for (int x = e.getPosicion().getCeldaX(); x <= getCeldaXFinal(e); x++)
            for (int y = e.getPosicion().getCeldaY(); y <= getCeldaYFinal(e); y++)
                if (dentroGrilla(x, y)) celdas[x][y].remove(e);
    }

    public void actualizarPosicion(Ente e, Coordenada antes) {
        for (int x = antes.getCeldaX(); x <= getCeldaXFinal(e, antes); x++)
            for (int y = antes.getCeldaY(); y <= getCeldaYFinal(e, antes); y++)
                if (dentroGrilla(x, y)) celdas[x][y].remove(e);
        agregarEnte(e);
    }

    public void chequearColisiones(Ente e) {
        Set<Ente> posibles = new HashSet<>();
        for (int x = e.getPosicion().getCeldaX(); x <= getCeldaXFinal(e); x++)
            for (int y = e.getPosicion().getCeldaY(); y <= getCeldaYFinal(e); y++)
                if (dentroGrilla(x, y)) posibles.addAll(celdas[x][y]);

        for (Ente otro : posibles) {
            if (otro != e && otro.estaActivo() && e.intersecta(otro)) {
                colisionHandler.manejarColision(e, otro);
            }
        }
    }

    private boolean dentroGrilla(int x, int y) {
        return x >= 0 && x < COLS && y >= 0 && y < FILAS;
    }

    // ---------- Helpers para celdas usando los bordes del ente ----------
    private int getCeldaXFinal(Ente e) {
        return (int) ((e.getPosicion().getPixelX() + e.getDimensiones().getAncho() - 1) / Coordenada.TAM_CELDA);
    }

    private int getCeldaYFinal(Ente e) {
        return (int) ((e.getPosicion().getPixelY() + e.getDimensiones().getAlto() - 1) / Coordenada.TAM_CELDA);
    }

    // Sobrecarga para posición anterior
    private int getCeldaXFinal(Ente e, Coordenada pos) {
        return (int) ((pos.getPixelX() + e.getDimensiones().getAncho() - 1) / Coordenada.TAM_CELDA);
    }

    private int getCeldaYFinal(Ente e, Coordenada pos) {
        return (int) ((pos.getPixelY() + e.getDimensiones().getAlto() - 1) / Coordenada.TAM_CELDA);
    }
}