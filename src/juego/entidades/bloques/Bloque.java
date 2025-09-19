package juego.entidades.bloques;

import juego.entidades.*;
import juego.utilidades.*:

public class Bloque extends Ente {

    private final TipoBloque tipo;
    private int vida;

    public Bloque(TipoBloque tipo, Coordenada posicion, Dimensiones dimensiones) {
        super(posicion, dimensiones);
        this.tipo = tipo;
        this.vida = tipo.getVidaInicial();
    }

    public TipoBloque getTipo() { return tipo; }
    public int getVida() { return vida; }

    @Override
    public boolean estaDestruido() {
        return vida <= 0 && tipo.esDestructible();
    }

    public void recibirDanio(int cantidad) {
        if (tipo.esDestructible()) {
            vida -= cantidad;
            notificarColision(); // informa al sistema que cambió su estado
        }
    }

    public boolean permitePaso() { return tipo.permitePaso(); }
    public boolean permiteBalas() { return tipo.permiteBalas(); }

    @Override
    public void actualizar(double deltaTime) {
        // Bloques estáticos normalmente no necesitan actualizar nada
    }

    @Override
    public TipoEnte getTipo() {
        return TipoEnte.BLOQUE;
    }
}
