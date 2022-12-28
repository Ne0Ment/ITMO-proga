package entities;

import enums.time;
import things.Thing;

import java.util.stream.Stream;

public class Him extends Person implements ISearchable{
    public Him(String name) {
        super();
        this.setName(name);
    }

    public String search(Thing[] items, time searchTime) {
        StringBuilder searchString = new StringBuilder()
                .append(this.getName() + " ищет ")
                .append(String.join(" и ", Stream.of(items).map(e -> e.getName()).toArray(String[]::new)));
        return searchString.toString();
    }
}