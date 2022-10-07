package lab2.src.pokemons;

import lab2.src.attacks.Liquidation;
import ru.ifmo.se.pokemon.Pokemon;

public class Golisopod extends Wimpod {
    public Golisopod(String name, int lvl) {
        super(name, lvl);
        this.setStats(75.0, 125.0, 140.0, 60.0, 90.0, 40.0);
        this.addMove(new Liquidation());
    }
}
