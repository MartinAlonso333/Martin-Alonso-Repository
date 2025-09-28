package org.modelo.entidades.bloques;

public enum TipoBloque {
    LADRILLO(true, false, false, 3),
    BASE(true, false, false, 1),
    BOSQUE(false, true, true, 0),
    AGUA(false, false, true, 0),
    ACERO(false, false, false, 0),
    TANQUE_DESTRUIDO(false, false, false, 0 );

    private final boolean destructible;
    private final boolean permitePaso;
    private final boolean balaimpacta;
    private final int vidaInicial;

    TipoBloque(boolean destructible, boolean permitePaso, boolean balaimpacta, int vidaInicial) {
        this.destructible = destructible;
        this.permitePaso = permitePaso;
        this.balaimpacta = balaimpacta;
        this.vidaInicial = vidaInicial;
    }

    public boolean esDestructible() { return destructible; }
    public boolean permitePaso() { return permitePaso; }
    public boolean balaimpacta() { return balaimpacta; }
    public int getVidaInicial() { return vidaInicial; }
}
