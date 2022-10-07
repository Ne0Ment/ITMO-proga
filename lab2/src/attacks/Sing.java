package lab2.src.attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Sing extends StatusMove {
    public Sing() {
        super(Type.NORMAL, 0.0, 55.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect.sleep(p);
    }

    @Override
    public String describe() {
        return "uses Sing";
    }
}
