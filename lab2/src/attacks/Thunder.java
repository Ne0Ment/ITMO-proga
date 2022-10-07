package lab2.src.attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class Thunder extends SpecialMove {
    public Thunder() {
        super(Type.ELECTRIC, 110.0, 70.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if ((new Effect().chance(0.3)).success()) {
            Effect.paralyze(p);
        }
        super.applyOppEffects(p);
    }

    @Override
    public String describe() {
        return "uses Thunder";
    }
}