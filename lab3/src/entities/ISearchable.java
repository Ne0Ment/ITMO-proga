package entities;

import enums.time;
import things.Thing;

public interface ISearchable {
    public String search(Thing[] items, time searchTime);
}
