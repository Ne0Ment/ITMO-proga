package lab2.src.attacks;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class PoisonTail extends PhysicalMove {
    public PoisonTail() {
        super(Type.POISON, 50.0, 100.0);
    }

    @Override
    public String describe() {
        return "uses Poison Tail"; 
    }
}
