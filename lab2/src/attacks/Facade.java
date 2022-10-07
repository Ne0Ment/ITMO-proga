package lab2.src.attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.Type;

public class Facade extends PhysicalMove {
    public Facade() {
        super(Type.NORMAL, 70.0, 100.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (p.getCondition()==Status.BURN || p.getCondition()==Status.POISON || p.getCondition()==Status.PARALYZE) {
            this.power = 140.0;
        } else {
            this.power = 70.0;
        }
        super.applyOppEffects(p);
    }

    @Override
    public String describe() {
        return "uses Facade";
    }
}
