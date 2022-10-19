package lab2.src.attacks;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.Type;

public class WakeupSlap extends PhysicalMove {
    public WakeupSlap() {
        super(Type.FIGHTING, 70.0, 100.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (p.getCondition()==Status.SLEEP) {
            this.power = 140.0;
        } else {
            this.power = 70.0;
        }
    }

    @Override
    public String describe() {
        return "uses Wake-up Slap";
    }
}
