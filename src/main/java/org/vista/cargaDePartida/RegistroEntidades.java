package org.vista.cargaDePartida;

import org.modelo.entidades.*;
import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.bloques.TipoBloque;
import org.modelo.entidades.tanques.*;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class RegistroEntidades {
    private static final int ANCHO = 20;
    private static final int ALTO = 20;

    private static final Map<TipoEnte, BiFunction<Coordenada, Direccion, Ente>> registro = new HashMap<>();
    private static final Map<String, TipoEnte> tipoStrToEnte = new HashMap<>();
    private static final Map<String, TipoTanqueEnemigo> tipoStrToTanque = new HashMap<>();
    private static final Map<String, TipoBloque> tipoStrToBloque = new HashMap<>();

    static {
        // --- Map de string a TipoEnte (jugadores y generales) ---
        tipoStrToEnte.put("player1", TipoEnte.JUGADOR);
        tipoStrToEnte.put("player2", TipoEnte.JUGADOR);
        tipoStrToEnte.put("bullet", TipoEnte.BALA);

        // FIX: Enemigos - Agrega TODOS los tipos posibles de tu XML
        tipoStrToEnte.put("basicEnemy", TipoEnte.ENEMIGO);
        tipoStrToEnte.put("fastEnemy", TipoEnte.ENEMIGO);
        tipoStrToEnte.put("heavyEnemy", TipoEnte.ENEMIGO);
        tipoStrToEnte.put("powerfulEnemy", TipoEnte.ENEMIGO);
        tipoStrToEnte.put("regularEnemy", TipoEnte.ENEMIGO);  // FIX NUEVO: Para tu XML

        // FIX: Bloques (para no fallar en tipoDesdeString)
        tipoStrToEnte.put("brickBlock", TipoEnte.BLOQUE);
        tipoStrToEnte.put("steelBlock", TipoEnte.BLOQUE);
        tipoStrToEnte.put("waterBlock", TipoEnte.BLOQUE);
        tipoStrToEnte.put("forestBlock", TipoEnte.BLOQUE);
        tipoStrToEnte.put("baseBlock", TipoEnte.BLOQUE);
        tipoStrToEnte.put("whiteBlock", TipoEnte.BLOQUE);  // Si usas "blanco" o similar

        // --- Map de string a TipoBloque ---
        tipoStrToBloque.put("brickBlock", TipoBloque.LADRILLO);
        tipoStrToBloque.put("steelBlock", TipoBloque.ACERO);
        tipoStrToBloque.put("waterBlock", TipoBloque.AGUA);
        tipoStrToBloque.put("forestBlock", TipoBloque.BOSQUE);
        tipoStrToBloque.put("baseBlock", TipoBloque.BASE);
        tipoStrToBloque.put("whiteBlock", TipoBloque.LADRILLO);  // Ejemplo default

        // --- Map de string a TipoTanqueEnemigo (incluye regularEnemy) ---
        tipoStrToTanque.put("basicEnemy", TipoTanqueEnemigo.BASICO);
        tipoStrToTanque.put("fastEnemy", TipoTanqueEnemigo.RAPIDO);
        tipoStrToTanque.put("heavyEnemy", TipoTanqueEnemigo.BLINDADO);
        tipoStrToTanque.put("powerfulEnemy", TipoTanqueEnemigo.POTENTE);
        tipoStrToTanque.put("regularEnemy", TipoTanqueEnemigo.BASICO);  // FIX NUEVO: Asume BASICO; cambia si tienes REGULAR

        // --- Registro de constructores para entidades que no son jugadores ---
        registro.put(TipoEnte.BLOQUE, (pos, dir) ->
                new Bloque(TipoBloque.LADRILLO, pos, new Dimensiones(ANCHO, ALTO))
        );

        registro.put(TipoEnte.ENEMIGO, (pos, dir) ->
                new TanqueEnemigo(pos, new Dimensiones(ANCHO, ALTO), dir, TipoTanqueEnemigo.BASICO)
        );

        // Nota: No registrar constructor para TipoEnte.JUGADOR aquí porque necesitan ID
    }

    /**
     * Crea un jugador con ID explícito.
     * @param idJugador Identificador único del jugador (1, 2, ...)
     * @param pos Posición inicial
     * @param dir Dirección inicial
     * @return TanqueJugador creado
     */
    public static TanqueJugador crearJugador(int idJugador, Coordenada pos, Direccion dir) {
        return new TanqueJugador(pos, new Dimensiones(ANCHO, ALTO), dir, 3, 1, 1, 2, dir,idJugador);
    }

    /**
     * Crea una entidad genérica (enemigos, bloques, balas, etc).
     * No se debe usar para crear jugadores, para eso usar crearJugador().
     * @param tipoStr String que representa el tipo de entidad
     * @param pos Posición inicial
     * @param dir Dirección inicial
     * @return Entidad creada
     */
    public static Ente crearEntidad(String tipoStr, Coordenada pos, Direccion dir) {
        TipoEnte tipo = tipoDesdeString(tipoStr);

        if (tipo == TipoEnte.JUGADOR) {
            throw new IllegalArgumentException("No crear jugador sin id desde crearEntidad(String). Usa crearJugador(id, pos, dir).");
        } else if (tipo == TipoEnte.ENEMIGO) {
            TipoTanqueEnemigo tipoTanque = tipoStrToTanque.getOrDefault(tipoStr, TipoTanqueEnemigo.BASICO);
            return new TanqueEnemigo(pos, new Dimensiones(ANCHO, ALTO), dir, tipoTanque);
        } else if (tipo == TipoEnte.BLOQUE) {
            TipoBloque tipoBloque = tipoStrToBloque.getOrDefault(tipoStr, TipoBloque.LADRILLO);
            return new Bloque(tipoBloque, pos, new Dimensiones(ANCHO, ALTO));
        } else {
            throw new IllegalArgumentException("Tipo de entidad no soportado para crearEntidad: " + tipo);
        }
    }

    /**
     * Convierte string a TipoEnte.
     * @param str String tipo
     * @return TipoEnte correspondiente
     */
    public static TipoEnte tipoDesdeString(String str) {
        TipoEnte tipo = tipoStrToEnte.get(str);
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo desconocido: " + str + ". Agrega al mapa tipoStrToEnte.");
        }
        return tipo;
    }
}