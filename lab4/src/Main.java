import entities.Alice;
import entities.Rabbit;
import enums.time;
import exceptions.NothingToSearchException;
import exceptions.StorytellerFellAsleepException;
import places.Dungeon;
import places.Place;
import story.Storyteller;
import things.*;

public class Main {
    public static void main(String[] args) {

        //first sentence objects
        Rabbit rabbit = new Rabbit("он", "Белый Кролик");

        //first sentence objects
        Alice alice = new Alice("Алиса", "девочка", "очень добрая");
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
        try {
            String outStr = rabbit.existAgain() + rabbit.trotWithMutter() + //1-3 sentence

                            alice.think(rabbit.search(new Thing[]{fan, gloves}, time.DEFAULT), time.STRAIGHTAWAY) +
                            alice.asPronounCharacter(alice.search(new Thing[]{}, time.IMMEDIATELY)) +

                            //second sentence
                            storyteller.notSearchable(new Thing[]{fan, gloves}) +
                            everything.changedAfter(around, time.AFTER, alice.cry()) +

                            //third sentence
                            dungeon.disappearWithFullDescription() +

                            //last sentence
                            rabbit.noticeAndSay("Алису", time.SOON) +
                            rabbit.askTask("Мэри-Энн") +
                            rabbit.giveCommand(new Thing[]{fan, gloves});
            System.out.println(outStr);
        } catch (NothingToSearchException err){
            System.out.println("Не было вещей для поиска, следовательно не было конфликта и возникающей истории.");
        } catch (StorytellerFellAsleepException err) {
            System.out.println("Рассказчик случайно уснул, истории не будет.");
        }


    }
}