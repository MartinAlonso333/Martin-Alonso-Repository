package main.java.juego.entidades.bloques;

public enum TipoBloque {
    LADRILLO(true, false, false, 3),
    BASE(true, false, false, 1),
    BOSQUE(false, true, true, 0),
    AGUA(false, false, true, 0),
    METAL(false, false, false, 0),
    VACIO(false, true, true, 0);

    private final boolean destructible;
    private final boolean permitePaso;
    private final boolean permiteBalas;
    private final int vidaInicial;

    TipoBloque(boolean destructible, boolean permitePaso, boolean permiteBalas, int vidaInicial) {
        this.destructible = destructible;
        this.permitePaso = permitePaso;
        this.permiteBalas = permiteBalas;
        this.vidaInicial = vidaInicial;
    }

    public boolean esDestructible() { return destructible; }
    public boolean permitePaso() { return permitePaso; }
    public boolean permiteBalas() { return permiteBalas; }
    public int getVidaInicial() { return vidaInicial; }
}
