package entities;

import enums.time;
import exceptions.NothingToSearchException;
import things.Thing;

import java.util.Objects;

public abstract class Person implements ISearchable {
    private String name;

    protected Person() { }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    abstract public String search(Thing[] items, time searchTime) throws NothingToSearchException;

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) return true;
        if (otherObj == null) return false;
        if (!this.getClass().equals(otherObj.getClass())) return false;
        Person other = (Person) otherObj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name=" + this.name + "]";
    }
}
