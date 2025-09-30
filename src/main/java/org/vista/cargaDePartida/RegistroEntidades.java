package org.vista.cargaDePartida;

import org.modelo.entidades.Ente;
import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.bloques.TipoBloque;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.entidades.tanques.TipoTanque;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.vista.GestorSprites;
import org.modelo.entidades.TipoEnte;
public class RegistroEntidades {

    private final Map<String, Function<Element, Ente>> registro = new HashMap<>();
    private final int jugadoresMaximos;

    public RegistroEntidades(int jugadoresMaximos) {
        this.jugadoresMaximos = jugadoresMaximos;
        inicializarRegistro();
    }

    private void inicializarRegistro() {
        // BLOQUES
        registrarBloque("steelBlock");
        registrarBloque("brickBlock");
        registrarBloque("baseBlock");
        registrarBloque("forestBlock");
        registrarBloque("waterBlock");

        // ENEMIGOS
        registrarTanqueEnemigo("regularEnemy");
        registrarTanqueEnemigo("enemyFast");
        registrarTanqueEnemigo("enemyStrong");
        registrarTanqueEnemigo("enemyArmored");
    }

    private void registrarBloque(String clave) {
        registro.put(clave, elem -> {
            Dimensiones dim = GestorSprites.getDimensionesPara(clave);
            return new Bloque(
                    TipoBloque.valueOf(clave.toUpperCase()), // o un mapeo si no coincide
                    new Coordenada(
                            Integer.parseInt(elem.getAttribute("x")),
                            Integer.parseInt(elem.getAttribute("y"))
                    ),
                    dim
            );
        });
    }

    private void registrarTanqueEnemigo(String clave) {
        registro.put(clave, elem -> {
            Dimensiones dim = GestorSprites.getDimensionesPara(clave);
            TipoTanque tipo = switch (clave) {
                case "regularEnemy" -> TipoTanque.BASICO;
                case "enemyFast" -> TipoTanque.RAPIDO;
                case "enemyStrong" -> TipoTanque.POTENTE;
                case "enemyArmored" -> TipoTanque.BLINDADO;
                default -> throw new IllegalArgumentException("Enemigo no reconocido: " + clave);
            };
            return new TanqueEnemigo(
                    new Coordenada(
                            Integer.parseInt(elem.getAttribute("x")),
                            Integer.parseInt(elem.getAttribute("y"))
                    ),
                    dim,
                    Direccion.ABAJO,
                    tipo
            );
        });
    }

    public Ente crearEnte(Element elem) {
        String tipo = elem.getAttribute("type");

        // JUGADORES
        if (tipo.startsWith("player")) {
            int idJugador = Integer.parseInt(tipo.substring(6)); // player1 → 1
            if (idJugador > jugadoresMaximos) return null;

            Dimensiones dim = GestorSprites.getDimensionesPara(tipo);

            return new TanqueJugador(
                    new Coordenada(
                            Integer.parseInt(elem.getAttribute("x")),
                            Integer.parseInt(elem.getAttribute("y"))
                    ),
                    dim,
                    Direccion.ARRIBA,
                    idJugador
            );
        }

        // BLOQUES / ENEMIGOS
        Function<Element, Ente> constructor = registro.get(tipo);
        if (constructor != null) return constructor.apply(elem);

        throw new IllegalArgumentException("Tipo de ente desconocido: " + tipo);
    }
}
