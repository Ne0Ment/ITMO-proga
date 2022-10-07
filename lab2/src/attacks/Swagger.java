package lab2.src.attacks;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Swagger extends StatusMove{
    public Swagger() {
        super(Type.NORMAL, 0.0, 100.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.ATTACK, -2);
        Effect.confuse(p);
        super.applyOppEffects(p);
    }

    @Override
    public String describe() {
        return "uses Swagger"; 
    }
}
