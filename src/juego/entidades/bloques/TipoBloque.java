package juego.entidades.bloques;

public enum TipoBloque {
    LADRILLO(true, true, true),
    BASE(true, true, true),
    BOSQUE(false, true, true),
    AGUA(false, false, false),
    METAL(false, false, true);

    private final boolean destructible;
    private final boolean permitePaso;
    private final boolean permiteBalas;

    TipoBloque(boolean destructible, boolean permitePaso, boolean permiteBalas) {
        this.destructible = destructible;
        this.permitePaso = permitePaso;
        this.permiteBalas = permiteBalas;
    }

    public boolean esDestructible() { return destructible; }
    public boolean permitePaso() { return permitePaso; }
    public boolean permiteBalas() { return permiteBalas; }
}
