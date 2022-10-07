package lab2.src.pokemons;
import lab2.src.attacks.DoubleTeam;
import lab2.src.attacks.Facade;
import lab2.src.attacks.Waterfall;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Wimpod extends Pokemon {
    public Wimpod(String name, int lvl) {
        super(name, lvl);
        this.setType(Type.BUG, Type.WATER);
        this.setStats(25.0, 35.0, 40.0, 20.0, 30.0, 80.0);
        this.addMove(new Facade());
        this.addMove(new Waterfall());
        this.addMove(new DoubleTeam());
    }
}
