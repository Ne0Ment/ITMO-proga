package lab2.src.attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class DarkPulse extends SpecialMove{
    public DarkPulse() {
        super(Type.DARK, 80.0, 100.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random()>0.3) {
            Effect.flinch(p);
        }
        super.applyOppEffects(p);
    }

    @Override
    public String describe() {
        return "uses Dark Pulse";
    }
}
