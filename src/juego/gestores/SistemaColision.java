package juego.gestores;

import juego.entidades.*;
import java.util.*;

public class SistemaColision implements ObserverColision {

    private final Map<List<TipoEnte>, BiConsumer<Ente, Ente>> reglas = new HashMap<>();
    private final GestorPowerUps gestorPowerUps;
    private final List<Ente> entes = new ArrayList<>();

    public SistemaColision(GestorPowerUps gestor) {
        this.gestorPowerUps = gestor;
        inicializarReglas();
    }

    public void registrarEnte(Ente e) {
        e.agregarObserverColision(this);
        entes.add(e);
    }

    @Override
    public void enteSeMovio(Ente e) {
        for (Ente otro : entes) {
            if (otro != e && otro.estaActivo()) {
                BiConsumer<Ente, Ente> accion = reglas.get(Arrays.asList(e.getTipo(), otro.getTipo()));
                if (accion != null && e.intersecta(otro)) {
                    accion.accept(e, otro);
                }
            }
        }
    }

    public void registrar(TipoEnte t1, TipoEnte t2, BiConsumer<Ente, Ente> accion) {
        reglas.put(Arrays.asList(t1, t2), accion);
        reglas.put(Arrays.asList(t2, t1), (a, b) -> accion.accept(b, a));
    }

    private void inicializarReglas() {
        // Tanque vs Bloque
        registrar(TipoEnte.TANQUE, TipoEnte.BLOQUE, (a, b) -> {
            Tanque t = (Tanque)a;
            Bloque bloque = (Bloque)b;

            if (!bloque.permitePaso()) {
                t.revertirMovimiento();
                System.out.println("Tanque chocó con bloque " + bloque.getTipo() + ": bloqueado!");
            }
        });

        // Tanque vs PowerUp
        registrar(TipoEnte.TANQUE, TipoEnte.POWERUP, (a, b) -> {
            Tanque t = (Tanque)a;
            PowerUpEnTablero pu = (PowerUpEnTablero)b;
            if (pu.estaActivo()) {
                gestorPowerUps.activarPowerUp(t, pu.getTipoPowerUp(), pu.getDuracion());
                pu.setActivo(false);
                System.out.println("Tanque tomó power-up: " + pu.getTipoPowerUp());
            }
        });

        // Tanque vs Tanque
        registrar(TipoEnte.TANQUE, TipoEnte.TANQUE, (a, b) -> {
            Tanque t1 = (Tanque)a;
            Tanque t2 = (Tanque)b;
            t1.revertirMovimiento();
            t2.revertirMovimiento();
            System.out.println("Dos tanques colisionaron: bloqueado!");
        });

        // Bala vs Tanque
        registrar(TipoEnte.BALA, TipoEnte.TANQUE, (a, b) -> {
            Bala bala = (Bala)a;
            Tanque t = (Tanque)b;
            t.recibirDanio(bala.getDanio());
            bala.setActivo(false);
            System.out.println("Bala golpeó un tanque!");
        });

        // Bala vs Bloque
        registrar(TipoEnte.BALA, TipoEnte.BLOQUE, (a, b) -> {
            Bala bala = (Bala)a;
            Bloque bloque = (Bloque)b;

            if (!bloque.permiteBalas()) {
                bala.setActivo(false);
                if (bloque.getTipo().esDestructible()) {
                    bloque.recibirDanio(bala.getDanio());
                    System.out.println("Bala dañó el bloque " + bloque.getTipo());
                } else {
                    System.out.println("Bala chocó con bloque indestructible " + bloque.getTipo());
                }
            }
        });
    }
}
