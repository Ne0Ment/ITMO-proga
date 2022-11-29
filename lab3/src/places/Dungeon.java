package places;

import things.Thing;

import java.util.stream.Stream;

public class Dungeon extends Place implements IDisappearable{
    private String description;
    private Thing[] contents;
    public Dungeon(String name, String description, Thing[] contents) {
        super();
        this.setName(name);
        this.setDescription(description);
        this.setContents(contents);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public Thing[] getContents() {
        return this.contents;
    }

    public void setContents(Thing[] newContents) {
        this.contents = newContents;
    }

    public String descriptionWithContents() {
        StringBuilder str = new StringBuilder().append(this.getDescription() + " " + this.getName() + ", где стояли ");
        str.append(String.join(" и ", Stream.of(contents).map(Thing::getName).toArray(String[]::new))).append(",");
        return str.toString();
    }

    public String disappearWithFullDescription(){
        return this.descriptionWithContents() + " исчезло без следа, словно его никогда и не было.";
    }
}
