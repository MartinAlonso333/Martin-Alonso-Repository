package org.vista.cargaDePartida;

import org.modelo.entidades.Ente;
import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.bloques.TipoBloque;
import org.modelo.entidades.powerups.PowerUp;
import org.modelo.entidades.powerups.TipoPowerUp;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.entidades.tanques.TipoTanque;
import org.modelo.entidades.tanques.Bala;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Direccion;
import org.modelo.utilidades.Dimensiones;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RegistroEntidades {

    private final Map<String, Function<Element, Ente>> registro = new HashMap<>();
    private final int jugadoresMaximos;

    public RegistroEntidades(int jugadoresMaximos) {
        this.jugadoresMaximos = jugadoresMaximos;
        inicializarRegistro();
    }

    private void inicializarRegistro() {
        // BLOQUES
        registrarBloque("steelBlock", TipoBloque.ACERO);
        registrarBloque("brickBlock", TipoBloque.LADRILLO);
        registrarBloque("baseBlock", TipoBloque.BASE);
        registrarBloque("forestBlock", TipoBloque.BOSQUE);
        registrarBloque("waterBlock", TipoBloque.AGUA);

        // ENEMIGOS
        registrarTanqueEnemigo("regularEnemy", TipoTanque.BASICO);
        registrarTanqueEnemigo("enemyFast", TipoTanque.RAPIDO);
        registrarTanqueEnemigo("enemyStrong", TipoTanque.POTENTE);
        registrarTanqueEnemigo("enemyArmored", TipoTanque.BLINDADO);

        // POWERUPS
        registrarPowerUp("helmetPowerUp", TipoPowerUp.CASCO);
        registrarPowerUp("starPowerUp", TipoPowerUp.ESTRELLA);
        registrarPowerUp("grenadePowerUp", TipoPowerUp.GRANADA);

        // BALA
        registro.put("bullet", elem -> new Bala(
                Direccion.ABAJO,
                1,
                new Coordenada(
                        Double.parseDouble(elem.getAttribute("x")),
                        Double.parseDouble(elem.getAttribute("y"))
                ),
                new Dimensiones(6, 6),
                3,
                null // el dueño se asigna al disparar
        ));
    }

    private void registrarBloque(String clave, TipoBloque tipo) {
        registro.put(clave, elem -> new Bloque(
                tipo,
                new Coordenada(
                        Double.parseDouble(elem.getAttribute("x")),
                        Double.parseDouble(elem.getAttribute("y"))
                ),
                new Dimensiones(20, 20) // después GestorSprites da la real
        ));
    }

    private void registrarTanqueEnemigo(String clave, TipoTanque tipo) {
        registro.put(clave, elem -> new TanqueEnemigo(
                new Coordenada(
                        Double.parseDouble(elem.getAttribute("x")),
                        Double.parseDouble(elem.getAttribute("y"))
                ),
                new Dimensiones(20, 20),
                Direccion.ABAJO,
                tipo
        ));
    }

    private void registrarPowerUp(String clave, TipoPowerUp tipo) {
        registro.put(clave, elem -> new PowerUp(
                new Coordenada(
                        Double.parseDouble(elem.getAttribute("x")),
                        Double.parseDouble(elem.getAttribute("y"))
                )
                , new Dimensiones(20, 20),
                tipo
        ));
    }

    public Ente crearEnte(Element elem) {
        String tipo = elem.getAttribute("type");

        // --- JUGADORES ---
        if (tipo.startsWith("player")) {
            int idJugador = Integer.parseInt(tipo.substring(6)); // player1 → 1
            if (idJugador > jugadoresMaximos) return null;

            return new TanqueJugador(
                    new Coordenada(
                            Double.parseDouble(elem.getAttribute("x")),
                            Double.parseDouble(elem.getAttribute("y"))
                    ),
                    new Dimensiones(20, 20),
                    Direccion.ARRIBA,
                    idJugador
            );
        }

        // --- RESTO (bloques, enemigos, powerups, bala) ---
        Function<Element, Ente> constructor = registro.get(tipo);
        if (constructor != null) {
            return constructor.apply(elem);
        }

        throw new IllegalArgumentException("Tipo de ente desconocido en XML: " + tipo);
    }
}
