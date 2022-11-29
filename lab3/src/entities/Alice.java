package entities;

import enums.time;
import things.Thing;

import java.util.stream.Stream;

public class Alice extends Person implements IThinkable, ISearchable, ICriable{
    private String character;
    private String pronoun;

    public Alice(String name, String pronoun, String character) {
        super();
        this.setName(name);
        this.setPronoun(pronoun);
        this.setCharacter(character);
    }

    public String getCharacter(){
        return this.character;
    }

    public void setCharacter(String newCharacter){
        this.character = newCharacter;
    }
    public String getPronoun() { return this.pronoun; }

    public void setPronoun(String newPronoun) {
        this.pronoun = newPronoun;
    }

    public String think(String thoughtString) {
        return this.think(thoughtString, time.DEFAULT);
    }

    public String asPronounCharacter (String text) {
        return " как " + this.getPronoun() + " " + this.getCharacter() + ", " + text;
    }

    public String think(String thoughtString, time thoughtTime) {
        return this.getName() + " " + thoughtTime.getDescription() + " сообразила, что " +  thoughtString + " и,";
    }

    public String search(Thing[] items, time searchTime) {
        StringBuilder searchString = new StringBuilder()
                .append(this.getName() + " " + searchTime.getDescription() + " принялась за поиски");
        if (items.length==0) {
            searchString.append('.');
        } else {
            searchString.append(' ').append(String.join(" и ", Stream.of(items).map(e -> e.getName()).toArray(String[]::new)));
            ;
        }
        return searchString.toString();
    }

    public String cry(){
        return this.getName() + " купалась в слезах.";
    }
}
