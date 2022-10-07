package lab2.src.attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Rest extends StatusMove {
    public Rest() {
        super(Type.PSYCHIC, 0.0, 0.0);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.addEffect(new Effect().condition(Status.SLEEP).turns(2));
        p.restore();
        super.applySelfEffects(p);
    }

    @Override
    public String describe() {
        return "uses Rest";
    }
}
