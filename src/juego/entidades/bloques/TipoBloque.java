package juego.entidades.bloques;

public enum TipoBloque {
    LADRILLO(true, true, true, 3),
    BASE(true, true, true, 1),
    BOSQUE(false, true, true, 0),
    AGUA(false, false, false, 0),
    METAL(false, false, true, 0);

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
