package org.modelo.entidades.bloques;

import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;

public enum TipoBloque {
    LADRILLO("BrickBlock", true, false, true, 3) {
        @Override
        public void emitirEvento(GestorEventos em, Bloque bloque) {
            if (bloque.estaDestruido()) em.notificar(TipoEvento.BLOQUE_DESTRUIDO, bloque);
        }
    },
    BASE("BaseBlock", true, false, true, 1) {
        @Override
        public void emitirEvento(GestorEventos em, Bloque bloque) {
            em.notificar(TipoEvento.BASE_DESTRUIDA, bloque);
        }
    },
    BOSQUE("ForestBlock", false, true, false, 0),
    AGUA("WaterBlock", false, false, false, 0),
    ACERO("SteelBlock", false, false, true, 0) {
        @Override
        public void emitirEvento(GestorEventos em, Bloque bloque) {
            em.notificar(TipoEvento.BLOQUE_ACERO_IMPACTADO, bloque);
        }
    },
    TANQUE_DESTRUIDO("TankDestroyed", false, false, false, 0);
    private final String claveSprite;
    private final boolean destructible;
    private final boolean permitePaso;
    private final boolean balaimpacta;
    private final int vidaInicial;

    TipoBloque(String claveSprite, boolean destructible, boolean permitePaso, boolean balaimpacta, int vidaInicial) {
        this.claveSprite = claveSprite;
        this.destructible = destructible;
        this.permitePaso = permitePaso;
        this.balaimpacta = balaimpacta;
        this.vidaInicial = vidaInicial;
    }

    public String getClaveSprite() {
        return claveSprite;
    }

    public boolean esDestructible() { return destructible; }
    public boolean permitePaso() { return permitePaso; }
    public boolean balaimpacta() { return balaimpacta; }
    public int getVidaInicial() { return vidaInicial; }
    public void emitirEvento(GestorEventos em, Bloque bloque) {}
}
