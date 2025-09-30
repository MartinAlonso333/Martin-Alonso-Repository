package org.vista;

import org.modelo.entidades.TipoEnte;

public record ClaveSprite(TipoEnte tipo, Enum<?> subtipo) {
    @Override
    public String toString() {
        return "ClaveSprite{tipo=" + tipo + ", subtipo=" + subtipo + "}";
    }
}
