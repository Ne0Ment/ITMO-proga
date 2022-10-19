package lab2.src.attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class Waterfall extends PhysicalMove{
    public Waterfall() {
        super(Type.WATER, 80.0, 100.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.HP, 80);
        if (Math.random()>0.2) {
            Effect.flinch(p);
        }
        super.applyOppEffects(p);
    }

    @Override
    public String describe() {
        return "uses Waterfall";
    }
}
