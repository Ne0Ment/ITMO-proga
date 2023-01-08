package entities;

import enums.time;
import exceptions.NothingToSearchException;
import things.Thing;

public interface ISearchable {
    public String search(Thing[] items, time searchTime) throws NothingToSearchException;
}
