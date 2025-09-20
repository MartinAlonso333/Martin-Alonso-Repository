package juego.entidades.tanques;


import juego.entidades.TipoEnte;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

public abstract class TanqueEnemigo extends Tanque {
    private long tiempoConducta;
    private long inicioConducta;
    private Direccion direccion;
    private Coordenada ultimaPosicionChequear;
    private long ultimoTiempoQuieto;

    public TanqueEnemigo(Coordenada posicion, Dimensiones dimensiones, int vida, int danio,
                         String spriteNormal, String spriteDestruido, int velocidad) {
        super(posicion, dimensiones, vida, danio, spriteNormal, spriteDestruido, velocidad);
        this.tiempoConducta = sortearTiempoConducta();
        this.inicioConducta = System.currentTimeMillis();
        this.direccion = sortearDireccion();
        this.ultimaPosicionChequear = new Coordenada(posicion.getX(), posicion.getY());
        this.ultimoTiempoQuieto = System.currentTimeMillis();
    }

    @Override
    public void disparar() {
        if (puedeDisparar(1500)) { // Ejemplo: 1.5s entre disparos
            System.out.println("Tanque enemigo disparó desde " + getPosicion());
            registrarDisparo();
        }
    }

    @Override
    public void actualizar(double deltaTime) {
        long ahora = System.currentTimeMillis();

        // Verificar si se venció el tiempo de conducta
        if (ahora - inicioConducta >= tiempoConducta) {
            direccion = sortearDireccion();
            tiempoConducta = sortearTiempoConducta();
            inicioConducta = ahora;
        }

        // Verificar si quedó quieto más de 2s
        if (getPosicion().equals(ultimaPosicionChequear)) {
            if (ahora - ultimoTiempoQuieto >= 2000) {
                direccion = sortearDireccion();
                ultimoTiempoQuieto = ahora;
            }
        } else {
            ultimaPosicionChequear = new Coordenada(getPosicion().getX(), getPosicion().getY());
            ultimoTiempoQuieto = ahora;
        }

        // Mover tanque en la dirección actual
        mover(direccion);

        // Intentar disparar
        disparar();
    }

    @Override
    public TipoEnte getTipo() {
        return null;
    }

    private long sortearTiempoConducta() {
        return (1 + (int)(Math.random() * 5)) * 1000L;
    }

    private Direccion sortearDireccion() {
        Direccion[] direcciones = Direccion.values();
        return direcciones[(int)(Math.random() * direcciones.length)];
    }
}

