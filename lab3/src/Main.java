import entities.Alice;
import entities.Him;
import enums.time;
import places.Around;
import places.Dungeon;
import story.Storyteller;
import things.*;

public class Main {
    public static void main(String[] args) {
        //first sentence objects
        Alice alice = new Alice("Алиса", "девочка", "очень добрая");
        Him him = new Him("он");
        Fan fan = new Fan("веер", "веера");
        Gloves gloves = new Gloves("бальные перчатки", "перчаток");

        //second sentence objects
        Storyteller storyteller = new Storyteller();
        Everything everything = new Everything("все");
        Around around = new Around("вокруг");

        //third sentence objects
        Table table = new Table("стеклянный столик");
        Door door = new Door("заветная дверна");
        Dungeon dungeon = new Dungeon("подземелье", "Громадное", new Thing[]{ table, door});

        //construct output string
        String outStr = alice.think(him.search(new Thing[]{fan, gloves}, time.DEFAULT), time.STRAIGHTAWAY) + //first sentence
                alice.asPronounCharacter(alice.search(new Thing[]{}, time.IMMEDIATELY)) +

                //second sentence
                storyteller.notSearchable(new Thing[]{fan, gloves}) +
                everything.changedAfter(around, time.AFTER, alice.cry()) +

                //third sentence
                dungeon.disappearWithFullDescription();

        System.out.println(outStr);
    }
}