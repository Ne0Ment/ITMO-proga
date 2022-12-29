package entities;

import enums.time;
import things.Thing;

import java.util.Objects;
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

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) return true;
        if (otherObj == null) return false;
        if (!this.getClass().equals(otherObj.getClass())) return false;
        Alice other = (Alice) otherObj;
        return Objects.equals(this.getName(), other.getName()) & Objects.equals(this.getPronoun(), other.getPronoun()) & Objects.equals(this.getCharacter(), other.getCharacter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName() + this.getCharacter() + this,getPronoun());
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name=" + this.getName() + ",pronoun=" + this.getPronoun() + ",character=" + this.getCharacter() + "]" ;
    }
}
