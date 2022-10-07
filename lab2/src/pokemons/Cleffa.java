package lab2.src.pokemons;

import lab2.src.attacks.Flamethrower;
import lab2.src.attacks.Sing;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Cleffa extends Pokemon {
    public Cleffa(String name, int lvl) {
        super(name, lvl);
        this.setType(Type.FAIRY);
        this.setStats(50.0, 25.0, 28.0, 45.0, 55.0, 15.0);
        this.addMove(new Flamethrower());
        this.addMove(new Sing());
    }
}