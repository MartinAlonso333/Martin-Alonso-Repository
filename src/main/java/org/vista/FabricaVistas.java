package org.vista;

import org.modelo.entidades.Ente;
import org.modelo.entidades.powerups.PowerUp;
import org.modelo.entidades.tanques.Bala;
import org.modelo.entidades.tanques.Tanque;
import org.modelo.entidades.bloques.Bloque;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class FabricaVistas {

    private static final Map<Class<? extends Ente>, Function<Ente, EnteVista>> registro = new HashMap<>();

    static {
        registro.put(Tanque.class, e -> new TanqueVista((Tanque) e, GestorSprites.getAnimacionesPara(e)));
        registro.put(Bloque.class,
                e -> new EnteVista(e, GestorSprites.getAnimacionesPara(e)));
        registro.put(PowerUp.class,
                e -> new EnteVista(e, GestorSprites.getAnimacionesPara(e)));
        registro.put(Bala.class,
                e -> new EnteVista(e, GestorSprites.getAnimacionesPara(e)));
    }

    public static EnteVista crearVista(Ente e) {
        Function<Ente, EnteVista> constructor = registro.get(e.getClass());
        if (constructor != null) return constructor.apply(e);

        return new EnteVista(e, GestorSprites.getAnimacionesPara(e));
    }
}
