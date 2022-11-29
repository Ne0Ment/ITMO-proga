package lab2.src;
import lab2.src.pokemons.Clefable;
import lab2.src.pokemons.Clefairy;
import lab2.src.pokemons.Cleffa;
import lab2.src.pokemons.Golisopod;
import lab2.src.pokemons.Seviper;
import lab2.src.pokemons.Wimpod;
import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Pokemon p1 = new Seviper("ArLet", 1);
        Pokemon p2 = new Wimpod("ExcaLet", 1);
        Pokemon p3 = new Golisopod("NeoLet", 1);

        Pokemon p4 = new Cleffa("Gepeusea", 1);
        Pokemon p5 = new Clefairy("Fergie", 1);
        Pokemon p6 = new Clefable("Slonser", 1);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}