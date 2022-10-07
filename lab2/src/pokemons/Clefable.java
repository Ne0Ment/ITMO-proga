package lab2.src.pokemons;

import lab2.src.attacks.Thunder;

public class Clefable extends Clefairy {
    public Clefable(String name, int lvl) {
        super(name, lvl);
        this.setStats(95.0, 70.0, 73.0, 95.0, 90.0, 60.0);
        this.addMove(new Thunder());
    }
}
