package juego.vista;

import juego.entidades.Ente;
import juego.utilidades.Direccion;

import java.io.InputStream;
import java.util.*;

public class GestorSprites {

    /**
     * Devuelve rutas de sprites por dirección para un Ente usando convención de carpetas.
     * Cada clase de Ente debe tener su carpeta en /imagenes/<clase>/
     * Ej: Tanque -> /imagenes/tanque/tanque_arriba_1.png, etc.
     */
    public static Map<Direccion, List<String>> obtenerRutas(Ente e) {
        String clase = e.getClass().getSimpleName().toLowerCase(); // "tanque", "bloque", "powerup", etc.

        Map<Direccion, List<String>> rutas = new HashMap<>();
        for (Direccion dir : Direccion.values()) {
            List<String> frames = new ArrayList<>();
            int i = 1;

            while (true) {
                String path = String.format("/imagenes/%s/%s_%s_%d.png",
                        clase, clase, dir.name().toLowerCase(), i);
                InputStream stream = GestorSprites.class.getResourceAsStream(path);
                if (stream == null) break; // no hay más frames
                frames.add(path);
                i++;
            }

            if (!frames.isEmpty()) {
                rutas.put(dir, frames);
            }
        }

        // Si no hay frames por dirección, usa un sprite genérico
        if (rutas.isEmpty()) {
            String path = String.format("/imagenes/%s/%s.png", clase, clase);
            rutas.put(Direccion.ARRIBA, List.of(path));
        }

        return rutas;
    }
}
