package org.modelo.colisiones;

import org.modelo.entidades.*;
import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.powerups.PowerUp;
import org.modelo.entidades.tanques.*;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.GestorPowerUp;
import org.modelo.eventos.TipoEvento;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ColisionHandler {

    private final Map<EntesInvolucrados, BiConsumer<Ente, Ente>> reglas = new HashMap<>();
    private final GestorPowerUp gestorPowerUp;
    private GestorEventos em;

    public ColisionHandler(GestorPowerUp gestorPowerUp, GestorEventos gestorEventos) {
        registrarReglas();
        this.gestorPowerUp = gestorPowerUp;
        this.em = gestorEventos;
    }

    private void registrarReglas() {
        registrarRegla(Bala.class, TanqueJugador.class, (a, b) -> colisionBalaConTanque((Bala) a, (TanqueJugador) b));
        registrarRegla(Bala.class, TanqueEnemigo.class, (a, b) -> colisionBalaConTanque((Bala) a, (TanqueEnemigo) b));
        registrarRegla(TanqueJugador.class, PowerUp.class, (a, b) -> colisionTanqueConPowerUp((TanqueJugador) a, (PowerUp) b));
        registrarRegla(Bala.class, Bala.class, (a, b) -> colisionBalaConBala((Bala) a, (Bala) b));
        registrarRegla(TanqueJugador.class, TanqueJugador.class, (a, b) -> colisionTanqueConTanque((Tanque) a, (Tanque) b));
        registrarRegla(TanqueJugador.class, TanqueEnemigo.class, (a, b) -> colisionTanqueConTanque((Tanque) a, (Tanque) b));
        registrarRegla(TanqueEnemigo.class, TanqueEnemigo.class, (a, b) -> colisionTanqueConTanque((Tanque) a, (Tanque) b));
        registrarRegla(TanqueJugador.class, Bloque.class, (a, b) -> colisionTanqueConBloque((Tanque) a, (Bloque) b));
        registrarRegla(TanqueEnemigo.class, Bloque.class, (a, b) -> colisionTanqueConBloque((Tanque) a, (Bloque) b));
        registrarRegla(Bala.class, Bloque.class, (a, b) -> colisionBalaConBloque((Bala) a, (Bloque) b));
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
        tanque.impactoConBala(bala, em);
    }

    private void colisionTanqueConPowerUp(TanqueJugador tanque, PowerUp powerUp) {
        if (!powerUp.estaActivo()) return;
        gestorPowerUp.activarPowerUp(tanque, powerUp, em);
        powerUp.setActivo(false);
        em.notificar(TipoEvento.POWERUP_RECOGIDO, powerUp.getTipoPowerUp());
    }

    private void colisionBalaConBala(Bala a, Bala b) {
        a.setActivo(false);
        b.setActivo(false);
    }

    private void colisionTanqueConTanque(Tanque a, Tanque b) {
        a.revertirMovimiento(a.getUltimaPosicion());
        b.revertirMovimiento(b.getUltimaPosicion());
    }

    private void colisionTanqueConBloque(Tanque tanque, Bloque bloque) {
        if (!bloque.permitePaso()) {
            tanque.revertirMovimiento(tanque.getUltimaPosicion());
        }
    }

    private void colisionBalaConBloque(Bala bala, Bloque bloque) {
        bloque.impactoConBala(bala, em);
    }

    // ------------------ CLASE AUXILIAR ------------------
    private record EntesInvolucrados(Class<? extends Ente> c1, Class<? extends Ente> c2) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EntesInvolucrados e)) return false;
            return c1.equals(e.c1) && c2.equals(e.c2);
        }

        @Override
        public int hashCode() {
            return c1.hashCode() * 31 + c2.hashCode();
        }
    }
}
