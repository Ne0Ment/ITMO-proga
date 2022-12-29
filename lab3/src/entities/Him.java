package entities;

import enums.time;
import things.Thing;

import java.util.Objects;
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

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) return true;
        if (otherObj == null) return false;
        if (!this.getClass().equals(otherObj.getClass())) return false;
        Him other = (Him) otherObj;
        return Objects.equals(this.getName(), other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name=" + this.getName() + "]";
    }
}