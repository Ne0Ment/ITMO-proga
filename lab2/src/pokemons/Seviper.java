package lab2.src.pokemons;
import lab2.src.attacks.PoisonTail;
import lab2.src.attacks.Rest;
import lab2.src.attacks.Swagger;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Seviper extends Pokemon {
    public Seviper(String name, int lvl) {
        super(name, lvl);
        this.setType(Type.POISON);
        this.setStats(73.0, 100.0, 60.0, 100.0, 60.0, 65.0);
        this.addMove(new PoisonTail());
        this.addMove(new Swagger());
        this.addMove(new Rest());
    }
}