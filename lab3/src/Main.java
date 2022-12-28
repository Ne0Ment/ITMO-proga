import entities.Alice;
import entities.Him;
import enums.time;
import places.Dungeon;
import places.Place;
import story.Storyteller;
import things.*;

public class Main {
    public static void main(String[] args) {
        //first sentence objects
        Alice alice = new Alice("Алиса", "девочка", "очень добрая");
        Him him = new Him("он");
        Thing fan = new Thing("веер", "веера");
        Thing gloves = new Thing("бальные перчатки", "перчаток");

        //second sentence objects
        Storyteller storyteller = new Storyteller();
        Everything everything = new Everything("все", "всего");
        Place around = new Place("вокруг");

        //third sentence objects
        Thing table = new Thing("стеклянный столик", "стеклянного столика");
        Thing door = new Thing("заветная дверна", "заветной дверцы");
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