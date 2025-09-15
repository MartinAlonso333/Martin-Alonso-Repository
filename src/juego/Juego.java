import java.util.*;
import java.util.function.Consumer;
import juego.entidades.powerups.*;
import juego.entidades.tanques.Tanque;

public class Juego {

    private List<Tanque> tanques = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private final Map<TipoPowerUp, Consumer<Tanque>> efectos = new HashMap<>();

    public Juego() {
        agregarEfectos();
    }

    // Cuando un tanque agarra un powerup
    public void tanqueAgarraPowerUp(Tanque tanque, PowerUp powerUp) {
        if (!powerUp.activo()) return;
        Consumer<Tanque> efecto = efectos.get(powerUp.getTipo());
        if (efecto != null) {
            efecto.accept(tanque); // aplica el efecto
        }
        powerUp.desactivar();
    }

    private void agregarEfectos(){
        efectos.put(TipoPowerUp.GRANADA, juego -> {
            for (Tanque t : juego.tanques) {
                t.recibirDanio(9999); // daño gigante para “destruir”
            }
        });
        efectos.put(TipoPowerUp.CASCO, t -> t.aumentarVida50());
        efectos.put(TipoPowerUp.VELOCIDAD, t -> t.aumentarVelocidad());
        efectos.put(TipoPowerUp.ESCUDO, t -> t.activarEscudo());
    }
}
