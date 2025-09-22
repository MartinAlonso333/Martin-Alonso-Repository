package juego.gestores;

import juego.entidades.Ente;
import juego.entidades.powerups.PowerUp;
import juego.entidades.tanques.*;


import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ColisionHandler {

    private final Map<EntesInvolucrados, BiConsumer<Ente, Ente>> reglas = new HashMap<>();
    private final GestorPowerUp gestorPowerUp = new GestorPowerUp();

    public ColisionHandler() { registrarReglas(); }

    private void registrarReglas() {
        registrarRegla(Bala.class, Tanque.class, (a, b) -> colisionBalaConTanque((Bala) a, (Tanque) b));
        registrarRegla(TanqueJugador.class, PowerUp.class, (a, b) -> colisionTanqueConPowerUp((TanqueJugador) a, (PowerUp) b));
        registrarRegla(Bala.class, Bala.class, (a, b) -> colisionBalaConBala((Bala) a, (Bala) b));
    }

    public void registrarRegla(Class<? extends Ente> c1, Class<? extends Ente> c2,
                               BiConsumer<Ente, Ente> accion) {
        reglas.put(new EntesInvolucrados(c1, c2), accion);
    }

    public void manejarColision(Ente a, Ente b) {
        BiConsumer<Ente, Ente> accion = reglas.get(new EntesInvolucrados(a.getClass(), b.getClass()));
        if (accion != null) accion.accept(a, b);
        else {
            accion = reglas.get(new EntesInvolucrados(b.getClass(), a.getClass()));
            if (accion != null) accion.accept(b, a);
        }
    }

    // ------------------ FUNCIONES CONCRETAS ------------------
    private void colisionBalaConTanque(Bala bala, Tanque tanque) {
        tanque.recibirDanio(bala.getDanio());
        bala.setActivo(false);
    }

    private void colisionTanqueConPowerUp(TanqueJugador tanque, PowerUp powerUp) {
        if (!powerUp.estaActivo()) return;

        powerUp.getTipoPowerUp().aplicar(tanque); // aplica el efecto
    }


    private void colisionBalaConBala(Bala a, Bala b) {
        a.setActivo(false);
        b.setActivo(false);
    }

    // ------------------ CLASE AUXILIAR ------------------
        private record EntesInvolucrados(Class<? extends Ente> c1, Class<? extends Ente> c2) {

        @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || o.getClass() != EntesInvolucrados.class) return false;
                EntesInvolucrados e = (EntesInvolucrados) o;
                return c1.equals(e.c1) && c2.equals(e.c2);
            }

    }
}
