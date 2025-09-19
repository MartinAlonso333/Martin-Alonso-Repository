package juego.entidades.tanques;

public class Bala {
    private final String imagen;
    private final String direccion;
    private int danio;
    private boolean activo;

    public Bala(String imagen, String direccion, danio) {
        this.imagen = imagen;
        this.direccion = direccion;
        this.danio = danio;
    }

    public int getDanio() {return danio;}
    public boolean estaActivo() {return activo;}

    public void setDanio(int danio) {this.danio = danio;}
    public void setActivo(boolean activo) {this.activo = activo;}
}
