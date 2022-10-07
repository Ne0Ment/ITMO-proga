package lab2.src.attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class Liquidation extends PhysicalMove {
    public Liquidation() {
        super(Type.WATER, 85.0, 100.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().chance(0.2).stat(Stat.DEFENSE, -1));
    }

    @Override
    public String describe() {
        return "uses Liquidation";
    }
}
