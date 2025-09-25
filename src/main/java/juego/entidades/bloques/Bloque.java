package juego.entidades.bloques;

import juego.entidades.Ente;
import juego.entidades.TipoEnte;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;

public class Bloque extends Ente {
    private final TipoBloque tipo;
    private int vida;

    public Bloque(TipoBloque tipo, Coordenada posicion, Dimensiones dimensiones) {
        super(posicion, dimensiones);
        this.tipo = tipo;
        this.vida = tipo.getVidaInicial();
    }

    public TipoBloque getTipoBloque() { return tipo; }
    public int getVida() { return vida; }
    public boolean permitePaso() { return tipo.permitePaso(); }
    public boolean balaimpacta() { return tipo.balaimpacta(); }
    public boolean esDestructible() { return tipo.esDestructible(); }

    public void recibirDanio(int cantidad) {
        if (tipo.esDestructible()) {
            vida -= cantidad;
            if (estaDestruido()) {
                setActivo(false);
            }
        }
    }

    @Override
    public void actualizar() {}

    @Override
    public boolean estaDestruido() { return tipo.esDestructible() && vida <= 0; }

    @Override
    public TipoEnte getTipo() { return TipoEnte.BLOQUE; }
}
