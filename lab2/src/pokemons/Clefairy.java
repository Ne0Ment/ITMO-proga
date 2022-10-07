package lab2.src.pokemons;

import lab2.src.attacks.WakeupSlap;

public class Clefairy extends Cleffa{
    public Clefairy(String name, int lvl) {
        super(name, lvl);
        this.setStats(70.0, 45.0, 48.0, 60.0, 65.0, 35.0);
        this.addMove(new WakeupSlap());
    }
}
