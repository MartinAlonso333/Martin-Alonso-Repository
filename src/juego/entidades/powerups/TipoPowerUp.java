package juego.entidades;

import juego.entidades.Tanque;

public enum TipoPowerUp {
    CASCO {
        @Override
        public void aplicar(Tanque t) {
            t.activarInvencibilidad();
        }

        @Override
        public void finalizar(Tanque t) {
            t.desactivarInvencibilidad();
        }
    },
    ESTRELLA {
        @Override
        public void aplicar(Tanque t) {
            t.recibirEstrella();
        }
    },
    GRANADA {
        @Override
        public void aplicar(Tanque t) {
            System.out.println("¡Todos los enemigos destruidos!");
        }
    };

    public abstract void aplicar(Tanque t);

    // Por defecto no hace nada; se usa solo para efectos temporales
    public void finalizar(Tanque t) {}
}
