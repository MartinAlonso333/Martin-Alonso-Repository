package org.vista;

import org.modelo.utilidades.Direccion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpriteConfig {

    private static final Map<String, String> mapeoTiposXML = new HashMap<>();
    static {
        mapeoTiposXML.put("player1", "player1");
        mapeoTiposXML.put("player2", "player2");
        mapeoTiposXML.put("regularEnemy", "basico");
        mapeoTiposXML.put("fastEnemy", "rapido");
        mapeoTiposXML.put("heavyEnemy", "pesado");
        mapeoTiposXML.put("powerfulEnemy", "potente");
        mapeoTiposXML.put("brickBlock", "ladrillo");
        mapeoTiposXML.put("steelBlock", "acero");
        mapeoTiposXML.put("waterBlock", "agua");
        mapeoTiposXML.put("forestBlock", "bosque");
        mapeoTiposXML.put("baseBlock", "base");
        mapeoTiposXML.put("bullet", "disparo");
        // Agrega más si tienes power-ups en XML
    }

    public static Map<Direccion, List<String>> getConfig(String tipoStr) {
        String tipoInterno = mapeoTiposXML.getOrDefault(tipoStr, tipoStr);

        Map<Direccion, List<String>> config = new HashMap<>();

        if (tipoStr.startsWith("player")) {
            int playerId = tipoStr.equals("player1") ? 1 : 2;
            List<String> framesGen = obtenerSpritesTanqueJugador(playerId);
            for (Direccion dir : Direccion.values()) {
                config.put(dir, framesGen);
            }

        } else if (tipoStr.contains("Enemy")) {
            List<String> framesGen = obtenerSpritesTanqueEnemigo(tipoInterno);
            for (Direccion dir : Direccion.values()) {
                config.put(dir, framesGen);
            }

        } else if (tipoStr.contains("Block") || tipoStr.equals("baseBlock")) {
            String spritePath = obtenerSpriteBloque(tipoInterno);
            config.put(Direccion.ARRIBA, List.of(spritePath));

        } else if (tipoStr.equals("bullet")) {
            String spritePath = obtenerSpriteDisparo();
            for (Direccion dir : Direccion.values()) {
                config.put(dir, List.of(spritePath));
            }

        } else if (tipoStr.startsWith("powerUp")) {
            String tipoPower = tipoStr.replace("powerUp", "").toLowerCase();
            String spritePath = obtenerSpritePowerUp(tipoPower);
            config.put(Direccion.ARRIBA, List.of(spritePath));

        } else if (tipoStr.equals("escudo")) {
            String spritePath = obtenerSpriteEscudo();
            config.put(Direccion.ARRIBA, List.of(spritePath));

        } else {
            throw new IllegalArgumentException("Tipo desconocido en SpriteConfig: " + tipoStr);
        }

        return config;
    }

    public static List<String> obtenerSpritesTanqueJugador(int playerId) {
        if (playerId == 1) {
            return List.of("Player1Tank0_20x20.png", "Player1Tank1_20x20.png");
        } else {
            return List.of("Player2Tank0_20x20.png", "Player2Tank1_20x20.png");
        }
    }

    public static List<String> obtenerSpritesTanqueEnemigo(String tipo) {
        return switch (tipo) {
            case "basico" -> List.of("EnemyTankRegular0_20x20.png", "EnemyTankRegular1_20x20.png");
            case "rapido" -> List.of("EnemyTankFast0_20x20.png", "EnemyTankFast1_20x20.png");
            case "pesado" -> List.of("EnemyTankHeavy0_20x20.png", "EnemyTankHeavy1_20x20.png");
            case "potente" -> List.of("EnemyTankPowerful0_20x20.png", "EnemyTankPowerful1_20x20.png");
            default -> throw new IllegalArgumentException("Tipo enemigo desconocido: " + tipo);
        };
    }

    public static String obtenerSpriteBloque(String tipo) {
        return switch (tipo) {
            case "ladrillo" -> "BrickBlock20x20.png";
            case "acero" -> "SteelBlock20x20.png";
            case "blanco" -> "WhiteBlock20x20.png";
            case "agua" -> "Water20x20.png";
            case "bosque" -> "Forest20x20.png";
            case "base" -> "base20x20.png";
            case "tanque_destruido" -> "TankDestroyed20x20.png";
            default -> throw new IllegalArgumentException("Bloque desconocido: " + tipo);
        };
    }

    public static String obtenerSpriteDisparo() {
        return "Shot.png";
    }

    public static String obtenerSpritePowerUp(String tipo) {
        return switch (tipo) {
            case "granada" -> "PowerUp-Grenade20x20.png";
            case "casco" -> "PowerUp-Helmet20x20.png";
            case "estrella" -> "PowerUp-Star20x20.png";
            default -> throw new IllegalArgumentException("PowerUp desconocido: " + tipo);
        };
    }

    public static String obtenerSpriteEscudo() {
        return "InvulnerableRing20x20.png";
    }
}