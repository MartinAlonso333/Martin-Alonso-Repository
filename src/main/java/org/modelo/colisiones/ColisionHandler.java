package org.modelo.colisiones;

import org.modelo.entidades.*;
import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.powerups.PowerUp;
import org.modelo.entidades.tanques.*;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.GestorPowerUp;
import org.modelo.eventos.TipoEvento;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ColisionHandler {

    private final Map<EntesInvolucrados, BiConsumer<Ente, Ente>> reglas = new HashMap<>();
    private final GestorPowerUp gestorPowerUp;

    private static final int TIEMPOATURDIDO = 2000; // milisegundos

    public ColisionHandler(GestorPowerUp gestorPowerUp) {
        registrarReglas();
    this.gestorPowerUp = gestorPowerUp;
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
        if(bala.getDuenio()==tanque){
            return;
        }

        if (bala.getDuenio().getTipoEnte() == TipoEnte.JUGADOR && tanque.getTipoEnte() == TipoEnte.JUGADOR) {
            tanque.aturdir(TIEMPOATURDIDO);
            bala.setActivo(false);
            return;
        }
        tanque.recibirDanio(bala.getDanio());
        bala.setActivo(false);

        if (tanque.getTipoTanque() == TipoTanque.BLINDADO) {
            EventoManager.getInstancia().notificar(TipoEvento.TANQUE_BLINDADO_IMPACTADO);
        }
    }

    private void colisionTanqueConPowerUp(TanqueJugador tanque, PowerUp powerUp) {
        if (!powerUp.estaActivo()) return;
        gestorPowerUp.activarPowerUp(tanque, powerUp);
        powerUp.setActivo(false);
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
        if (!bloque.balaimpacta()) return;

        bloque.recibirDanio(bala.getDanio());
        bala.setActivo(false);


        switch (bloque.getTipoBloque()) {
            case BASE -> EventoManager.getInstancia().notificar(TipoEvento.BASE_DESTRUIDA);
            case LADRILLO -> {
                if (bloque.estaDestruido()) EventoManager.getInstancia().notificar(TipoEvento.BLOQUE_DESTRUIDO);
            }
            case ACERO -> EventoManager.getInstancia().notificar(TipoEvento.BLOQUE_ACERO_IMPACTADO);
        }
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
